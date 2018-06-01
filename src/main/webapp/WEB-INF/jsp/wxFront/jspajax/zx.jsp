<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="javascript">  
      
    var XMLHttpReq;  
        //创建XMLHttpRequest对象         
        function createXMLHttpRequest() {  
            if(window.XMLHttpRequest) { //Mozilla 浏览器  
                XMLHttpReq = new XMLHttpRequest();  
            }  
            else if (window.ActiveXObject) { // IE浏览器  
                try {  
                    XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");  
                } catch (e) {  
                    try {  
                        XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");  
                    } catch (e) {}  
                }  
            }  
        }  
        //发送请求函数  
        function sendRequest() {  
            createXMLHttpRequest();  
            var url = "../ajax.jsp";  
            XMLHttpReq.open("GET", url, true);  
            XMLHttpReq.onreadystatechange = processResponse;//指定响应函数  
            XMLHttpReq.send(null);  // 发送请求  
        }  
        // 处理返回信息函数  
        function processResponse() {  
            if (XMLHttpReq.readyState == 4) { // 判断对象状态  
                if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息  
                    DisplayHot();  
                    setTimeout("sendRequest()", 1000);  
                } else { //页面不正常  
                    window.alert("您所请求的页面有异常。");  
                }  
            }  
        }  
        function DisplayHot() {  
            var name = XMLHttpReq.responseXML.getElementsByTagName("name")[0].firstChild.nodeValue;  
            var count = XMLHttpReq.responseXML.getElementsByTagName("count")[0].firstChild.nodeValue;  
            document.getElementById("product").innerHTML = name;      
            document.getElementById("count").innerHTML = count;   
        }  
      
      
</script>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body onload ="sendRequest()">  
    <table style="BORDER-COLLAPSE: collapse" borderColor=#111111 cellSpacing=0 cellPadding=0 width=200    bgColor=#f5efe7 border=0>  
      
    <TR>  
       <TD align=middle bgColor=#dbc2b0 height=19 colspan="2"><B>无线传感网</B> </TD>  
    </TR>  
    <tr>  
       <td height="20"> 传感器：</td>  
       <td height="20" id="product"> </td>  
    </tr>  
    <tr>  
       <td height="20">传感器个数：</td>  
       <td height="20" id="count"> </td>  
    </tr>  
    </table> 
</body>  
</html>