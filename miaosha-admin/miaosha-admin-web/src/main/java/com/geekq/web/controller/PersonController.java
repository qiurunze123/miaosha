package com.geekq.web.controller;

import com.geekq.admin.entity.Logininfo;
import com.geekq.admin.service.IAccountService;
import com.geekq.admin.service.IUserService;
import com.geekq.admin.service.RedisCacheStorageService;
import com.geekq.web.interceptor.RequiredLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PersonController extends BaseController {

    @Autowired
    private IUserService userinfoService;

    @Autowired
    private IAccountService accountService;


    @Autowired
    private RedisCacheStorageService redisService;

    @RequiredLogin
    @RequestMapping("/personal")
    public String personal(Model model, HttpServletRequest request) {
        //从中拿到 用户信息对象
        String username = request.getParameter("username");
        Logininfo info = redisService.get("Login" + username);
        model.addAttribute("userinfo", userinfoService.get(info.getId()));
        model.addAttribute("account", accountService.get(info.getId()));
        model.addAttribute("logininfo", info);

        return "personal";
    }
}
