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

<script src="<%=basePath%>js/jquery-3.2.0.min.js"></script>
<script src="<%=basePath%>js/bootstrap/bootstrap.min.js"></script>
<title>后台主页</title>
<style type="text/css">

* {margin:0;padding:0;}
         #mobile-menu {position:fixed;top:0;left:0;width:220px;height:100%;background-color:#373737;z-index:9999;}
         a:hover ,a:focus{text-decoration:none}
        .mobile-nav ul li a {color:gray;display:block;padding:1em 5%;    border-top:1px solid #4f4f4f;border-bottom:1px solid #292929;transition:all 0.2s ease-out;cursor:pointer;#mobile-menu {position:fixed;top:0;left:0;width:220px;height:100%;background-color:#373737;z-index:9999;transition:all 0.3s ease-in;}}
        .mobile-nav ul li a:hover {background-color: #23A1F6;color: #ffffff;}
        .show-nav {transform:translateX(0);}
        .hide-nav {transform:translateX(-220px);} /*侧滑关键*/
        .mobile-nav-taggle {height:35px;line-height:35px;width:35px;background-color:#23A1F6;color:#ffffff;display:inline-block;text-align:center;cursor:pointer}
        .nav.avbar-inverse{position:relative;}
        .nav-btn {position:absolute;right:20px;top:20px;}

</style>
</head>
<body>
<!--手机导航栏-->
    <div id="mobile-menu" class="mobile-nav visible-xs visible-sm">
        <ul>
            <li><a href="#">首页</a></li>
            <li><a href="#">Java</a></li>
            <li><a href="#">SVN</a></li>
            <li><a href="#">iOS</a></li>
        </ul>
    </div>
    <!--pc导航栏-->
    <nav class="navbar-inverse visible-lg visible-md" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">菜鸟教程</a>
            </div>
            <div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">iOS</a></li>
                    <li><a href="#">SVN</a></li>
                    <li><a href="#" class="dropdown-toggle" data-toggle="dropdown">Java</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <!--手机导航栏侧滑-->
    <div class="nav-btn visible-xs visible-sm">
        <a href="#" class="mobile-nav-taggle" id="mobile-nav-taggle">
            <span class="glyphicon glyphicon-align-justify"></span>
        </a>
    </div>
    
    
    <script type="text/javascript">
    $("#mobile-nav-taggle").click(function () {
        var mobileMenu = $("#mobile-menu");
        if (mobileMenu.hasClass("show-nav")) {
            setTimeout(function () {
                mobileMenu.addClass("hide-nav").removeClass("show-nav");
            }, 100)
        }
        else {
            setTimeout(function (){
                mobileMenu.addClass("show-nav").removeClass("hide-nav");
            }, 100)
        }
    })
    </script>
    
</body>
</html>