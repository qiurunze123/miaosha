package com.geekq.miaosha.service;

import com.geekq.miaosha.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geekq.miaosha.domain.User;

@Service
public class UserService {
	
	@Autowired
    UserDao userDao;
	
	public User getById(int id) {
		return userDao.getById(id);
	}

	@Transactional
	public void tx() {
		userDao.insert(new User(2,"22222"));
		userDao.insert(new User(1, "1111"));
	}
}
