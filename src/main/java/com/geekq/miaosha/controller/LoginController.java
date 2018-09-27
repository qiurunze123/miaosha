package com.geekq.miaosha.controller;

import com.geekq.miaosha.dao.UserMapper;
import com.geekq.miaosha.domain.MiaoshaUser;
import com.geekq.miaosha.result.Result;
import com.geekq.miaosha.service.MiaoShaUserService;
import com.geekq.miaosha.vo.LoginVo;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MiaoShaUserService userService;


    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/to_login")
    public String tologin(LoginVo loginVo) {
        log.info(loginVo.toString());
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> dologin(HttpServletResponse response, @Valid LoginVo loginVo) {
        log.info(loginVo.toString());
        userService.login(response, loginVo);
        return Result.success(true);
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result<String> register(MiaoshaUser user) {
        return userService.insertMiaoShaUser(user);
    }

    @RequestMapping("/mybatis")
    public void mybatisXml() {
        //
        int count = userMapper.getMiaoShaUserNum();
        System.out.println(count);
        MiaoshaUser user = userMapper.getMiaoShaUserById(Long.valueOf("18612766134"));
        System.out.println(user);
//
//        MiaoshaUser miaoshaUser =new MiaoshaUser();
//        miaoshaUser.setId(Long.valueOf("1234569879"));
//        miaoshaUser.setNickname("test");
//        Long num = userMapper.insertMiaoShaUser(miaoshaUser);

        MiaoshaUser upmiaoshaUser =new MiaoshaUser();
        upmiaoshaUser.setId(Long.valueOf("1234569879"));
        upmiaoshaUser.setNickname("test1");
        userMapper.updateMiaoShaUser(upmiaoshaUser);

        userMapper.deleteMiaoShaUser(Long.valueOf("1234569879"));
    }

}
