<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css" media="all">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<script type="text/javascript">
    $(function(){
        search();
    })
    function search(){
        $.post(
            "<%=request.getContextPath()%>/attr/attrList",
            function(result){
                var html = "";
                var attr = result.data;
                for (var i = 0; i < attr.length; i++) {
                    html += "<tr>";
                    html += "<th>"+attr[i].id+"</th>"
                    html += "<th>"+attr[i].attrName+"</th>"
                    html += "<th>"+attr[i].attrValue+"</th>"
                    html += "<th><input type='button' value='关联属性值' onclick='toRelated("+attr[i].id+")' class='btn btn-primary'/>"
                    html += "</tr>"
                }
                $("#tb").html(html);
            }
        )
    }

    function add () {
        $.post("<%=request.getContextPath()%>/attr/insert",
            {"attrName":$("#attrName").val()},
            function(result){
                layer.msg(result.msg, {icon: 1,
                    time: 3000,shade:0.2},function(){
                    search();
                });
            })
    }
    /* 关联属性 */
    function toRelated (id) {
        layer.open({
            type: 2,
            title: '关联商品属性值',
            shadeClose: true,
            shade: 0.8,
            area: ['30%', '90%'],
            content:"<%=request.getContextPath()%>/attr/toRelated?id=" + id,
        })
    }
</script>
<body>
<form id = "fm">
    属性名：<input type="text" name="attrName" id="attrName"/>
        <input type="button" onclick="add()" value="新增商品属性" class='btn btn-primary'/>
</form>

<table class="layui-table" lay-skin="line" lay-size="sm">
    <colgroup>
        <col width="300">
        <col width="300">
        <col width="500">
        <col width="300">
    </colgroup>
    <tr>
        <th>编号</th>
        <th>属性名</th>
        <th>属性值</th>
        <th>操作</th>
    </tr>
    <tbody id="tb" style="align: center">
    </tbody>
</table>
</body>
</html>
