package com.wx.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class WeatherServiceUtil {
	
	private static Logger logger = Logger.getLogger(WeatherServiceUtil.class);
	
	 /** 
     * 获取今天、明天和后天的天气预报信息并返回 
     * @param source 
     * @return 
     */  
    public static String getWeatherInfo(String source) {  
        StringBuffer buffer = new StringBuffer();  
        buffer.append(source).append(" 今明后三天天气情况如下：\n\n");  
        for(int i=0;i<3;i++) {  
            String weatherXml = getWeatherXml(source,i);  
            if(null==weatherXml || "".equals(weatherXml))  
                return "";  
            String status1 = "";  
            String direction1 = "";  
            String temperature1 = "";  
            String temperature2 = "";   
            String savedate_weather = "";  
            String ssd_l = "";  
            String yd_s = "";  
            Pattern p = Pattern.compile("(.*)(<status1>)(.*?)(</status1>)(.*)");  
            Matcher m = p.matcher(weatherXml);  
            if(m.matches())  
                status1 = m.group(3);  
            if(null==status1 || "".endsWith(status1))  
                return "";  
            p = Pattern.compile("(.*)(<direction1>)(.*?)(</direction1>)(.*)");  
            m = p.matcher(weatherXml);  
            if(m.matches())  
                direction1 = m.group(3);  
            p = Pattern.compile("(.*)(<temperature1>)(.*?)(</temperature1>)(.*)");  
            m = p.matcher(weatherXml);  
            if(m.matches())  
                temperature1 = m.group(3);  
            p = Pattern.compile("(.*)(<temperature2>)(.*?)(</temperature2>)(.*)");  
            m = p.matcher(weatherXml);  
            if(m.matches())  
                temperature2 = m.group(3);  
            p = Pattern.compile("(.*)(<savedate_weather>)(.*?)(</savedate_weather>)(.*)");  
            m = p.matcher(weatherXml);  
            if(m.matches())  
                savedate_weather = m.group(3);  
            p = Pattern.compile("(.*)(<ssd_l>)(.*?)(</ssd_l>)(.*)");  
            m = p.matcher(weatherXml);  
            if(m.matches())  
                ssd_l = m.group(3);  
            p = Pattern.compile("(.*)(<yd_s>)(.*?)(</yd_s>)(.*)");  
            m = p.matcher(weatherXml);  
            if(m.matches())  
                yd_s = m.group(3);  
            buffer.append(savedate_weather).append("\n").append(status1).append(" ").append(direction1).append(" ").append(temperature2)  
                .append("°-").append(temperature1).append("° ").append(ssd_l).append("\n").append("温馨提示：").append(yd_s).append("\n\n");  
        }  
        return (null==buffer?"":buffer.toString());  
    }  
	
	/**
	 * 获取天气xml信息返回
	 * @param city
	 * @param day
	 * @return
	 */
	public static String getWeatherXml(String city,int day){
		String requestUrl = "http://php.weather.sina.com.cn/xml.php?"
				+ "city="+urlEncode(city, "GBK")+"&password=DJOYnieT8234jlsK&day="+day;
		logger.info(requestUrl);
		String weatherXml = APIMethod.get(requestUrl);
		logger.info(weatherXml);
		return weatherXml;
	}
	
	/**
	 * 编码
	 * @param encodeStr
	 * @param encodeType
	 * @return
	 */
	public static String urlEncode(String encodeStr,String encodeType){
		String result = encodeStr;
		try {
			result = URLEncoder.encode(result,encodeType);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
