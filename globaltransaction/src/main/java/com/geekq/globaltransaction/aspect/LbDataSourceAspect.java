package com.geekq.globaltransaction.aspect;

import com.geekq.globaltransaction.connection.LbConnection;
import com.geekq.globaltransaction.transactional.GlobalTransactionManager;
import com.geekq.globaltransaction.transactional.LbTransaction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Connection;
@Aspect
@Component
public class LbDataSourceAspect {
    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Connection around(ProceedingJoinPoint point) throws Throwable{
        System.out.println("进入切面方法！！！");
        Connection coon=(Connection) point.proceed();
        LbTransaction transaction=GlobalTransactionManager.getCurrent();
        return new LbConnection(coon,transaction);
    }

}
