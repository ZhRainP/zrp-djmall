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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
<script type="text/javascript">
    $(function () {
        var describes = '${product.productDescription}';
        $("#describes").val(describes);
        search();
    })
    function search() {
        $.post(
            "<%=request.getContextPath()%>/product/productSkuList",
            {"productId":"${product.id}"},
            function(result){
                var html = "";
                var proSku = result.data;
                for (var i = 0; i < proSku.length; i++) {
                    html += "<tr>";
                    html += "<td><input type='checkbox' value='" + proSku[i].id +","
                        + proSku[i].skuCount + ","+ i +"'/></td>";
                    html += "<td><input type='text' value='"+ proSku[i].skuName +"' id='skuName_"+i+"'" +
                        " readonly='readonly' name='list["+i+"].skuName'/></td>";
                    html += "<td><input type='text' value='"+ proSku[i].skuCount +"' id='skuCount_"+i+"'" +
                        " readonly='readonly' name='list["+i+"].skuCount'/></td>";
                    html += "<td><input type='text' value='"+ proSku[i].skuPrice +"' id='skuPrice_"+i+"'" +
                        " readonly='readonly' name='list["+i+"].skuPrice'/></td>";
                    html += "<td><input type='text' value='"+ proSku[i].skuRate +"' id='skuRate_"+i+"'" +
                        " readonly='readonly' name='list["+i+"].skuRate'/></td>";
                    if(proSku[i].isDefault == 0){
                        html += "<td>是</td>";
                    }else{
                        html += "<td>不是</td>";
                    }
                    /*html += "<td><input type='button' value='"+ proSku[i].skuStatusShow +"'" +
                        " onclick='updateStatus("+ proSku[i].skuId +")'/></td>";*/
                    html += "<td><input type='hidden' value='"+ proSku[i].skuId +"'" +
                        " name='list["+i+"].skuId'/></td>";
                    html += "</tr>";
                }
                $("#tab").html(html);
            })
    }

    /* 修改 */
    function updateProduct(){
        var index = layer.load(2);
        var formData = new FormData($("#fmt")[0]);
        $.ajax({
            url: "<%=request.getContextPath()%>/product/updateProduct",
            dataType: 'json',
            type: 'post',
            data: formData,
            processData: false, // 使数据不做处理
            contentType: false,
            success:function (data){
                if (data.code == 200) {
                    layer.msg(data.msg, {
                        icon: 6,
                        time: 1000 //2秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        layer.close(index);
                        window.location.href = "<%=request.getContextPath()%>/product/toList";
                    })
                    return;
                }
                layer.msg(data.msg, {
                    icon: 5,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                }, function () {
                    layer.close(index);
                    window.location.reload();
                    return;
                })
            }}
        )
    }

    function toUpdateEdits(){
        var skuCount = $("#counts").val();
        var skuPrice = $("#price").val();
        var skuRate = $("#rate").val();
        var i = $("#ii").val();
        $("#skuCount_"+i).val(skuCount);
        $("#skuPrice_"+i).val(skuPrice);
        $("#skuRate_"+i).val(skuRate);
        $("#myModals").modal('hide');
    }
    function edits(){
        var index = layer.load(2);
        var checked = $("#tab :checked");
        if (checked.length > 1) {
            layer.msg('只能选一个', {time: 1000});
            layer.close(index);
            return;
        }
        if (checked.length == 0) {
            layer.msg('请选择一个', {time: 1000});
            layer.close(index);
            return;
        }
        var check = checked[0].value.split(",");
        var i = check[2];
        var skuName = $("#skuName_"+i).val();
        var skuCount = $("#skuCount_"+i).val();
        var skuPrice = $("#skuPrice_"+i).val();
        var skuRate = $("#skuRate_"+i).val();
        $("#skuNames").val(skuName);
        $("#counts").val(skuCount);
        $("#price").val(skuPrice);
        $("#rate").val(skuRate);
        $("#ii").val(i);
        $("#myModals").modal('show');
        layer.close(index);
    }
    function toUpdateCount(){
        var count = $("#count").val();
        var i = $("#i").val();
        $("#skuCount_"+i).val(count);
        $("#myModal").modal('hide');
    }

    /* 修改库存 */
    function updateCount() {
        var index = layer.load(2);
        var checked = $("#tab :checked");
        if (checked.length > 1) {
            layer.msg('只能选一个', {time: 1000});
            layer.close(index);
            return;
        }
        if (checked.length == 0) {
            layer.msg('请选择一个', {time: 1000});
            layer.close(index);
            return;
        }
        var check = checked[0].value.split(",");
        var count = check[1];
        var i = check[2];
        $("#count").val(count);
        $("#i").val(i);
        $("#myModal").modal('show');
        layer.close(index);
    }
    function ass(val){
        var count = $("#count").val()
        if(val == 0){
            count--;
        }else{
            count++;
        }
        $("#count").val(count);
    }

    /* 设为默认 */
    function updateDefault() {
        var index = layer.load(2);
        var checked = $("#tab :checked");
        if (checked.length > 1) {
            layer.msg('只能选一个', {time: 1000});
            layer.close(index);
            return;
        }
        if (checked.length == 0) {
            layer.msg('请选择一个', {time: 1000});
            layer.close(index);
            return;
        }
        var checks = checked[0].value.split(",");
        var id = checks[0];
        $.post(
            "<%=request.getContextPath()%>/product/updateDefault",
            {"id": id, "productId": '${product.id}'},
            function (data) {
                if (data.code == 200) {
                    layer.msg(data.msg, {
                        icon: 6,
                        time: 1000 //2秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        layer.close(index);
                        window.location.reload();
                    })
                    return;
                }
                layer.msg(data.msg, {
                    icon: 5,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                }, function () {
                    layer.close(index);
                    window.location.reload();
                    return;
                })
            }
        )
        layer.close(index);
    }
</script>

<form id="fmt">
    <input type="hidden" value="${product.id}" name="productId">
    <div class="layui-form-item">
        <label class="layui-form-label">名称：</label>
        <div class="layui-input-block">
            <input type="text" name="productName" value="${product.productName}" required  lay-verify="required" placeholder="请输入名称" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-input-block">
        邮费：<select name="productFreight"  lay-verify="">
        <c:forEach items="${freight}" var="f">
            <option value="${f.logisticsCompany} - ${f.freight}" <c:if test="${f.logisticsCompany} - ${f.freight} == ${f.logisticsCompany} - ${f.freight}">selected="selected"</c:if>>
                    ${f.logisticsCompany} - ${f.freight}</option>
        </c:forEach>
    </select>
    </div>

    </select><br>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">描述：</label>
        <div class="layui-input-block">
            <textarea name="productDescription"  placeholder="请输入内容" class="layui-textarea">${product.productDescription}</textarea>
        </div><br>
    </div>

    <img src="http://qjrxidgn4.hb-bkt.clouddn.com/${product.productImg}" width="50px" height="50px"/>
    <input type="file" name="img">

    <div class="layui-input-block">
        分类：<select name="productType" id="code" disabled="disabled">
        <c:forEach items="${productType}" var="p">
        <option value="${p.code}"<c:if test="${p.code == product.productType}" >selected="selected"</c:if>>${p.dictionaryName}</option>
        </c:forEach>
    </select><br/>
    </div>


    SKU列表:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" value="修改库存" onclick="updateCount()" class="layui-btn layui-btn-sm"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" value="编辑" onclick="edits()" class="layui-btn layui-btn-sm"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" value="设为默认" onclick="updateDefault()" class="layui-btn layui-btn-sm"/>
    <div id="appendSku" style="width: 100%;" >
        <table class="layui-table"  lay-size="sm">
            <tr>
                <th>编号</th>
                <th>sku属性</th>
                <th>库存</th>
                <th>价格（元）</th>
                <th>折扣（%）</th>
                <th>是否默认</th>
            </tr>
            <tbody id="tab">
            </tbody>
        </table>
    </div>
    <input type="button" value = "修改" onclick="updateProduct()" class="layui-btn layui-btn-sm"/>
    </select><br>
</form>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <input type="button" value="-" onclick="ass(0)"/>
                <input type="text" readonly="readonly" id="count"/>
                <input type="button" value="+" onclick="ass(1)"/>
                <input type="hidden" id="i">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="toUpdateCount()">提交</button>
            </div>
        </div>
    </div>
</div>

<!-- Button trigger modal -->

<!-- Modal -->
<div class="modal fade" id="myModals" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body">
                SKU属性<input type="text" id="skuNames" disabled="disabled"/><br/>
                库存<input type="text" id="counts"/><br/>
                价格<input type="text" id="price"/><br/>
                折扣<input type="text" id="rate"/><br/>
                <input type="hidden" id="ii"><br/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="toUpdateEdits()">提交</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
