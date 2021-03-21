package com.geekq.miaosha.service;

import com.geekq.miaosha.mapper.GoodsMapper;
import com.geekq.miaosha.entity.MiaoshaGoods;
import com.geekq.miaosha.vo.GoodsExtVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
	
	@Autowired
	GoodsMapper goodsMapper;


	public List<GoodsExtVo> listGoodsVo(){
		return goodsMapper.listGoodsVo();
	}

	public GoodsExtVo getGoodsVoByGoodsId(long goodsId) {
		return goodsMapper.getGoodsVoByGoodsId(goodsId);
	}

	public boolean reduceStock(GoodsExtVo goods) {
		MiaoshaGoods g = new MiaoshaGoods();
		g.setGoodsId(goods.getId());
		int ret = goodsMapper.reduceStock(g);
		return ret > 0;
	}
	
	
	
}
