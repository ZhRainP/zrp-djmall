<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/11
  Time: 11:09
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
    $(function(){
        search();
    })
    function search(){
        $.get(
            "<%=request.getContextPath()%>/dict/dictList",
            function(result){
                var html = "";
                var dict = result.data;
                for (var i = 0; i < dict.length; i++) {
                    html += "<tr>";
                    html += "<th>"+dict[i].code+"</th>"
                    html += "<th>"+dict[i].dictionaryName+"</th>"
                    html += "<th>"+dict[i].superCode+"</th>"
                    html += "<th><input type='button' value='修改' onclick='toUpdate("+'"'+dict[i].code+'"'+")' class='btn btn-primary'/>"
                    html += "</tr>"
                }
                $("#tb").html(html);
            }
        )
    }
    /* 添加 */
    function add(){
        layer.open({
            type: 2,
            title: '添加角色',
            shadeClose: true,
            shade: 0.8,
            area: ['680px', '70%'],
            content: '<%=request.getContextPath()%>/dict/toInsert',
        });
    }

    /*修改*/
    function toUpdate(code){
        layer.open({
            type: 2,
            title: '修改页面',
            shadeClose: true,
            shade: 0.8,
            area: ['380px', '90%'],
            content: "<%=request.getContextPath()%>/dict/toUpdate?code=" + code,
        });
    }
</script>
<body>
<shiro:hasPermission name="DICT_INSERT_BUT">
    <input type="button" onclick="add()" value="添加" class='btn btn-primary'/>
</shiro:hasPermission>
<table class="layui-table" lay-skin="line" lay-size="sm">
    <colgroup>
        <col width="300">
        <col width="300">
        <col width="300">
    </colgroup>
    <tr>
        <th>CODE</th>
        <th>字典名</th>
        <th>上级CODE</th>
        <th>操作</th>
    </tr>
    <tbody id="tb" style="align: center">
    </tbody>
</table>
</body>
</html>
