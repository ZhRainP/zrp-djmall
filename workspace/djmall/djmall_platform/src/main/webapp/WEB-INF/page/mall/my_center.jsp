<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/20
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人中心</title>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/js/token.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/js/cookie.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css" media="all">
</head>
<script type="text/javascript">
    function toInsertAddress() {
        if (check_login()) {
            debugger
            window.location.href = "<%=request.getContextPath()%>/user/toAddressList?TOKEN=" + getToken();
        } else {
            layer.open({
                type: 2,
                title: '登录',
                shadeClose: true,
                shade: 0.8,
                anim: 1,
                area: ['380px', '90%'],
                content: "<%=request.getContextPath() %>/user/toLogin" //iframe的url
            });
        }
    }

    function toPIM() {
        layer.open({
            type: 2,
            title: '个人信息管理',
            shadeClose: true,
            shade: 0.8,
            anim: 1,
            area: ['380px', '90%'],
            content: "<%=request.getContextPath() %>/user/toPim?TOKEN=" + getToken(),
        });
    }
</script>
<body>
<ul class="layui-nav layui-bg-blue" align="right">
    <li class="layui-nav-item">
        <a href="<%=request.getContextPath()%>/mall/toIndex">首页</a>
    </li>
    <li class="layui-nav-item">
        <a id="aa" href="javascript:toLogin()">登陆</a>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:toAdd()">注册</a>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:toMyCar()">我的购物车</a>
    </li>
</ul>

<div class="layui-form-item">
    <a href="javascript:toPIM()">个人信息</a><br>
    <a href="javascript:toInsertAddress()">收货地址</a><br>
    <a href="">我的订单</a><br>
</div>

</body>
</html>
