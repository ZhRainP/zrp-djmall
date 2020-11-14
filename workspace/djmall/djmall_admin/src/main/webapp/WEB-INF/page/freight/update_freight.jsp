<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/12
  Time: 13:01
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
     function updateFreight() {
         $.post("<%=request.getContextPath()%>/freight/updateFreight",
             {"freight":$("#freight").val(),"id":$("#id").val()},
             function(result){
                 layer.msg(result.msg, {icon: 1,
                     time: 3000,shade:0.2},function(){
                     parent.location.reload();
                 });
             })
     }
</script>
<body>
<input type="hidden" value="${freight.id}" id = "id"/>
运费：<input type = "text" value="${freight.freight}" id="freight"/><br/>
<input type="button" value="提交" onclick="updateFreight()" class="layui-btn layui-btn-sm"/>
</body>
</html>
