package com.wx.utils;

import java.security.MessageDigest;

public class EncryptUtil {
	public static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',  
        '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'}; 

	public static String getFormattedText(byte[] bytes){
		int len = bytes.length;
		StringBuilder sb = new StringBuilder(len*2);
		//字节密文转换成十六进制的字符串
		for(int i = 0;i < len;i++){
			sb.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);  
            sb.append(HEX_DIGITS[bytes[i] & 0x0f]); 
		}
		return sb.toString();
	}
	
	/**
	 * sha1加密
	 * @param str
	 * @return
	 */
	public static String sha1(String str){
		if(str == null){
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException();
		}
	}
}
