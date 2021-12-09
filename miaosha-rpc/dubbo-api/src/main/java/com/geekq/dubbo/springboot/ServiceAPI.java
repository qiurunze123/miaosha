package com.geekq.dubbo.springboot;

public interface ServiceAPI {

    String sendMessage(String message);

    /*
        背景：传入购票数量、传入购买座位、影厅编号
        业务：
            1、判断传入的座位是否存在
            2、查询过往订单、判断座位是否已售
            3、新增订单
        逻辑：
            1、新增一条订单
            2、判断座位是否存在 & 是否已售
            3、任意一条为假，则修改订单为无效状态
     */

    // 判断是否为真座位
    boolean isTrueSeats(String seats);

    // 是否已售
    boolean isNotSold(String seats);

    // 保存订单
    String saveOrder(String fieldId, String seats, String seatsNum);

}
