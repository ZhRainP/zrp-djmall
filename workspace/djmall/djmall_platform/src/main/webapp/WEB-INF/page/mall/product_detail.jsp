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

</script>
<body>
<ul class="layui-nav layui-bg-blue" align="right">
    <li class="layui-nav-item">
        <a href="">首页</a>
    </li>
    <li class="layui-nav-item">
        <a id="aa" href="javascript:toLogin()">登陆</a>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:toAdd()">注册</a>
    </li>
    <li class="layui-nav-item">
        <a href="">我的购物车</a>
    </li>
</ul>
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
                        <td><span class="layui-field-hd">原价：</span><span class="layui-field-bd" id="skuPrice"></span></td>
                    </tr>
                    <tr>
                        <td><span class="layui-field-hd">折扣：</span><span class="layui-field-bd price" id="skuRate"></span></td>
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
                                <input type="radio" name="productId" value="${s.productId}" onclick="skuChange(${s.id})"
                                <c:if test="${s.isDefault == 0}">checked</c:if>/> ${s.skuName}
                                <input type="hidden" id="skuPrice${s.id}" value="${s.skuPrice}">
                                <input type="hidden" id="skuRate${s.id}" value="${s.skuRate}">
                            </c:forEach>

                        </td>
                    </tr>
                    </tbody>
                </table>
        </div>
</body>
</html>
