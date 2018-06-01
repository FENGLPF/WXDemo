<%@page import="java.net.URLDecoder"%>  
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()
			+path+"/";
	
	String userName = "";
	String userPassword = "";
	Cookie[] userCookies = request.getCookies();
	if(userCookies!=null&&userCookies.length>0){
		for(int i=0; i< userCookies.length; i++){  
	        if(userCookies[i].getName().equals("userFrontName")){  
	            userName = URLDecoder.decode(userCookies[i].getValue(),"utf-8");  
	        }
	        if(userCookies[i].getName().equals("userFrontPassword")){  
	            userPassword = URLDecoder.decode(userCookies[i].getValue(),"utf-8");  
	        } 
	    }  
	}
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=basePath%>js/jquery-3.2.0.min.js"></script>
<script src="<%=basePath%>js/bootstrap/bootstrap.min.js"></script>
<title>前端登录</title>
</head>
<body>
<div style="padding: 100px 100px 10px;">
	<form class="bs-example bs-example-form" role="form" action="../user/userLogin" method="post">
		<div class="input-group">
			<span class="input-group-addon"></span>
			<input type="text" class="form-control" name="userName" placeholder="请输入账号" value="<%=userName%>">
		</div>
		<br>
		<div class="input-group">
			<input type="password" class="form-control" name="userPassword" placeholder="请输入密码" value="<%=userPassword%>">
		</div>
		<br>
		<div>
			<input type="text" name="pageType" value="3" style="display:none;">
		</div>
		<div>
			<button type="submit" class="btn btn-primary">登录</button>
		</div>
	</form>
</div>
<script type="text/javascript">
var resLogin = "${resLogin}";
console.log(resLogin);
$(function(){
	if(resLogin=="error"){
		alert("账号密码错误");
	}
});
</script>
</body>
</html>