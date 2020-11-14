<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/5
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>关联资源</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/zTree_v3/css/demo.css" type="text/css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.exedit.js"></script>
</head>
<script type="text/javascript">
    var setting = {
        check: {
            enable: true,
        },
        data: {
            simpleData: {
                enable: true,
                idKey:"id",
                pIdKey:"parentId",
            },
            key:{
                name:"resourceName",
                url: "xUrl"
            },
        }
    };
    $(function(){
        $.get("<%=request.getContextPath()%>/role/getRoleResource",
            {"id": '${id}'},
            function(result){
            $.fn.zTree.init($("#treeDemo"), setting, result.data);
        })
    })

    /* 保存 */
    function sava() {
        var resourceIds ="";
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getCheckedNodes(true);
        for(var i=0;i<nodes.length;i++){
            resourceIds += nodes[i].id+",";
        }
        var  resourceIds = resourceIds.substring(0,resourceIds.length-1);
        $.post( '<%=request.getContextPath()%>/role/savaRoleResource',
            {"resourceIds":resourceIds,
                "id":${id}},
            function(result){
                layer.msg(result.msg, function (){
                    parent.location.reload();
                });
            });

    }
</script>
<body>
<input type="button" value="保存" onclick="sava()"/>
<div class="zTreeDemoBackground left">
    <ul id="treeDemo" class="ztree"></ul>
</div>
</body>
</html>
