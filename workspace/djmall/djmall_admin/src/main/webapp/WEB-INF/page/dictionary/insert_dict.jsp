<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath() %>\static\jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery.validate.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/additional-methods.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layer\layer.js"></script>
</head>
<script>
    function add() {
        $.post("<%=request.getContextPath()%>/dict/insertDict",
            $("#fm").serialize(),
            function(result){
                layer.msg(result.msg, {icon: 1,
                    time: 3000,shade:0.2},function(){
                    parent.location.href = "<%=request.getContextPath()%>/dict/toList";
                });
            })
    }
</script>
<body>
<form id="fm">
    分类上级：<select name="superCode">
    <c:forEach items="${parents}" var="p">
        <option value="${p.code}">${p.dictionaryName}</option>
    </c:forEach>
    </select><br/>
    分类名称：<input type="text" name="dictionaryName"/><br/>
    分类code：<input type="text" name="code"/><br/>
    <input type="button" value="新增" onclick="add()"/>
</form>
</body>

</html>
