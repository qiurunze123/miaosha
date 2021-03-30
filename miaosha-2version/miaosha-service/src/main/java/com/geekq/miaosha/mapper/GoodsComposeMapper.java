package com.geekq.miaosha.mapper;

import com.geekq.miaosha.vo.GoodsExtVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 邱润泽
 */
public interface GoodsComposeMapper {

    public List<GoodsExtVo> listGoodsVo();

    public GoodsExtVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);



}
