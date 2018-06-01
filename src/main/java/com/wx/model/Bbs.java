package com.wx.model;

import java.io.Serializable;
import java.util.Date;

public class Bbs implements Serializable {
    private Integer bbsid;

    private Integer userid;

    private String bbstitle;

    private String bbscontent;

    private Date bbsaddtime;

    private Integer bbsdel;

    private static final long serialVersionUID = 1L;

    public Integer getBbsid() {
        return bbsid;
    }

    public void setBbsid(Integer bbsid) {
        this.bbsid = bbsid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getBbstitle() {
        return bbstitle;
    }

    public void setBbstitle(String bbstitle) {
        this.bbstitle = bbstitle == null ? null : bbstitle.trim();
    }

    public String getBbscontent() {
        return bbscontent;
    }

    public void setBbscontent(String bbscontent) {
        this.bbscontent = bbscontent == null ? null : bbscontent.trim();
    }

    public Date getBbsaddtime() {
        return bbsaddtime;
    }

    public void setBbsaddtime(Date bbsaddtime) {
        this.bbsaddtime = bbsaddtime;
    }

    public Integer getBbsdel() {
        return bbsdel;
    }

    public void setBbsdel(Integer bbsdel) {
        this.bbsdel = bbsdel;
    }
}