package com.wx.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Integer userid;

    private String username;

    private String userpassword;

    private Date useraddtime;

    private Integer usertype;

    private Integer userdel;

    private static final long serialVersionUID = 1L;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword == null ? null : userpassword.trim();
    }

    public Date getUseraddtime() {
        return useraddtime;
    }

    public void setUseraddtime(Date useraddtime) {
        this.useraddtime = useraddtime;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public Integer getUserdel() {
        return userdel;
    }

    public void setUserdel(Integer userdel) {
        this.userdel = userdel;
    }
}