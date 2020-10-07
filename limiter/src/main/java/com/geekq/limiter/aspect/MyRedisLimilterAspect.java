package com.geekq.limiter.aspect;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.geekq.limiter.annotation.MyRedisLimiter;
import com.google.common.collect.Lists;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@Aspect
@Component
public class MyRedisLimilterAspect {
     private final Log log= LogFactory.get();
     @Autowired
     private HttpServletResponse response;
     @Autowired
     private StringRedisTemplate stringRedisTemplate;
     private DefaultRedisScript<List> redisScript;
     public void init(){
          redisScript=new DefaultRedisScript<List>();
          redisScript.setResultType(List.class);
          redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("")));
     }

     @Pointcut("@annotation(com.geekq.limiter.annotation.MyRedisLimiter)")
     public void pointcut(){

     }
     @Around("pointcut()")
     public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
          MethodSignature methodSignature=(MethodSignature) joinPoint.getSignature();
          MyRedisLimiter myRedisLimiter=methodSignature.getMethod().getDeclaredAnnotation(MyRedisLimiter.class);
          if(null==myRedisLimiter){
               return joinPoint.proceed();
          }
          double value=myRedisLimiter.value();
          String key="ip:"+System.currentTimeMillis()/1000;
          List<String> keyList= Lists.newArrayList(key);
          List<String> argvList=Lists.newArrayList(String.valueOf(value));
          List result=stringRedisTemplate.execute(redisScript,keyList,argvList);
          log.info("lua脚本的执行结果:"+result);
          if("0".equals(result.get(0).toString())){
               fullBack();
               return null;

          }

          return joinPoint.proceed();

     }

     private void fullBack(){
          response.setHeader("Content-Type","text/html;charset=UTF8");
          PrintWriter pw=null;
          try{
               pw=response.getWriter();
               pw.println("回退失败，请稍后阅读....");
               pw.flush();

          } catch (IOException e) {
               e.printStackTrace();
          }finally {
               if(null!=pw){
                    pw.close();

               }
          }
     }



}
