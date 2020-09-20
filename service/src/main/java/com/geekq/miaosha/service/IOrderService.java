package com.geekq.miaosha.service;

import com.geekkq.globaltransaction.annotation.GlobalTransaction;
import com.geekq.miaosha.domain.MiaoshaUser;
import com.geekq.miaosha.domain.OrderInfo;
import com.geekq.miaosha.vo.GoodsVo;
import org.springframework.transaction.annotation.Transactional;

public interface IOrderService {
    @Transactional
    @GlobalTransaction(isStart = true)
    OrderInfo createOrder(MiaoshaUser user, GoodsVo goods);
}
