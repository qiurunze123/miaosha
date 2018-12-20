package com.geekq.miaosha.controller;

import com.geekq.miaosha.redis.redismanager.RedisLua;
import com.geekq.miaosha.vo.LoginVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lua")
public class TestLuaController {

    @RequestMapping("/testlua")
    public void lua() {

        RedisLua.getLUa();
    }

}
