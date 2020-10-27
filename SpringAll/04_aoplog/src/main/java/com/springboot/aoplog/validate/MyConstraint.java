package com.springboot.aoplog.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义规格校验
 * @Constraint: 用于定义校验的逻辑类
 * @Valid: 在控制层的参数添加该注解，用于表示该参数会用过逻辑校验类。
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyConstrantValidator.class)
public @interface MyConstraint {
    String message();

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};
}
