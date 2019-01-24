package com.geekq.web.controller;

import com.geekq.common.utils.resultbean.ResultGeekQ;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisterController extends  BaseController{


    @RequestMapping("/register")
    @ResponseBody
    public ResultGeekQ<Boolean> register(String username, String password) {
        ResultGeekQ<Boolean> result = ResultGeekQ.build();
        try {
        } catch (RuntimeException e) {
        }
        return result;
    }

    @RequestMapping("/checkUsername")
    @ResponseBody
    public ResultGeekQ<Boolean> checkUsername(String username) {
        ResultGeekQ<Boolean> result = ResultGeekQ.build();
        try {
        } catch (RuntimeException e) {
        }
        return result;
    }
}
