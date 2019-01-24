package com.geekq.web.controller;

import com.geekq.admin.service.ILogininfoService;
import com.geekq.common.enums.Constants;
import com.geekq.common.enums.ResultStatus;
import com.geekq.common.utils.resultbean.ResultGeekQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 邱润泽
 */
@Controller
public class RegisterController extends  BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private ILogininfoService logininfoService ;

    @RequestMapping("/register")
    @ResponseBody
    public ResultGeekQ<Boolean> register(String username, String password) {
        /**
         * 登录注册
         */
       return logininfoService.register(username,password);

    }

    @RequestMapping("/checkUsername")
    @ResponseBody
    public ResultGeekQ<Boolean> checkUsername(String username) {
        ResultGeekQ<Boolean> result = ResultGeekQ.build();
        try {
            result.setData(this.logininfoService.checkUsername(username, Constants.USERTYPE_NORMAL));
        } catch (RuntimeException e) {
            logger.error("检查是否存在该用户名!",e);
            result.withError(ResultStatus.SYSTEM_ERROR);
        }
        return result;
    }
}
