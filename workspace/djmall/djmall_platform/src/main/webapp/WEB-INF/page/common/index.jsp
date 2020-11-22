<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/17
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>dj商城</title>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/js/token.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/js/cookie.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css" media="all">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<script type="text/javascript">

    $(function () {
        if(check_login()){
            $("#aa").html(cookie.get("NICK_NAME"));
            $("#aa").attr("href", "<%=request.getContextPath()%>/mall/toIndex?TOKEN=" + getToken());
            $("#aa").attr("href","<%=request.getContextPath()%>/user/toMyCenter?TOKEN="+ getToken());
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

    /* 加载商品主类 */
    var pageNo = 1;
    $(function(){
        search();
        loadProductType();
    })

    /*加载商品信息*/
    function search(){
        $.get(
            "<%=request.getContextPath()%>/user/productList",
            {"pageNo":pageNo,"pageSize":4},
            function(result){
                var html = "";
                var pageHtml = "";
                var product = result.data.records;
                for (var i = 0; i < product.length; i++) {
                    html += "<tr>";
                    html += "<th><a href = '<%=request.getContextPath()%>/product/toProduct/"+product[i].id+"'>"+product[i].productName+"</a></th>"
                    html += "<th>"+product[i].skuPrice+"</th>"
                    html += "<th>"+product[i].skuCount+"</th>"
                    html += "<th>"+product[i].productType+"</th>"
                    html += "<th>"+product[i].skuRate+"</th>"
                    html += "<th>"+product[i].productFreight+"</th>"
                    html += "<th><img src='"+product[i].productImg+"' width = '400px' height = '100px'/> </th>"
                    html += "<th>"+product[i].productDescription+"</th>"
                    html += "</tr>"
                }
                pageHtml += "<input type = 'button' value = '上一页' onclick = 'putDown(true)' class=\"layui-btn layui-btn-radius layui-btn-warm\"/>"
                pageHtml += "<input type = 'button' value = '下一页' onclick = 'putDown(false , "+result.data.pages+")' class=\"layui-btn layui-btn-radius layui-btn-warm\"/>"
                $("#page").html(pageHtml);
                $("#tb").html(html);
            }
        )
    }

    /* 分页 */
    function putDown(isUp, pages){
        if(isUp){
            if(pageNo <= 1){
                layer.msg("在首页了");
                return;
            }
            pageNo = pageNo - 1;
        }else{
            if(pageNo >= pages){
                layer.msg("在尾页了");
                return;
            }
            pageNo = pageNo + 1;
        }
        layer.msg(pageNo)
        search();
    }

    /* 去购物车 */
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

<div class="layui-container">
    <form class="layui-form1">
        <div class="layui-form-item">
            <div class="layui-input-block">
                <input type="text" name="productKeyWords" lay-verify="title" autocomplete="off" placeholder="搜索"
                       class="layui-input"/>
                价格：<input type="text" name="startPrice"/>~<input type="text" name="endPrice"/>
                <div id="productType">
                </div>
                <button type="button" onclick="page(1)">search</button>
            </div>
        </div>
    </form>
    <div class="layui-row layui-col-space30" id="show"></div>
    <div class="layui-flow-more"></div>
</div>


<table class="layui-table" lay-skin="line" lay-size="sm">
    <tr>
        <th>名称</th>
        <th>价格</th>
        <th>库存</th>
        <th>分类</th>
        <th>折扣</th>
        <th>邮费</th>
        <th>图片</th>
        <th>描述</th>
        <th>点赞</th>
    </tr>
    <tbody id="tb"></tbody>
</table>
<div id = "page"></div>



</body>
</html>
