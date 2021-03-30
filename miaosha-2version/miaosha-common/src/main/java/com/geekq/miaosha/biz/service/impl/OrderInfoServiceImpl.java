package com.geekq.miaosha.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
    public boolean closeOrderByOrderInfo(int id) {
        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setStatus(0);
        UpdateWrapper<OrderInfo> updateWrapper=new UpdateWrapper<>();
        updateWrapper.lambda().eq(OrderInfo::getId,id);

        return this.update(orderInfo,updateWrapper);

    }

    @Override
    public List<OrderInfo> selectOrderStatusByCreateTime(Integer valueOf, String dateToStr) {
        QueryWrapper<OrderInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderInfo::getStatus,valueOf)
                .eq(OrderInfo::getCreateDate,dateToStr);
        return this.list(queryWrapper);
    }
}
