package com.geekq.miaosha.mapper;

import com.geekq.miasha.entity.MiaoshaOrder;
import com.geekq.miasha.entity.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 邱润泽
 */
public interface OrderMapper {

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userNickName") long userNickName, @Param("goodsId") long goodsId);

    public long insert(OrderInfo orderInfo);

    public int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    public OrderInfo getOrderById(@Param("orderId") long orderId);

    public List<OrderInfo> selectOrderStatusByCreateTime(@Param("status") Integer status, @Param("createDate") String createDate);

    public int closeOrderByOrderInfo();

}
