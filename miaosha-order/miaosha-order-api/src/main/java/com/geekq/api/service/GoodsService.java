package com.geekq.api.service;

import com.geekq.api.entity.GoodsVo;
import com.geekq.common.utils.resultbean.ResultGeekQ;

import java.util.List;

/**
 * @author 邱润泽
 */
public interface GoodsService {

    public ResultGeekQ<List<GoodsVo>> listGoodsVo();
}
