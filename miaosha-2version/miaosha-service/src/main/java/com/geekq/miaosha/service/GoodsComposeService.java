package com.geekq.miaosha.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.geekq.miaosha.biz.entity.Goods;
import com.geekq.miaosha.biz.entity.MiaoshaGoods;
import com.geekq.miaosha.biz.service.MiaoshaGoodsService;
import com.geekq.miaosha.mapper.GoodsComposeMapper;
import com.geekq.miaosha.vo.GoodsExtVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsComposeService {
	
	@Autowired
	GoodsComposeMapper goodsComposeMapper;
	@Autowired
	private MiaoshaGoodsService miaoshaGoodsService;




	public List<GoodsExtVo> listGoodsVo(){
		return goodsComposeMapper.listGoodsVo();
	}

	public GoodsExtVo getGoodsVoByGoodsId(long goodsId) {
		return goodsComposeMapper.getGoodsVoByGoodsId(goodsId);
	}

	public boolean reduceStock(GoodsExtVo goods) {
		MiaoshaGoods g = new MiaoshaGoods();
		g.setStockCount(goods.getStockCount()-1);
		UpdateWrapper<MiaoshaGoods> temp=new UpdateWrapper<>();
		temp.lambda().eq(MiaoshaGoods::getGoodsId,goods.getId())
				.gt(MiaoshaGoods::getStockCount,0);
		return miaoshaGoodsService.update(g,temp);
	}


    public boolean addStock(GoodsExtVo goods) {

		MiaoshaGoods temp=new MiaoshaGoods();
		temp.setStockCount(goods.getStockCount()+1);
		UpdateWrapper<MiaoshaGoods> updateWrapper=new UpdateWrapper<>();
		updateWrapper.lambda().eq(MiaoshaGoods::getGoodsId,goods.getId());
        return miaoshaGoodsService.update(temp,updateWrapper);

    }
}
