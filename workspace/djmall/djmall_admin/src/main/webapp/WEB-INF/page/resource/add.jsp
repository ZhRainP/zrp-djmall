<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="<%=request.getContextPath() %>\static\jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/additional-methods.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\layer\layer.js"></script>
<html>
<head>
    <title>Title</title>
    <style>
        .error {
            color: red;
        }
    </style>
</head>
<body>
<form id="fm">

    <input type="hidden" name="parentId" value="${pId}"/>

    上级节点 <input type="text" value="${resource.resourceName}" disabled /><br/>
    <label for="resourceName">资源名字</label>
    <input type="text" name="resourceName" id="resourceName"/><br/>
    <label for="url">资源路径</label>
    <input type="text" name="url" id="url"/><br/>
    <label for="resourceCode">资源编码</label>
    <input type="text" name="resourceCode" id="resourceCode"/><br/>
    <select name="resourceType">
        <option value="1">菜单</option>
        <option value="2">按钮</option>
    </select><br/>
    <input type="submit" value="新增"/>
</form>
</body>
<script>

    $(function () {
        $("#fm").validate({
            rules: {
                resourceName: {
                    required: true,
                },
                url: {
                    required: true,
                },
                resourceCode: {
                    required: true,
                },
            },
            messages: {
                resourceName: {
                    required: "请填写资源",
                },
                url: {
                    required: "请填写路径",
                },
                resourceCode: {
                    required: "请填写编码",
                },
            },
            submitHandler: function (data) {
                $.post(
                    "<%=request.getContextPath()%>/res/insert",
                    $("#fm").serialize(),
                    function (data) {
                        if (data.code == 200) {
                            layer.msg(data.msg);
                            parent.location.href = "<%=request.getContextPath()%>/res/toList";
                        } else {
                            layer.msg(data.msg);
                        }
                    }
                )
            }
        })
    })


</script>
</html>
