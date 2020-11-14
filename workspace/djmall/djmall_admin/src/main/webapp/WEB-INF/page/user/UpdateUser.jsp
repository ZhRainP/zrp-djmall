<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/8
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery.validate.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/additional-methods.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/md5-min.js"></script>
</head>
<body>
<script type="text/javascript">
    function updateUser() {
        $.post("<%=request.getContextPath()%>/user/updateUser",
            $("#fm").serialize(),
            function(result){
                parent.location.href = "<%=request.getContextPath()%>/user/toList";
             })
    }
</script>
<form id="fm">
    <input type="text" name="id" value="${user.id}"/>
    用户名：<input type="text" name="username" value="${user.username}"/><br/>
    手机号：<input type="text" name="phone" value="${user.phone}"/><br/>
    邮箱：<input type="text" name="mail" value="${user.mail}"/><br/>
    性别：<input type="radio" name="sex" value = "SEX_MAN" <c:if test="${user.sex == 'SEX_MAN'}">checked</c:if>/>男
    <input type="radio" name="sex" value="SEX_LADY" <c:if test="${user.sex == 'SEX_LADY'}">checked</c:if>/>女<br/>
    <input type="button" value="查询" onclick="updateUser()" class="btn btn-primary"/>
</form>
</body>
</html>
