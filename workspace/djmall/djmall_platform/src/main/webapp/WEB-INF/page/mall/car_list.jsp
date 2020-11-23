<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>购物车</title>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/js/token.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/js/cookie.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css" media="all">
</head>

<script type="text/javascript">

    $(function (){
        money();

    })
    //点击复选框后生成金额信息
    function money() {
        obj = document.getElementsByName("id");
        var allprice = 0;//商品总金额
        var allnewPrice = 0;//折后总金额
        var carrprice = 0;//总运费
        var allmoney = 0;//应付金额
        var num = 0;//勾选商品总数量=
        for (i = 0; i < obj.length; i++) {
            if (obj[i].checked) {
                //根据选中的复选框的购物车编号来取值
                var price = $("#pr" + obj[i].value).val(); //原价
                var newPrice = $("#npr" + obj[i].value).val();  //现价
                // var proPos = $("#car" + obj[i].value).val() * 1; //邮费
                var pnum = $("#num" + obj[i].value).val() * 1; //购买数量
                var oneAllPrice = price * pnum; //单个商品原价总金额   商品原价*商品数量
                var oneAllNewPri = newPrice * pnum;//单个商品折扣价总金额   折扣价*商品数量
                allprice += oneAllPrice;
                allnewPrice += oneAllNewPri;
                // carrprice += proPos;
                num += pnum;
            }
        }
        allmoney = allnewPrice + carrprice;
        //选中的数量
        var a = $("input[name='id']:checked").length;
        $("#di").html("选择了件" + num + "商品,总商品金额" + allprice + "元，折后金额" + allnewPrice + "元，应付总额：" + allmoney + "元")
        //给上面赋值
        $("#totalMoney").val(allprice);     //订单总金额
        $("#totalPayMoney").val(allmoney);  //实付总金额
        $("#totalFreight").val(carrprice);   //总运费
        $("#totalBuyCount").val(num);  //总购买数量
        $("#allnewPrice").val(allnewPrice);  //总折后金额
    }

    function check(id, checkStatus) {
        token_post(
            "<%=request.getContextPath()%>/user/updateStatus",
            {"carId": id, "checkStatus": checkStatus},
            function (data) {
                if (data.data == 200) {
                    window.location.reload();
                }
            }
        )
        var a = $("input[name='id']:checked").length;
        money();
    }
    //全选
    function checkAll() {
        var checkedOfAll = $("#quanxuan").prop("checked");
        $("input[name='id']").prop("checked", checkedOfAll);
        money();
    }

    /*后悔了不想要了*/
    function delShopCar(id) {
        $.post("<%=request.getContextPath()%>/user/delCarByCarId?carId="+id,function(result){
            if(result.code == 200){
                location.reload();
            }
        })
        <%--location.href = "<%=request.getContextPath()%>/user/delCarByCarId?carId="+id;--%>
        <%--location.reload();--%>
    }

    /* 返回首页 */
    function banck () {
        window.location.href = "<%=request.getContextPath()%>/mall/toIndex"
    }

    function toSet () {
        location.href = "<%=request.getContextPath()%>/user/toSet?TOKEN="+getToken();
    }
</script>

<body>
<form id = "fm">
<input type="button" value = "返回" onclick="banck()" class="layui-btn layui-btn-xs"/>
全选：<input type="checkbox" onclick="checkAll()" id="quanxuan">&nbsp;&nbsp;&nbsp;<input type="button" onclick="del()"
                                                                                      value="删除选中商品"
                                                                                      class="layui-btn layui-btn-primary layui-btn-radius"><br><span></span><br>
<c:forEach var="allCart" items="${productDTO}" varStatus="index">
    <fieldset style="width: 500px">
        <legend>商品信息</legend>
        <input type="checkbox"
        <c:if test="${allCart.checkStatus == 1}"> checked</c:if>
        <c:if test="${allCart.skuCount == 0}"> disabled="disabled"</c:if>
               value="${allCart.id}" name="id" class="qx"
               onclick="check(${allCart.id}, ${allCart.checkStatus})">
        购物车编号：${allCart.id}<br>
        名称： ${allCart.productName}&nbsp;&nbsp;&nbsp;
        原价：${allCart.skuPrice} <br><span></span><br>
        属性：${allCart.skuName} &nbsp;&nbsp;&nbsp;
        折扣：${allCart.skuRate} &nbsp;&nbsp;&nbsp;
        数量：<input type="button" value="加"
        <c:if test="${allCart.count == 0}"> disabled="disabled"</c:if> onclick="add(${allCart.id})">
        <input type="text" onblur="onb(${allCart.id})" readonly="readonly" style="width: 35px" value="${allCart.count}"
               id="num${allCart.id}" name="productList[${index.index}].count"  <c:if
                test="${allCart.count == 0}"> disabled="disabled"</c:if>>
        <input type="button" value="减"
        <c:if test="${allCart.count == 0}"> disabled="disabled"</c:if> onclick="sub(${allCart.id})">
                       库存：${allCart.skuCount}
        <span id="skuCountMsg${allCart.id}" style="color: red"></span>
        <br><span></span><br>
        邮费：
        ${allCart.productFreight}&nbsp&nbsp&nbsp
        折后单价：<c:if test="${allCart.skuRate == 0}"> <span style='color: #009688'><fmt:formatNumber
            value="${allCart.skuPrice}" maxFractionDigits="2"/></span><br><br></c:if>
        <c:if test="${allCart.skuRate != 0}"><span style="color: red"><fmt:formatNumber
                value="${allCart.skuPrice*(allCart.skuRate/100)}"
                maxFractionDigits="2"/></span><br><br></c:if>
        <input type="hidden" value="${allCart.count}" id="count${allCart.id}">
            <%--原价--%>
        <input type="hidden" value="${allCart.skuPrice}" id="pr${allCart.id}">
            <%--折后价格--%>
        <input type="hidden" value="<c:if test="${allCart.skuRate == '0'}">${allCart.skuPrice}</c:if>
        <c:if test="${allCart.skuRate != '0'}">${allCart.skuPrice*(allCart.skuRate*0.01)}</c:if>"
               id="npr${allCart.id}">
            <%--邮费--%>
        <input type="hidden" value="${allCart.productFreight}" id="car${allCart.id}">
        <a href="#" onclick="delShopCar(${allCart.id})">后悔了，不要了</a>
    </fieldset>
</c:forEach><br><span></span><br>
<div id="di"></div>
<input type="button" value="去结算" onclick="toSet()"
       class="layui-btn layui-btn-primary layui-btn-radius"><br><span></span><br>
</body>
</form>
</html>
