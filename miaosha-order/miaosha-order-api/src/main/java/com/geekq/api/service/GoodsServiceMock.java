package com.geekq.api.service;

import com.geekq.api.entity.GoodsVoOrder;
import com.geekq.api.utils.ResultGeekQOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 邱润泽
 */
@Service
public class GoodsServiceMock implements GoodsService {
    @Override
    public ResultGeekQOrder<List<GoodsVoOrder>> listGoodsVo() {
        List<GoodsVoOrder> list1 = new ArrayList<>();
        ResultGeekQOrder<List<GoodsVoOrder>> list = ResultGeekQOrder.build();
        list.setData(list1);
        return list;
    }

    @Override
    public ResultGeekQOrder<GoodsVoOrder> getGoodsVoByGoodsId(long goodsId) {
        return null;
    }

    @Override
    public boolean reduceStock(GoodsVoOrder goods) {
        return false;
    }
}
