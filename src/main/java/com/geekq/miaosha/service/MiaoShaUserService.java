package com.geekq.miaosha.service;

import com.geekq.miaosha.Md5Utils.MD5Utils;
import com.geekq.miaosha.dao.MiaoShaUserDao;
import com.geekq.miaosha.domain.MiaoshaUser;
import com.geekq.miaosha.exception.GlobleException;
import com.geekq.miaosha.redis.MiaoShaUserKey;
import com.geekq.miaosha.redis.RedisService;
import com.geekq.miaosha.result.CodeMsg;
import com.geekq.miaosha.result.Result;
import com.geekq.miaosha.utils.UUIDUtil;
import com.geekq.miaosha.vo.LoginVo;
import com.sun.org.apache.bcel.internal.classfile.Code;
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
        //取缓存
        MiaoshaUser user = redisService.get(MiaoShaUserKey.getById, ""+id, MiaoshaUser.class);
        if(user != null) {
            return user;
        }
        //取数据库
        user = miaoShaUserDao.getById(id);
        if(user != null) {
            redisService.set(MiaoShaUserKey.getById, ""+id, user);
        }
        return user;
    }

    // http://blog.csdn.net/tTU1EvLDeLFq5btqiK/article/details/78693323
    public boolean updatePassword(String token, long id, String formPass) {
        //取user
        MiaoshaUser user = getById(id);
        if(user == null) {
            throw new GlobleException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新数据库
        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Utils.formPassToDBPass(formPass, user.getSalt()));
        miaoShaUserDao.update(toBeUpdate);
        //处理缓存
        redisService.delete(MiaoShaUserKey.getById, ""+id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoShaUserKey.token, token, user);
        return true;
    }

    public Result<String> insertMiaoShaUser(MiaoshaUser miaoshaUser){

        long resultRegister  = miaoShaUserDao.insertMiaoShaUser(miaoshaUser);

        if(resultRegister == 0){
            throw new GlobleException(CodeMsg.RESIGETER_FAIL);
        }

        return Result.success(CodeMsg.SUCCESS_RESIGETER);
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
