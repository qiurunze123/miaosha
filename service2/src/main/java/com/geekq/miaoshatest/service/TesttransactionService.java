package com.geekq.miaoshatest.service;

import com.geekq.miaoshatest.entity.Testtransaction;
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

    void saveInfo(String name, String code);
}
