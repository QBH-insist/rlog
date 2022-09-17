package com.qbh.github.rlog.processor;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.JavaMethodReflectionFunctionMissing;

import java.util.List;
import java.util.Map;

/**
 * rLog expression processor
 *
 * @author QBH
 * @date 2022/8/29
 */
public class RLogEvaluator {
  private static AviatorEvaluatorInstance evaluator;

  public RLogEvaluator() {
    evaluator = AviatorEvaluator.getInstance();

    // enable method lookup and invocation base on reflectance
    evaluator.setFunctionMissing(JavaMethodReflectionFunctionMissing.getInstance());
  }

  public <T> T execute(String expression, Map<String, Object> env, Class<T> t) {
    // cache compiled expression
    Expression compile = getEvaluator().compile(expression, true);

    return (T) compile.execute(env);
  }

  public void registerFunctions(List<ParseFunction> functions) {
    for (ParseFunction function : functions) {
      getEvaluator().addFunction(function);
    }
  }

  private AviatorEvaluatorInstance getEvaluator() {
    return evaluator;
  }
}
