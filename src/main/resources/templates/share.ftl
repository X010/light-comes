<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>${title!""}</title>
    <link href="${baseUrl}css/sign.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="${baseUrl}js/jquery.min.js"></script>
    <link rel="stylesheet" href="${baseUrl}ratchet/weui.css" type="text/css">
    <script type="text/javascript" src="${baseUrl}ratchet/jquery-weui.js"></script>
</head>
<body>
<div class="container" id="cont">
    <div class="title">
    <#if pt.photo!=''>
        <img src="http://www.qubulikou.com/${pt.photo!""}"/>
    <#else>
        <img src="${baseUrl}images/bgReplace.jpg"/>
    </#if>
    </div>
    <div class="md">
        <div class="mid-top">
            <div class="mid-left">
                <p class="drink">酒瓶总量</p>

                <p class="ml"><span id="tt_drunk"></span>ml</p>
            </div>
            <div class="mid-right">
                <p class="drink">今日好友喝掉</p>
                <p class="ml"><span id="oy_drunk"></span>ml</p>
            </div>
        </div>
        <div class="other">
            <p>目前酒瓶容量</p>
            <p style="font-weight: bold;"><span id="toy_drunk"></span>ml</p>
        </div>
        <div class="help">
            <input type="button" id="otherchess" value="我来帮忙干杯" class="otherchess" style="background-color: #FFB046;" onclick="changeNum();"/>
            <input type="button" value="我也要领酒水券" class="otherchess" style="background-color: #89CF46;" onclick="javascript:window.location.href='${baseUrl}pt/past.action';"/>
        </div>
    </div>
</div>
<script language="JavaScript" type="text/javascript">
    $(document).ready(function () {
        loadAjax();
    });
    function getUrlParam(name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
                var r = window.location.search.substr(1).match(reg);  //匹配目标参数
                if (r != null) {
                return unescape(r[2]);
                return null;
                } //返回参数值
            }
    var phoneNum=getUrlParam("phone");
    console.log(phoneNum);
    function changeNum() {
            $.ajax({
            type: 'GET',
            url: 'other_past.action?phone='+phoneNum,
            timeout: 10000,
            dataType: 'json',
            success: function (re_json) {
                data = re_json.data;
                if (data.today_other_times < 1) {
           		    $.alert("今天干杯次数已用完");
                }else{
                $("#tt_drunk").text(data.total_drunk);
                $("#oy_drunk").text(data.today_other_drunk);
                    $.alert("今日为朋友干杯"+data.today_other_drunk+"ml酒量，真给力！");
                    if(data.total_drunk-(data.today_other_drunk+data.today_drunk)<=0){
                        $("#toy_drunk").text(0);
                        $("#otherchess").attr("style","background-color: #80807b;");
                        $("#otherchess").attr("value","已帮朋友干杯");
                        $("#otherchess").attr("disabled",true);
                    }else {
                        $("#toy_drunk").text(data.total_drunk - (data.today_other_drunk+data.today_drunk));
                        $("#otherchess").attr("style","background-color: #80807b;");
                        $("#otherchess").attr("value","已帮朋友干杯");
                        $("#otherchess").attr("disabled",true);
                    }
                }
		 },
            complete: function (XMLHttpRequest, status) { //请求完成后最终执行参数
                if (status == 'timeout') {//超时,status还有success,error等值的情况
                    //ajaxTimeoutTest.abort();
                    alert("超时");
                }
                if (status == "parsererror") {
                    isload = false;
                    console.log("pasererror");
                }
            }
        });
    }

    function loadAjax() {
        $.ajax({
            type: 'GET',
            url: 'other_info.action?phone='+phoneNum,
            timeout: 10000, //超时时间设置，单位毫秒
            data: "ac=index_data",
            dataType: 'json',
            success: function (re_json) {
                data = re_json.data;
                $("#tt_drunk").text(data.total_drunk);
                $("#oy_drunk").text(data.today_other_drunk);
                if(data.total_drunk-(data.today_other_drunk+data.today_drunk)<=0){
                    $("#toy_drunk").text(0);
                    $("#otherchess").attr("style","background-color: #80807b;");
                    $("#otherchess").attr("value","酒瓶已经空了");
                    $("#otherchess").attr("disabled",true);
                }else {
                    $("#toy_drunk").text(data.total_drunk - (data.today_other_drunk+data.today_drunk));
                }
                if(data.today_times>0){
                    $("#otherchess").attr("style","background-color: #80807b;");
                    $("#otherchess").attr("value","已帮朋友干杯");
                    $("#otherchess").attr("disabled",true);
                }
            },
            complete: function (XMLHttpRequest, status) { //请求完成后最终执行参数
                if (status == 'timeout') {//超时,status还有success,error等值的情况
                    //ajaxTimeoutTest.abort();
                    alert("超时");
                }
                if (status == "parsererror") {
                    isload = false;
                    console.log("pasererror");
                }
            }
        });
    }
</script>
</body>
</html>
