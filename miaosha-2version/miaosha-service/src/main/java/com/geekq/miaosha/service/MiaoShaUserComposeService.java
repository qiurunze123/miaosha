package com.geekq.miaosha.service;

import com.geekq.miaosha.biz.entity.MiaoshaUser;
import com.geekq.miaosha.biz.service.MiaoshaUserService;
import com.geekq.miaosha.mq.MQSender;
import com.geekq.miaosha.redis.MiaoShaUserKey;
import com.geekq.miaosha.entity.IpLog;
import com.geekq.miaosha.exception.GlobleException;
import com.geekq.miaosha.redis.RedisService;
import com.geekq.miaosha.utils.MD5Utils;
import com.geekq.miaosha.utils.UUIDUtil;
import com.geekq.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.geekq.miaosha.enums.Constants.USERTYPE_NORMAL;
import static com.geekq.miaosha.enums.enums.ResultStatus.*;


@Service
public class MiaoShaUserComposeService {

    public static final String COOKIE_NAME_TOKEN = "token" ;
    private static Logger logger = LoggerFactory.getLogger(MiaoShaUserComposeService.class);

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @Autowired
    private RedisService redisService ;



    public boolean getNickNameCount(String userName){

      return miaoshaUserService.getCountByUserName(userName,USERTYPE_NORMAL) <=0;

    }
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
        user = miaoshaUserService.getByNickname(nickName);
        if(user != null) {
            redisService.set(MiaoShaUserKey.getByNickName, ""+nickName, user);
        }
        return user;
    }


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
        miaoshaUserService.update(toBeUpdate);
        //处理缓存
        redisService.delete(MiaoShaUserKey.getByNickName, ""+nickName);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoShaUserKey.token, token, user);
        return true;
    }


    public boolean register(String userName , String passWord ,
                            HttpServletResponse response , HttpServletRequest request) {
        MiaoshaUser miaoShaUser =  new MiaoshaUser();
        miaoShaUser.setNickname(userName);
        //password  应该在前段进行一次MD5 在后端在进行一个MD5 在入库
        String salt = MD5Utils.getSaltT();
        String DBPassWord =  MD5Utils.formPassToDBPass(passWord ,salt);
        miaoShaUser.setPassword(DBPassWord);
        miaoShaUser.setRegisterDate(new Date());
        miaoShaUser.setSalt(salt);
        miaoShaUser.setNickname(userName);
        try {
            miaoshaUserService.save(miaoShaUser);
            IpLog log = new IpLog(userName,new Date(),request.getRemoteAddr(),
                    USERTYPE_NORMAL,null);

            MiaoshaUser user = miaoshaUserService.getByNickname(miaoShaUser.getNickname());
            if(user == null){
                return false;
            }


            //生成cookie 将session返回游览器 分布式session
            String token= UUIDUtil.uuid();
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

        String mobile =loginVo.getNickname();
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

        String mobile =loginVo.getNickname();
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
        return token ;
    }
    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        redisService.set(MiaoShaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        //设置有效期
        cookie.setMaxAge(MiaoShaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
