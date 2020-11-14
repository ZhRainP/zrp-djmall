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
    function search() {
        $.post(
            "<%=request.getContextPath()%>/product/productSkuList",
            {"productId":"${product.id}"},
            function(result){
                var html = "";
                var proSku = result.data;
                for (var i = 0; i < proSku.length; i++) {
                    html += "<tr>";
                    html += "<th>"+i+"</th>"
                    html += "<th>"+proSku[i].skuName+"</th>"
                    html += "<th>"+proSku[i].skuCount+"</th>"
                    html += "<th>"+proSku[i].skuPrice+"</th>"
                    html += "<th>"+proSku[i].skuRate+"</th>"
                    if(proSku[i].isDefault == 0){
                        html += "<th>是</th>"
                    }else{
                        html += "<th>不是</th>"
                    }
                    html += "</tr>"
                }
                $("#tab").html(html);
            })
    }
</script>
<form id="fmt">
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
    <div class="layui-input-block">
        分类：<select name="productType" id="code" disabled="disabled">
        <c:forEach items="${productType}" var="p">
        <option value="${p.code}"<c:if test="${p.code == product.productType}" >selected="selected"</c:if>>${p.dictionaryName}</option>
        </c:forEach>
    </select><br/>
    </div>


    SKU列表:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" value="修改器库存" onclick="toUpdateCount()" class="layui-btn layui-btn-sm"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" value="编辑" onclick="toUpdate()" class="layui-btn layui-btn-sm"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
    <input type="button" value = "修改" onclick="updateYes()" class="layui-btn layui-btn-sm"/>
    </select><br>
</form>
</body>
</html>
