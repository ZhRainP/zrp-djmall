<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/4
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
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
    /*html+="<td><shiro:hasPermission name='ROLE_RESOURCE_BTN'><input type='button' value='关联资源' onclick='roleResource("+role[i].id+")'/></shiro:hasPermission>";
    html+="<shiro:hasPermission name='ROLE_UPDATE_BTN'><input type='button' value='编辑' onclick='update("+role[i].id+")'/></shiro:hasPermission>";
    html+="<shiro:hasPermission name='ROLE_DEL_BTN'><input type='button' value='删除'/></shiro:hasPermission></td>";*/
    function search(){
        $.get(
            "<%=request.getContextPath()%>/role/roleList",
            function(result){
                var html = "";
                var role = result.data;
                for (var i = 0; i < role.length; i++) {
                    html += "<tr>";
                    html += "<th>"+role[i].id+"</th>"
                    html += "<th>"+role[i].roleName+"</th>"
                    html += "<th><shiro:hasPermission name='ROLE_UPDATA_BUT'><input type='button' value='编辑' onclick='toUpdate("+role[i].id+")' class='btn btn-primary'/></shiro:hasPermission>"
                    html += "  &nbsp;&nbsp | &nbsp;&nbsp;  "
                    html += "<shiro:hasPermission name='ROLE_RESOURCE_BUT'><input type='button' value='关联资源' onclick='toRoleResource("+role[i].id+")' class='btn btn-primary'/></shiro:hasPermission>"
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
            content: '<%=request.getContextPath()%>/role/toAdd'
        });
    }

    /*修改*/
    function toUpdate(id){
        layer.open({
            type: 2,
            title: '修改页面',
            shadeClose: true,
            shade: 0.8,
            area: ['380px', '90%'],
            content: "<%=request.getContextPath()%>/role/toUpdate?id="+id,
        });
    }

    /* 去关联资源 */
    function toRoleResource (id){
        layer.open({
            type: 2,
            title: '关联资源页面',
            shadeClose: true,
            shade: 0.8,
            area: ['380px', '90%'],
            content: "<%=request.getContextPath()%>/role/toRoleResource?id="+id,
        });
    }

</script>
<body>
<shiro:hasPermission name="ROLE_INSERT_BUT">
    <input type="button" onclick="add()" value="添加" class='btn btn-primary'/>
</shiro:hasPermission>
<table class="layui-table" lay-skin="line" lay-size="lg">
    <colgroup>
        <col width="300">
        <col width="300">
        <col width="300">
    </colgroup>
    <tr>
        <th>id</th>
        <th>角色名</th>
        <th>操作</th>
    </tr>
    <tbody id="tb" style="align: center">
    </tbody>
</table>
</body>
</html>
