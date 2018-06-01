package com.wx.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;

public class APIMethod {
	
	private static Logger logger = Logger.getLogger(APIMethod.class);
	
	
	public static String getMethod(String getUrl){
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(getUrl);
			try {
				HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
				InputStream urlStream = new GZIPInputStream(httpURLConnection.getInputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(urlStream,"UTF-8"));
				String line = "";
				while((line=br.readLine())!=null){
					sb.append(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = sb.toString();
		return result;
	}
	
	
	/** 
	 * 向指定URL发送GET方法的请求 
	 *  
	 */  
	public static String get(String url) {  
	    BufferedReader in = null;  
	    try {  
	        URL realUrl = new URL(url);  
	        // 打开和URL之间的连接  
	        URLConnection connection = realUrl.openConnection();  
	        // 设置通用的请求属性  
	        connection.setRequestProperty("accept", "*/*");  
	        connection.setRequestProperty("connection", "Keep-Alive");  
	        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
	        connection.setConnectTimeout(5000);  
	        connection.setReadTimeout(5000);  
	        // 建立实际的连接  
	        connection.connect();  
	        // 定义 BufferedReader输入流来读取URL的响应  
	        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));  
	        StringBuffer sb = new StringBuffer();  
	        String line;  
	        while ((line = in.readLine()) != null) {  
	            sb.append(line);  
	        }  
	        return sb.toString();  
	    } catch (Exception e) {  
	        logger.error("Exception occur when send http get request!", e);  
	    }  
	    // 使用finally块来关闭输入流  
	    finally {  
	        try {  
	            if (in != null) {  
	                in.close();  
	            }  
	        } catch (Exception e2) {  
	            e2.printStackTrace();  
	        }  
	    }  
	    return null;  
	}
	
	/**
	 * 发送HttpPost请求 
	 * @param strURL
	 * @param params
	 * @return
	 */
	public static String jsonPost(String strURL, Map<String, String> params) {  
	    try {  
	        URL url = new URL(strURL);// 创建连接  
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
	        connection.setDoOutput(true);  
	        connection.setDoInput(true);  
	        connection.setUseCaches(false);  
	        connection.setInstanceFollowRedirects(true);  
	        connection.setRequestMethod("POST"); // 设置请求方式  
	        connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
	        connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式  
	        connection.connect();  
	        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码  
	        out.append(JsonUtils.objectToJsonStr(params));  
	        out.flush();  
	        out.close();  

	        int code = connection.getResponseCode();  
	        InputStream is = null;  
	        if (code == 200) {  
	            is = connection.getInputStream();  
	        } else {  
	            is = connection.getErrorStream();  
	        }  

	        // 读取响应  
	        int length = (int) connection.getContentLength();// 获取长度  
	        if (length != -1) {  
	            byte[] data = new byte[length];  
	            byte[] temp = new byte[512];  
	            int readLen = 0;  
	            int destPos = 0;  
	            while ((readLen = is.read(temp)) > 0) {  
	                System.arraycopy(temp, 0, data, destPos, readLen);  
	                destPos += readLen;  
	            }  
	            String result = new String(data, "UTF-8"); // utf-8编码  
	            return result;  
	        }  

	    } catch (IOException e) {  
	        logger.error("Exception occur when send http post request!", e);  
	    }  
	    return "error"; // 自定义错误信息  
	}
}
