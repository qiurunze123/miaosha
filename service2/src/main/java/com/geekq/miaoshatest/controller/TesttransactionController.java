package com.geekq.miaoshatest.controller;


import com.alibaba.fastjson.JSONObject;
import com.geekq.globaltransaction.transactional.GlobalTransactionManager;
import com.geekq.miaoshatest.service.TesttransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangchuan
 * @since 2020-09-13
 */
@Controller
@RequestMapping("testtransaction")
public class TesttransactionController {
    @Autowired
    private TesttransactionService service;


    @RequestMapping("/createInfo")
    @ResponseBody
    public boolean createInfo(String name, String code,HttpServletRequest request,HttpServletResponse response){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("jslfsf","sdfsdlfj");
       // GlobalTransactionManager.nettyClient.send(jsonObject);
        boolean resutl=true;
                service.saveInfo(name,code);
      return resutl;
     }
}
