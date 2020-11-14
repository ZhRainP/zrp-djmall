<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script type="text/javascript" src="<%=request.getContextPath() %>/static/jquery-1.12.4.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/static/zTree_v3/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/static/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/static/zTree_v3/js/jquery.ztree.core.js"></script>
</head>

<SCRIPT type="text/javascript">
	var setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId",
			},
			key: {
				name: "resourceName",
				url:"xUrl",
			},
		},
		callback:{
			onClick:zTreeOnMouseDown
		},
	}
	function zTreeOnMouseDown(event, treeId, treeNode) {
		if(!treeNode.isParent){
			parent.ppp.location ="<%=request.getContextPath()%>"+treeNode.url
		}
	}
	$(document).ready(function(){
		$.post(
				"<%=request.getContextPath()%>/user/menuList",
				function (data){
					$.fn.zTree.init($("#treeDemo"), setting, data.data);
				}
		)
	});
	/*var setting = {
		data: {
			simpleData: {
				//设置启用简单数据模式
				enable: true,

				idKey: "id",

				pIdKey: "parentId",

			},
			key: {
				name: "resourceName",
				url: "xUrl"
			},
		},
		callback: {
			onClick: zTreeOnClick
		},
	};

	$(function(){
		$.post(
			"<%=request.getContextPath() %>/user/menuList",
			function(result){
				$.fn.zTree.init($("#treeDemo"), setting, result.data);
			}
		)
	})

	function zTreeOnClick(event, treeId, treeNode) {
		if(!treeNode.isParent) {
			parent.name.location.href = "<%=request.getContextPath()%>" + treeNode.url;
		}
	};*/
</SCRIPT>

<body>
<div class="zTreeDemoBackground left">
	<ul id="treeDemo" class="ztree"></ul>
</div>

	<%--<ol type="1">
		<li>
			<a  href = "<%=request.getContextPath() %>/user/toList" target="name" >
				<h3>用户列表</h3>
			</a>
		</li>
		<li>
			<a  href = "<%=request.getContextPath() %>/role/toList" target="name" >
				<h3>角色列表</h3>
			</a>
		</li>
		<li>
			<a  href = "<%=request.getContextPath() %>/res/toList" target="name" >
				<h3>资源列表</h3>
			</a>
		</li>
	</ol>--%>
</body>
</html>