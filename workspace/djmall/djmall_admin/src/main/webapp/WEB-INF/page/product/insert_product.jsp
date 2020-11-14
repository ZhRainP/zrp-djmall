<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/12
  Time: 20:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css" media="all">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>
<script type="text/javascript">
    $(function () {
        search();
    })
    function search(){
        $.post("<%=request.getContextPath()%>/product/attrList",
            {"productType": $("#code").val()},
            function (data) {
                var html = "";
                for (var i = 0; i < data.data.length; i++) {
                    html += "<tr>"
                    html += "<td>" + data.data[i].attrName + "</td>"
                    html += "<td>"
                    var s = data.data[i].attrValue.split(",")
                    for (var j = 0; j <s.length ; j++) {
                        html+="<input type='checkbox' value='"+s[j]+"'>"+s[j];
                    }
                    html += "</td>"
                    html += "</tr>"
                }
                $("#tbd").html(html);

            })
    }

    function addSku(){
        var s = "<input type='text' id='attrName' placeholder='属性名'><br>";
        s+="<input type='text' id=attrValue placeholder='属性值'>";
        s+="多个属性之间用,分开哦哦哦哦哦";
        layer.open({
            type: 1,
            title: '新增页面',
            shadeClose: false,
            shade: 0.8,
            area: ['380px', '90%'],
            btn: ["确定", "取消"],
            content: s ,
            yes:function (index,layero){
                var h = "<tr>"
                h += "<td>" + $("#attrName").val() ;
                h += "<input type='hidden' value='-1'>";
                h += "<input type='hidden' value='" + $("#attrName").val() + "'>";
                h += "</td>"
                h += "<td>"
                var value = $("#attrValue").val().split(",")
                for (var j = 0; j <value.length ; j++) {
                    h+="<input type='checkbox' value='-1,"+value[j]+"'>"+value[j];
                }
                h += "</td>"
                h += "</tr>"
                $("#tbd").append(h);
                layer.close(index);
            }
        });
    }

    function attrAdd() {
        //获取表格 tr
        var tb = $("#tbd tr")
        var checkValues = new Array();
        for (var i = 0; i < tb.length; i++) {
            //表格check=true
            var check = $(tb[i]).find(":checked")
            //非空判断
            if (check.length == 0) {
                continue;
            }
            var checkValue = new Array();
            for (var j = 0; j < check.length; j++) {
                // check的值
                checkValue.push(check[j].value);
            }
            checkValues.push(checkValue);
        }
        skuList = dkej(checkValues);
        var h = "";
        for (var q = 0; q <skuList.length ; q++) {
            h += "<tr>"
            h += "<td>"+q+"</td>"
            h += "<td><input type='text' readonly = 'readonly' name='skuList["+q+"].skuName' value='"+skuList[q]+"'/></td>"
            h += "<td><input type='text' value='10' name='skuList["+q+"].skuCount'></td>"
            h += "<td><input type='text' value='9999' name='skuList["+q+"].skuPrice'></td>"
            h += "<td><input type='text' value='0' name='skuList["+q+"].skuRate'></td>"
            h += "<td><input type='button' value='移除' onclick='del(this)' class=\"layui-btn layui-btn-sm\"/></td>"
            h += "</tr>"
        }
        $("#tab").append(h);

    }

    function del(val){
        $(val).parent().parent().remove();
    }


    function dkej(d) {// d = 二维数组
        var total = 1;
        for (var i = 0; i < d.length; i++) {
            total *= d[i].length;
        }
        var e = [];
        var itemLoopNum = 1;
        var loopPerItem = 1;
        var now = 1;
        for (var i = 0; i < d.length; i++) {
            now *= d[i].length;
            var index = 0;
            var currentSize = d[i].length;
            itemLoopNum = total / now;
            loopPerItem = total / (itemLoopNum * currentSize);
            var myIndex = 0;
            for (var j = 0; j < d[i].length; j++) {
                for (var z = 0; z < loopPerItem; z++) {
                    if (myIndex == d[i].length) {
                        myIndex = 0;
                    }
                    for (var k = 0; k < itemLoopNum; k++) {
                        e[index] = (e[index] == null ? "" : e[index] + ":") + d[i][myIndex];
                        index++;
                    }
                    myIndex++
                }
            }
        }
        return e;
    }

    function addDict() {
        $.ajax({
            type: "post",
            url: "<%=request.getContextPath() %>/product/insertProduct",
            data: new FormData($("#fmt")[0]),
            dataType: "json",
            contentType: false, // 不要设置Content-Type请求头
            processData: false, // 使数据不做处理
            success: function (result) {
                if(result.code == 200){
                    layer.msg(result.msg, {icon: 1,
                        time: 3000,shade:0.2},function(){
                        parent.location.reload();
                    });
                }
            }
        })

    }
</script>
<form id="fmt">
    <div class="layui-form-item">
        <label class="layui-form-label">名称：</label>
        <div class="layui-input-block">
            <input type="text" name="productName" required  lay-verify="required" placeholder="请输入名称" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-input-block">
    邮费：<select name="productFreight" lay-verify="">
        <option value="">请选择邮费</option>
        <c:forEach items="${freight}" var="p">
            <option value="${p.logisticsCompany} - ${p.freight}">${p.logisticsCompany} - ${p.freight}</option>
        </c:forEach>
    </select>
    </div>

    </select><br>
    <div class="layui-form-item layui-form-text">
    <label class="layui-form-label">描述：</label>
    <div class="layui-input-block">
        <textarea name="productDescription" placeholder="请输入内容" class="layui-textarea"></textarea>
    </div><br>
    </div>
    <div class="layui-input-block">
    分类：<select name="productType" id="code" onchange="search()">
    <option value="aaa">请选择</option>
    <c:forEach items="${productType}" var="p">
        <option value="${p.code}" >${p.dictionaryName}</option>
    </c:forEach>
    </select><br/>
    </div>


    SKU:&nbsp;&nbsp;<input type="button" value="+" onclick="addSku()" class="layui-btn layui-btn-sm"/>
    <input type="button" value="生成SKU" onclick="attrAdd()" class="layui-btn layui-btn-sm"/>
    <table class="layui-table" lay-skin="line" lay-size="sm">
        <tr>
            <th>属性名</th>
            <th colspan="10">属性值</th>
        </tr>
        <tbody id="tbd"></tbody>
    </table><br>
    <div id="appendSku" style="width: 100%;" >生成后SKU：
        <table class="layui-table"  lay-size="sm">
            <colgroup>
                <col width="300">
                <col width="800">
                <col width="500">
                <col width="300">
            </colgroup>
            <tr>
                <th>编号</th>
                <th>sku属性</th>
                <th>库存</th>
                <th>价格（元）</th>
                <th>折扣（%）</th>
                <th>操作</th>
            </tr>
            <tbody id="tab">
            </tbody>
        </table>
    </div>
    <input type="button" value = "新增" onclick="addDict()" class="layui-btn layui-btn-sm"/>
</select><br>
</form>
</body>
</html>
