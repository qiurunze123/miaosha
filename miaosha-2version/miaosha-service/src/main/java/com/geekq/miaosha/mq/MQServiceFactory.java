package com.geekq.miaosha.mq;

import com.geekq.miaosha.mq.imp.CancelOrderRabbitMQService;
import com.geekq.miaosha.mq.imp.MiaoShaMessageRabbitMQService;
import com.geekq.miaosha.mq.imp.RegisterMessageRabbitMQService;
import com.geekq.miaosha.utils.SpringContextUtil;

import java.util.HashMap;
import java.util.Map;
/*
* 简单工厂将对列的创建和使用分离
*
* */
public class MQServiceFactory {
    private static Map<String, Map<String,IMQService>> map=new HashMap<>();
    static {
        Map<String,IMQService> rabbitMap=new HashMap<>();
        rabbitMap.put("cancelorder", SpringContextUtil.getBean("cancelOrderRabbitMQService",IMQService.class));
        rabbitMap.put("miaoshamessage",SpringContextUtil.getBean("miaoShaMessageRabbitMQService",IMQService.class));
        rabbitMap.put("registermessage",SpringContextUtil.getBean("registerMessageRabbitMQService",IMQService.class));

        Map<String,IMQService> rocketMap=new HashMap<>();
        rocketMap.put("cancelorder",new CancelOrderRabbitMQService());
        rocketMap.put("miaoshamessage",new MiaoShaMessageRabbitMQService());
        rocketMap.put("registermessage",new RegisterMessageRabbitMQService());
        map.put("rabbitmq",rabbitMap);
        map.put("rocketmq",rocketMap);
    }

    public static IMQService create(String mqType,String bizType){
        return map.get(mqType).get(bizType);
    }

}