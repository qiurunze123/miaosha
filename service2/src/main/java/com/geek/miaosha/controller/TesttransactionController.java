package com.geek.miaosha.controller;


import com.geek.miaosha.entity.Testtransaction;
import com.geek.miaosha.service.TesttransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangchuan
 * @since 2020-09-13
 */
@Controller
@RequestMapping("/testtransaction")
public class TesttransactionController {
    @Autowired
    private TesttransactionService service;


    @RequestMapping("/createInfo")
    @ResponseBody
    public boolean createInfo(String name, String code,HttpServletRequest request,HttpServletResponse response){

        boolean resutl=service.saveInfo(name,code);
      return resutl;
     }
}
