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
        debugger
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
        $("#di").html("选择了件" + '<span style="color: #ef0d07;font-size:24px;font-weight:bold">'+num+' </span>' + "商品,总商品金额" + allprice + "元，折后金额" + allnewPrice + "元，应付总额：" + '<span style="color: #ef0707;font-size:24px;font-weight:bold">'+allmoney+' </span>' + "元")
        //给上面赋值
        $("#totalMoney").val(allprice);     //订单总金额
        $("#totalPayMoney").val(allmoney);  //实付总金额
        $("#totalFreight").val(carrprice);   //总运费
        $("#totalBuyCount").val(num);  //总购买数量
        $("#allnewPrice").val(allnewPrice);  //总折后金额
    }

    /* 返回首页 */
    function banck () {
        window.location.href = "<%=request.getContextPath()%>/mall/toIndex"
    }

    function submitOrder () {
        $.post(
            "<%=request.getContextPath()%>/order/insertOrder?TOKEN=" + getToken(),
            $("#fm").serialize(),
            function(result){
                if(result.code == 200){
                    alert(1);
                }
            })
    }
</script>

<body>

<br/><span></span><br/>
<input type="button" value = "返回" onclick="banck()" class="layui-btn layui-btn-xs"/>
<form id = "fm">
    收货人信息：<select name="addressId">
        <c:forEach var="a" items="${address}">
            <option value="${a.addressId}">${a.userName}-${a.phone}-${a.provice}-${a.city}-${a.counties}-${a.address}</option>
        </c:forEach>
    </select>
     <c:forEach var="c" items="${car}" varStatus="index">
    <fieldset style="width: 500px">
        <legend>商品信息</legend>

        <input type="checkbox"
        <c:if test="${c.checkStatus == 1}"> checked</c:if>
        <c:if test="${c.skuCount == 0}"> disabled="disabled"</c:if>
               value="${c.id}" name="id" class="qx" onclick="check(${c.id}, ${c.checkStatus})" style="display:none">
        名称： ${c.productName}&nbsp;&nbsp;&nbsp;
        原价：${c.skuPrice} <br><span></span><br>
        属性：${c.skuName} &nbsp;&nbsp;&nbsp;
        折扣：${c.skuRate} &nbsp;&nbsp;&nbsp;
        数量：<input type="text" onblur="onb(${c.id})" readonly="readonly" style="width: 35px" value="${c.count}"
               id="num${c.id}" name="productList[${index.index}].count"  <c:if
                test="${cookie.count == 0}"> disabled="disabled"</c:if>><br>
        邮费：
            ${c.productFreight}&nbsp&nbsp&nbsp
        折后单价：<c:if test="${c.skuRate == 0}"> <span style='color: #009688'><fmt:formatNumber
            value="${c.skuPrice}" maxFractionDigits="2"/></span><br><br></c:if>
        <c:if test="${c.skuRate != 0}"><span style="color: red"><fmt:formatNumber
                value="${c.skuPrice*(c.skuRate/100)}"
                maxFractionDigits="2"/></span><br><br></c:if>
        <input type="hidden" value="${c.count}" id="count${c.id}">
            <%--原价--%>
        <input type="hidden" value="${c.skuPrice}" id="pr${c.id}">
            <%--折后价格--%>
        <input type="hidden" value="<c:if test="${c.skuRate == '0'}">${c.skuPrice}</c:if>
        <c:if test="${c.skuRate != '0'}">${c.skuPrice*(c.skuRate*0.01)}</c:if>"
               id="npr${c.id}">
            <%--邮费--%>
        <input type="hidden" value="${c.productFreight}" id="car${c.id}">
    </fieldset>
    </c:forEach><br><span></span><br>
    <div id="di"></div>
    <span style ='white-space:pre;'>
        支付方式：<select name="payType">
        <c:forEach var="d" items="${dictList}">
            <option value="${d.code}">${d.dictionaryName}</option>
        </c:forEach>
    </select>
    </span>
</form>
<input type="button" value="提交订单" onclick="submitOrder()" class="layui-btn layui-btn-radius layui-btn-normal"/>

</body>
</html>
