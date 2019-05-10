package com.geekq.provider.service.impl;

import com.geekq.api.entity.GoodsVoOrder;
import com.geekq.api.service.GoodsService;
import com.geekq.api.utils.ResultGeekQOrder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 邱润泽
 */
@Service("goodGroupService")
public class GoodsGroupServiceImpl implements GoodsService {
    @Override
    public ResultGeekQOrder<List<GoodsVoOrder>> listGoodsVo() {

        System.out.println("测试 group!");
        return null;
    }

    @Override
    public ResultGeekQOrder<GoodsVoOrder> getGoodsVoByGoodsId(long goodsId) {
        System.out.println("测试 group!");
        return null;
    }

    @Override
    public boolean reduceStock(GoodsVoOrder goods) {
        System.out.println("测试 group!");
        return false;
    }
}
