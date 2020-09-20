package com.geek.miaosha.service.impl;

import com.geek.miaosha.entity.Testtransaction;
import com.geek.miaosha.mapper.TesttransactionMapper;
import com.geek.miaosha.service.TesttransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geekkq.globaltransaction.annotation.GlobalTransaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @GlobalTransaction
    @Transactional
    public boolean saveInfo(String name, String code) {
        Testtransaction testtransaction=new Testtransaction();
        testtransaction.setName(name+"_"+Math.random());
        testtransaction.setCode(UUID.randomUUID().toString());
        boolean result=this.save(testtransaction);
        return result;
    }
}
