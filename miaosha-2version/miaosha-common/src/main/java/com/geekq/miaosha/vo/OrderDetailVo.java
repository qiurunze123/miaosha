package com.geekq.miaosha.vo;

import com.geekq.miaosha.entity.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {
	private GoodsExtVo goods;
	private OrderInfo order;
	public GoodsExtVo getGoods() {
		return goods;
	}
	public void setGoods(GoodsExtVo goods) {
		this.goods = goods;
	}
	public OrderInfo getOrder() {
		return order;
	}
	public void setOrder(OrderInfo order) {
		this.order = order;
	}
}
