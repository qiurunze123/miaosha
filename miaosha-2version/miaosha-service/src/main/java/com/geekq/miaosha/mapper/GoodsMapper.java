package com.geekq.miaosha.mapper;

import com.geekq.miasha.entity.MiaoshaGoods;
import com.geekq.miasha.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 邱润泽
 */
public interface GoodsMapper {

    public List<GoodsVo> listGoodsVo();

    public GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    public int reduceStock(MiaoshaGoods g);

}
