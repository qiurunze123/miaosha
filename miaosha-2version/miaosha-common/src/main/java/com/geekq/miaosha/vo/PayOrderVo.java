package com.geekq.miaosha.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "订单支付对象")
public class PayOrderVo {
    private String orderId;
    private String createBy;
    private Date createAt;
    private Double amount;
    private String channel;

}
