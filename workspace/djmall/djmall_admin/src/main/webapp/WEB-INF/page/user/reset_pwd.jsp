<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/9
  Time: 0:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改密码</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/md5-min.js"></script>
</head>
<script type="text/javascript">
    function resetPwd() {
        var password = md5(md5($("#password").val()) + $("#salt").val());
        var confirmPassword = md5(md5($("#confirmPassword").val()) + $("#salt").val());
        $("#password").val(password);
        $("#confirmPassword").val(confirmPassword);
        $.post("<%=request.getContextPath()%>/user/updatePwd",
            $("#fm").serialize(),
            function(result){
                layer.msg("重置成功");
                parent.location.reload();
        })
    }
</script>
<body>
<form id = "fm">
<input type="hidden" name="username" value="${username}"/>
<input type="hidden" name="salt" id="salt" value="${salt}"/>
修改后的密码：<input type="text" name="password" id="password"/><br/>
确认密码：<input type="text" name="confirmPassword" id = "confirmPassword"/><br/>
</form>
<input type="button" value="提交" onclick="resetPwd()"/>
</body>
</html>
