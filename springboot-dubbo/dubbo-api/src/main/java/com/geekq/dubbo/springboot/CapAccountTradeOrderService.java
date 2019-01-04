package com.geekq.dubbo.springboot;

import dto.CapitalTradeOrderDto;
import org.mengyun.tcctransaction.api.Compensable;

import java.math.BigDecimal;

/**
 * @author qiurunze
 */
public interface CapAccountTradeOrderService {


    BigDecimal getCapAccountByUserId(Long userId );

    @Compensable
    public String record(CapitalTradeOrderDto tradeOrderDto);

}
