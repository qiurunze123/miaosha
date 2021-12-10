package com.geekq.miaosha.rabbitmq;

import com.geekq.api.entity.GoodsVoOrder;
import com.geekq.api.utils.AbstractResultOrder;
import com.geekq.api.utils.ResultGeekQOrder;
import com.geekq.miaosha.redis.RedisService;
import com.geekq.miaosha.service.GoodsService;
import com.geekq.miaosha.service.MiaoshaService;
import com.geekq.miaosha.service.OrderService;
import com.geekq.miasha.entity.MiaoshaOrder;
import com.geekq.miasha.entity.MiaoshaUser;
import com.geekq.miasha.enums.enums.ResultStatus;
import com.geekq.miasha.exception.GlobleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    private com.geekq.api.service.GoodsService goodsServiceRpc;

//		@Autowired
//        MiaoShaMessageService messageService ;

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receive(String message) {
        log.info("receive message:" + message);
        MiaoshaMessage mm = RedisService.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = mm.getUser();
        long goodsId = mm.getGoodsId();

//			GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        ResultGeekQOrder<GoodsVoOrder> goodsVoOrderResultGeekQOrder = goodsServiceRpc.getGoodsVoByGoodsId(goodsId);
        if (!AbstractResultOrder.isSuccess(goodsVoOrderResultGeekQOrder)) {
            throw new GlobleException(ResultStatus.SESSION_ERROR);
        }

        GoodsVoOrder goods = goodsVoOrderResultGeekQOrder.getData();
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(Long.valueOf(user.getNickname()), goodsId);
        if (order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        miaoshaService.miaosha(user, goods);
    }


//	@RabbitListener(queues=MQConfig.MIAOSHATEST)
//	public void receiveMiaoShaMessage(Message message, Channel channel) throws IOException {
//		log.info("接受到的消息为:{}",message);
//		String messRegister = new String(message.getBody(), "UTF-8");
//		channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
//		MiaoShaMessageVo msm  = RedisService.stringToBean(messRegister, MiaoShaMessageVo.class);
//		messageService.insertMs(msm);
//		}
}
