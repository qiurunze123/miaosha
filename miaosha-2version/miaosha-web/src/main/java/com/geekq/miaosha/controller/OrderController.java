package com.geekq.miaosha.controller;

import com.geekq.miaosha.biz.entity.MiaoshaUser;
import com.geekq.miaosha.biz.entity.OrderInfo;
import com.geekq.miaosha.redis.RedisService;
import com.geekq.miaosha.service.GoodsComposeService;
import com.geekq.miaosha.service.MiaoShaUserComposeService;
import com.geekq.miaosha.service.OrderComposeService;
import com.geekq.miaosha.enums.resultbean.ResultGeekQ;
import com.geekq.miaosha.vo.GoodsExtVo;
import com.geekq.miaosha.vo.OrderDetailVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.geekq.miaosha.enums.enums.ResultStatus.ORDER_NOT_EXIST;
import static com.geekq.miaosha.enums.enums.ResultStatus.SESSION_ERROR;


@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	MiaoShaUserComposeService userService;
	
	@Autowired
    RedisService redisService;
	
	@Autowired
	OrderComposeService orderComposeService;
	
	@Autowired
	GoodsComposeService goodsComposeService;
	
    @RequestMapping("/detail")
    @ResponseBody
    public ResultGeekQ<OrderDetailVo> info(Model model, MiaoshaUser user,
										   @RequestParam("orderId") long orderId) {
		ResultGeekQ<OrderDetailVo> result = ResultGeekQ.build();
		if (user == null) {
			result.withError(SESSION_ERROR.getCode(), SESSION_ERROR.getMessage());
			return result;
		}
		OrderInfo order = orderComposeService.getOrderById(orderId);
    	if(order == null) {
			result.withError(ORDER_NOT_EXIST.getCode(), ORDER_NOT_EXIST.getMessage());
			return result;
    	}

    	long goodsId = order.getGoodsId();
    	GoodsExtVo goods = goodsComposeService.getGoodsVoByGoodsId(goodsId);
    	OrderDetailVo vo = new OrderDetailVo();
    	vo.setOrder(order);
    	vo.setGoods(goods);
    	result.setData(vo);
    	return result;
    }
    
}
