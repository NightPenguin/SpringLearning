package com.springboot.aoplog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于描述Bean 的成员变量
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    // 用于设置字段值
    String name() default "";

    // 用于设置长度
    int length() default 0;

    // 用于设置是否为空
    boolean isNotNull() default false;

    // 是否自增
    boolean isAutoIncrement() default false;

    // 用于设置默认值
    String defaultValue() default "";

    // 用于设置字段注释
    String comment() default "";

    // 用于设置是否为主键
    boolean isPrimaryKey() default false;
}
