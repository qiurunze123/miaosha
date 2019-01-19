package com.geekq.miaosha.mybatis.Mapper;


import com.geekq.miaosha.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    public User getUser(Integer id);

    public int insert(User user);

    public List<User> getUserList(@Param("id") Integer id , @Param("name") String name);
}
