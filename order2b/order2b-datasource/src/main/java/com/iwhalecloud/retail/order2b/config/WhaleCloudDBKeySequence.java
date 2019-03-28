package com.iwhalecloud.retail.order2b.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义主键序列化
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface WhaleCloudDBKeySequence {

    /**
     * 主键
     */
    String keySeqName() default "";

    /**
     * 主键序列值
     */
    DBTableSequence keySeqValue() default DBTableSequence.NULL;

    /**
     * 分片字段
     */
    String fragmentField() default "";

}
