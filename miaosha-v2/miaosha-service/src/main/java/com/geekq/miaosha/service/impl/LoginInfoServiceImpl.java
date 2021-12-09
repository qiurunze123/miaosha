package com.geekq.miaosha.service.impl;

import com.geekq.miaosha.mapper.IpLogMapper;
import com.geekq.miaosha.mapper.LogininfoMapper;
import com.geekq.miaosha.service.LoginInfoService;
import com.geekq.miasha.entity.IpLog;
import com.geekq.miasha.entity.Logininfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.geekq.miasha.utils.SnowflakeIdWorker;

import java.util.List;

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

        return "" ;
    }
}
