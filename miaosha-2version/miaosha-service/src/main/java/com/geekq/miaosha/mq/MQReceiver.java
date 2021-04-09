package com.geekq.miaosha.mq;


import com.geekq.miaosha.biz.entity.MiaoshaUser;
import com.geekq.miaosha.biz.entity.OrderInfo;
import com.geekq.miaosha.redis.RedisService;
import com.geekq.miaosha.service.GoodsComposeService;
import com.geekq.miaosha.service.MiaoShaComposeService;
import com.geekq.miaosha.service.OrderComposeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/*
* 异步、削峰、解耦
*
* */
@Service
public class MQReceiver {

		private static Logger log = LoggerFactory.getLogger(MQReceiver.class);
		
		@Autowired
        RedisService redisService;
		
		@Autowired
		GoodsComposeService goodsComposeService;
		
		@Autowired
		OrderComposeService orderComposeService;
		
		@Autowired
		MiaoShaComposeService miaoShaComposeService;

		/*@Autowired
		private com.geekq.api.service.GoodsService goodsServiceRpc;*/

	//    @RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
		public void receive(String message) {
			log.info("receive message:"+message);
			MiaoShaMessage mm  = RedisService.stringToBean(message, MiaoShaMessage.class);
			MiaoshaUser user = mm.getUser();
			long goodsId = mm.getGoodsId();
	    	//减库存 下订单 写入秒杀订单
	    	miaoShaComposeService.miaosha(user, goodsId,true);
		}
		/*
		*
		* 延时取消订单
		* 释放锁定库存，删除秒杀信息
		* */
	//	@RabbitListener(queues = MQConfig.DELAY_QUEUE_1)
		public void receiveCancelOrder(String message){
			OrderInfo orderDetailVo=RedisService.stringToBean(message, OrderInfo.class);
            Date expireDate=orderDetailVo.getExpireDate();
			Long id=orderDetailVo.getId();
			Integer status=orderDetailVo.getStatus();
			if(status.equals(0) ){
				miaoShaComposeService.cancelOrder(orderDetailVo);
			}
		}




}
