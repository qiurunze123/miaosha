package com.geekq.provider.service.impl;

import com.geekq.api.entity.GoodsVoOrder;
import com.geekq.api.entity.MiaoshaGoods;
import com.geekq.api.service.GoodsService;
import com.geekq.api.utils.ResultGeekQOrder;
import com.geekq.api.utils.ResultStatusOrder;
import com.geekq.provider.mapper.GoodsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 邱润泽
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    private static Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);


    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public ResultGeekQOrder<List<GoodsVoOrder>> listGoodsVo() {

        ResultGeekQOrder<List<GoodsVoOrder>> resultGeekQ = ResultGeekQOrder.build();
        try {
            resultGeekQ.setData(goodsMapper.listGoodsVo());
        } catch (Exception e) {
            logger.error("获取订单数据失败！", e);
            resultGeekQ.withError(ResultStatusOrder.ORDER_GET_FAIL);
        }
        return resultGeekQ;
    }

    @Override
    public ResultGeekQOrder<GoodsVoOrder> getGoodsVoByGoodsId(long goodsId) {
        ResultGeekQOrder<GoodsVoOrder> resultGeekQ = ResultGeekQOrder.build();

        try {
            GoodsVoOrder goodsVoOrder = goodsMapper.getGoodsVoByGoodsId(goodsId);
            resultGeekQ.setData(goodsVoOrder);
        } catch (Exception e) {
            logger.error("获取单个订单失败！", e);
            resultGeekQ.withError(ResultStatusOrder.ORDER_GET_FAIL);
        }
        return resultGeekQ;
    }

    @Override
    public boolean reduceStock(GoodsVoOrder goods) {
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goods.getId());
        int ret = goodsMapper.reduceStock(g);
        return ret > 0;
    }


}
