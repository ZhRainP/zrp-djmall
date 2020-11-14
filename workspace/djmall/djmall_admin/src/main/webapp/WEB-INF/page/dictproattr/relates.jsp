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
    $(function () {
        search();
    })
    function search() {
        $.post(
            "<%=request.getContextPath()%>/attr/attrList",
            function (data) {
                var html = "";
                var productAttr = data.data;
                for (var i = 0; i < productAttr.length; i++) {
                    html += "<tr>"
                    html += '<td><input type="checkbox"  name="id" value="' + productAttr[i].id + '"/>'+"&nbsp;&nbsp;" + productAttr[i].id + '</td>';
                    html += "<td>" + productAttr[i].attrName + "</td>";
                    html += "<td>" + productAttr[i].attrValue + "</td>";
                    html += "</tr>"
                }
                $("#tb").html(html);

                var checkeds = '${ids}';
                for(var i=0;i<checkeds.length;i++){
                    //alert(checkeds[i])
                }
                //拆分为字符串数组
                var checkArray =checkeds.split(",");
                var checkBoxAll = $("input[name='id']");
                for(var i=0;i<checkArray.length;i++){
                    $.each(checkBoxAll,function(j,checkbox){
                        var checkValue=$(checkbox).val();
                        if(checkArray[i]==checkValue){
                            $(checkbox).attr("checked",true);
                        }
                    })
                }


            },
        )
    }

    /* 字典修改 */
    function add() {
        var checked = ($("#tb :checked"));
        var ids =" ";
        for (var i = 0; i < checked.length; i++) {
            ids += checked[i].value + ",";
        }
        ids = ids.substr(0,ids.length-1);
        $.post(
            "<%=request.getContextPath()%>/attr/insertSku",
            {"ids": ids, "productType": $("#code").val()},
            function (data) {
                layer.msg(data.msg, {icon: 6, time: 1000}, function (index) {
                    parent.location.href = "<%=request.getContextPath()%>/attr/toSkuList";
                });
            })
    }
</script>
<body>
<input type="button" value="保存" onclick="add()" class='btn btn-primary'/><br/>
<table class="layui-table" lay-skin="line" lay-size="sm">
    <tr>
        <td>编号</td>
        <td>属性名</td>
        <td>属性值</td>
    </tr>
    <tbody id="tb"></tbody>
</table>
<input type="hidden" value="${code}" id="code"/>
</body>
</html>
