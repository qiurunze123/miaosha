package com.geekq.miaosha.mq;

import com.geekq.miaosha.biz.entity.OrderInfo;
import com.geekq.miaosha.util.StringBeanUtil;
import com.geekq.miaosha.vo.MiaoShaMessageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {

	private static Logger log = LoggerFactory.getLogger(MQSender.class);
	
	@Autowired
    AmqpTemplate amqpTemplate ;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendMiaoshaMessage(MiaoShaMessage mm) {
		String msg = StringBeanUtil.beanToString(mm);
		log.info("send message:"+msg);
		amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, msg);
	}

	/**
	 * 站内信
	 * @param mm
	 */
	public void sendMessage(MiaoShaMessage mm) {
//		String msg = StringBeanUtil.beanToString(mm);
		log.info("send message:"+"11111");
		rabbitTemplate.convertAndSend(MQConfig.EXCHANGE_TOPIC,"miaosha_*", "111111111");
	}

    /**
     * 站内信
     * @param
     */
    public void sendRegisterMessage(MiaoShaMessageVo miaoShaMessageVo) {
		String msg = StringBeanUtil.beanToString(miaoShaMessageVo);
        log.info("send message:{}" , msg);
		rabbitTemplate.convertAndSend(MQConfig.MIAOSHATEST,msg);
//        rabbitTemplate.convertAndSend(MQConfig.EXCHANGE_TOPIC,"miaosha_*", msg);
    }

    /*
    * 延时取消订单
    *
    * */
    public void sendCancelOrderMessage(OrderInfo miaoShaMessageVo){
    	String msg= StringBeanUtil.beanToString(miaoShaMessageVo);
    	rabbitTemplate.convertAndSend(MQConfig.DELAYED_EXCHANGE, MQConfig.DELAY_QUEUE_1, msg, new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				int delay_time=5*60*1000;
				message.getMessageProperties().setHeader("x-delay",delay_time);
				return message;
			}
		});
	}

}
