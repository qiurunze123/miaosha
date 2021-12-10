package com.geekq.admin.service;


import com.geekq.admin.entity.Userinfo;

public interface IUserService {

    void update(Userinfo userinfo);

    Userinfo get(Long id);

    boolean bindPhone(String phoneNumber, String verifyCode);

    /**
     * 修改基本信息
     *
     * @param userinfo
     */
    void updateBasicInfo(Userinfo userinfo);
}
