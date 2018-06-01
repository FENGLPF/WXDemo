package com.wx.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.persistence.criteria.CriteriaBuilder.Case;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wx.model.User;

public class CookieUtil {
	public static void addCookie(int pageType,User user,HttpServletRequest request,HttpServletResponse response) {
		if(user!=null){
			String userName = user.getUsername();
			String userPassword = user.getUserpassword();
			String strName = "";
			String strPassword = "";
			int userType = pageType;
			switch(userType){
			case 0:
				break;
			case 1:
				break;
			case 2:
				strName = "userBackName";
				strPassword = "userBackPassword";
				break;
			case 3:
				strName = "userFrontName";
				strPassword = "userFrontPassword";
				break;
			default:
				break;
			}
			try {
				Cookie nameCookie = new Cookie(strName, 
						URLEncoder.encode(userName,"utf-8"));
				nameCookie.setPath(request.getContextPath()+"/");
				nameCookie.setMaxAge(7*24*60*60);
				
				Cookie passwordCookie = new Cookie(strPassword, 
						URLEncoder.encode(userPassword,"utf-8"));
				passwordCookie.setPath(request.getContextPath()+"/");
				passwordCookie.setMaxAge(7*24*60*60); 
				
				response.addCookie(passwordCookie);
				response.addCookie(nameCookie);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
