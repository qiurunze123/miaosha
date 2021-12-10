package com.geekq.miaosha.mybatis.Mapper;


import com.geekq.miaosha.mybatis.entity.User;
import com.geekq.miaosha.mybatis.vo.TeacherListVo;
import com.geekq.miaosha.mybatis.vo.TeacherVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    public User getUser(Integer id);

    public int insert(User user);

    public int update(User user);

    public int delete(Integer id);

    /**
     * 返回值时resulttype 与数据库字段一一对应  没有的话无法对应
     *
     * @param id
     * @param name
     * @return
     */
    public List<User> getUserList(@Param("id") Integer id, @Param("name") String name);

    /**
     * 返回值是resultmap 可以对应实体类的字段 与 数据库 字段对应起来
     *
     * @param id
     * @param name
     * @return
     */
    public List<User> getUserListMap(@Param("id") Integer id, @Param("name") String name);

    public List<TeacherVo> getTeacherAndUser(@Param("uId") Integer uId);

    public List<TeacherVo> getTeacherAndUserList(@Param("uId") List<Integer> uId);

    public List<TeacherListVo> getTeacherAndUserListVo(@Param("uId") Integer uId);

}
