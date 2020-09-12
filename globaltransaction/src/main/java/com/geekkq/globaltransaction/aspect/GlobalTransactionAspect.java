package com.geekkq.globaltransaction.aspect;

import com.geekkq.globaltransaction.annotation.GlobalTransaction;
import com.geekkq.globaltransaction.transactional.GlobalTransactionManager;
import com.geekkq.globaltransaction.transactional.LbTransaction;
import com.geekkq.globaltransaction.transactional.TransactionType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class GlobalTransactionAspect implements Ordered {


   @Around("@annotation(com.geekkq.globaltransaction.annotation.GlobalTransaction)")
    public void invoke(ProceedingJoinPoint joinPoint) throws Throwable {
       MethodSignature signature=(MethodSignature)joinPoint.getSignature();
       Method method=signature.getMethod();
       GlobalTransaction globalTransaction=method.getAnnotation(GlobalTransaction.class);
       String groupId=null;
       if(globalTransaction.isStart()){
           groupId=GlobalTransactionManager.createGroup();
       }

       LbTransaction transaction=GlobalTransactionManager.createLbTransaction(groupId);
       try{
           joinPoint.proceed();
           GlobalTransactionManager.addLbTransaction(transaction, TransactionType.COMMIT);
       }catch(Exception e){
           GlobalTransactionManager.addLbTransaction(transaction, TransactionType.ROLLBACK);
       }



   }


    @Override
    public int getOrder() {
        return 0;
    }
}
