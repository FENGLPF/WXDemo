package com.wx.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.wx.utils.APIMethod;
import com.wx.utils.CheckUtil;
import com.wx.utils.IpUtil;
import com.wx.utils.LocationUtil;
import com.wx.utils.MatrixToImageWriter;
import com.wx.utils.WXUtil;
import com.wx.utils.XmlUtil;

@Controller
@RequestMapping("/test")
public class TestController {
	private static Logger logger = Logger.getLogger(TestController.class);
	
	@RequestMapping("/t1")
	public String test(){
		logger.info("11");
		return "test";
	}
	
	/**
     * 
     * @Description: 用于接收 get 参数，返回验证参数
     * @param @param request
     * @param @param response
     * @param @param signature
     * @param @param timestamp
     * @param @param nonce
     * @param @param echostr
     * @author dapengniao
     * @date 2016 年 3 月 4 日 下午 6:20:00
     */
    @RequestMapping("/security")
    public void doGet(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "signature", required = true) String signature,
            @RequestParam(value = "timestamp", required = true) String timestamp,
            @RequestParam(value = "nonce", required = true) String nonce,
            @RequestParam(value = "echostr", required = true) String echostr) {
        try {
        	logger.info("开始验证");
            if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
                PrintWriter out = response.getWriter();
                out.print(echostr);
                out.close();
            } else {
                logger.info("这里存在非法请求！");
            }
        } catch (Exception e) {
//            logger.error(e, e);
        }
    }

    @RequestMapping(value = "security", method = RequestMethod.POST)
    // post 方法用于接收微信服务端消息
    public void DoPost(HttpServletRequest request,HttpServletResponse response){
    	try {
//    		logger.info("接受到消息");
    		//接受信息
            InputStream ins = request.getInputStream();
            Map<String, String> map =XmlUtil.xmlToMap(ins);
            for (Map.Entry<String, String> entry : map.entrySet()) {
				System.out.println(entry.getKey()+":"+entry.getValue());
			}
            
            if(!map.containsKey("Event")||!map.get("Event").equals("VIEW")){ 
                //返回信息
                String responseString=WXUtil.returnMessage(request,map);
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.print(responseString);
                out.close();
            }else{
            	//VIEW事件处理
            }
        	logger.info(WXUtil.OpenId);
        	WXUtil.OpenId = map.get("FromUserName");
            
		} catch (Exception e) {
			// TODO: handle exception
//			logger.info(e);
		}
    }
    
    //生成菜单栏
    @RequestMapping("/allUrl")
    public String allUrl(HttpServletRequest request,Model model) throws Exception{
    	model.addAttribute("msg",WXUtil.createMenu(request,WXUtil.AppID,WXUtil.AppSecret));
    	return "test";
    }
    
    @RequestMapping("/h1")
    public String h1(Model model){
//    	model.addAttribute("img","http://fenglpf.free.ngrok.cc/WXDemo/images/face.jpg");
    	model.addAttribute("appId",WXUtil.AppID);
    	return "h1";
    }
    @RequestMapping("/h2")
    public String h2(Model model){
    	model.addAttribute("openId",WXUtil.OpenId);
    	return "h2";
    }
    
    @RequestMapping("/temp")
    public String temp(){
    	return "temp";
    }
    
    @RequestMapping("/img")
    public String img(){
    	return "picture";
    }
    
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Map<String, String> getUserInfo(HttpServletRequest request,Model model) throws Exception{
    	String openId = request.getParameter("openId");
    	Map<String, String> resMap = new HashMap<String, String>();
    	
    	Map<String, String> map = WXUtil.getUserInfo(request, openId);
    	resMap.put("nickName", map.get("nickname"));
    	resMap.put("city",map.get("city"));
    	resMap.put("headimgurl", map.get("headimgurl"));
    	return resMap;
    }
    
    @RequestMapping("/getUserInfo2")
    @ResponseBody
    public Map<String, String> getUserInfo2(HttpServletRequest request,Model model) throws Exception{
    	String openId = WXUtil.OpenId;
    	Map<String, String> resMap = new HashMap<String, String>();
    	
    	Map<String, String> map = WXUtil.getUserInfo(request, openId);
    	resMap.put("nickName", map.get("nickname"));
    	resMap.put("city",map.get("city"));
    	resMap.put("headimgurl", map.get("headimgurl"));
    	return resMap;
    }
    
    
    
    @RequestMapping("/getOpenId")
    @ResponseBody
    public Map<String, String> getOpenId(HttpServletRequest request){
    	String code = request.getParameter("code");
    	String openId = WXUtil.getOpenId(code);
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("openId", openId);
    	return map;
    }
    
    @RequestMapping("/code")
    public String getCode(HttpServletRequest request){
    	String retUrl = request.getParameter("retUrl");
    	String wxUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WXUtil.AppID
    			+"&redirect_uri=" + retUrl + 
    			"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
    	return "redirect:"+wxUrl;
    }
    
    @RequestMapping("/getSignature")
    @ResponseBody
    public Map<String, String> getSignature(HttpServletRequest request,HttpServletResponse response){
    	String url = request.getParameter("url");
    	String jsapi_ticket = WXUtil.getWXDetail(request, WXUtil.AppID, WXUtil.AppSecret).get("JsapiTicket");
    	return WXUtil.getJsapiSign(url, jsapi_ticket);
    }
    
    @RequestMapping("/scan")
    public String scan(Model model){
    	model.addAttribute("appId",WXUtil.AppID);
    	return "scan";
    }
    
    @RequestMapping("/location")
    public String location(Model model){
    	model.addAttribute("appId",WXUtil.AppID);
    	return "wxFront/location";
    }
    
    @RequestMapping(value="/getLocation",produces = {"application/json;charset=UTF-8;"})
    @ResponseBody
    public String getLocation(HttpServletRequest request,HttpServletResponse response){
    	String lat = request.getParameter("lat");
    	String log = request.getParameter("log");
    	String str = LocationUtil.getAddress(log, lat);
    	response.setCharacterEncoding("UTF-8");
    	logger.info(str);
    	return str;
    }
    
    @RequestMapping("/getLocationByIp")
    @ResponseBody
    public String getLocationByIp(HttpServletRequest request){
    	String ip = IpUtil.getClientIp(request);
    	String url = "http://ip.taobao.com/service/getIpInfo.php?ip="+ip;
    	String res = APIMethod.get(url);
    	logger.info(res);
    	return res;
    }
    
    @RequestMapping(value="/getWeather",produces = {"application/json;charset=UTF-8;"})
    @ResponseBody
    public String getWeather(HttpServletRequest request) {
    		String city = request.getParameter("city");
//	    	String city = new String(request.getParameter("city").getBytes("iso-8859-1"), "utf-8");  
	    	logger.info(city);
	    	String url = "http://wthrcdn.etouch.cn/weather_mini?city="+city;
//	    	try {
//				url = URLEncoder.encode(url);
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	    	logger.info(url);
	    	String res = APIMethod.getMethod(url);
	    	logger.info(res);
	    	return res;

    	
    }
    
    @RequestMapping("/toQRCode")
    public String toQRCode(){
    	return "QRCode";
    }
    
    @RequestMapping("/showQRCode")
    @ResponseBody
    public void showQRCode(HttpServletRequest request,HttpServletResponse response){
    	String sn = request.getParameter("sn");
    	try {
			String content = "http://iot.yihuan100.com/bd.php?sn="+sn;
			 
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
			BufferedImage img = MatrixToImageWriter.toBufferedImage(bitMatrix);
			logger.info(img);
			ImageIO.write(img, "gif", response.getOutputStream());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    //测试ajax异步刷新
    
    @RequestMapping("/wxIndex")
    public String wxIndex(){
    	return "wxFront/index";
    }
    
    @RequestMapping("/zx")
    public String zx(){
    	return "wxFront/jspajax/zx";
    }
    
    @RequestMapping("/ajax")
    public String ajax(){
    	return "wxFront/jspajax/ajax";
    }
    
    @RequestMapping("/showContentJson")
    @ResponseBody
    public Object showContentJson(){
    	JSONObject json = new JSONObject();
    	json.put("content", "局部刷新返回json后拼凑html");
    	String detailOK = "ok";
    	json.put("detailOK", detailOK);
    	logger.info(json);
    	return json;
    }
    
    @RequestMapping("/showcontent")  
    public String content(Model model)  
            throws IOException {  
        model.addAttribute("content", "局部刷新返回整个页面");  
        return "wxFront/content";  
    }  
    
    //end 测试ajax异步刷新
    
}
