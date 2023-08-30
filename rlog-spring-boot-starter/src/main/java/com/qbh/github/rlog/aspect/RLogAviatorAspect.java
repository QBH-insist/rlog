package com.qbh.github.rlog.aspect;

import com.qbh.github.rlog.annotation.RLog;
import com.qbh.github.rlog.context.RLogContext;
import com.qbh.github.rlog.context.RLogContextBuilder;
import com.qbh.github.rlog.context.RLogVariableContext;
import com.qbh.github.rlog.processor.OperatorAccessor;
import com.qbh.github.rlog.processor.RLogEvaluator;
import com.qbh.github.rlog.processor.RpLogSink;
import com.qbh.github.rlog.type.JoinPointExecuteResult;
import com.qbh.github.rlog.type.LogRecord;
import java.util.Date;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author QBH
 * @date 2022/8/26
 */
@Aspect
public class RLogAviatorAspect {

  private static final Logger log = LoggerFactory.getLogger(RLogAviatorAspect.class);

  private final RpLogSink rpLogSink;

  private final RLogEvaluator rLogEvaluator;

  private final OperatorAccessor operatorAccessor;

  public RLogAviatorAspect(
      OperatorAccessor operatorAccessor, RpLogSink rpLogSink, RLogEvaluator rLogEvaluator) {
    this.rpLogSink = rpLogSink;
    this.rLogEvaluator = rLogEvaluator;
    this.operatorAccessor = operatorAccessor;
  }

  @Pointcut(value = "@annotation(com.qbh.github.rlog.annotation.RLog)")
  public void logPointCut() {}

  @Around(value = "logPointCut()")
  public Object rLogAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    log.debug("enter rLog aspect");

    Object returnValue = null;
    RLogContext context = null;

    JoinPointExecuteResult joinPointResult = new JoinPointExecuteResult();

    try {
      RLogVariableContext.pushEmptyEle();

      context = createContext(joinPoint);
    } catch (Throwable e) {
      log.error("rLog logic occurred error before method execute", e);
    }

    try {
      returnValue = joinPoint.proceed();
    } catch (Throwable e) {
      joinPointResult.executeFail(e);
    }

    try {
      fillRLogVariables(context);

      LogRecord logRecord = assembleLogRecord(context, returnValue, joinPoint, joinPointResult);

      rpLogSink.sink(logRecord);

    } catch (Throwable e) {
      log.error("rLog logic occurred error after method executed", e);
    } finally {
      RLogVariableContext.clear();
    }

    if (joinPointResult.getThrowable() != null) {
      throw joinPointResult.getThrowable();
    }

    log.debug("finished rLog aspect");

    return returnValue;
  }

  private LogRecord assembleLogRecord(
      RLogContext context,
      Object returnValue,
      ProceedingJoinPoint joinPoint,
      JoinPointExecuteResult joinPointResult) {
    LogRecord logRecord = new LogRecord();

    logRecord.setPayload(executeParse(context.getPayload(), context));
    logRecord.setUniqueId(executeParse(context.getUniqueId(), context));
    logRecord.setBizNo(context.getLog().bizNo());
    logRecord.setCreatedTime(new Date());
    logRecord.setReturnValue(returnValue);
    logRecord.setArgs(joinPoint.getArgs());
    logRecord.setExecuteSuccess(joinPointResult.getExecuteSuccess());
    logRecord.setErrorMsg(joinPointResult.getErrorMsg());
    logRecord.setThrowable(joinPointResult.getThrowable());

    logRecord.setOperator(operatorAccessor.getOperator());

    return logRecord;
  }

  private String executeParse(String expression, RLogContext context) {
    return StringUtils.isNoneBlank(expression)
        ? rLogEvaluator.execute(expression, context.getEnvironment(), String.class)
        : expression;
  }

  private void fillRLogVariables(RLogContext context) {
    Map<String, Object> variableTable = RLogVariableContext.peek();

    for (Map.Entry<String, Object> entry : variableTable.entrySet()) {
      context.putVariable(entry.getKey(), entry.getValue());
    }
  }

  private RLogContext createContext(ProceedingJoinPoint joinPoint) {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

    return RLogContextBuilder.builder()
        .method(methodSignature.getMethod())
        .args(joinPoint.getArgs())
        .log(methodSignature.getMethod().getAnnotation(RLog.class))
        .build()
        .parseArgs();
  }
}
