package com.wx.controller;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wx.model.User;
import com.wx.service.UserService;
import com.wx.utils.CookieUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	
	@RequestMapping("/front")
	public String frontPage(){
		return "front/front_login";
	}
	
	@RequestMapping("/back")
	public String backPage(Model model){
		return "back/back_login";
	}
	
	@RequestMapping("/userLogin")
	public String backLogin(HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			request.setCharacterEncoding("UTF-8");
			String userName = request.getParameter("userName");
			String userPassword = request.getParameter("userPassword");
			int pageType = Integer.parseInt(request.getParameter("pageType"));
			logger.info(pageType);
			User user = userService.login(userName, userPassword);
			String successPage = "";
			String errorPage = "";
			switch(pageType){
			case 0:
				break;
			case 1:
				break;
			case 2:
				successPage = "back/back_index";
				errorPage = "back/back_login";
				break;
			case 3:
				successPage = "front/success";
				errorPage = "front/front_login";
				break;
			default:
				successPage = "404";
				errorPage = "404";
				break;
			}
			
			if(user!=null&&successPage!=""&&errorPage!=""){
				int userType =  user.getUsertype();
				int ckPageType = 0;
				switch(userType){
				case 0:
					ckPageType=2;
					break;
				case 1:
					ckPageType=2;
					break;
				case 2:
					ckPageType=2;
					break;
				case 3:
					ckPageType=3;
					break;
				default:
					break;
				}
				if(ckPageType==pageType){
					CookieUtil.addCookie(pageType, user, request, response);
					return successPage;
				}
				else{
					model.addAttribute("resLogin","error");
					return errorPage;
				}
			}
			else {
				model.addAttribute("resLogin","error");
				return errorPage;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
