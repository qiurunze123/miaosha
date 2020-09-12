package com.geekkq.globaltransaction.annotation;
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
public @interface GlobalTransaction {
   boolean isStart() default  false;


}
