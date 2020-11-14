<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery.validate.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/additional-methods.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/md5-min.js"></script>
    <style>
        .error {
            color: red;
        }
    </style>
</head>
<script type="text/javascript">

    //判断当前窗口路径与加载路径是否一致
    if (window.top.document.URL != document.URL) {
        //讲窗口路径与加载路径同步
        window.top.location = document.URL;
    }

    $(function () {
        $("#fm").validate({
            rules: {
                userName: {
                    required: true,
                },
                pwd: {
                    required: true,
                }
            },
            messages: {
                userName: {
                    required: "请输入账号",
                },
                password: {
                    required: "请输入密码",
                },
            },
            submitHandler: function (data) {
                var password = md5(md5($("#password").val()) + $("#salt").val());
                $("#password").val(password);
                $.post(
                    "<%=request.getContextPath()%>/user/login",
                    $("#fm").serialize(),
                    function (data) {
                        if (data.code == 200) {
                            layer.msg(data.msg, {time: 2000});
                            window.location.href = "<%=request.getContextPath()%>/common/toIndex";
                        } else if(data.code == -5){
                            $("#password").val("");
                            layer.open({
                                type: 2,
                                title: '修改密码',
                                shadeClose: true,
                                shade: 0.8,
                                area: ['380px', '90%'],
                                content:"<%=request.getContextPath()%>/user/toResetPwd?username="+$("#username").val(),
                            })
                        } else {
                            layer.msg(data.msg, {time: 2000});
                        }
                    }
                )
            }
        })
    })
    function register (){
        layer.open({
            type: 2,
            title: '注册',
            shadeClose: true,
            shade: 0.8,
            area: ['380px', '90%'],
            content:"<%=request.getContextPath()%>/user/toRegister"
        })
    }
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
<form id="fm" algin="center">
    <input type="hidden" name="salt" id="salt"/><br/>

    <label for="userName" >账号</label>
    <input type="text" name="username" id="username" onblur="findSalt(this.value)"/><br/>
    <label for="password">密码</label>
    <input type="password" name="password" id="password"/><br/>
    <input type="button" value="还没账号？点我去注册" onclick="register()"/>          |
    <input type="submit" value="登录"/>
</form>
</body>
</html>
