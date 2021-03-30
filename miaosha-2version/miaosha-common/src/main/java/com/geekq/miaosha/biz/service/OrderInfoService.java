package com.geekq.miaosha.biz.service;

import com.geekq.miaosha.biz.entity.MiaoshaOrder;
import com.geekq.miaosha.biz.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangc
 * @since 2021-03-28
 */
public interface OrderInfoService extends IService<OrderInfo> {

    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    List<OrderInfo> selectOrderStatusByCreateTime(Integer valueOf, String dateToStr);
}
