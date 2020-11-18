<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/17
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/js/token.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/js/cookie.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/md5-min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css" media="all">

</head>
<script type="text/javascript">
    $(function () {
        //登陆按钮
        $("#loginBtn").click(function () {
            var password = md5(md5($("#password").val()) + $("#salt").val());
            $("#password").val(password);
            $.post("<%=request.getContextPath()%>/user/login",$("#fm").serialize(),function(result){
                if(result.code == 200){
                    //存cookie
                    cookie.set("TOKEN", result.data.token);
                    cookie.set("NICK_NAME", result.data.nickName);
                    //刷新父页面
                    parent.window.location.reload();
                } else {
                    layer.msg(result.msg);
                    $(":password").val("");
                }
            })
        })
    })
    /* 获取盐 */
    function findSalt(username){
        if(username != null && username != undefined){
            $.post("<%=request.getContextPath()%>/user/findSalt",
                {"username":username},
                function (data) {
                    if(data.code == 200){
                        if(data.data != null){
                            $("#salt").val(data.data);
                        }
                    }
                })
        }
    }
</script>
<body>
<form id="fm">
    <input type="hidden" name="salt" id="salt"/>
    用户名：<input type="text" name="username" id = "username" onblur="findSalt(this.value)"/><br>
    <br/>
    密码：&nbsp;&nbsp;&nbsp;<input type="password" name="password" id = "password"/><br>
    <br>
    <input type="button" value="还没账号 ？点我去注册" onclick="register()" class="layui-btn layui-btn-xs"/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" id="loginBtn" class="layui-btn layui-btn-xs">登录</button>
</form>
</body>
</html>
