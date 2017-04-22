<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>签到</title>
    <link href="${baseUrl}css/sign.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="${baseUrl}js/jquery.min.js"></script>
    <script type="text/javascript" src="${baseUrl}js/laytpl.js"></script>
    <link rel="stylesheet" href="${baseUrl}ratchet/weui.css" type="text/css">
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript" src="${baseUrl}ratchet/jquery-weui.js"></script>
</head>
<body>
<div class="container" id="cont">
    <div class="mid">
        <div class="mid-top">
            <div class="mid-left">
                <p class="drink">今天喝掉</p>
                <p class="ml"><span id="td_drunk"></span>ml</p>
            </div>
            <div class="mid-right">
                <p class="drink">当前喝掉</p>

                <p class="ml"><span id="cy_drunk"></span>ml</p>
            </div>
        </div>
        <div class="bottle">
            <img src="${baseUrl}images/cover.png" class="cover" alt=""/>
            <svg id="fillgauge" width="50%" height="120" onclick="gauge.update(NewValue());">
		<p class="now">当前容量</p>	
	    </svg>
        </div>
    </div>
    <div class="bottom">
        <div class="bottom-box">
            <div class="bottom-d">
                <p class="drink">今日您已喝掉</p>

                <p class="mll"><span id="tdu_drunk"></span>ml</p>
            </div>
            <div class="bottom-d">
                <p class="drink">朋友已帮你喝掉</p>
                <p class="mll"><span id="tdo_drunk"></span>ml</p>
            </div>
        </div>
    </div>
    <input type="button" id="chess" value="我也要干杯" class="chess" onclick="changeNum();"/>
    <div class="good">
	<p>今日好人榜</p>
	<div class="goodBottom">
	    <p>今日没有好友干杯</p>
	    <input type="button" value="召唤朋友帮您喝掉" class="call" onclick="sharewx();"/>
	</div>
    </div>
    <div>
 	
    </div>
    <#--<div class="msgn">-->
        <#--<p style="width: 100%;">-->
        <#--<span class="msgbold">活动规则说明：<span>-->
        <#--<span>-->
        <#--<#if info??>-->
        <#--${info}-->
        <#--<#else>-->
            <#--无-->
        <#--</#if>-->
        <#--</span>-->
        <#--</p>-->
    <#--</div>-->
</div>
		<div id="shareit" onclick="close_sharewx()">
			<img class="arrow" src='${baseUrl}images/share.jpg'/>
			<a href="#" id="follow"><p id="share-text"></p></a>
		</div>
<script src="${baseUrl}js/d3.v3.min.js" language="JavaScript"></script>
<script src="${baseUrl}js/liquidFillGauge.js" language="JavaScript"></script>
<script language="JavaScript" type="text/javascript">
    $(document).ready(function () {
        var maxValue;
        maxValue = loadAjax();
    });
    var config = liquidFillGaugeDefaultSettings();
    config.circleThickness = 0;
    config.circleColor = "#ED1E37";
    config.textColor = "#ED1E37";
    config.waveTextColor = "#FD8F94";
    config.waveColor = "#FFDDDD";
    config.textVertPosition = 0.8;
    config.waveAnimateTime = 2000;
    config.waveHeight = 0.1;
    config.waveAnimate = true;
    config.waveCount = 2;
    config.waveOffset = 0.5;
    config.textSize = .5;
    config.minValue = 0;
    config.displayPercent = false;
    var gauge;
    wx.config({
        debug: false,
        appId: '${app_id}',
        timestamp: ${timestamp!},
        nonceStr: '${nonceStr!}',
        signature: '${signature!}',
        jsApiList: [
            'onMenuShareTimeline',
            'onMenuShareAppMessage',
            'onMenuShareQQ',
            'onMenuShareWeibo',
            'onMenuShareQZone'
        ]
    });
    wx.ready(function () {
    wx.onMenuShareTimeline({
        title: '${pt.share_title}', // 分享标题
        link: 'http://www.qubulikou.com/qblk/pt/share.action?phone='+${phone}, // 分享链接
        imgUrl: 'http://www.qubulikou.com/qblk/photo/${pt.share_photo}' // 分享图标
        });
    wx.onMenuShareAppMessage({
	title:'${pt.share_title}',
	desc:'${pt.share_desc}',
	link:'http://www.qubulikou.com/qblk/pt/share.action?phone='+${phone},
	imgUrl:'http://www.qubulikou.com/qblk/photo/${pt.share_photo}'
	});
    wx.onMenuShareQQ({
        title:'${pt.share_title}',
        desc:'${pt.share_desc}',
	link:'http://www.qubulikou.com/qblk/pt/share.action?phone='+${phone},
	imgUrl:'http://www.qubulikou.com/qblk/photo/${pt.share_photo}'
    	});
    wx.onMenuShareWeibo({
        title:'${pt.share_title}',
        desc:'${pt.share_desc}',
	link:'http://www.qubulikou.com/qblk/pt/share.action?phone='+${phone},
	imgUrl:'http://www.qubulikou.com/qblk/photo/${pt.share_photo}'
	});
    wx.onMenuShareQZone({
        title:'${pt.share_title}',
        desc:'${pt.share_desc}',
        link:'http://www.qubulikou.com/qblk/pt/share.action?phone='+${phone},
        imgUrl:'http://www.qubulikou.com/qblk/photo/${pt.share_photo}'
	});
    });

    function changeNum() {
             if (data.today_have_times < 1) {
		$.confirm("今天干杯次数已用完，点击确定分享给朋友", function() {
           		  //点击确认后的回调函数
           		  sharewx();
           		    }, function() {
           		      //点击取消后的回调函数
           		        });
           		       }
	    else{
	    $.ajax({
            type: 'GET',
            url: 'self_past.action',
            timeout: 10000,
            dataType: 'json',
            success: function (re_json) {
                data = re_json.data;
                $("#td_drunk").text(data.today_drunk);
                $("#cy_drunk").text(data.cycle_drunk);
                $("#tdu_drunk").text(data.today_drunk);
                $("#tdo_drunk").text(data.today_other_drunk);
                var drunk = data.total_drunk - data.cycle_drunk;
                var gauge = loadLiquidFillGauge("fillgauge", 120, config);
                gauge.update(drunk);
                $.alert("今日已签到！今日干杯获得"+data.today_drunk+"ml酒量，继续加油哦！",function(){
			window.location.reload();
		});
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
    }

    function loadAjax() {
	var chess = document.getElementById("chess");
        $.ajax({
            type: 'GET',
            url: 'info.action',
            timeout: 10000, //超时时间设置，单位毫秒
            data: "ac=index_data",
            dataType: 'json',
            async: false,
            success: function (re_json) {
                data = re_json.data;
		if(data.today_have_times < 1){
		   chess.style.display = "none";
		}
                $("#td_drunk").text(data.today_drunk);
                $("#cy_drunk").text(data.cycle_drunk);
                $("#tdu_drunk").text(data.today_drunk);
                $("#tdo_drunk").text(data.today_other_drunk);
                var drunk = data.total_drunk - data.cycle_drunk;//清空后total_drunk变成了0
                if (drunk<0){
                    drunk=0;
                }
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
