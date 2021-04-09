package com.geekq.miaosha.mq;

import com.geekq.miaosha.mq.imp.CancelOrderRabbitMQService;
import com.geekq.miaosha.mq.imp.MiaoShaMessageRabbitMQService;
import com.geekq.miaosha.mq.imp.RegisterMessageRabbitMQService;

import java.util.HashMap;
import java.util.Map;

public class MQServiceFactory {
    private static Map<String, Map<String,IMQService>> map=new HashMap<>();
    static {
        Map<String,IMQService> rabbitMap=new HashMap<>();
        rabbitMap.put("cancelorder",new CancelOrderRabbitMQService());
        rabbitMap.put("miaoshamessage",new MiaoShaMessageRabbitMQService());
        rabbitMap.put("registermessage",new RegisterMessageRabbitMQService());

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
