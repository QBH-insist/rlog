package com.qbh.github.rlog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author QBH
 * @date 2022/8/26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RLog {

    /** 业务标识 */
    String bizNo() default "";

    /** 业务对象唯一 ID */
    String uniqueId() default "";

    /** 日志载体 */
    String payload();
}
