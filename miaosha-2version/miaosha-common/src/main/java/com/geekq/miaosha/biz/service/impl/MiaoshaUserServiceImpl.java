package com.geekq.miaosha.biz.service.impl;

import com.geekq.miaosha.biz.entity.MiaoshaUser;
import com.geekq.miaosha.biz.mapper.MiaoshaUserMapper;
import com.geekq.miaosha.biz.service.MiaoshaUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangc
 * @since 2021-03-29
 */
@Service
public class MiaoshaUserServiceImpl extends ServiceImpl<MiaoshaUserMapper, MiaoshaUser> implements MiaoshaUserService {

    @Override
    public int getCountByUserName(String userName, int usertypeNormal) {
        return this.getBaseMapper().getCountByUserName(userName, usertypeNormal);
    }

    @Override
    public MiaoshaUser getByNickname(String nickName) {
        return this.getBaseMapper().getByNickname(nickName);
    }

    @Override
    public void update(MiaoshaUser toBeUpdate) {
         this.getBaseMapper().update(toBeUpdate);
    }

    @Override
    public void insertMiaoShaUser(MiaoshaUser miaoShaUser) {
       this.getBaseMapper().insertMiaoShaUser(miaoShaUser);
    }
}
