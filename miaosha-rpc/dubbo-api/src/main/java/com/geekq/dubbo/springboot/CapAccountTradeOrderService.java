package com.geekq.dubbo.springboot;

import dto.CapitalTradeOrderDto;

import java.math.BigDecimal;

/**
 * @author qiurunze
 */
public interface CapAccountTradeOrderService {
    BigDecimal getCapAccountByUserId(Long userId);

    public String record(CapitalTradeOrderDto tradeOrderDto);
}
