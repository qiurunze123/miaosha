package com.geekq.miaosha.service;

import com.geekq.miaosha.entity.Goods;
import com.geekq.miaosha.mapper.GoodsComposeMapper;
import com.geekq.miaosha.entity.MiaoshaGoods;
import com.geekq.miaosha.vo.GoodsExtVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsComposeService {
	
	@Autowired
	GoodsComposeMapper goodsComposeMapper;




	public List<GoodsExtVo> listGoodsVo(){
		return goodsComposeMapper.listGoodsVo();
	}

	public GoodsExtVo getGoodsVoByGoodsId(long goodsId) {
		return goodsComposeMapper.getGoodsVoByGoodsId(goodsId);
	}

	public boolean reduceStock(GoodsExtVo goods) {
		MiaoshaGoods g = new MiaoshaGoods();
		g.setGoodsId(goods.getId());
		int ret = goodsComposeMapper.reduceStock(g);
		return ret > 0;
	}


    public void addStock(Goods goods) {


    }
}
