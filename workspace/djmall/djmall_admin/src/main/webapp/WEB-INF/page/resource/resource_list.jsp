<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>

    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/zTree_v3/css/demo.css" type="text/css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.exedit.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<SCRIPT type="text/javascript">
    var setting = {
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
    $(document).ready(function(){
        $.get(
            "<%=request.getContextPath()%>/res/resourceList",
            function (data){
                $.fn.zTree.init($("#treeDemo"), setting, data.data);
            }
        )
    });

    /* 新增 */
    function add(){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getSelectedNodes();
        var pId = 0 ;
        if (nodes.length>0 ){
            pId = nodes[0].id;
        }
        layer.open({
            type: 2,
            title: '新增',
            shadeClose: true,
            shade: 0.8,
            area: ['380px', '90%'],
            content: "<%=request.getContextPath()%>/res/toAdd?pId="+pId,
        });
    }

    /* 修改 */
    function toUpdate(){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getSelectedNodes();
        if(nodes.length == 0){
            layer.msg("请选择要修改的资源！");
            return ;
        }
        $("#id").val(nodes[0].id);
        $("#resourceName").val(nodes[0].resourceName);
        $("#url").val(nodes[0].url);
        $("#resourceCode").val(nodes[0].resourceCode);
        $("#resourceType").val(nodes[0].resourceType);
        $("#myModal").modal("show");
    }
    function update(){
        $.post(
            "<%=request.getContextPath()%>/res/update",
            $("#fm").serialize(),
            function(result){
                layer.msg(result.msg,{time:1000},function(){
                    $("#myModal").modal("hide");
                    window.location.reload();
                });
            }
        );
    }

    /* 删除 */
    function deleteResource(){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getSelectedNodes();
        if(nodes.length == 0){
            layer.msg("请选择要删除的节点");
            return ;
        }
        var resourceIds = "";
        var node = nodes[0];
        if(node.isParent){
            resourceIds = getResourceIds(node,resourceIds);
        }
        resourceIds += node.id;
        $.post(
            "<%=request.getContextPath()%>/res/deleteResource",
            {"resourceIds":resourceIds},
            function(result){
                layer.msg(result.msg,{time:1000},function(){
                    if(result.code == 200){
                        location.reload();
                    }
                })
            }
        )
    }
    /*递归*/
    function getResourceIds(node,resourceIds){
        var child = node.children;
        for(var i =0;i<child.length;i++){
            resourceIds += child[i].id+ ",";
            if(child[i].isParent){
                resourceIds = getResourceIds(child[i],resourceIds);
            }
        }
        return resourceIds;
    }
</script>
<body>
<shiro:hasPermission name="RESOURCE_INSERT_BUT">
    <input type="button" value="新增" onclick="add()" class="btn btn-primary">
</shiro:hasPermission>

<shiro:hasPermission name="RESOURCE_UODATE_BUT">
    <input type="button"  onclick="toUpdate()" value="修改"  class="btn btn-primary"/>
</shiro:hasPermission>

<shiro:hasPermission name="RESOURCE_UODATE_BUT">
    <input type="button"  onclick="deleteResource()" value="删除"  class="btn btn-primary"/>
</shiro:hasPermission>
<div class="zTreeDemoBackground left">
    <ul id="treeDemo" class="ztree"></ul>
</div>


<%-- 修改 --%>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">资源修改</h4>
            </div>
            <div class="modal-body">
                <form id="fm">
                    <input type="hidden" name="id" id="id"/>
                    资源名字：
                    <input type="text" name="resourceName" id="resourceName" /><br/>
                    资源路径：
                    <input type="text" name="url" id="url" /><br/>
                    资源编码：<input type="text" name="resourceCode" id="resourceCode" /><br/>
                    资源类型：<select name="resourceType" id="resourceType">
                    <option value="2">菜单</option>
                    <option value="1">按钮</option>
                </select><br/>
                </form>
            </div>
            <div class="modal-footer">
                <input type="button"  class="btn btn-primary"  value="修改" onclick="update()">
            </div>
        </div>
    </div>
</div>

</body>
</html>
