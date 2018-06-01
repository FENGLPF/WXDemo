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
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>获取地理位置</title>
<script type="text/javascript" src="<%=basePath %>js/jquery-3.2.0.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>  
<script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>  
</head>
<body>
<form action="">
	<div class="local">		
	</div>
</form>
<div class="d2">
	<ul class="weather">
	</ul>
</div>

<script type="text/javascript">
	var appId = '${appId}';
	var req = {
		url:location.href,
	};
	$(function(){
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
					jsApiList:['checkJsApi','openLocation', 'getLocation']
				});
			},
		});
		 wx.checkJsApi({  
	        jsApiList: ['getLocation'], // 需要检测的JS接口列表，所有JS接口列表见附录2,  
	        success: function(res) {  
	        	alert("checkJsApi");
	            if (res.checkResult.getLocation == false) {    
	                alert('你的微信版本太低，不支持微信JS接口，请升级到最新的微信版本！');    
	                return;    
	         	}  
	        }  
		 });  
	});
	
	var latitude;    
    var longitude;    
    var speed;    
    var accuracy;   
    wx.ready(function(){  
        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。  
        wx.getLocation({    
            success : function(res) {    
                latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90    
                longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。    
                speed = res.speed; // 速度，以米/每秒计    
                accuracy = res.accuracy; // 位置精度    
                getLocation(latitude,longitude);
            },    
            cancel : function(res) {    
                alert('未能获取地理位置');    
            }  
        });    
    });  
    
    function getLocation(lat,log){
    	$.ajax({
    		"url":"../test/getLocation",
    		datatype:"json",
    		contentType:"application/json;charset=utf-8",
    		data:{"lat":lat,"log":log},
    		success:function(res){
    			var data = res.addrList;
    			var cityArray = data[0].admName.split(",");
    			var city = cityArray[0]+cityArray[1];
    			var xhtml = "<li>当前地址:"+city+"</li>";
    			$('.local').append(xhtml);
    			var cityName = cityArray[1];
    			console.log(cityName);
    			getWeather(cityName);
    		}
    	});
    }
    
    function getWeather(cityName){
    	
    	$.ajax({
    		"url":"<%=basePath%>test/getWeather",
    		datatype:"json",
    		contentType:"application/json;charset=utf-8",
    		data:{"city":cityName},
    		success:function(res){
    			var data = res.data.forecast;
    			var xhtml = "<li>"+data[0].date+"</li>"+
    				"<li>"+data[0].high+"~"+data[0].low+"</li>"+
    				"<li>"+data[0].type+"</li>";
    			$('.weather').append(xhtml);
    			
    		}
    	});
    }
      
    wx.error(function(res){  
        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。  
        alert("验证出错");    
    });

</script>
</body>
</html>