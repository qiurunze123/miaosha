package com.geekq.admin.service.impl;

import com.geekq.admin.entity.Userinfo;
import com.geekq.admin.mapper.UserinfoMapper;
import com.geekq.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userServiceImpl")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserinfoMapper userinfoMapper;

	/*@Autowired
	private ISendVerifyCodeService verifyCodeService;*/
	
/*
	@Value("${db.timeout}")
	private String salt;
*/

    @Override
    public void update(Userinfo userinfo) {
	/*	int ret = userinfoMapper.updateByPrimaryKey(userinfo,salt);
		if (ret <= 0) {
			throw new RuntimeException("乐观锁失败");*/
		/*	throw new RuntimeException("Userinfo对象:" + userinfo.getId()
					+ " 乐观锁失败!");*/
    }

    @Override
    public Userinfo get(Long id) {

        return null;/*userinfoMapper.selectByPrimaryKey(id,salt);*/
    }

    @Override
    public boolean bindPhone(String phoneNumber, String verifyCode) {
        return false;
    }

	/*@Override
	public boolean bindPhone(String phoneNumber, String verifyCode) {
		boolean ret = verifyCodeService.verifyCode(phoneNumber, verifyCode);
		if (ret) {
			Userinfo ui = this.get(UserContext.getCurrent().getId());
			ui.setPhoneNumber(phoneNumber);
			ui.addState(BitStatesUtils.OP_BIND_PHONE);
			this.update(ui);
			return true;
		}
		return false;
	}*/

    @Override
    public void updateBasicInfo(Userinfo userinfo) {
	/*	Userinfo current = this.userinfoMapper.selectByPrimaryKey(UserContext
				.getCurrent().getId(),salt);*/
	/*	current.setEducationBackground(userinfo.getEducationBackground());
		current.setHouseCondition(userinfo.getHouseCondition());
		current.setIncomeGrade(userinfo.getIncomeGrade());
		current.setKidCount(userinfo.getKidCount());
		current.setMarriage(userinfo.getMarriage());
		if (!current.getBaseInfo()) {
			current.addState(BitStatesUtils.OP_BASE_INFO);
		}
		this.update(current);*/
    }

}
