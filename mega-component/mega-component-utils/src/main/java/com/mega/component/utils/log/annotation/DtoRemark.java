package com.mega.component.utils.log.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * <p>类说明：DTO对象的注解</p>
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface DtoRemark {

    String table() default "";

    String object() default "";

    String name() default "";
}
