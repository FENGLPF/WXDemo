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
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;">
<script type="text/javascript" src="<%=basePath %>js/jquery-3.2.0.min.js"></script>
<title>微信扫描</title>
</head>
<body>
<div class="center" align="center"><img src="../images/saomiao.png" width="45%" class="saomiao"/></div>
<div align="center">
	<p>扫描结果：</p>
	<textarea id="scanRes" style="width:50%;height: 33px;"></textarea>
</div>
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript">
	var appId = "${appId}";
	$(function(){
		wxpz();
	});
	
	wx.ready(function(){
		wx.checkJsApi({
			jsApiList:['scanQRCode'],
			success:function(res){
			}
		});
		$('.saomiao').click(function(){
			wx.scanQRCode({
				needResult:1,
				scanType:["qrCode","barCode"],
				success:function(res){
					$('#scanRes').val(res.resultStr);
				},
				fail:function(res){
					$('#scanRes').val(res.resultStr);
				}
			});
		});
	});
	
	function wxpz(){
		var req = {
			url:location.href,
		};
		$.ajax({
			"url":"<%=basePath%>test/getSignature",
			datatype:"json",
			contentType:"application/json,charset=utf-8",
			data:req,
			success:function(data){
				wx.config({
					debug:false,
					appId:appId,
					timestamp:data.timestamp,
					nonceStr:data.nonceStr,
					signature:data.signature,
					jsApiList:['checkJsApi','scanQRCode']
				});
			},
		});
	}
</script>
</body>
</html>