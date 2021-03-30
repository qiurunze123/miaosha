package com.geekq.miaosha.biz.mapper;

import com.geekq.miaosha.biz.entity.MiaoshaOrder;
import com.geekq.miaosha.biz.entity.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangc
 * @since 2021-03-28
 */

public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    public int insert(OrderInfo orderInfo);

    public int closeOrderByOrderInfo();
}
