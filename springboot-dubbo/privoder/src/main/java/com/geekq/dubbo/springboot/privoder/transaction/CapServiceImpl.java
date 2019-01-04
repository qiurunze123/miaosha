package com.geekq.dubbo.springboot.privoder.transaction;


import com.geekq.dubbo.springboot.CapAccountTradeOrderService;
import dto.CapitalTradeOrderDto;

import java.math.BigDecimal;

public class CapServiceImpl implements CapAccountTradeOrderService {

    @Override
    public BigDecimal getCapAccountByUserId(Long userId) {
        return null;
    }

    @Override
    public String record(CapitalTradeOrderDto tradeOrderDto) {
        return null;
    }
}
