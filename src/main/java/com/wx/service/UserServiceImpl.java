package com.wx.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.dao.UserMapper;
import com.wx.model.User;

@Service("UserService")
public class UserServiceImpl implements UserService{

	@Resource
	private UserMapper userMapper;
	
	@Override
	public User login(String userName, String userPassword) {
		// TODO Auto-generated method stub
		return userMapper.loginUser(userName, userPassword);
	}

}
