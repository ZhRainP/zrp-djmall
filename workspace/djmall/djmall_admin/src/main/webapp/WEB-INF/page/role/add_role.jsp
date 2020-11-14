<%--
  Created by IntelliJ IDEA.
  User: dj
  Date: 2020/10/16
  Time: 19:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="<%=request.getContextPath() %>\static\jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/additional-methods.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <style>
        .error {
            color: red;
        }
    </style>
</head>
<script>

    $(function () {
        $("#fm").validate({
            rules: {
                roleName: {
                    required: true,

                },

            },
            messages: {
                roleName: {
                    required: "请填入角色",

                },

            },
            submitHandler: function (data) {
                $.post(
                    "<%=request.getContextPath()%>/role/insert",
                    $("#fm").serialize(),
                    function (data) {
                        if (data.code == 200) {
                            layer.msg(data.msg);
                            parent.location.href = "<%=request.getContextPath()%>/role/toList";
                        } else {
                            layer.msg(data.msg);
                        }
                    }
                )
            }
        })
    })
</script>
<body>
<form id="fm">
    <label for="roleName">角色</label>
    <input type="text" name="roleName" id="roleName"/><br/>
    <input type="submit" value="新增"/>
</form>
</body>
</html>
