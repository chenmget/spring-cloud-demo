package com.iwhalecloud.retail.order2b.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumCheckValidate {

    Class enumClass();

    String message() default "类型不匹配";

}
