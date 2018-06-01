package com.wx.test;

import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.wx.controller.TestController;
import com.wx.utils.APIMethod;
import com.wx.utils.WeatherServiceUtil;

public class ApiTest {
	
	private static Logger logger = Logger.getLogger(ApiTest.class);
	
	@Test
	public void testApi(){
		String string = APIMethod.get("http://wechat.yihuan100.com/api/select_UpdateMateid?mateid=35710&endtime=");
		System.out.println(string);
	}
	
	@Test
	public void testWeatherService(){
//		WeatherServiceUtil.getWeatherXml("广州", 3);
		String url = "http://wthrcdn.etouch.cn/weather_mini?citykey=101010100";
		url = "http://wthrcdn.etouch.cn/weather_mini?city=东莞市";
		String str1 = APIMethod.getMethod(url);
		logger.info(str1);
		try {
			String value = new String(str1.getBytes("ISO-8859-1"),"utf-8");
			logger.info(value);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String add = getAdd("116.3039", "39.97646");  
		logger.info(add);
	}
	
	@Test
	public void testWeather(){
		TestController testController = new TestController();
		HttpServletRequest request = null;
		testController.getWeather(request);
	}
	
	public static String getAdd(String log, String lat ){  
        //lat 小  log  大  
        //参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)  
        String urlString = "http://gc.ditu.aliyun.com/regeocoding?l="+lat+","+log+"&type=010";  
        String res = "";     
        try {     
            URL url = new URL(urlString);    
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();    
            conn.setDoOutput(true);    
            conn.setRequestMethod("POST");    
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));    
            String line;    
           while ((line = in.readLine()) != null) {    
               res += line+"\n";    
         }    
            in.close();    
        } catch (Exception e) {    
            System.out.println("error in wapaction,and e is " + e.getMessage());    
        }   
        System.out.println(res);  
        return res;    
    } 
}
