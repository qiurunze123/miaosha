package com.geekkq.globaltransaction.aspect;

import com.geekkq.globaltransaction.connection.LbConnection;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.sql.Connection;
@Aspect
@Component
public class LbDataSourceAspect {
    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Connection around(ProceedingJoinPoint point) throws Throwable{

        Connection coon=(Connection) point.proceed();
        return new LbConnection(coon);
    }

}
