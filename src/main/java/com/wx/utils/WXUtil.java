package com.wx.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.mysql.jdbc.StringUtils;
import com.thoughtworks.xstream.XStream;
import com.wx.model.Article;
import com.wx.model.PictureTextMsg;
import com.wx.model.TextMsg;

public class WXUtil {
	public static String AppID = "wx7e14a652807a4ab2";
	public static String AppSecret = "d0d95020a0c9b6bb8ce00ec8467f29aa";
	private static Logger logger = Logger.getLogger(WXUtil.class);
	public static String OpenId = "";
	
	/**
	 * 获取token
	 * @param request
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static Map<String, String> getWXDetail(HttpServletRequest request,String appId,String appSecret){
		String filepath = request.getSession().getServletContext().getRealPath("WEB-INF/json/wxDetail.xml");
		
		Map<String, String> map = XmlUtil.getXml(filepath);
		Map<String, String> resMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(appId)||StringUtils.isNullOrEmpty(appSecret)){
			logger.info("appid or appSecret is null");
			return null;
		}
		long experies = Long.parseLong(map.get("Experies"));
		if(System.currentTimeMillis()>=experies){
			try {
				logger.info("连接微信获取token");
				//获取token
				String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+
						appId+"&secret="+appSecret; 
				logger.info(url);
				String reString = APIMethod.get(url);
				String accessToken = (String)JsonUtils.stringToMap(reString).get("access_token");
				
				//获取jsapi_ticket
				String urlStr = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+
						accessToken+"&type=jsapi"; 
				String jsapiString = APIMethod.get(urlStr);
				String jsapiTicket = (String)JsonUtils.stringToMap(jsapiString).get("ticket");
				experies = System.currentTimeMillis()+2*60*60*1000;
				
				StringBuilder sb = new StringBuilder();
				sb.append("<xml>");
				sb.append("<AccessToken>"+accessToken+"</AccessToken>");
				sb.append("<JsapiTicket>"+jsapiTicket+"</JsapiTicket>");
				sb.append("<Experies>"+experies+"</Experies>");
				sb.append("</xml>");
				
				XmlUtil.setXml(filepath, sb.toString());
				
				resMap.put("AccessToken", accessToken);
				resMap.put("JsapiTicket", jsapiTicket);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else{
			resMap.put("AccessToken", map.get("AccessToken"));
			resMap.put("JsapiTicket", map.get("JsapiTicket"));
			logger.info(map.get("AccessToken"));
		}
		
		return resMap;
		
	}
	
	/**
	 * 获取jspapi签名
	 * @param url
	 * @param jsapi_ticket
	 * @return
	 */
	public static Map<String, String> getJsapiSign(String url,String jsapi_ticket){
		String nonce_str = UUID.randomUUID().toString();
		String timestamp = Long.toString(System.currentTimeMillis());
		//注意这里参数名必须全部小写，且必须有序
	    String str1 = "jsapi_ticket=" + jsapi_ticket +
	              "&noncestr=" + nonce_str +
	              "&timestamp=" + timestamp +
	              "&url=" + url;
	    String signature = EncryptUtil.sha1(str1);
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("url", url);
	    map.put("jsapi_ticket", jsapi_ticket);
	    map.put("nonceStr", nonce_str);
	    map.put("timestamp", timestamp);
	    map.put("signature", signature);
	    
	    return map;
	}
	
	//创建菜单
	public static String createMenu(HttpServletRequest request,String appId,String appSecret) throws Exception {
	      String menu = getMenuString(request);
	      //此处改为自己想要的结构体，替换即可
	      String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+getWXDetail(request, appId, appSecret).get("AccessToken");
	      try {
	           URL url = new URL(action);
	           HttpURLConnection http =   (HttpURLConnection) url.openConnection();   
	 
	           http.setRequestMethod("POST");       
	           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");   
	           http.setDoOutput(true);       
	           http.setDoInput(true);
	           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
	           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
	           http.connect();
	           OutputStream os= http.getOutputStream();   
	           os.write(menu.getBytes("UTF-8"));//传入参数   
	           os.flush();
	           os.close();
	 
	           InputStream is =http.getInputStream();
	           int size =is.available();
	           byte[] jsonBytes =new byte[size];
	           is.read(jsonBytes);
	           String message=new String(jsonBytes,"UTF-8");
	           logger.info(message);
	           return "返回信息"+message;
           } catch (MalformedURLException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }  
	       return "createMenu 失败";
	   }
		
	//读取json文件创建菜单
	public static String getMenuString(HttpServletRequest request) throws Exception{
		String url = request.getSession().getServletContext().getRealPath("WEB-INF/json/menu.json"); 
		File file = new File(url);
		String content = FileUtils.readFileToString(file,"UTF-8");
		logger.info(content);
//		JSONObject jsonObject = JSONObject.parseObject(content);
//		logger.info(jsonObject);
//		//项目根路径
		String reUrl = request.getRequestURL().toString().split("WXDemo")[0]+"WXDemo";
		logger.info(content.replace("reUrl", reUrl));
		
		return content.replace("reUrl", reUrl);
	}

	/**
	 * 处理消息内容
	 * @param map
	 * @return
	 */
	public static String returnMessage(HttpServletRequest request,Map<String, String> map){
		String content = map.get("Content");
//		String msgType = map.get("MsgType");
		String resultXML = "";
		String resultMsg = "";
		switch(content){
			case "h":
				resultMsg = "您好！请回复序号获取服务。\n\r[1]文本\n[2]图文\n";
				resultXML = returnText(map,resultMsg);
				break;
			case "1":
				resultMsg = "这是一个文本";
				resultXML = returnText(map,resultMsg);
				break;
			case "2":
				resultMsg = "这是一个图文";
				resultXML = returnPictureText(request,map);
				break;
			default:
				resultMsg = "您好，智能大脑处于成长期，您的问题超越了极限。\n\r您可以根据以下选项重新提问:\n\r-回复“h”获取导航菜单";
				resultXML = returnText(map,resultMsg);
				break;
		}

		return resultXML;
	}
	
	/**
	 * 回复文本信息
	 * @param map
	 * @return
	 */
	public static String returnText(Map<String, String> map,String content){
		
//		String content = "这是文本信息\n这是另一个文本";
		
		TextMsg textMsg = new TextMsg();
		textMsg.setMsgId(map.get("MsgId"));
		textMsg.setFromUserName(map.get("ToUserName"));
		textMsg.setToUserName(map.get("FromUserName"));
		textMsg.setCreateTime(new Date().getTime());
		textMsg.setMsgType("text");
		textMsg.setContent(content);
		
		XStream xStream = new XStream();
		xStream.alias("xml", textMsg.getClass());
		String textMsgXml = xStream.toXML(textMsg);
		
		return textMsgXml;
		
	}

	/**
	 * 图文消息
	 * @param map
	 * @return
	 */
	public static String returnPictureText(HttpServletRequest request,Map<String, String> map){
				
		//项目根路径
		String reUrl = request.getRequestURL().toString().split("WXDemo")[0]+"WXDemo";
		
		List<Article> articles = new ArrayList<Article>();
		Article article = new Article();
//		article.setTitle("我是图片标题");
		article.setUrl(reUrl+"/test/img");// 该地址是点击图片跳转后
		article.setPicUrl(reUrl+"/images/face.jpg");// 该地址是一个有效的图片地址
		article.setDescription("我是图片的描述");
		articles.add(article);
		PictureTextMsg pictureTextMsg = new PictureTextMsg();
		pictureTextMsg.setMsgId(map.get("MsgId"));
		pictureTextMsg.setToUserName(map.get("FromUserName"));// 发送和接收信息“User”刚好相反
		pictureTextMsg.setFromUserName(map.get("ToUserName"));
		pictureTextMsg.setCreateTime(new Date().getTime());// 消息创建时间 （整型）
		pictureTextMsg.setMsgType("news");// 图文类型消息
		pictureTextMsg.setArticleCount(1);
		pictureTextMsg.setArticles(articles);
		
		XStream xStream = new XStream();
		xStream.alias("xml", pictureTextMsg.getClass());
		xStream.alias("item", article.getClass());
		String pictureXml = xStream.toXML(pictureTextMsg);
		return pictureXml;
	}
	

	/**
	 * 通过code获取openid
	 * @param code
	 * @return
	 */
	public static String getOpenId(String code){
		String url="https://api.weixin.qq.com/sns/oauth2/access_token?"+
				"appid="+WXUtil.AppID+"&secret="+WXUtil.AppSecret+
				"&code="+code+"&grant_type=authorization_code";
		String retStr = APIMethod.get(url);
		Map<String, String> map = JsonUtils.stringToMap(retStr);
		return map.get("openid");
	}
	
	/**
	 * 获取用户信息
	 */
	public static Map<String, String> getUserInfo(HttpServletRequest request,String openId){
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
    	requestUrl = requestUrl.replace("ACCESS_TOKEN", WXUtil.getWXDetail(request, WXUtil.AppID, WXUtil.AppSecret).get("AccessToken")).replace("OPENID", openId);
    	String resultString = APIMethod.get(requestUrl);
    	try {
			resultString = new String(resultString.getBytes(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Map<String, String> map = JsonUtils.stringToMap(resultString);
    	return map;
	}
	
	
}
