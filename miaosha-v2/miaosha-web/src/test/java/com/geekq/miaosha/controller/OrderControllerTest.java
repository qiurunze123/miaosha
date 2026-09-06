package com.geekq.miaosha.controller;

import com.geekq.miaosha.service.GoodsService;
import com.geekq.miaosha.service.OrderService;
import com.geekq.miasha.entity.MiaoshaUser;
import com.geekq.miasha.entity.OrderInfo;
import com.geekq.miasha.enums.enums.ResultStatus;
import com.geekq.miasha.enums.resultbean.ResultGeekQ;
import com.geekq.miasha.vo.OrderDetailVo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    @Test
    public void detailHidesAnOrderOwnedByAnotherUser() {
        OrderController controller = new OrderController();
        controller.orderService = mock(OrderService.class);
        controller.goodsService = mock(GoodsService.class);
        MiaoshaUser requester = new MiaoshaUser();
        requester.setNickname("42");
        OrderInfo order = new OrderInfo();
        order.setUserId(99L);
        order.setGoodsId(7L);
        when(controller.orderService.getOrderById(10L)).thenReturn(order);

        ResultGeekQ<OrderDetailVo> result = controller.info(null, requester, 10L);

        assertEquals(ResultStatus.ORDER_NOT_EXIST.getCode(), result.getCode());
        assertEquals(ResultStatus.ORDER_NOT_EXIST.getMessage(), result.getMessage());
        assertNull(result.getData());
        verifyNoInteractions(controller.goodsService);
    }
}
