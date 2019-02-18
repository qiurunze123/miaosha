package com.geekq.miaosha.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.geekq.miaosha.common.resultbean.ResultGeekQ;
import com.geekq.miaosha.distributelock.dbexclusivelock.NewLock;
import com.geekq.miaosha.distributelock.dbtable.Lock;
import com.geekq.miaosha.redis.redismanager.RedisLua;
import com.geekq.miaosha.service.MiaoShaUserService;
import com.geekq.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.geekq.miaosha.common.Constanst.COUNTLOGIN;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MiaoShaUserService userService;

    @Autowired
    private Lock lock;

    @Autowired
    private NewLock newLock;

    @Autowired
    private NewLock newLock1;

    @Reference
    @RequestMapping("/to_login")
    public String tologin(LoginVo loginVo, Model model) {
        logger.info(loginVo.toString());
//        testLock();
        testNewLock();
//        //访问次数增加一次
        RedisLua.vistorCount(COUNTLOGIN);
        String count = RedisLua.getVistorCount(COUNTLOGIN).toString();
        logger.info("访问网站的次数为:{}",count);
        model.addAttribute("count",count);
        return "login";
    }

    private void testNewLock() {
        logger.info("测试分布式锁");
        boolean result = newLock.lock("testNewLock");
        newLock.unLock();
        logger.info("分布式锁获取：{}", result);
        logger.info("测试分布式锁2");
        boolean result1 = newLock1.lock("testNewLock");
        logger.info("分布式锁获取2：{}", result1);

    }

    private void testLock() {
        logger.info("测试分布式锁");
        Boolean result = lock.tryLock("test", "测试方法");
        logger.info("分布式锁获取：{}", result);
        logger.info("测试分布式锁11111");
        Boolean result1 = lock.tryLock("test", "测试方法111");
        lock.releaseLock("test");
        logger.info("测试分布式锁222");
        Boolean result2 = lock.tryLock("test", "测试方法2222");
        logger.info("分布式锁获取2222：{}", result2);
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public ResultGeekQ<Boolean> dologin(HttpServletResponse response, @Valid LoginVo loginVo) {
        ResultGeekQ<Boolean> result = ResultGeekQ.build();
        logger.info(loginVo.toString());
        userService.login(response, loginVo);
        return result;
    }


    @RequestMapping("/create_token")
    @ResponseBody
    public String createToken(HttpServletResponse response, @Valid LoginVo loginVo) {
        logger.info(loginVo.toString());
        String token = userService.createToken(response, loginVo);
        return token;
    }
}
