package com.geekq.miaosha.biz.mapper;

import com.geekq.miaosha.biz.entity.MiaoshaUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangc
 * @since 2021-03-29
 */
public interface MiaoshaUserMapper extends BaseMapper<MiaoshaUser> {
    public MiaoshaUser getByNickname(@Param("nickname") String nickname) ;

    public MiaoshaUser getById(@Param("id") long id) ;

    public void update(MiaoshaUser toBeUpdate);

    public void insertMiaoShaUser(MiaoshaUser miaoshaUser);

    public int getCountByUserName(@Param("userName") String userName, @Param("userType") int userType);

}
