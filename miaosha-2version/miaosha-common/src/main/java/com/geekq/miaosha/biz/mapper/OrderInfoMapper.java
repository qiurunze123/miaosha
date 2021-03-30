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
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userNickName") long userNickName, @Param("goodsId") long goodsId);

    public int insert(OrderInfo orderInfo);

    public int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    public OrderInfo getOrderById(@Param("orderId") long orderId);

    public List<OrderInfo> selectOrderStatusByCreateTime(@Param("status") Integer status, @Param("createDate") String createDate);

    public int closeOrderByOrderInfo();
}
