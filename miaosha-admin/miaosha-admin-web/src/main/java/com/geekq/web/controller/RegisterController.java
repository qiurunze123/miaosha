package com.geekq.web.controller;

import com.geekq.admin.service.ILogininfoService;
import com.geekq.common.enums.Constants;
import com.geekq.common.enums.ResultStatus;
import com.geekq.common.utils.resultbean.ResultGeekQ;
import com.geekq.common.utils.resultbean.ResultJSON;
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
public class RegisterController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private ILogininfoService logininfoService;

    @RequestMapping("/register")
    @ResponseBody
    public ResultJSON register(String username, String password) {
        ResultJSON json = new ResultJSON();
        try {
            this.logininfoService.register(username, password);
            json.setSuccess(true);
        } catch (RuntimeException e) {
            logger.error("注册失败", e);
            json.setMsg("注册失败,请联系相关人员!");
        }
        return json;
    }

    @RequestMapping("/checkUsername")
    @ResponseBody
    public ResultGeekQ<Boolean> checkUsername(String username) {
        ResultGeekQ<Boolean> result = ResultGeekQ.build();
        try {
            result.setData(this.logininfoService.checkUsername(username, Constants.USERTYPE_NORMAL));
        } catch (RuntimeException e) {
            logger.error("检查是否存在该用户名!", e);
            result.withError(ResultStatus.SYSTEM_ERROR);
        }
        return result;
    }
}
