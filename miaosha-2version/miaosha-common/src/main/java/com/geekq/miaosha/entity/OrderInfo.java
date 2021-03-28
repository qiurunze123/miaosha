package com.geekq.miaosha.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Alias("OrderInfo")
@ApiModel(value="商品订单信息对象")
public class OrderInfo {
	@ApiModelProperty(value="主键id")
	private Long id;
	@ApiModelProperty(value="用户id")
	private Long userId;
	@ApiModelProperty(value="商品id")
	private Long goodsId;
	@ApiModelProperty(value="收货地址id")
	private Long  deliveryAddrId;
	@ApiModelProperty(value="商品名")
	private String goodsName;
	@ApiModelProperty(value="购买商品数量")
	private Integer goodsCount;
	@ApiModelProperty(value="商品价格")
	private Double goodsPrice;
	@ApiModelProperty(value="")
	private Integer orderChannel;
	@ApiModelProperty(value="订单状态,0-未完成,1-已完成")
	private Integer status;
	@ApiModelProperty(value="订单创建时间")
	private Date createDate;
	@ApiModelProperty(value="付款时间")
	private Date payDate;
	@ApiModelProperty(value="订单过期时间")
	private Date expireDate;

}
