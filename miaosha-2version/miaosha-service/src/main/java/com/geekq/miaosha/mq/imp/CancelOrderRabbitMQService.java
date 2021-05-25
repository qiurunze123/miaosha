package com.geekq.miaosha.mq.imp;

import com.geekq.miaosha.biz.entity.OrderInfo;
import com.geekq.miaosha.mq.IMQService;
import com.geekq.miaosha.mq.MQConfig;
import com.geekq.miaosha.service.MiaoShaComposeService;
import com.geekq.miaosha.utils.StringBeanUtil;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
 * 订单延时服务
 * */
@Component(value = "cancelOrderRabbitMQService")
public class CancelOrderRabbitMQService implements IMQService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    MiaoShaComposeService miaoShaComposeService;
    @Override
    public String send(String msg) {

        rabbitTemplate.convertAndSend(MQConfig.DELAYED_EXCHANGE, MQConfig.DELAY_QUEUE_1, msg, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                int delay_time=5*60*1000;
                message.getMessageProperties().setHeader("x-delay",delay_time);
                return message;
            }
        });

        return null;
    }

    @Override
    @RabbitListener(queues = MQConfig.DELAY_QUEUE_1)
    public String receive(String message) {
        OrderInfo orderDetailVo= StringBeanUtil.stringToBean(message, OrderInfo.class);
        Date expireDate=orderDetailVo.getExpireDate();
        Long id=orderDetailVo.getId();
        Integer status=orderDetailVo.getStatus();
        if(status.equals(0) ){
            miaoShaComposeService.cancelOrder(orderDetailVo);
        }
        return null;
    }


}
