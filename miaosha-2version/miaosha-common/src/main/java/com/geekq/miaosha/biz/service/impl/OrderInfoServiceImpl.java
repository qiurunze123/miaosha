package com.geekq.miaosha.biz.service.impl;

import com.geekq.miaosha.biz.entity.MiaoshaOrder;
import com.geekq.miaosha.biz.entity.OrderInfo;
import com.geekq.miaosha.biz.mapper.OrderInfoMapper;
import com.geekq.miaosha.biz.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangc
 * @since 2021-03-28
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Override
    public int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder) {
        return this.getBaseMapper().insertMiaoshaOrder(miaoshaOrder);
    }

    @Override
    public List<OrderInfo> selectOrderStatusByCreateTime(Integer valueOf, String dateToStr) {
        return this.getBaseMapper().selectOrderStatusByCreateTime(valueOf, dateToStr);
    }
}
