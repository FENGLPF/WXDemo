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
<meta http-equiv="Content-Type" name="viewport" 
	content="width=device-width,initial-scale=1.0,maxinum-scale=1.0,user-scalable=0">
<script type="text/javascript" src="<%=basePath %>js/jquery-3.2.0.min.js"></script>
<title>测试主页</title>
<script>  
function startTime(){  
    var today=new Date();  
    var h=today.getHours();  
    var m=today.getMinutes();  
    var s=today.getSeconds();// 在小于10的数字钱前加一个‘0’  
    m=checkTime(m);  
    s=checkTime(s);  
    document.getElementById('txt').innerHTML=h+":"+m+":"+s;  
    t=setTimeout(function(){startTime();},500);  
}  
function checkTime(i){  
    if (i<10){  
        i="0" + i;  
    }  
    return i;  
}  
</script> 
</head>
<body onload="startTime()">
<div id="txt"></div>
<input type="button"  onclick="jsonhtml()" value="json返回">
<input type="button"  onclick="allhtml()"   value="整页返回"> 
<div id="showdiv">
	
</div>

	
<script type="text/javascript">

	function allhtml() {  
	    document.getElementById("showdiv").innerHTML ="";  
	       $.ajax({  
	           type:"post", 
	           url: '<%=basePath%>test/showcontent', //这里是接收数据的程序  
	           data: 'data=2', //传给后台的数据，多个参数用&连接  
	           dataType: 'html', //服务器返回的数据类型 可选XML ,Json jsonp script html text等  
	           success: function(msg) {  
	               //这里是ajax提交成功后，程序返回的数据处理函数。msg是返回的数据，数据类型在dataType参数里定义！  
	               document.getElementById("showdiv").innerHTML = msg;  
	               //$("#duoduo").innerHTML = msg;  
	           },  
	           error: function() {  
	               alert('对不起失败了');  
	           }  
	       });  
  	 } 

	function jsonhtml(){
		$.ajax({
			dataType:"json",
			url:"<%=basePath%>test/showContentJson",
			success:function(data){
				console.log(data)
				if(data.detailOK=="ok"){
					document.getElementById("showdiv").innerHTML =data.content;
				}
			},
			cache:false
		});
	}
</script>
</body>
</html>