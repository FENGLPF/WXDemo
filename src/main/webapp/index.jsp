<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()
			+path+"/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=basePath %>js/jquery-3.2.0.min.js"></script>
<title>主页</title>
</head>
<body>
<form class="front" action="<%=basePath%>user/front" method="post">
	<input type="submit"  value="前往前端"/> 
</form>
<form class="back" action="<%=basePath%>user/back" method="post">
	<input type="submit" value="前往后台"/> 
</form>
<script type="text/javascript">
	
</script>

</body>
</html>