package com.geekq.miaosha.utils;


import com.geekq.miaosha.biz.entity.MiaoshaUser;

public class UserContext {
	
	private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<MiaoshaUser>();
	
	public static void setUser(MiaoshaUser user) {
		userHolder.set(user);
	}
	
	public static MiaoshaUser getUser() {

		return userHolder.get();
	}

	public static void removeUser() {
		userHolder.remove();
	}

}
