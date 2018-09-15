package com.geekq.miaosha.controller;

import com.geekq.miaosha.domain.MiaoshaUser;
import com.geekq.miaosha.redis.RedisService;
import com.geekq.miaosha.service.MiaoShaUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    private static Logger log = LoggerFactory.getLogger(GoodsController.class) ;

    @Autowired
    private MiaoShaUserService userService ;

    @Autowired
    private RedisService redisService ;


    @RequestMapping("/to_list")
    public String tolist(Model model,MiaoshaUser user
                         ){
        model.addAttribute("user",user);

        return "goods_list" ;
    }
}
