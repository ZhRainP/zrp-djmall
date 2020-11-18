<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/16
  Time: 14:32
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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css" media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/md5-min.js"></script>
</head>
<script type="text/javascript">

    // 修改密码
    function updatePwd() {
        var salt = "${salt}";
        $.post("<%=request.getContextPath()%>/user/forgetPwd", {
            "phone":  $("#phone").val(),
            "smsCode" : $("#smsCode").val(),
            "password" : md5(md5($("#pwd").val()) + salt),
            "confirmPassword" : md5(md5($("#pwd2").val()) + salt),
            "salt": salt
        }, function (result) {
            if (result.code == 200) {
                window.location.href = "<%=request.getContextPath()%>/user/toLogin";
            } else {
                layer.msg(result.msg);
                $("#pwd").val("");
                $("#pwd2").val("");
            }
        });
    }

    // 发送短信验证码
    function sendSms(obj) {
        var timeOut = 60;
        var interval;
        $.post("<%=request.getContextPath()%>/user/sendSms", {
            "phone" : $("#phone").val(),
            "verifyCode" : $("#verifyCode").val()
        },function (result) {
            if (result.code == 200) {
                $(obj).attr("disabled", "disabled");
                interval = setInterval(function () {
                    $(obj).html("在("+(timeOut--)+")后,重新发送");
                    if (timeOut < 0) {
                        clearInterval(interval);// 停止定时
                        $(obj).html("发送短信验证码");
                        $(obj).removeAttr("disabled");
                    }
                }, 1000);
            } else {
                layer.msg(result.msg);
            }
        });
    }
</script>
<body>
<form id="forgetForm">
    手机号：<input type="text" name="phone" id="phone"/><br/>
    图形验证码：<input type="text" name="verifyCode" id="verifyCode"/>
    <img src="<%=request.getContextPath()%>/user/getVerifyCode" onclick="this.src='<%=request.getContextPath()%>/user/getVerifyCode?d='+Math.random();"/>
    <br/>
    短信验证码：<input type="text" name="smsCode" id="smsCode"/>
    <button type="button" onclick="sendSms(this)">发送短信验证码</button>
    <br/>
    新密码：<input type="password" name="password" id="pwd"/><br/>
    确认新密码：<input type="password" name="confirmPassword" id="pwd2"/>
    <button type="button" onclick="updatePwd()">确认修改</button>
</form>
</body>
</html>
