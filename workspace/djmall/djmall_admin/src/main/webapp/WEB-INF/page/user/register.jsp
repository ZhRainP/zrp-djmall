<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/6
  Time: 11:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery.validate.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/additional-methods.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/md5-min.js"></script>
</head>
<script type="text/javascript">
    $(function () {
        $("#fm").validate({
            rules: {
                userName: {
                    required: true,
                },
                password: {
                    required: true,
                },
                confirmPassword: {
                    required:true,
                    equalTo:"#password"
                },
            },
            messages: {
                userName: {
                    required: "请输入账号",
                },
                password: {
                    required: "请输入密码",
                },
                confirmPassword: {
                    required:"不能为空",
                    equalTo:"密码不一致"
                },
            },
            submitHandler: function (data) {
                var password = md5(md5($("#password").val()) + $("#salt").val());
                var confirmPassword = md5(md5($("#confirmPassword").val()) + $("#salt").val());
                $("#password").val(password);
                $("#confirmPassword").val(confirmPassword);
                $.post(
                    "<%=request.getContextPath()%>/user/register",
                    $("#fm").serialize(),
                    function (data) {
                        if (data.code == 200) {
                            layer.msg(result.msg, {
                                skin: 'layui-layer-molv' //样式类名
                                ,closeBtn: 0
                                ,shade: 0.3
                            }, function(){parent.location.href = "<%=request.getContextPath()%>/user/toLogin";});
                            /*layer.msg(data.msg);
                            parent.location.href = "<%=request.getContextPath()%>/user/toLogin";*/
                        } else {
                            layer.msg(data.msg, {time: 2000});
                        }
                    }
                )
            }
        })
    })
</script>
<body>
<style>
    .error {
        color: red;
    }
</style>
</head>
<body>
<form id="fm" algin="center">
    <input type="hidden" name="salt" value="${salt}" id="salt"/>
    <label for="userName">账号</label>
    <input type="text" name="username" id="username"/><br/>
    <label for="password">密码</label>
    <input type="text" name="password" id="password"/><br/>
    <label for="confirmPassword">确认密码</label>
    <input type="text" name = "confirmPassword" id="confirmPassword"/><br>
    昵称：<input type="text" name="nickName" id="nickName"/><br/>
    级别：<input type="radio" name="level" value="2"/>商户
        <input type="radio" name="level" value="1"/>管理员<br/>

    手机号：<input type="text" name="phone" id="phone"/><br/>
    邮箱：<input type="text" name="mail" id="mail"/><br/>
    性别：<input type="radio" name="sex" value = "SEX_MAN"/>男
        <input type="radio" name="sex" value="SEX_LADY"/>女<br/>
    <input type="submit" value="注册"/>
</form>
</body>
</body>
</html>
