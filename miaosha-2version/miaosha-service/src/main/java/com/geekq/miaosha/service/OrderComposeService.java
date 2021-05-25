package com.geekq.miaosha.service;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.geekq.miaosha.biz.entity.MiaoshaOrder;
import com.geekq.miaosha.biz.entity.MiaoshaUser;
import com.geekq.miaosha.biz.entity.OrderInfo;
import com.geekq.miaosha.biz.service.MiaoshaOrderService;
import com.geekq.miaosha.biz.service.OrderInfoService;
import com.geekq.miaosha.mq.MQServiceFactory;
import com.geekq.miaosha.redis.OrderKey;

import com.geekq.miaosha.redis.RedisService;
import com.geekq.miaosha.utils.StringBeanUtil;
import com.geekq.miaosha.utils.DateTimeUtils;
import com.geekq.miaosha.vo.GoodsExtVo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geekq.miaosha.enums.Constanst.orderStaus.ORDER_NOT_PAY;


@Service
public class OrderComposeService {

	@Autowired
	private RedisService redisService ;

	@Autowired
	private OrderInfoService orderInfoService;
    @Autowired
	private MiaoshaOrderService miaoshaOrderService;


	public OrderInfo getOrderById(long orderId) {
		return orderInfoService.getById(orderId);

	}


    @Transactional
	public OrderInfo createOrderInfoAndMIaoShaOrder(MiaoshaUser user, GoodsExtVo goods,int expireTime){
		OrderInfo orderInfo=this.addOrderInfo(user,goods,expireTime);
		MiaoshaOrder miaoshaOrder=this.saveMiaoShaOrderInfo(user,orderInfo.getGoodsId(),orderInfo.getId());
		return orderInfo;
	}

/*生成订单信息，并设置订单超时机制
*
* */
	public OrderInfo addOrderInfo(MiaoshaUser user, GoodsExtVo goods,int expiretime){
		Date date=new Date();
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(date);
		orderInfo.setExpireDate(DateUtil.offsetMinute(date,expiretime));
		//orderInfo.setPayDate();
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(Long.valueOf(user.getNickname()));

		boolean success= orderInfoService.save(orderInfo);
		//发送延时订单取消通知
		if(success){
			String msg= StringBeanUtil.beanToString(orderInfo);
			MQServiceFactory.create("rabbitmq","cancelorder").send(msg);

		}else{
			orderInfo=null;
		}
		return orderInfo;
	}

	/*
	* 记录用户秒杀商品信息
	* */

	public MiaoshaOrder saveMiaoShaOrderInfo(MiaoshaUser user, Long goodsId, Long orderId ){
		MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
		miaoshaOrder.setGoodsId(goodsId);
		miaoshaOrder.setOrderId(orderId);
		miaoshaOrder.setUserId(Long.valueOf(user.getNickname()));
		miaoshaOrderService.save(miaoshaOrder);
		redisService.set(OrderKey.getMiaoshaOrderByUidGid,""+user.getNickname()+"_"+goodsId,miaoshaOrder) ;
		return miaoshaOrder;
	}




	public void closeOrder(int hour){
		Date closeDateTime = DateUtils.addHours(new Date(),-hour);
		List<OrderInfo> orderInfoList = orderInfoService.selectOrderStatusByCreateTime(Integer.valueOf(ORDER_NOT_PAY.ordinal()), DateTimeUtils.dateToStr(closeDateTime));
		for (OrderInfo orderInfo:orderInfoList){
			System.out.println("orderinfo  infomation "+orderInfo.getGoodsName());
		}
	}

    @Transactional
    public boolean deleteOrder(Long id) {
		boolean orderDeleted=true;
		boolean miaoshaOrderDeleted=true;
		orderDeleted=orderInfoService.removeById(id);
		Map<String,Object> paramsMap=new HashMap<>();
		paramsMap.put("order_id",id);
		QueryWrapper<MiaoshaOrder> queryWrapper=new QueryWrapper<>();
		queryWrapper.lambda().eq(MiaoshaOrder::getOrderId,id);
		List<MiaoshaOrder> miaoshaOrderList=miaoshaOrderService.list(queryWrapper);
		if(CollUtil.isNotEmpty(miaoshaOrderList)){
		miaoshaOrderDeleted=miaoshaOrderService.removeByMap(paramsMap);

		}
		return orderDeleted & miaoshaOrderDeleted;
    }
}
