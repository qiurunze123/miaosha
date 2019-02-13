package com.geekq.miaosha.service;

import com.geekq.miaosha.common.SnowflakeIdWorker;
import com.geekq.miaosha.common.enums.MessageStatus;
import com.geekq.miaosha.controller.RegisterController;
import com.geekq.miaosha.dao.MiaoShaUserDao;
import com.geekq.miaosha.domain.MiaoshaUser;
import com.geekq.miaosha.exception.GlobleException;
import com.geekq.miaosha.rabbitmq.MQSender;
import com.geekq.miaosha.redis.MiaoShaUserKey;
import com.geekq.miaosha.redis.RedisService;
import com.geekq.miaosha.utils.MD5Utils;
import com.geekq.miaosha.utils.UUIDUtil;
import com.geekq.miaosha.vo.LoginVo;
import com.geekq.miaosha.vo.MiaoShaMessageVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;

import static com.geekq.miaosha.common.enums.ResultStatus.*;

@Service
public class MiaoShaUserService {

    public static final String COOKIE_NAME_TOKEN = "token" ;
    private static Logger logger = LoggerFactory.getLogger(MiaoShaUserService.class);

    @Autowired
    private MiaoShaUserDao miaoShaUserDao ;

    @Autowired
    private RedisService redisService ;

    @Autowired
    private MQSender sender ;


    public MiaoshaUser getByToken(HttpServletResponse response , String token) {

        if(StringUtils.isEmpty(token)){
            return null ;
        }
        MiaoshaUser user =redisService.get(MiaoShaUserKey.token,token,MiaoshaUser.class) ;
        if(user!=null) {
            addCookie(response, token, user);
        }
        return user ;

    }

    public MiaoshaUser getByNickName(String nickName) {
        //取缓存
        MiaoshaUser user = redisService.get(MiaoShaUserKey.getByNickName, ""+nickName, MiaoshaUser.class);
        if(user != null) {
            return user;
        }
        //取数据库
        user = miaoShaUserDao.getByNickname(nickName);
        if(user != null) {
            redisService.set(MiaoShaUserKey.getByNickName, ""+nickName, user);
        }
        return user;
    }


    // http://blog.csdn.net/tTU1EvLDeLFq5btqiK/article/details/78693323
    public boolean updatePassword(String token, String nickName, String formPass) {
        //取user
        MiaoshaUser user = getByNickName(nickName);
        if(user == null) {
            throw new GlobleException(MOBILE_NOT_EXIST);
        }
        //更新数据库
        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setNickname(nickName);
        toBeUpdate.setPassword(MD5Utils.formPassToDBPass(formPass, user.getSalt()));
        miaoShaUserDao.update(toBeUpdate);
        //处理缓存
        redisService.delete(MiaoShaUserKey.getByNickName, ""+nickName);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoShaUserKey.token, token, user);
        return true;
    }

    /**
     * 注册用户信息
     * @param response
     * @param userName
     * @param passWord
     * @param salt
     * @return
     */
    public boolean register(HttpServletResponse response , String userName , String passWord , String salt) {
        MiaoshaUser miaoShaUser =  new MiaoshaUser();
        miaoShaUser.setNickname(userName);
        //salt与用户输入的密码进行密码的计算
        String DBPassWord =  MD5Utils.formPassToDBPass(passWord , salt);
        miaoShaUser.setPassword(DBPassWord);
        miaoShaUser.setRegisterDate(new Date());
        //设置salt值
        miaoShaUser.setSalt(salt);
        miaoShaUser.setNickname(userName);
        try {
            //将用户信息存放到数据库
            miaoShaUserDao.insertMiaoShaUser(miaoShaUser);
            MiaoshaUser user = miaoShaUserDao.getByNickname(miaoShaUser.getNickname());
            if(user == null){
                return false;
            }

            MiaoShaMessageVo vo = new MiaoShaMessageVo();
            vo.setContent("尊敬的用户你好，你已经成功注册！");
            vo.setCreateTime(new Date());
            vo.setMessageId(SnowflakeIdWorker.getOrderId(0,0));
            vo.setSendType(0);
            vo.setStatus(0);
            vo.setMessageType(MessageStatus.messageType.system_message.ordinal());
            vo.setUserId(miaoShaUser.getId());
            vo.setMessageHead(MessageStatus.ContentEnum.system_message_register_head.getMessage());
            //注册成功之后向消息队列中发送消息,这里为什么使用消息队列发送消息，就是为了达到异步的场景，减少系统的耗时，加快响应速度
            sender.sendRegisterMessage(vo);
            //生成cookie 将session返回游览器 分布式session
            String token= UUIDUtil.uuid();
            //将用户信息存放到redis中
            addCookie(response, token, user);
        } catch (Exception e) {
            logger.error("注册失败",e);
            return false;
        }
        return true;
    }

    public boolean login(HttpServletResponse response , LoginVo loginVo) {
        if(loginVo ==null){
            throw  new GlobleException(SYSTEM_ERROR);
        }

        String mobile =loginVo.getMobile();
        String password =loginVo.getPassword();
        MiaoshaUser user = getByNickName(mobile);
        if(user == null) {
            throw new GlobleException(MOBILE_NOT_EXIST);
        }

        String dbPass = user.getPassword();
        String saltDb = user.getSalt();
        String calcPass = MD5Utils.formPassToDBPass(password,saltDb);
        if(!calcPass.equals(dbPass)){
            throw new GlobleException(PASSWORD_ERROR);
        }
        //生成cookie 将session返回游览器 分布式session
        String token= UUIDUtil.uuid();
        addCookie(response, token, user);
        return true ;
    }




    public String createToken(HttpServletResponse response , LoginVo loginVo) {
        if(loginVo ==null){
            throw  new GlobleException(SYSTEM_ERROR);
        }

        String mobile =loginVo.getMobile();
        String password =loginVo.getPassword();
        MiaoshaUser user = getByNickName(mobile);
        if(user == null) {
            throw new GlobleException(MOBILE_NOT_EXIST);
        }

        String dbPass = user.getPassword();
        String saltDb = user.getSalt();
        String calcPass = MD5Utils.formPassToDBPass(password,saltDb);
        if(!calcPass.equals(dbPass)){
            throw new GlobleException(PASSWORD_ERROR);
        }
        //生成cookie 将session返回游览器 分布式session
        String token= UUIDUtil.uuid();
        //添加cookie信息
        addCookie(response, token, user);
        return token ;
    }

    /**
     * 将用户信息以及token放入到redis缓存中
     * @param response
     * @param token
     * @param user
     */
    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        redisService.set(MiaoShaUserKey.token, token, user);
        //返回的cookie中只存放用户的token信息
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        //设置有效期
        cookie.setMaxAge(MiaoShaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
