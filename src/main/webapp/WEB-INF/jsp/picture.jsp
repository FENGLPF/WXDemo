<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
 String path = request.getContextPath();
 String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
   + path + "/";
%>
<meta http-equiv="Content-Type" name="viewport" 
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" >
<title>显示图文</title>
<style type="text/css">
	img{
		max-width: 100%;
    	height: auto;
	}
</style>
</head>
<body>
<img src="<%=basePath%>images/1.jpg"/>
</body>
</html>