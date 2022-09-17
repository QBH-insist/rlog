package com.qbh.github.rlog.context;

import com.qbh.github.rlog.annotation.RLog;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author QBH
 * @date 2022/8/31
 */
public class RLogContextBuilder {
  private Method method;

  private Object[] args;

  private RLog rLog;

  public RLogContextBuilder method(Method method) {
    this.method = method;

    return this;
  }

  public RLogContextBuilder args(Object[] args) {
    this.args = args;

    return this;
  }

  public RLogContextBuilder log(RLog opRLog) {
    this.rLog = opRLog;

    return this;
  }

  public RLogContext build() {
    if (Objects.isNull(method) || Objects.isNull(args) || Objects.isNull(rLog)) {
      throw new IllegalArgumentException("method and args and rLog must not null");
    }

    return new RLogContext(method, args, rLog);
  }

  public static RLogContextBuilder builder() {
    return new RLogContextBuilder();
  }
}
