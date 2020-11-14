<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/11
  Time: 21:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css" media="all">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<script type="text/javascript">
    $(function () {
        search();
    })
    function search() {
        $.post(
            "<%=request.getContextPath()%>/attr/skuList",
            function (data) {
                var html = "";
                var productAttr = data.data;
                for (var i = 0; i < productAttr.length; i++) {
                    html += "<tr>"
                    html += "<td>" + i+1 + "</td>";
                    html += "<td>" + productAttr[i].productType + "</td>";
                    html += "<td>" + productAttr[i].attrName + "</td>";
                    html += "<td><input type='button' value='关联商品属性' onclick='toGeneralAssociated("+'"'+productAttr[i].code+'"'+")' class='btn btn-primary'/></td>";
                    html += "</tr>"
                }
                $("#tb").html(html);
            }
        )
    }

    function toGeneralAssociated(code){
        layer.open({
            type: 2,
            title: '字典修改',
            area: ['500px', '450px'],
            fixed: true, //不固定
            maxmin: true,
            content: '<%=request.getContextPath()%>/attr/toGeneralAssociated?code='+code
        });
    }
</script>
<body>
<table class="layui-table" lay-skin="line" lay-size="sm">
    <th>编号</th>
    <th>商品类型</th>
    <th>属性名</th>
    <th>操作</th>
    <tbody id="tb"></tbody>
</table>
</body>
</html>
