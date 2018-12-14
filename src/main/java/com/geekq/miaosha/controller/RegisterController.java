package com.geekq.miaosha.controller;

import com.geekq.miaosha.common.resultbean.ResultGeekQ;
import com.geekq.miaosha.domain.MiaoshaUser;
import com.geekq.miaosha.service.MiaoShaUserService;
import com.geekq.miaosha.service.MiaoshaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

import static com.geekq.miaosha.common.enums.ResultStatus.CODE_FAIL;
import static com.geekq.miaosha.common.enums.ResultStatus.RESIGETER_FAIL;

@Controller
@RequestMapping("/user")
public class RegisterController {

    private static Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private MiaoShaUserService miaoShaUserService;
    @Autowired
    private MiaoshaService miaoshaService ;
    @RequestMapping("/do_register")
    public String registerIndex(){
        return "register";
    }

    /**
     * 注册网站
     * @param userName
     * @param passWord
     * @param salt
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public ResultGeekQ<String> register(@RequestParam("username") String userName ,
                                        @RequestParam("password") String passWord,
                                        @RequestParam("verifyCode") String verifyCode,
                                        @RequestParam("salt") String salt,HttpServletResponse response ){

        ResultGeekQ<String> result = ResultGeekQ.build();
        /**
         * 校验验证码
         */
        boolean check = miaoshaService.checkVerifyCodeRegister(Integer.valueOf(verifyCode));
        if(!check){
            result.withError(CODE_FAIL.getCode(),CODE_FAIL.getMessage());
            return result;

        }
        boolean registerInfo  = miaoShaUserService.register(response , userName,passWord,salt);
        if(!registerInfo){
           result.withError(RESIGETER_FAIL.getCode(),RESIGETER_FAIL.getMessage());
           return result;
        }
        return result;
    }
}
