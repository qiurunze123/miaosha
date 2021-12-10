package com.geekq.admin.service.impl;

import com.geekq.admin.entity.Account;
import com.geekq.admin.entity.IpLog;
import com.geekq.admin.entity.Logininfo;
import com.geekq.admin.entity.Userinfo;
import com.geekq.admin.mapper.AccountMapper;
import com.geekq.admin.mapper.IpLogMapper;
import com.geekq.admin.mapper.LogininfoMapper;
import com.geekq.admin.mapper.UserinfoMapper;
import com.geekq.admin.service.ILogininfoService;
import com.geekq.admin.service.RedisCacheStorageService;
import com.geekq.common.enums.Constants;
import com.geekq.common.enums.ResultStatus;
import com.geekq.common.utils.MD5.MD5Utils;
import com.geekq.common.utils.resultbean.ResultGeekQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 邱润泽
 */
@Service("logininfoServiceImpl")
public class LogininfoServiceImpl implements ILogininfoService {

    private static final Logger logger = LoggerFactory.getLogger(LogininfoServiceImpl.class);
    @Autowired
    private LogininfoMapper loginInfoMapper;

    @Autowired
    private IpLogMapper ipLogMapper;

    @Autowired
    private UserinfoMapper userinfoMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private RedisCacheStorageService redisService;

    @Override
    public void register(String username, String password) {

        int count = loginInfoMapper.getCountByNickname(username, Constants.USERTYPE_NORMAL);
        if (count <= 0) {
            Logininfo logininfo = new Logininfo();
            logininfo.setNickname(username);
            //获取随机salt
            String salt = MD5Utils.getSaltT();
            //MD5(MD5(password)+salt)
            logininfo.setPassword(MD5Utils.formPassToDBPass(password, salt));
            logininfo.setState(Constants.STATE_NORMAL);
            logininfo.setUserType(Constants.USERTYPE_NORMAL);
            logininfo.setRegisterDate(new Date());
            logininfo.setLastLoginDate(new Date());
            logininfo.setSalt(salt);
            this.loginInfoMapper.insert(logininfo);

            //初始化一个account
            Account account = Account.empty(logininfo.getId());
            accountMapper.insert(account);


            //初始化一个Userinfo
            Userinfo userinfo = Userinfo.empty(logininfo.getId());
            int result = this.userinfoMapper.insert(userinfo);
        } else {
            throw new RuntimeException("用户名已经存在!");
        }
    }

    @Override
    public boolean checkUsername(String name, int userType) {
        return this.loginInfoMapper.getCountByNickname(name, userType) <= 0;
    }

    @Override
    public ResultGeekQ<Logininfo> login(String name, String password, int userType, String ip) {
        ResultGeekQ<Logininfo> resultGeekQ = ResultGeekQ.build();

        try {
            IpLog log = new IpLog(name, new Date(), ip, userType, null);
            Logininfo logininfo = loginInfoMapper.getLoginInfoByNickname(name, Constants.USERTYPE_NORMAL);
            String salt = logininfo.getSalt();
            Logininfo current = this.loginInfoMapper.login(name,
                    MD5Utils.formPassToDBPass(password, salt), userType);
            if (current != null) {
                redisService.set("Login" + current.getNickname(), current);
//				RedisCacheStorageService.set("login"+current.getId().toString(),10000,current);
                log.setLoginInfoId(current.getId());
                log.setLoginState(IpLog.LOGINSTATE_SUCCESS);
            }
            ipLogMapper.insert(log);
            resultGeekQ.setData(logininfo);
        } catch (Exception e) {
            logger.error("登录发生错误!", e);
            resultGeekQ.withError(ResultStatus.LOGIN_FIAL);
        }
        return resultGeekQ;
    }

    @Override
    public boolean hasAdmin() {
        return false;
    }

    @Override
    public void createDefaultAdmin() {

    }

    @Override
    public List<Map<String, Object>> autoComplate(String word, int userType) {
        return null;
    }
}
