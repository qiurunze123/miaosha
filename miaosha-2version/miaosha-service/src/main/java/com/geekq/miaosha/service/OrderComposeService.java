package com.geekq.miaosha.service;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.geekq.miaosha.biz.service.MiaoshaOrderService;
import com.geekq.miaosha.biz.service.OrderInfoService;
import com.geekq.miaosha.entity.GoodsVoOrder;
import com.geekq.miaosha.mapper.OrderComposeMapper;
import com.geekq.miaosha.rabbitmq.MQSender;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geekq.miaosha.enums.Constanst.orderStaus.ORDER_NOT_PAY;


@Service
public class OrderComposeService {
	
	@Autowired
	OrderComposeMapper orderComposeMapper;

	@Autowired
	private RedisService redisService ;
	@Autowired
	private MQSender mqSender;
	@Autowired
	private OrderInfoService orderInfoService;
    @Autowired
	private MiaoshaOrderService miaoshaOrderService;
	
	public MiaoshaOrder getCachedMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
		return	redisService.get(OrderKey.getMiaoshaOrderByUidGid,""+userId+"_"+goodsId,MiaoshaOrder.class) ;
	}

	public OrderInfo getOrderById(long orderId) {
		return orderComposeMapper.getOrderById(orderId);
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

/*生成订单信息，并设置订单超时机制
*
* */
	public OrderInfo addOrderInfo(MiaoshaUser user, GoodsExtVo goods){
		Date date=new Date();
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(date);
		orderInfo.setExpireDate(DateUtil.offsetMinute(date,30));
		//orderInfo.setPayDate();
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(Long.valueOf(user.getNickname()));

		long success= orderComposeMapper.insert(orderInfo);
		//发送延时订单取消通知
		if(success>0){
			mqSender.sendCancelOrderMessage(orderInfo);
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
		int res= orderComposeMapper.insertMiaoshaOrder(miaoshaOrder);
		redisService.set(OrderKey.getMiaoshaOrderByUidGid,""+user.getNickname()+"_"+goodsId,miaoshaOrder) ;
		return miaoshaOrder;
	}




	public void closeOrder(int hour){
		Date closeDateTime = DateUtils.addHours(new Date(),-hour);
		List<OrderInfo> orderInfoList = orderComposeMapper.selectOrderStatusByCreateTime(Integer.valueOf(ORDER_NOT_PAY.ordinal()), DateTimeUtils.dateToStr(closeDateTime));
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
		QueryWrapper<com.geekq.miaosha.biz.entity.MiaoshaOrder> queryWrapper=new QueryWrapper<>();
		queryWrapper.lambda().eq(com.geekq.miaosha.biz.entity.MiaoshaOrder::getOrderId,id);
		List<com.geekq.miaosha.biz.entity.MiaoshaOrder> miaoshaOrderList=miaoshaOrderService.list(queryWrapper);
		if(CollUtil.isNotEmpty(miaoshaOrderList)){
		miaoshaOrderDeleted=miaoshaOrderService.removeByMap(paramsMap);

		}
		return orderDeleted & miaoshaOrderDeleted;
    }
}
