package com.geekq.admin.service.impl;

import com.geekq.admin.entity.Logininfo;
import com.geekq.admin.entity.Userinfo;
import com.geekq.admin.mapper.IpLogMapper;
import com.geekq.admin.mapper.LogininfoMapper;
import com.geekq.admin.mapper.UserinfoMapper;
import com.geekq.admin.service.ILogininfoService;
import com.geekq.common.enums.Constants;
import com.geekq.common.enums.ResultStatus;
import com.geekq.common.utils.Constanst;
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
@Service
public class LogininfoServiceImpl implements ILogininfoService {

	private static final Logger logger = LoggerFactory.getLogger(LogininfoServiceImpl.class);
	@Autowired
	private LogininfoMapper loginInfoMapper;

	@Autowired
	private IpLogMapper ipLogMapper;

	@Autowired
	private UserinfoMapper userinfoMapper;

	@Override
	public ResultGeekQ<Boolean> register(String username, String password) {

	ResultGeekQ<Boolean> resultGeekQ = ResultGeekQ.build();
	int count = loginInfoMapper.getCountByUsername(username, Constants.USERTYPE_NORMAL);
	if(count > 0) {
		try {
			Logininfo logininfo =new Logininfo();
			logininfo.setNickname(username);
			//获取随机salt
			String salt = MD5Utils.getSaltT();
			//MD5(MD5(password)+salt)
			logininfo.setPassword(MD5Utils.formPassToDBPass(password,salt));
			logininfo.setState(Constants.STATE_NORMAL);
			logininfo.setUserType(Constants.USERTYPE_NORMAL);
			logininfo.setRegisterDate(new Date());
			logininfo.setSalt(salt);
			this.loginInfoMapper.insert(logininfo);
			//初始化一个Userinfo
			Userinfo userinfo = Userinfo.empty(logininfo.getId());
			this.userinfoMapper.insert(userinfo);
		} catch (Exception e) {
			logger.error("注册失败!",e);
			resultGeekQ.withError(ResultStatus.RESIGETER_FAIL);
		}
	}else{
		resultGeekQ.withError(ResultStatus.RESIGETER_NICKNAMEEXIST);
	}
		return resultGeekQ;
	}

	@Override
	public boolean checkUsername(String name, int userType) {
		return this.loginInfoMapper.getCountByUsername(name, userType)<=0;
	}

	@Override
	public Logininfo login(String name, String password, int userType, String ip) {
		return null;
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
