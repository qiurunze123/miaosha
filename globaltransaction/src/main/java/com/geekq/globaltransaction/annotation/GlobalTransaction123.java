package com.geekq.globaltransaction.annotation;
/*
*
* 全局事務
*
* 事务依赖datasource，datasource依赖connection
 *
* */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalTransaction123 {
   boolean isStart() default  false;
   boolean isEnd() default  false;
}
