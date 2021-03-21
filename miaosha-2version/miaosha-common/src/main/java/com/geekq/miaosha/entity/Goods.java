package com.geekq.miaosha.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="商品实体对象")
public class Goods {
	@ApiModelProperty(value = "主键Id")
	private Long id;
	@ApiModelProperty(value = "商品名")
	private String goodsName;
	@ApiModelProperty(value = "商品主题")
	private String goodsTitle;
	@ApiModelProperty(value = "商品图片")
	private String goodsImg;
	@ApiModelProperty(value = "商品详情")
	private String goodsDetail;
	@ApiModelProperty(value = "商品价格")
	private Double goodsPrice;
	@ApiModelProperty(value = "商品库存")
	private Integer goodsStock;
}
