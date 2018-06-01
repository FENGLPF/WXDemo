package com.wx.dao;

import org.apache.ibatis.annotations.Param;

import com.wx.model.User;

public interface UserMapper {
	
	public User loginUser(@Param("userName")String userName,@Param("userPassword")String userPassword);
	
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}