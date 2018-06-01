package com.wx.dao;

import com.wx.model.Bbs;

public interface BbsMapper {
    int deleteByPrimaryKey(Integer bbsid);

    int insert(Bbs record);

    int insertSelective(Bbs record);

    Bbs selectByPrimaryKey(Integer bbsid);

    int updateByPrimaryKeySelective(Bbs record);

    int updateByPrimaryKey(Bbs record);
}