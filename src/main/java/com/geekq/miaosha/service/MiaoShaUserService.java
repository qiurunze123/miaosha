package com.geekq.miaosha.service;

import com.geekq.miaosha.Md5Utils.MD5Utils;
import com.geekq.miaosha.dao.MiaoShaUserDao;
import com.geekq.miaosha.domain.MiaoshaUser;
import com.geekq.miaosha.exception.GlobleException;
import com.geekq.miaosha.redis.MiaoShaUserKey;
import com.geekq.miaosha.redis.RedisService;
import com.geekq.miaosha.result.CodeMsg;
import com.geekq.miaosha.utils.UUIDUtil;
import com.geekq.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoShaUserService {

    public static final String COOKIE_NAME_TOKEN = "token" ;

    @Autowired
    private MiaoShaUserDao miaoShaUserDao ;

    @Autowired
    private RedisService redisService ;


    public MiaoshaUser getByToken(HttpServletResponse response , String token) {

        if(StringUtils.isEmpty(token)){
            return null ;
        }
        MiaoshaUser user =redisService.get(MiaoShaUserKey.token,token,MiaoshaUser.class) ;
        if(user!=null) {
            addCookie(response, user);
        }
        return user ;

    }

    public MiaoshaUser getById(long id) {
        return miaoShaUserDao.getById(id);
    }

    public boolean login(HttpServletResponse response , LoginVo loginVo) {
        if(loginVo ==null){
            throw  new GlobleException(CodeMsg.SERVER_ERROR);
        }

        String mobile =loginVo.getMobile();
        String password =loginVo.getPassword();
        MiaoshaUser user = getById(Long.valueOf(mobile));
        if(user == null) {
            throw new GlobleException(CodeMsg.MOBILE_NOT_EXIST);
        }

        String dbPass = user.getPassword();
        String saltDb = user.getSalt();
        String calcPass = MD5Utils.formPassToDBPass(password,saltDb);
        if(!calcPass.equals(dbPass)){
            throw new GlobleException(CodeMsg.PASSWORD_ERROR);
        }
       //生成cookie
        addCookie(response,user);
        return true ;
    }

    private void addCookie(HttpServletResponse response ,MiaoshaUser user){
        String token = UUIDUtil.uuid();
        redisService.set(MiaoShaUserKey.token,token,user) ;
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN , token) ;
        cookie.setMaxAge(MiaoShaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
