package com.geekq.miaosha.mq.imp;

import com.alibaba.fastjson.JSONObject;
import com.geekq.miaosha.biz.entity.MiaoshaUser;
import com.geekq.miaosha.biz.entity.OrderInfo;
import com.geekq.miaosha.mq.IMQService;
import com.geekq.miaosha.mq.MQConfig;
import com.geekq.miaosha.service.MiaoShaComposeService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.geekq.miaosha.enums.enums.ResultStatus.EXCEPTION;

@Component(value="checkMiaoShaRabbitMQService")
public class CheckMiaoShaRabbitMQService  implements IMQService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    MiaoShaComposeService miaoShaComposeService;
    @Override
    public String send(String paramsJson) {
        rabbitTemplate.convertAndSend(MQConfig.CHECK_MIAOSHA_EXCHANGE,"checkmiaosha" , paramsJson);
        return "排队秒杀中，请稍后。。。。。";
    }

    @Override
    @RabbitListener(queues= MQConfig.CHECK_MIAOSHA_QUEUE)
    public String receive(String paramsJson) {
        JSONObject param=JSONObject.parseObject(paramsJson);
        String checkResult=miaoShaComposeService.doCheckMiaoSha((MiaoshaUser) param.get("user"),param.getString("user"),param.getLong("user"));
        if("success".equals(checkResult)){
            OrderInfo orderInfo= miaoShaComposeService.doMiaoSha((MiaoshaUser) param.get("user"),param.getLong("user"),true);
        }else{

        }

        return checkResult;
    }
}
