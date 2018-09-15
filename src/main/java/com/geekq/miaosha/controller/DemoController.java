package com.geekq.miaosha.controller;

import com.geekq.miaosha.domain.User;
import com.geekq.miaosha.redis.RedisService;
import com.geekq.miaosha.redis.Userkey;
import com.geekq.miaosha.result.CodeMsg;
import com.geekq.miaosha.result.Result;
import com.geekq.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private UserService userService ;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/")
    @ResponseBody
    String home(){
        return "hello world" ;
    }

    @RequestMapping("/user")
    @ResponseBody
    public Result<User> user(){
        User user =  userService.getById(1);
        return Result.success(user) ;
    }


    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello(){
        return Result.success("hello world!");
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError(){
        Map<String,String> map = new HashMap<String, String>() ;
        map.put("a","1") ;
        return Result.error(CodeMsg.SERVER_ERROR) ;
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model ){
        model.addAttribute("name","world") ;
       return "hello" ;
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redis(){
        User user =  userService.getById(1);
        boolean ret = redisService.set(Userkey.getById,""+1,user);
        return Result.success(ret);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisG(){
        User user = redisService.get(Userkey.getById,""+1,User.class) ;
        return Result.success(user) ;
    }
}
