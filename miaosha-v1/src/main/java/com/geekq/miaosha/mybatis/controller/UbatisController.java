package com.geekq.miaosha.mybatis.controller;

import com.geekq.miaosha.mybatis.Mapper.UserMapper;
import com.geekq.miaosha.mybatis.entity.User;
import com.geekq.miaosha.mybatis.vo.TeacherListVo;
import com.geekq.miaosha.mybatis.vo.TeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class UbatisController {


    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/testUser", produces = "text/html")
    @ResponseBody
    public void testMybatis() {
        User user = userMapper.getUser(1);
        System.out.println(user.getId());
    }

    @RequestMapping(value = "/testSelectLIst", produces = "text/html")
    @ResponseBody
    public void testSelectUser() {
        List<User> result = userMapper.getUserList(null, "");
        System.out.println(result.size());
    }

    @RequestMapping(value = "/testInsert", produces = "text/html")
    @ResponseBody
    public void testInsert() {
        User user = new User();

        user.setName("xiaoming");
        user.setAge(16);
        int result = userMapper.insert(user);
        System.out.println(result);
    }


    @RequestMapping(value = "/testUpdate", produces = "text/html")
    @ResponseBody
    public void testUpdate() {
        User user = new User();

        user.setName("xiaoming");
        user.setAge(16);
        user.setId(1);
        user.setAddress("xingong");
        int result = userMapper.update(user);
        System.out.println(result);
    }

    @RequestMapping(value = "/testDelete", produces = "text/html")
    @ResponseBody
    public void testDelete() {
        int result = userMapper.delete(2);
        System.out.println(result);
    }


    /**
     * 测试多表联合查询
     */
    @RequestMapping(value = "/testTandU", produces = "text/html")
    @ResponseBody
    public void testTandU() {
        List<TeacherVo> teacherAndUser = userMapper.getTeacherAndUser(1);
        System.out.println(teacherAndUser.size());
    }


    /**
     * 测试多表联合查询  in
     */
    @RequestMapping(value = "/testTandUIn", produces = "text/html")
    @ResponseBody
    public void testTandUIn() {
        List<Integer> list = new ArrayList<Integer>();

        list.add(1);
        list.add(2);
        List<TeacherVo> teacherAndUser = userMapper.getTeacherAndUserList(list);
        System.out.println(teacherAndUser.size());
    }

    /**
     * 测试多表联合查询  in
     */
    @RequestMapping(value = "/testAssc", produces = "text/html")
    @ResponseBody
    public void testAssc() {

        List<TeacherListVo> teacherAndUser = userMapper.getTeacherAndUserListVo(1);
        System.out.println(teacherAndUser.toString());
    }
}
