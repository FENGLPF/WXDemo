<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+
			":"+request.getServerPort()+path+"/";
%>
<meta http-equiv="Content-Type" name="viewport" 
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;">
<script type="text/javascript" src="<%=basePath %>js/jquery-3.2.0.min.js"></script>
<style type="text/css">
	
	input{
		margin:8px auto;
		padding:8px;
		font-size: 15px;
	}
}
</style>
<title>二维码生成</title>
</head>
<body >
<div class="entry" align="center">
	<div>
		<input type="text" placeholder="请输入sn号" id="snText" name="ckSN"/>
	</div>
	<div>
		<input type="button" value="生成二维码" onclick="createQRCode()">
	</div>
</div>


<div class="showDiv" style="display: none">
	<div class="QRImg" align="center">
		
	</div>
	<div align="center">
		<h3><label class="snlab"></label></h3>
	</div>
	<div class="p_QRCode" align="center">
		<input type="button" value="打印" onclick="printQRCode()"/>
	</div>
</div>
<script type="text/javascript">

	if(IsPC()){  
		$(".p_QRCode").show(); 
	}else{   
		$(".p_QRCode").hide(); 
	}  
	  
	function IsPC(){    
		var userAgentInfo = navigator.userAgent;    
		var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");    
		var flag = true;    
		for (var v = 0; v < Agents.length; v++) {    
		    if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = false; break; }    
		}    
		return flag;    
	}  
	
	function createQRCode(){
		var sn = document.getElementById("snText").value;
		if(sn==""||sn==null){
			alert("sn不能为空");
		}
		else{
			$(".showDiv").show();
			$(".imgClass").remove();
			var url = "<%=basePath%>test/showQRCode?sn="+sn;
			var addXHTML = "<img class='imgClass' style='max-width:100%;overflow:hidden;' alt='' src='"+url+"'/>";
			$(".QRImg").append(addXHTML);
			$(".snlab").html(sn);
		}
	}
	
	function printQRCode(){
		
		if($(".snlab").html()==""||$(".snlab").html()==null){
			alert("无可打印内容");
		}
		else{
			$(".entry").hide();
			$(".p_QRCode").hide(); 
			window.print();
			window.close();
			if(IsPC()){  
				$(".p_QRCode").show(); 
			}
			$(".entry").show();
		}
	}
	
</script>
</body>
</html>