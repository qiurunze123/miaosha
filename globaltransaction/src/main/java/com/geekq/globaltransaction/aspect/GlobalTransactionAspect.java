package com.geekq.globaltransaction.aspect;

import com.geekq.globaltransaction.annotation.GlobalTransaction123;
import com.geekq.globaltransaction.transactional.GlobalTransactionManager;
import com.geekq.globaltransaction.transactional.LbTransaction;
import com.geekq.globaltransaction.transactional.TransactionType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class GlobalTransactionAspect implements Ordered {


   @Around("@annotation(com.geekq.globaltransaction.annotation.GlobalTransaction123)")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
       System.out.println("进入拦截器！！！");
       MethodSignature signature=(MethodSignature)joinPoint.getSignature();
       Method method=signature.getMethod();
       Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
       GlobalTransaction123 globalTransaction=realMethod.getAnnotation(GlobalTransaction123.class);
       String groupId=null;
       if(globalTransaction.isStart()){
           groupId= GlobalTransactionManager.createGroup();
       }else{
           groupId=GlobalTransactionManager.getCurrentGroupId();
       }

       LbTransaction transaction=GlobalTransactionManager.createLbTransaction(groupId);
       if(globalTransaction.isEnd()){
            GlobalTransactionManager.addEndGroup(groupId,transaction);
       }
       try{
           joinPoint.proceed();
           GlobalTransactionManager.addLbTransaction(transaction, TransactionType.COMMIT);
       }catch(Exception e){
           GlobalTransactionManager.addLbTransaction(transaction, TransactionType.ROLLBACK);
       }

       System.out.println("退出拦截器!!");
       return ;
   }


    @Override
    public int getOrder() {
        return 0;
    }
}
