package com.geekq.web.controller;

import com.geekq.admin.entity.Logininfo;
import com.geekq.admin.service.IAccountService;
import com.geekq.admin.service.IUserService;
import com.geekq.admin.service.RedisCacheStorageService;
import com.geekq.admin.utils.UserContext;
import com.geekq.web.interceptor.RequiredLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String personal(Model model) {
        //从中拿到 用户信息对象
        Logininfo info = redisService.get("Loginqiurunze11");
        model.addAttribute("userinfo", userinfoService.get(info.getId()));
//        model.addAttribute("account", accountService.get(info.getId()));
        return "personal";
    }
}
