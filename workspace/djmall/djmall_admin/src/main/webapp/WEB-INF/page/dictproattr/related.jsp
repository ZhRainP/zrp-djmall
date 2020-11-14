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
        $.post(
            "<%=request.getContextPath()%>/attr/attrValue",
            {"attrId":${attr.id}},
            function(result){
                var html = "";
                var attrValue = result.data;
                for (var i = 0; i < attrValue.length; i++) {
                    html += "<tr>";
                    html += "<th>"+attrValue[i].id+"</th>"
                    html += "<th>"+attrValue[i].attrValue+"</th>"
                    html += "<th><input type='button' value='移除' onclick='deleteAttr("+attrValue[i].id+")' class='btn btn-primary'/>"
                    html += "</tr>"
                }
                $("#tb").html(html);
            }
        )
    }

    /*添加属性值*/
    function add(){
        $.post("<%=request.getContextPath()%>/attr/insertAttrValue",
            $("#fm").serialize(),
            function(result){
                layer.msg(result.msg, {icon: 1,
                    time: 3000,shade:0.2},function(){
                    search();
                });
            })
    }

    /* 删除属性值 */
    function deleteAttr(id) {
        $.post("<%=request.getContextPath()%>/attr/deleteArrt?id=" + id,
            function(result){
                layer.msg(result.msg, {icon: 1,
                    time: 3000,shade:0.2},function(){
                    search();
                });
            })
    }
</script>
<body>

属性名：<input disabled value="${attr.attrName}"/><br/><hr/>
<form id="fm">
    已关联值 &nbsp;&nbsp; 属性值：<input type="text" name="attrValue" id="attrValue"/>
    <input type="hidden" name="attrId" id="attrId" value="${attr.id}"/>
</form>
<input type="button" value="新增" onclick="add()"/>
<table class="layui-table" lay-skin="line" lay-size="sm">
    <colgroup>
        <col width="300">
        <col width="300">
        <col width="300">
        <col width="300">
    </colgroup>
    <tr>
        <th>编号</th>
        <th>属性名</th>
        <th>操作</th>
    </tr>
    <tbody id="tb" style="align: center">
    </tbody>
</table>
</body>
</html>
