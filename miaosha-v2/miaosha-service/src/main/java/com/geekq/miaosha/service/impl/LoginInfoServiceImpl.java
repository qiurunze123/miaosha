package com.geekq.miaosha.service.impl;

import com.geekq.miaosha.mapper.LogininfoMapper;
import com.geekq.miaosha.service.LoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 邱润泽
 */
@Service
public class LoginInfoServiceImpl implements LoginInfoService {

    @Autowired
    private LogininfoMapper logininfoMapper;

    @Override
    public String checkName() {
        logininfoMapper.selectAll();

        return "";
    }
}
