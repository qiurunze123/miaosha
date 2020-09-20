package com.geek.miaosha.service;

import com.geek.miaosha.entity.Testtransaction;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangchuan
 * @since 2020-09-13
 */
public interface TesttransactionService extends IService<Testtransaction> {

    boolean saveInfo(String name, String code);
}
