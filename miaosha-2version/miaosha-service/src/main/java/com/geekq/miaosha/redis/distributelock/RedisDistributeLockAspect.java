package com.geekq.miaosha.redis.distributelock;

import cn.hutool.core.lang.UUID;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class RedisDistributeLockAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;
    private Log log= LogFactory.get();
    @Pointcut("@annotation(com.geekq.miaosha.redis.distributelock.RedisDistributeLock)")
    public void disributeLock(){}

    @Around("disributeLock()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result=null;
        Class<?> aClass = joinPoint.getTarget().getClass();//获得所在切点的该类的class对象，也就是UserController这个类的对象
        String name = joinPoint.getSignature().getName();//获取该切点所在方法的名称，也就是listUser

        Method method = aClass.getMethod(name);//通过反射获得该方法
        RedisDistributeLock annotation = method.getAnnotation(RedisDistributeLock.class);//获得该注解
        String lockKey=name+"_"+annotation.lockKey();
        String lockValue= UUID.fastUUID().toString();
        long exireTime=annotation.expireTime();
        /*
        * 如果对应的key为空，则设置值并返回1
        * 如果不为空则返回0
        * 要有子线程定时监测锁过期情况
        * */
        log.info("try lock {}",lockKey);
        while(redisTemplate.opsForValue().setIfAbsent(lockKey,lockValue,exireTime, TimeUnit.MILLISECONDS)){
            log.info("lock {} success,",lockKey);
            result=joinPoint.proceed();
            break;
        }
         /*
          * 避免因锁过期问题导致客户端a删掉客户端b的锁
         *   每个客户端加唯一标识
         *
         * */
        if(redisTemplate.opsForValue().get(lockKey).equals(lockValue)){
            redisTemplate.delete(lockKey);
            log.info("release lock {}",lockKey);
        }

        return result;
    }



}
