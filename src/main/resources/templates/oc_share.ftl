<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>${oc.title!}</title>
    <link href="${baseUrl}css/header.css" type="text/css" rel="stylesheet">
    <link href="${baseUrl}css/auction.css" type="text/css" rel="stylesheet">
    <script src="${baseUrl}plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <link rel="stylesheet" href="${baseUrl}ratchet/weui.css" type="text/css">
    <script type="text/javascript" src="${baseUrl}ratchet/jquery-weui.js"></script>
</head>
<body style="background-color: #f3f3f3;">
<header>
    <a class="left" onclick="window.history.back();">
        <img src="${baseUrl}images/back.png"/>
    </a>

    <p>${oc.title}</p>
</header>
<div id="banner">
    <img src="${oc.good_photo}"/>
</div>

<div class="auction">
    <div class="msg-auct">
        <img src="${baseUrl}images/auction_on.png"/>

        <div class="title">
            <h3>${oc.title!}</h3>

            <p>${oc.good_name}</p>
        </div>


    </div>
    <div class="msg-time">
        <img src="${baseUrl}images/clock.png"/>
    <#if seconds gt 0>
        剩余时间<span id="day_show">0</span>天
        <span id="hour_show">0</span>时
        <span id="minute_show">0</span>分
        <span id="second_show">0</span>秒
    <#else>
        该活动已结束!
    </#if>
    </div>
</div>

<div class="auct-friend">
   <div class="progress"></div>
   <p>已有N位朋友帮忙砍价，共砍N元，再砍N元就成功了！加油！</p>
   <div class="friendList">
   	<p><span style="font-weight:bold; padding-right:2%;">12345678901</span><span>帮TA砍价14元</span></p>
	<p><span style="font-weight:bold; padding-right:2%;">12345678901</span><span>帮TA砍价14元</span></p>	
   </div> 
</div>

<div class="auct-progress">
    <span>198</span>
    <progress value="55" max="100">
    </progress>
    <span>108</span>
</div>

<div class="manual">
   <div class="msgt">
  	<p style="color:#000; font-size:14px;"><strong>活动说明</strong></p>
	<p><span>活动福利：</span>砍习酱纪念酒，夺茅台飞天酒！</p>
	<p><span>活动内容：</span>砍习酱纪念酒，夺茅台飞天酒！砍习酱纪念酒，夺茅台飞天酒！砍习酱纪念酒，夺茅台飞天酒！砍习酱纪念酒，夺茅台飞天酒！砍习酱纪念酒，夺茅台飞天酒！砍习酱纪念酒，夺茅台飞天酒！</p>
	<p><span>活动时间：</span>2017年3月22日14：30-2017年3月23日14：30</p>
   </div>
</div>
        <div class="help">
            <input type="button" value="我也要参与" class="otherchess" style="background-color: #FFB046;"/>
            <input type="button" value="帮TA砍一刀" class="otherchess" style="background-color: #FFB046;"/>
        </div>
     <div id="shareit" onclick="close_sharewx()">
         <img class="arrow" src='${baseUrl}images/share.jpg'/>
         <a href="#" id="follow"><p id="share-text"></p></a>
     </div>
</body>

<script language="JavaScript">
    function send_overcharged(aid) {
        $.ajax({
            url: "send_overcharged.action?aid=" + aid,
            dataType: "json",
            success: function (data, textStatus) {
                if (data != null) {
                    if (data.status == 1) {
                        //$.alert("您成功砍了一刀,但未获取该商品");
			$.modal({
  				 title: '已减5元',
 				 text: '你已自砍，想要获取商品需要集众人之力，砍价吧！',
  				 buttons: [
					{ text:"取消",className:"default",onclick:function(){}},
					{ text:"找朋友帮我砍",onclick:function(){
						sharewx();
					}},
				]
			});
                        $("#deposit").html("您已砍过一刀");
                        $("#deposit").click(function () {
                            $.alert("您已参与过该活动");
                        });
                    } else if (data.status == 5) {
                        $.alert("恭喜您成功获取该商品去我的进行支付!");
                    }
                }
            }
        });
    }
var share = document.getElementById("shareit");
       function sharewx(){
        share.style.display = 'block';
       };
       function close_sharewx(){
        share.style.display = 'none';
       };

    function timer(intDiff) {
        window.setInterval(function () {
            var day = 0,
                    hour = 0,
                    minute = 0,
                    second = 0;//时间默认值
            if (intDiff > 0) {
                day = Math.floor(intDiff / (60 * 60 * 24));
                hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
                minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
                second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
            }
            if (minute <= 9) minute = '0' + minute;
            if (second <= 9) second = '0' + second;
            $('#day_show').html(day);
            $('#hour_show').html(hour);
            $('#minute_show').html(minute);
            $('#second_show').html(second);
            intDiff--;
        }, 1000);
    }

    $(function () {
        var seconds =${seconds?c};
        //var intDiff = parseInt(${seconds});//倒计时总秒数量
        timer(seconds);
    });
</script>
</html>
