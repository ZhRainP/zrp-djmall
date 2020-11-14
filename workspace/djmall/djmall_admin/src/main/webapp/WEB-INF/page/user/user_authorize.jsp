<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/5
  Time: 21:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/layer/layer.js"></script>
</head>
<script type="text/javascript">
    function confirm () {
        $.post(
            "<%=request.getContextPath()%>/user/authorizes",
            $("#fm").serialize(),
            function (result){
                if(result.code == 200){
                    parent.location.reload();
                }

            }
        )
    }
</script>
<body>
<form id="fm">
    <input type="hidden" value="${user.id}" name="userId"/>
    商户：<input type="radio" name = "roleId" value="2" <c:if test="${user.level == 2}">checked</c:if>/>
    管理员：<input type="radio" name = "roleId" value="1" <c:if test="${user.level == 1}">checked</c:if>/>
    <input type="button" value="确认" onclick="confirm()"/>
</form>

</body>
</html>
