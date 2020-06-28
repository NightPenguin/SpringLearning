package com.springboot.aoplog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标注Bean
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
    /**
     * 用来设置表名
     * @return
     */
    String name();

    /**
     * 设置编码
     * @return
     */
    String charSet() default "utf8mb4";

    /**
     * 表注释
     * @return
     */
    String comment() default "";
}
