<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>签到</title>
    <link href="/css/sign.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <link rel="stylesheet" href="/ratchet/weui.css" type="text/css">
    <script type="text/javascript" src="/ratchet/jquery-weui.js"></script>
</head>
<body>
<div class="cont" id="cont">
    <div class="md">
        <div class="mid-top">
            <div class="mid-left">
                <p class="drink">水桶总量</p>

                <p class="ml"><span id="tt_drunk">2000</span>ml</p>
            </div>
            <div class="mid-right">
                <p class="drink">今日好友灌水</p>
                <p class="ml"><span id="oy_drunk">147.09</span>ml</p>
            </div>
        </div>
        <div class="other">
            <p>你给与别人干杯</p>
            <p style="font-weight: bold;">147.09ml</p>
        </div>
        <div class="help">
            <input type="button" value="我来帮忙灌水" class="otherchess" style="background-color: #FFB046;" onclick=""/>
            <input type="button" value="我也要领酒水券" class="otherchess" style="background-color: #89CF46;" onclick=""/>
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
    var phone=getUrlParam("phone");
    function changeNum() {
            $.ajax({
            type: 'GET',
            url: '/pt/other_past.action?phone='+phone,
            timeout: 10000,
            dataType: 'json',
            success: function (re_json) {
                data = re_json.data;
                if (data.today_have_times < 1) {
           		$.confirm("今天干杯次数已用完，点击确定分享给朋友", function() {
           		  //点击确认后的回调函数
           		  sharewx();
           		    }, function() {
           		      //点击取消后的回调函数
           		        });
           		       }
           	else{
                $("#td_drunk").text(data.today_drunk);
                $("#cy_drunk").text(data.cycle_drunk);
                $("#tdu_drunk").text(data.today_drunk);
                $("#tdo_drunk").text(data.today_other_drunk);
                var drunk = data.total_drunk - data.cycle_drunk;
                var gauge = loadLiquidFillGauge("fillgauge", 120, config);
                gauge.update(drunk);
                $.alert("今日已签到！今日干杯获得"+data.today_drunk+"ml酒量，继续加油哦！");
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
            url: '/pt/info.action',
            timeout: 10000, //超时时间设置，单位毫秒
            data: "ac=index_data",
            dataType: 'json',
            async: false,
            success: function (re_json) {
                data = re_json.data;
                $("#td_drunk").text(data.today_drunk);
                $("#cy_drunk").text(data.cycle_drunk);
                $("#tdu_drunk").text(data.today_drunk);
                $("#tdo_drunk").text(data.today_other_drunk);
                var drunk = data.total_drunk - data.cycle_drunk;
                maxValue = data.total_drunk;
                console.log(maxValue);
                config.maxValue = maxValue;//总容量
                var gauge = loadLiquidFillGauge("fillgauge", 120, config);
                gauge.update(drunk);
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
        return maxValue;
    }
    var share = document.getElementById("shareit");
       function sharewx(){
      	share.style.display = 'block';
       };
       function close_sharewx(){
       	share.style.display = 'none';
       };
</script>
</body>
</html>
