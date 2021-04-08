package com.geekq.miaosha.service.impl;

import com.geekq.miaosha.manager.ThirdPayService;
import com.geekq.miaosha.service.IPayService;
import com.geekq.miaosha.vo.PayOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayService implements IPayService {

    @Autowired
    private ThirdPayService thirdPayService;
    @Override
    public String pay(PayOrderVo payOrderVo) {


        return thirdPayService.pay(payOrderVo);
    }
}
