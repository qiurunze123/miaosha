package com.geekq.miaosha.mq.imp;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.geekq.miaosha.mq.IMQService;
import com.geekq.miaosha.mq.MQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * 用户注册消息服务
 * */
@Component(value="registerMessageRabbitMQService")
public class RegisterMessageRabbitMQService implements IMQService {
    private Log  log= LogFactory.get();

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public String send(String msg) {

        log.info("send message:{}" , msg);
        rabbitTemplate.convertAndSend(MQConfig.MIAOSHATEST,msg);
//        rabbitTemplate.convertAndSend(MQConfig.EXCHANGE_TOPIC,"miaosha_*", msg);

        return null;
    }

    @Override
    public String receive(String paramsJson) {
        return null;
    }
}
