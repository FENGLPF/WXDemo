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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=basePath %>js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/testjs/core.js"/></script>
<title>页面2</title>
<style type="text/css">
	input.userInput{
		font-size: 50px;
		width: 50%
	}
	table.userTable{
		font-size: 50px
	}
</style>
</head>
<body>
<h1>这是2页面</h1>
<form name="form1" method="post">
	<input class="userInput" type="button" value="获取用户信息" onclick="userSub()"/>
</form>
<form id="form2">
	<table class="userTable" align="center">
		
	</table>
</form>
<script type="text/javascript">
	function userSub(){ 
		$.ajax({
			"url":"<%=basePath%>test/getUserInfo2",
			 datatype: "json",
	         contentType: "application/json; charset=utf-8",
	         success:function(data){
	        	 var xhtml='<tr><td>昵称:</td><td>'+data.nickName+
	        	 		'</td></tr><tr><td>城市:</td><td>'+data.city+
	        	 		'</td></tr><tr><td>头像:</td><td><img src='+data.headimgurl+
	        	 		'/></td></tr>';
	        	 $('.userTable').append(xhtml);
	         },
		});
	}
</script>
</body>
</html>