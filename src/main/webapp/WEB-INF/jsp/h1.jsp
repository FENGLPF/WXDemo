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
<title>页面1</title>
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
<h1>这是1页面</h1>
<form name="form1" method="post">
	<input class="userInput" type="button" value="获取用户信息" onclick="userSub()"/>
</form>
<form id="form2">
	<table class="userTable" align="center">
		
	</table>
</form>

<script type="text/javascript">
	var code="";
	var openId=sessionStorage.getItem("openId");
	code = core.getQueryString('code');
	var retUrl = "<%=basePath%>test/h1";
	retUrl = retUrl.replace(":80","");
	var appId = "${appId}";
	$(function(){
		if(code==""||code==null||code=="null"||code=="undefine"){
			if(openId==""||openId==null||openId=="null"||openId=="undefine"){
				var wxUrl = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid='+appId
				+'&redirect_uri=' + retUrl + 
				'&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect';
				core.winJump(wxUrl);
			}
		}
		if(openId==""||openId==null||openId=="null"||openId=="undefine"){
			$.ajax({
				"url":"<%=basePath%>test/getOpenId",
				datatype:"json",
				contentType:"application/json;charset=utf-8",
				data:{"code":code},
				success:function(data){
					openId=data.openId;
					sessionStorage.setItem("openId", openId);
				}
			});
		}
		code="";
	});
	function userSub(){ 
		$.ajax({
			"url":"<%=basePath%>test/getUserInfo",
			 datatype: "json",
	         contentType: "application/json; charset=utf-8",
	         data:{"openId":openId},
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