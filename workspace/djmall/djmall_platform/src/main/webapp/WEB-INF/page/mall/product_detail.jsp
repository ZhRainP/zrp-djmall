<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/18
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>商品详情页</title>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/js/token.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/js/cookie.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css" media="all">
</head>
<script type="text/javascript">
    $(function () {
        if(check_login()){
            $("#aa").html(cookie.get("NICK_NAME"));
            $("#aa").attr("href", "<%=request.getContextPath()%>/mall/toIndex?TOKEN=" + getToken());
        }
    })

    function toLogin () {
        layer.open({
            type: 2,
            content:"<%=request.getContextPath()%>/user/toLogin",
            title: "登陆",
            shade: 0.6,
            area: ['300px', '300px']
        });
    }
    function toAdd () {
        layer.open({
            type: 2,
            content:"<%=request.getContextPath()%>/user/toAdd",
            title: "注册",
            shade: 0.6,
            area: ['300px', '300px']
        });
    }

    function skuChange(id) {
        $("#skuPrice").html($("#skuPrice" + id).val());
        if ($("#skuRate" + id).val() == 0) {
            $("#skuRate").html("无，按照原价");
        } else {
            $("#skuRate").html($("#skuRate" + id).val() + "%");
        }
        token_post("<%=request.getContextPath()%>/product/getSkuById?skuId=" + id,
            function (data) {
                if (data.code == 200) {
                    // sku 的价格 折扣 库存
                    $("#skuPrice").val(data.data.skuPrice);
                    $("#skuRate").val(data.data.skuRate);
                    $("#kuCun").val(data.data.skuCount);

                }
            })
    }

    function jia() {
        $("#buyNumber").val(parseInt($("#buyNumber").val()) + 1);
    }

    function jian() {
        if ($("#buyNumber").val() < 2) {
            layer.msg("不能再减啦", {icon: 5});
            $("#buyNumber").val(1);
            return;
        }
        $("#buyNumber").val(parseInt($("#buyNumber").val()) - 1);
    }

    // 购买数量不能超过200
    function buyNumberChange(buyNumber) {
        alert(1)
        if (buyNumber > 200) {
            layer.msg("最大不可超过200", {icon: 5});
            $("#buyNumber").val(1);
            return;
        }
        if (buyNumber < 1) {
            layer.msg("最少不可超过1", {icon: 5});
            $("#buyNumber").val(1);
            return;
        }
    }


    //添加到购物车
    function toCar() {
        var kuCuns = parseInt($("#kuCun").val())
        if (kuCuns < 1) {
            layer.msg("没有那么多库存了");
            return;
        }
        //用户有效性验证
        if (check_login()) {
            if ($("#buyNumber").val() >= 200) {
                layer.msg("购买数量不得超过200")
                return;
            } else {
                token_post("<%=request.getContextPath() %>/user/addShop?TOKEN=" + getToken(),
                    $("#fm").serialize(),
                    function (data) {
                        if (data.code == 200) {
                            layer.msg("添加成功")
                        }
                    })
            }
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

    function toMyCar(){
        if (check_login()) {
            window.location.href = "<%=request.getContextPath()%>/product/toCar?TOKEN=" + getToken();
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

    function toPersonCenter() {

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
<form id="fm">
    <input type="hidden" value="${product.id}" name="productId"/>
        <!-- 主要内容 -->
        <div class="container">
                <table class="layui-table layui-table-add" lay-skin="nob">
                    <td>
                        <img src='http://qjrxidgn4.hb-bkt.clouddn.com/${product.productImg}' width = '400px' height = '100px'/>
                    </td>
                    <tbody>
                    <tr>
                        <td><span class="layui-field-hd">商品名称：</span><span class="layui-field-bd">${product.productName}</span></td>
                    </tr>
                    <tr>
                        <td><span class="layui-field-hd">原价：</span><span class="layui-field-bd" id="skuPrice" name = "skuPrice"></span></td>
                    </tr>
                    <tr>
                        <td><span class="layui-field-hd">折扣：</span><span class="layui-field-bd price" id="skuRate" name = "skuRate"></span></td>
                    </tr>
                    <tr>
                        <td><span class="layui-field-hd">邮费：</span><span class="layui-field-bd">${product.productFreight}</span></td>
                    </tr>
                    <tr>
                        <td><span class="layui-field-hd">商品描述：</span><span class="layui-field-bd">${product.productDescription}</span></td>
                    </tr>
                    <tr>
                        <td>
                            <c:forEach items="${sku}" var="s" varStatus="i">
                                <input type="radio" name="skuId" value="${s.id}" onclick="skuChange(${s.id})"
                                <c:if test="${s.isDefault == 0}">checked</c:if>/> ${s.skuName}
                                <input type="hidden" id="skuPrice${s.id}" value="${s.skuPrice}">
                                <input type="hidden" id="skuRate${s.id}" value="${s.skuRate}">
                            </c:forEach>

                        </td>
                    </tr>

                    <tr>
                        <td colspan="2">
                            购买数量:
                            <input type="button" value="-" onclick="jian()"
                                   class="layui-btn layui-btn-primary layui-btn-radius">
                            <input type="text" id="buyNumber" name="count" readonly="readonly"
                                   onchange="buyNumberChange(this.value)" value="1">
                            <input type="button" value="+" onclick="jia()"
                                   class="layui-btn layui-btn-primary layui-btn-radius">
                            <input type="hidden" id="kuCun" value="">
                        </td>
                    </tr>
                    </tbody>
                </table>
            <input type="button" value="加入购物车" onclick="toCar()" class="layui-btn layui-btn-lg layui-btn-normal"/>
            <input type="button" value="立即购买" onclick="goBuy()" class="layui-btn layui-btn-lg layui-btn-normal"/>
        </div>
</form>
</body>
</html>
