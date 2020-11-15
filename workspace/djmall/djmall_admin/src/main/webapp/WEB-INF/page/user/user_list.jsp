<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/4
  Time: 14:42
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
    function search(){
        $.get(
            "<%=request.getContextPath()%>/user/list",
            $("#fm").serialize(),
            function(result){
                var html = "";
                var user = result.data;
                for (var i = 0; i < user.length; i++) {
                    html += "<tr>";
                    html += "<th><input type='checkbox' value='"+user[i].id+"'/></th>";
                    html += "<th>"+user[i].username+"</th>"
                    html += "<th>"+user[i].roleName+"</th>"
                    html += "<th>"+user[i].nickName+"</th>"
                    html += "<th>"+user[i].phone+"</th>"
                    html += "<th>"+user[i].mail+"</th>"
                    html += "<th>"+user[i].sex+"</th>"
                    html += "<th>"+user[i].status+"</th>"
                    html += "<th>"+user[i].startTime+"</th>"
                    html += "<th>"+user[i].endTime+"</th>"
                    html += "</tr>"
                }
                $("#tb").html(html)
            }
        )
    }

    //授权
    function authorizes(){
        var checkeBoxs=$("#tb :checked");
        var index = layer.load(2,{time:1500});
        if(checkeBoxs.length==0){
            layer.msg("，请选择一个", {icon: 6});
            layer.close(index);
            return;
        }
        var id= checkeBoxs.val();
        layer.open({
            type: 2,
            title: '授权页面',
            shadeClose: true,
            shade: 0.8,
            area: ['380px', '90%'],
            content: '<%=request.getContextPath()%>/user/toAuthorize?id='+id
        });
    }
    /* 删除 */
    function deleteUser() {
        var checkeBoxs=$("#tb :checked");
        var index = layer.load(2,{time:1500});
        if(checkeBoxs.length==0){
            layer.msg("，请选择一个", {icon: 6});
            layer.close(index);
            return;
        }
        var id= checkeBoxs.val();
        $.post("<%=request.getContextPath()%>/user/del",
            {"id":id, "userId":id},
            function(result){
                if(result.code == 200){
                    window.location.href = "<%=request.getContextPath()%>/user/toList";
                }
            })
    }
    /* 刷新 */
    function reFresh(){
        window.location.href="<%=request.getContextPath()%>/user/toList";
    }
    /* 修改 */
    function toUpdate() {
        var checkeBoxs=$("#tb :checked");
        var index = layer.load(2,{time:1500});
        if(checkeBoxs.length==0){
            layer.msg("，请选择一个", {icon: 6});
            layer.close(index);
            return;
        }
        if(checkeBoxs.length>1){
            layer.msg("，只能选择一个", {icon: 6});
            layer.close(index);
            return;
        }
        var id= checkeBoxs.val();
        layer.open({
            type: 2,
            title: '修改页面',
            shadeClose: true,
            shade: 0.8,
            area: ['380px', '90%'],
            content: '<%=request.getContextPath()%>/user/toUpdate?id='+id
        });
    }
    /* 重置密码 */
    function resetPassword () {
        var checkedBox=$("#tb :checked");
        var id= checkedBox.val();
        $.post("<%=request.getContextPath()%>/user/resetPassword?id=" + id,
            function(result){
                layer.msg("重置成功");
        })
    }
    /* 激活 */
    function toActive() {
        var checkeBoxs=$("#tb :checked");
        var index = layer.load(2,{time:1500});
        if(checkeBoxs.length==0){
            layer.msg("，请选择一个", {icon: 6});
            layer.close(index);
            return;
        }
        var id= checkeBoxs.val();
        $.post("<%=request.getContextPath()%>/user/active",
            {"id":id},
            function(result){
                if(result.code == 200){
                    if(result.status == "ACTIVE"){
                        layer.msg("已激活，不同激活");
                        return
                    }
                    window.location.href = "<%=request.getContextPath()%>/user/toList";
                }
            })
    }


    $(function (){
        $.post(
            "<%=request.getContextPath()%>/dict/USER_SEX/getChild",
            function(data){
                var dict = data.data;
                for (var i = 0; i < dict.length; i++) {
                    $("#sex").append("<input type='radio' name='sex' value='"+''+dict[i].code+''+"'> "+''+dict[i].dictionaryName+''+" "  )
                }
            }
        )

    })
    $(function (){
        $.post(
            "<%=request.getContextPath()%>/dict/USER_STATUS/getChild",
            function(data){
                var dict = data.data;
                for (var i = 0; i < dict.length; i++) {
                    $("#status").append("<option  name='status' value='"+''+dict[i].code+''+"' > "+''+dict[i].dictionaryName+''+" </option>")
                }
            }
        )
    })
</script>
<body>
<br><br>
<form id="fm">

<table>
    <tr>
        <td>用户名/手机号/邮箱：</td>
        <td><input type="text" id="nameLike" name="nameLike"/></td>
    </tr>
    <tr>
        <td>级别：</td>
        <td>
            商家：<input type="radio" value="2" name="roleName"/>
            管理员：<input type="radio" value="1" name="roleName"/>
        </td>
    </tr>
    <tr>
        <%--<td>性别</td>
        <td>
            男<input type="radio"  name="sex" value="1"/>
            女<input type="radio"  name="sex" value="2"/>
        </td>--%><div id = "sex">性别：</div>
    </tr>
    <tr>
        <%--<td>状态</td>
        <td>
            <select name="status">
                <option value="aaaa" >请选择</option>
                <option value="ACTIVE">正常</option>
                <option value="NOT_ACTIVE">未激活</option>
            </select>
        </td>--%>

        状态：<select id = "status" name = "status">
            <option value="aaaa" >请选择</option>
        </select>
    </tr>
</table>
<input type="button" value="查询" onclick="search()" class="btn btn-primary"/>
<input type="button" value="刷新" onclick="reFresh()" class="btn btn-primary"/><br><br><br><hr/><br>
</form>

<shiro:hasPermission name="USER_ROLE_BUT">
    <input type = "button" value="授权" onclick="authorizes()" class="btn btn-primary"/>
    <input type = "button" value="修改" onclick="toUpdate()" class="btn btn-primary"/>
    <input type = "button" value="删除" onclick="deleteUser()" class="btn btn-primary"/>
    <input type = "button" value="激活" onclick="toActive()" class="btn btn-primary"/>
    <input type = "button" value="重置密码" onclick="resetPassword()" class="btn btn-primary"/>
</shiro:hasPermission>
<form id="fm">
    <table class="layui-table" lay-skin="line" lay-size="sm">
        <colgroup>
            <col width="50">
            <col width="50">
            <col width="50">
        </colgroup>
        <tr>
            <th>id</th>
            <th>用户名</th>
            <th>级别</th>
            <th>昵称</th>
            <th>手机号</th>
            <th>邮箱</th>
            <th>性别</th>
            <th>状态</th>
            <th>注册时间</th>
            <th>最后登陆时间</th>
        </tr>
        <tbody id="tb"></tbody>
    </table>
</form>
</body>
</html>
