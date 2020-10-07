package com.geekq.limiter.annotation;

import org.springframework.core.annotation.AliasFor;

public @interface MyRedisLimiter {
    @AliasFor("limit")
    double value() default Double.MAX_VALUE;
    double limit() default Double.MAX_VALUE;

}
