package com.geekq.miaosha.dao;

import com.geekq.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * mybatis xml  写法
 */
@Mapper
public interface UserMapper {
    int countbyMenuId();
    MiaoshaUser getMiaoShaUserById(Long phoneId);
    //秒杀对象获取 mybatis的#与$的区别
    List<MiaoshaUser> queryMiaoShaUserOrderByColumn(String column);


    //查询总条数
    public int getMiaoShaUserNum();

    public Long insertMiaoShaUser(MiaoshaUser user);

    //更改表数据
    void updateMiaoShaUser(MiaoshaUser user) ;

    //删除表数据

    void deleteMiaoShaUser(long id );
}
