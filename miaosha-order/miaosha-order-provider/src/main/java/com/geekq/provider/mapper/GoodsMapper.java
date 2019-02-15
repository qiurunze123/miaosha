package com.geekq.provider.mapper;

import com.geekq.api.entity.GoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 邱润泽
 */
public interface GoodsMapper {

    public List<GoodsVo> listGoodsVo();

    public GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);


}
