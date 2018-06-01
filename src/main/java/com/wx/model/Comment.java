package com.wx.model;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private Integer commentid;

    private Integer userid;

    private String commentmsg;

    private Date commenttime;

    private Integer commentdel;

    private static final long serialVersionUID = 1L;

    public Integer getCommentid() {
        return commentid;
    }

    public void setCommentid(Integer commentid) {
        this.commentid = commentid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getCommentmsg() {
        return commentmsg;
    }

    public void setCommentmsg(String commentmsg) {
        this.commentmsg = commentmsg == null ? null : commentmsg.trim();
    }

    public Date getCommenttime() {
        return commenttime;
    }

    public void setCommenttime(Date commenttime) {
        this.commenttime = commenttime;
    }

    public Integer getCommentdel() {
        return commentdel;
    }

    public void setCommentdel(Integer commentdel) {
        this.commentdel = commentdel;
    }
}