package com.geekq.miaosha.mq.imp;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.geekq.miaosha.biz.entity.MiaoshaUser;
import com.geekq.miaosha.mq.IMQService;
import com.geekq.miaosha.mq.MQConfig;
import com.geekq.miaosha.mq.MiaoShaMessage;
import com.geekq.miaosha.service.MiaoShaComposeService;
import com.geekq.miaosha.util.StringBeanUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
* 秒杀消息服务
* */
@Component
public class MiaoShaMessageRabbitMQService implements IMQService {
    private Log log= LogFactory.get();
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    MiaoShaComposeService miaoShaComposeService;

    @Override
    public String send(String msg) {

        log.info("send message:"+msg);
        rabbitTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, msg);

        return null;
    }

    @Override
    @RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
    public String receive(String message) {
        log.info("receive message:"+message);
        MiaoShaMessage mm  = StringBeanUtil.stringToBean(message, MiaoShaMessage.class);
        MiaoshaUser user = mm.getUser();
        long goodsId = mm.getGoodsId();
        //减库存 下订单 写入秒杀订单
        miaoShaComposeService.doMiaoSha(user, goodsId,true);
        return null;
    }
}
