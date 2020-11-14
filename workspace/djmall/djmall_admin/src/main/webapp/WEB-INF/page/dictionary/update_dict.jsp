<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/8
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery.validate.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/additional-methods.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/md5-min.js"></script>
</head>
<body>
<script type="text/javascript">
    function updateDict() {
        $.post("<%=request.getContextPath()%>/dict/updateDict",
            $("#fm").serialize(),
            function(result){
                layer.confirm(result.msg, {icon: 1,
                    time: 10000000},function(){
                    parent.location.href = "<%=request.getContextPath()%>/dict/toList";
                });
             })
    }
</script>
<input type="hidden" name="code" value="${dict.code}"/>
<form id="fm">
    用户名：<input type="text" name="dictionaryName" value="${dict.dictionaryName}"/><br/>
    <input type="hidden" name="superCode" value="${dict.superCode}"/>
    <input type="button" value="确认修改" onclick="updateDict()" class="btn btn-primary"/>
</form>
</body>
</html>
