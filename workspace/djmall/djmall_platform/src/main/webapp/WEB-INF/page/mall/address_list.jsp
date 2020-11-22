<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/20
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/additional-methods.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/js/token.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/js/cookie.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css" media="all">
</head>
<script type="text/javascript">
    $(function(){
        search();
    })
    function search(){
        $.get(
            "<%=request.getContextPath()%>/user/addressList?TOKEN=" + getToken(),
            function(result){
                var html = "";
                var address = result.data;
                for (var i = 0; i < address.length; i++) {
                    html += "<tr>";
                    html += "<th>"+ i+1 +"</th>"
                    html += "<th>"+address[i].userName+"</th>"
                    html += "<th>"+ address[i].provice+'-'+address[i].city+'-'+address[i].counties+'-'+address[i].address+"</th>"
                    html += "<th>"+address[i].phone+"</th>"
                    html += "<th><input type = 'button' value = '删除' onclick = 'toDel()'/>" +
                        "<input type = 'button' value = '修改' onclick = 'toUpdate()'/></th>"
                    html += "</tr>"
                }
                $("#tb").html(html);
            }
        )
    }
    function toAdd() {
        layer.open({
            type: 2,
            title: '添加角色',
            shadeClose: true,
            shade: 0.8,
            area: ['680px', '70%'],
            content: '<%=request.getContextPath()%>/user/toAddress?TOKEN=' + getToken(),
        });
    }
</script>
<body>
<input type="button" value = "新增地址" onclick="toAdd()" class="layui-btn layui-btn-xs"/>
<table class="layui-table" lay-skin="line" lay-size="sm">
    <tr>
        <th>编号</th>
        <th>收货人</th>
        <th>详细地址</th>
        <th>手机号</th>
        <th>操作</th>
    </tr>
    <tbody id="tb"></tbody>
</table>
</body>
</html>
