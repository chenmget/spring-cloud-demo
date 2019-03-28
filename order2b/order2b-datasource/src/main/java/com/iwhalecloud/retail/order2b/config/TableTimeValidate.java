package com.iwhalecloud.retail.order2b.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableTimeValidate {

    String defTime() default TelDBDefValueConfig.TEL_DB_DEF_TIME;
}
