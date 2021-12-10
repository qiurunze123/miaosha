package com.geekq.miaosha.controller;

import com.geekq.miaosha.service.MiaoShaUserService;
import com.geekq.miaosha.service.MiaoshaService;
import com.geekq.miasha.enums.resultbean.ResultGeekQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.geekq.miasha.enums.enums.ResultStatus.RESIGETER_FAIL;


@Controller
@RequestMapping("/")
public class RegisterController {

    private static Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private MiaoShaUserService miaoShaUserService;
    @Autowired
    private MiaoshaService miaoshaService;


    /**
     * 登录页面
     *
     * @return
     */
    @RequestMapping("/do_login")
    public String loginIndex(Model model) {
//        //未完成
//        RedisLua.vistorCount(COUNTLOGIN);
//        String count = RedisLua.getVistorCount(COUNTLOGIN).toString();
//        logger.info("访问网站的次数为:{}",count);
        model.addAttribute("count", 100000);
        return "login";
    }

    /**
     * 注册页面
     *
     * @return
     */
    @RequestMapping("/registerv2")
    public String register() {
        return "register2";
    }


    /**
     * 校验程序
     *
     * @return
     */
    @RequestMapping("/checkUsername")
    @ResponseBody
    public ResultGeekQ<Boolean> checkUsername(String username) {

        ResultGeekQ<Boolean> result = ResultGeekQ.build();
        boolean nickNameCount = miaoShaUserService.getNickNameCount(username);
        result.setData(nickNameCount);
        return result;
    }

    /**
     * 注册网站
     *
     * @param userName
     * @param passWord
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public ResultGeekQ<Boolean> register(@RequestParam("username") String userName,
                                         @RequestParam("password") String passWord,
                                         HttpServletResponse response,
                                         HttpServletRequest request) {

        ResultGeekQ<Boolean> result = ResultGeekQ.build();
        boolean registerInfo = miaoShaUserService.register(userName, passWord, response, request);
        if (!registerInfo) {
            result.withError(RESIGETER_FAIL.getCode(), RESIGETER_FAIL.getMessage());
            result.setData(false);
        }
        result.setData(true);
        return result;
    }
}
