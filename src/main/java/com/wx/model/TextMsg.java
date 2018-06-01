package com.wx.model;

public class TextMsg {
	private String ToUserName;
	private String FromUserName;
	private long CreateTime;
	private String MsgType;
	private String Content;
	private String MsgId;
	
//	public TextMsg(String toUserName,String fromUserName,long createTime,
//			String msgType,String content,String msgId){
//		super();
//		ToUserName=toUserName;
//		FromUserName = fromUserName;
//		CreateTime = createTime;
//		MsgType = msgType;
//		Content = content;
//		MsgId = msgId;
//	}
	
	@Override
	 public String toString() {
	  return "TextMsg [ToUserName=" + ToUserName + ", FromUserName="
	    + FromUserName + ", CreateTime=" + CreateTime + ", MsgType="
	    + MsgType + ", Content=" + Content +"MsgId="+MsgId +"]";
	 }
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		this.MsgType = msgType;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		this.MsgId = msgId;
	}
	
	
}	
