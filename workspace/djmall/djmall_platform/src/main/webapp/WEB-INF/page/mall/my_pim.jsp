<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/21
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/js/token.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/js/cookie.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css" media="all">
</head>
<script type="text/javascript">
    function toUpdate () {
        $.post("<%=request.getContextPath()%>/user/updatePim",
            $("#fm").serialize(),
            function(result){
                if(result.code == 200){
                    parent.location.reload();
                }
        })
    }
</script>
<body>
<form id = "fm">
<input type="text" name="id" value="${user.id}">
昵称：<input type="text" name="nickName" value="${user.nickName}"/><br/>
性别：<input type="radio" name="sex" <c:if test="${user.sex == 'SEX_MAN'}">checked</c:if> value="${user.sex}"/>男
<input type="radio" name="sex" <c:if test="${user.sex == 'SEX_LADY'}">checked</c:if> value="${user.sex}"/>女<br/>
邮箱：<input type = "text" name="mail" value="${user.mail}"/><br/>
<input type="button" value = "修改" onclick="toUpdate()" class="layui-btn layui-btn-xs"/>
</form>
</body>
</html>
