package com.geekq.miaosha.service;

import com.geekq.globaltransaction.annotation.GlobalTransaction123;
import com.geekq.miaosha.domain.MiaoshaUser;
import com.geekq.miaosha.domain.OrderInfo;
import com.geekq.miaosha.vo.GoodsVo;
import org.springframework.transaction.annotation.Transactional;

public interface IOrderService {
    @Transactional
    @GlobalTransaction123(isStart = true)
    OrderInfo createOrder(MiaoshaUser user, GoodsVo goods);
}
