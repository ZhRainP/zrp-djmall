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
    var pageNo = 1;
    $(function(){
        search();
    })
    function search(){
        $.get(
            "<%=request.getContextPath()%>/product/productList",
            {"pageNo":pageNo,"pageSize":1},
            function(result){
                var html = "";
                var pageHtml = "";
                var product = result.data.records;
                for (var i = 0; i < product.length; i++) {
                    html += "<tr>";
                    html += "<th><input type='checkbox' value='"+product[i].id+"'/></th>"
                    html += "<th>"+product[i].productName+"</th>"
                    html += "<th>"+product[i].productType+"</th>"
                    if(product[i].productStatus == 1){
                        html += "<th>上架</th>"
                    }else{
                        html += "<th>下架</th>"
                    }
                    html += "<th>"+product[i].productFreight+"</th>"
                    html += "<th>"+product[i].productImg+"</th>"
                    html += "<th>"+product[i].productDescription+"</th>"
                    html += "</tr>"
                }
                pageHtml += "<input type = 'button' value = '上一页' onclick = 'putDown(true)'/>"
                pageHtml += "<input type = 'button' value = '下一页' onclick = 'putDown(false , "+result.data.pages+")'/>"
                $("#page").html(pageHtml);
                $("#tb").html(html);
            }
        )
    }

    /* 分页 */
    function putDown(isUp, pages){
        if(isUp){
            if(pageNo <= 1){
                layer.msg("在首页了");
                return;
            }
            pageNo = pageNo - 1;
        }else{
            if(pageNo >= pages){
                layer.msg("在尾页了");
                return;
            }
            pageNo = pageNo + 1;
        }
        layer.msg(pageNo)
        search();
    }
    /* 商品添加 */
    function add(){
        layer.open({
            type: 2,
            title: '新增sku',
            shadeClose: true,
            shade: 0.8,
            area: ['90%', '90%'],
            content:"<%=request.getContextPath()%>/product/toInsert",
        })
    }
    /* 修改 */
    function toUpdate (id) {
        var checkeBoxs=$("#tb :checked");
        var index = layer.load(2,{time:1500});
        if(checkeBoxs.length==0){
            layer.msg("，请选择一个", {icon: 6});
            layer.close(index);
            return;
        }
        var id= checkeBoxs.val();
        location.href = "<%=request.getContextPath()%>/product/toUpdate?id="+id;
    }
</script>
<body>
<input type="button" value="新增" onclick="add()" class="btn btn-primary"/>
<input type="button" value="修改" onclick="toUpdate()" class="btn btn-primary"/>
<table class="layui-table" lay-skin="line" lay-size="sm">
    <tr>
        <th>id</th>
        <th>名称</th>
        <th>类型</th>
        <th>状态</th>
        <th>邮费</th>
        <th>商品图</th>
        <th>描述</th>
    </tr>
    <tbody id="tb"></tbody>
</table>
<div id = "page"></div>
</body>
</html>
