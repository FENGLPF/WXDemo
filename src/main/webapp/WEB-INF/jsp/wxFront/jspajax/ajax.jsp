<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  
    //设置输出信息的格式及字符集  
    response.setContentType("text/xml; charset=UTF-8");  
    response.setHeader("Cache-Control","no-cache");  
    out.println("<response>");  
      
    for(int i=0;i<2;i++){  
    out.println("<name>"+(int)(Math.random()*10)+  
       "号传感器</name>");  
    out.println("<count>" +(int)(Math.random()*100)+ "</count>");  
    }  
    out.println("</response>");  
    out.close();  
    %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>