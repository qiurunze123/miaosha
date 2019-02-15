package com.geekq.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.geekq.api.entity.GoodsVo;
import com.geekq.api.service.GoodsService;
import com.geekq.common.enums.ResultStatus;
import com.geekq.common.utils.resultbean.ResultGeekQ;
import com.geekq.provider.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 邱润泽
 */
@Service(version = "${demo.service.version}")
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public ResultGeekQ<List<GoodsVo>> listGoodsVo() {
        ResultGeekQ resultGeekQ =  ResultGeekQ.build();

        try {
            resultGeekQ.setData(goodsMapper.listGoodsVo());
        } catch (Exception e) {
            resultGeekQ.withError(ResultStatus.SYSTEM_ERROR);
        }
        return resultGeekQ;
    }
}
