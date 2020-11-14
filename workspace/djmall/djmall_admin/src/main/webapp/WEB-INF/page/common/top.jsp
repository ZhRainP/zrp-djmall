<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
		<script type="text/javascript" src = "<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
		<script>
			setInterval("document.getElementById('datetime').innerHTML=new Date().toLocaleString();", 1000);
			function backLogin(){
				location.href = "<%=request.getContextPath()%>/user/toLogin"
			}
		</script>
	</head>
	<body>
		<h1 align="center">欢迎${USER.username}登陆系统</h1>
		<div id = "datetime" align = "right" style = "color:#8c3ffd;font-size:20px"></div>
	<input type="button" value="退出登录" onclick="backLogin()">
	</body>
</html>