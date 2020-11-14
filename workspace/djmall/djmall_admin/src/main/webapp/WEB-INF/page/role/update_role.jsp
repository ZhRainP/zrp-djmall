<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/4
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/layer/layer.js"></script>
</head>
<script type="text/javascript">
    function updateRole(){
        $.post("<%=request.getContextPath()%>/role/update",$("#fm").serialize(),function (result){
            if(result.code == 200){
                layer.msg(result.msg);
                parent.location.reload();
            }
        })
    }
</script>
<body>
<form id = "fm">
    <input type="hidden" name="id" value="${role.id}">
    角色名：<input type = "text" name="roleName" value="${role.roleName}"/>
    <input type="button" value="提交" onclick="updateRole()"/>
</form>
</body>
</html>
