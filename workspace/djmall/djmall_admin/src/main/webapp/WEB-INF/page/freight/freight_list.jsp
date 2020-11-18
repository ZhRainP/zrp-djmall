<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
            "<%=request.getContextPath()%>/freight/freList",
            function(result){
                var html = "";
                var freight = result.data;
                for (var i = 0; i < freight.length; i++) {
                    html += "<tr>";
                    html += "<th>"+freight[i].logisticsCompany+"</th>"
                    if(freight[i].freight == 0){
                        html += "<th>包邮</th>"
                    }else if (freight[i].freight > 0){
                        html += "<th>"+freight[i].freight+"</th>"
                    }
                    html += "<th><input type='button' value='修改' onclick='toUpdate("+freight[i].id+")' class='btn btn-primary'/>"
                    html += "</tr>"
                }
                $("#tb").html(html);
            }
        )
    }

    function add () {
        $.post("<%=request.getContextPath()%>/freight/insert",
            $("#fm").serialize(),
            function(result){
                layer.msg(result.msg, {icon: 1,
                    time: 3000,shade:0.2},function(){
                    location.reload();
                });
            })
    }

    function toUpdate (id) {
        layer.open({
            type: 2,
            title: '修改运费',
            shadeClose: true,
            shade: 0.8,
            area: ['30%', '30%'],
            content:"<%=request.getContextPath()%>/freight/toUpdate?id=" + id,
        })
    }
</script>
<body>
<form id = "fm">
    物流公司：<select name="logisticsCompany" lay-verify="">
    <c:forEach items="${parents}" var="p">
    <option value="${p.code}">${p.dictionaryName}</option>
    </c:forEach>
        </select><br/>
    运费：<input type="text" name="freight"/><br/>
    <input type="button" onclick="add()" value="新增" class="layui-btn layui-btn-sm"/>
</form>

<table class="layui-table" lay-skin="line" lay-size="sm">
    <colgroup>
        <col width="300">
        <col width="300">
        <col width="300">
    </colgroup>
    <tr>
        <th>物流公司</th>
        <th>运费</th>
        <th>操作</th>
    </tr>
    <tbody id="tb" style="align: center"></tbody>
</table>
</body>
</html>
