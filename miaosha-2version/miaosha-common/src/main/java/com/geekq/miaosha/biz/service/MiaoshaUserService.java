package com.geekq.miaosha.biz.service;

import com.geekq.miaosha.biz.entity.MiaoshaUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangc
 * @since 2021-03-29
 */
public interface MiaoshaUserService extends IService<MiaoshaUser> {

    int getCountByUserName(String userName, int usertypeNormal);

    MiaoshaUser getByNickname(String nickName);

    void update(MiaoshaUser toBeUpdate);

    void insertMiaoShaUser(MiaoshaUser miaoShaUser);
}
