package com.geekq.miaoshatest.service.impl;

import com.geekq.globaltransaction.annotation.GlobalTransaction123;
import com.geekq.miaoshatest.entity.Testtransaction;
import com.geekq.miaoshatest.mapper.TesttransactionMapper;
import com.geekq.miaoshatest.service.TesttransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangchuan
 * @since 2020-09-13
 */
@Service
public class TesttransactionServiceImpl extends ServiceImpl<TesttransactionMapper, Testtransaction> implements TesttransactionService {

    @Override
    @GlobalTransaction123(isEnd = true)
    public void saveInfo(String name, String code) {
        Testtransaction testtransaction=new Testtransaction();
        testtransaction.setName(name+"_"+Math.random());
        testtransaction.setCode(UUID.randomUUID().toString());
        boolean result=this.save(testtransaction);
        return ;
    }
}
