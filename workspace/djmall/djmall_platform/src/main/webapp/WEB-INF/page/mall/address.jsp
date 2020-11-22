<%--
  Created by IntelliJ IDEA.
  User: SANSUNG-GALAXY
  Date: 2020/11/20
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath() %>\static\jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layer\layer.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/js/token.js"></script>
    <script type="text/javascript" src = "<%=request.getContextPath()%>/static/js/cookie.js"></script>
    <!--鼠标特效-->
    <script src="<%=request.getContextPath()%>/static/js/click.js"></script>
    <canvas width="1777" height="841" style="position: fixed; left: 0px; top: 0px; z-index: 2147483647; pointer-events: none;"></canvas>
    <!--鼠标特效 end-->
</head>
<script type="text/javascript">
    $(function (){
        $.post(
            "<%=request.getContextPath() %>/user/areaLian",
            {"pId":0},
            function (data){
                var html = "";
                for(var i=0;i<data.data.length;i++){
                    html+="<option value='"+data.data[i].id+"'> "+data.data[i].areaName+"</option> ";
                }
                $("#sel_province").append(html);
                $("#sel_province").change(function (){
                    $("#sel_city").html("");
                    $("#sel_area").html("");
                    getCity($(this).val());
                })
                $("#sel_city").get(0).selectedIndex=0;
                getArea($("#sel_city").val());
            }
        )
    })
    function getCity(id){
        $.post(
            "<%=request.getContextPath()%>/user/areaLian",
            {"pId":id},
            function (data){
                var html="";
                for(var i=0;i<data.data.length;i++){
                    html+="<option value="+data.data[i].id+">"+data.data[i].areaName+"</option>"
                }
                $("#sel_city").append(html);
                $("#sel_city").change(function (){
                    $("#sel_area").html("");
                    getArea($(this).val());
                })
                $("#sel_city").get(0).selectedIndex=0;
                getArea($("#sel_city").val());
            }
        )
    }

    function getArea(id){
        $.post(
            "<%=request.getContextPath()%>/user/areaLian",
            {"pId":id},
            function (data){
                var html="";
                for(var i=0;i<data.data.length;i++){
                    html+="<option value="+data.data[i].id+">"+data.data[i].areaName+"</option>"
                }
                $("#sel_area").append(html);
            }
        )
    }

    function add () {
        var selProvince = $('#sel_province option:selected').text();
        var city = $('#sel_city option:selected').text();
        var area = $('#sel_area option:selected').text();
        $("#selProvince").val(selProvince);
        $("#city").val(city);
        $("#area").val(area);
        $.post("<%=request.getContextPath()%>/user/addAddress?TOKEN=" + getToken(),
            $("#fm").serialize(),
            function(result){
            if(result.code == 200){
                layer.msg(result.msg, {
                    skin: 'layui-layer-molv' //样式类名
                    ,closeBtn: 0
                    ,shade: 0.3
                }, function(){parent.window.location.reload();});
            }else{
                layer.msg(data.msg, {time: 2000});
            }
        })
    }
</script>
<body>
<form id="fm">
    <input type="hidden" name="provice" id="selProvince" />
    <input type="hidden" name="city" id="city" />
    <input type="hidden" name="counties" id="area" />
    收件人：<input type="text" name="userName"><br/>
    手机号：<input type="text" name="phone"><br/>
    <div class="layui-form-item">
        省：<select  id="sel_province" >
            <option value="">请选择</option>
        </select>
        市：<select  id="sel_city"> </select>

        县：<select id="sel_area" class="required"></select><br/>
    </div>
    详细位置：<input type="text" name="address"><br/>
    <input type="button" value = "提交" onclick="add()" class="layui-btn layui-btn-xs"/>
</form>
</body>
</html>
