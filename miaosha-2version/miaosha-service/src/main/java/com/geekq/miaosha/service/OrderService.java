package com.geekq.miaosha.service;


import cn.hutool.core.date.DateUtil;
import com.geekq.api.entity.GoodsVoOrder;
import com.geekq.miaosha.mapper.OrderMapper;
import com.geekq.miaosha.redis.OrderKey;
import com.geekq.miaosha.redis.RedisService;

import com.geekq.miaosha.entity.MiaoshaOrder;
import com.geekq.miaosha.entity.MiaoshaUser;
import com.geekq.miaosha.entity.OrderInfo;
import com.geekq.miaosha.utils.DateTimeUtils;
import com.geekq.miaosha.vo.GoodsExtVo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.geekq.miaosha.enums.Constanst.orderStaus.ORDER_NOT_PAY;


@Service
public class OrderService {
	
	@Autowired
	OrderMapper orderMapper;

	@Autowired
	private RedisService redisService ;
	
	public MiaoshaOrder getCachedMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
		return	redisService.get(OrderKey.getMiaoshaOrderByUidGid,""+userId+"_"+goodsId,MiaoshaOrder.class) ;
	}

	public OrderInfo getOrderById(long orderId) {
		return orderMapper.getOrderById(orderId);
	}


	public OrderInfo createOrderInfoAndMIaoShaOrder(MiaoshaUser user, GoodsVoOrder goods) {
		GoodsExtVo goodsExtVo=new GoodsExtVo();
		BeanUtils.copyProperties(goods,goodsExtVo);
		OrderInfo orderInfo=this.createOrderInfoAndMIaoShaOrder(user,goodsExtVo);
		return orderInfo;
	}
    @Transactional
	public OrderInfo createOrderInfoAndMIaoShaOrder(MiaoshaUser user, GoodsExtVo goods){
		OrderInfo orderInfo=this.addOrderInfo(user,goods);
		MiaoshaOrder miaoshaOrder=this.saveMiaoShaOrderInfo(user,orderInfo.getGoodsId(),orderInfo.getId());
		return orderInfo;
	}


	public OrderInfo addOrderInfo(MiaoshaUser user, GoodsExtVo goods){
		Date date=new Date();
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(date);
		orderInfo.setPayDate(DateUtil.offsetMinute(date,30));
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(Long.valueOf(user.getNickname()));
		long success=orderMapper.insert(orderInfo);
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
		int res=orderMapper.insertMiaoshaOrder(miaoshaOrder);
		redisService.set(OrderKey.getMiaoshaOrderByUidGid,""+user.getNickname()+"_"+goodsId,miaoshaOrder) ;
		return miaoshaOrder;
	}




	public void closeOrder(int hour){
		Date closeDateTime = DateUtils.addHours(new Date(),-hour);
		List<OrderInfo> orderInfoList = orderMapper.selectOrderStatusByCreateTime(Integer.valueOf(ORDER_NOT_PAY.ordinal()), DateTimeUtils.dateToStr(closeDateTime));
		for (OrderInfo orderInfo:orderInfoList){
			System.out.println("orderinfo  infomation "+orderInfo.getGoodsName());
		}
	}

	
}
