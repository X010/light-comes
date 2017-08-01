<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>${banquet.title}</title>
    <link href="${baseUrl}css/header.css" type="text/css" rel="stylesheet">
    <link href="${baseUrl}css/auction.css" type="text/css" rel="stylesheet">
    <link rel="stylesheet" href="${baseUrl}ratchet/weui.css" type="text/css">
    <script src="${baseUrl}plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <script type="text/javascript" src="${baseUrl}ratchet/jquery-weui.js"></script>
</head>
<body style="background-color: #f3f3f3;">
<header>
    <a class="left" onclick="window.history.back();">
        <img src="${baseUrl}images/back.png"/>
    </a>

    <p>${banquet.title}</p>
</header>
<div id="banner">
    <img src="${banquet.photo}"/>
</div>
<div class="auction">
    <div class="msg-auct">
        <img src="${baseUrl}images/auction_on.png"/>

        <div class="title">
            <h3>${banquet.title!}</h3>

            <p>${banquet.memo}</p>
        </div>
    </div>
    <div class="msg-time">
        <img src="${baseUrl}images/clock.png"/>
    <#if seconds gt 0>
        剩余时间:<span id="day_show">0</span>天
        <span id="hour_show">0</span>时
        <span id="minute_show">0</span>分
        <span id="second_show">0</span>秒

        <#if isjoin>
            <p class="seat">您的座位号:<span>${record.table_num}</span>桌<span>${record.seat_num}</span>号</p>
        </#if>
        <#if gapNum gt 0>
            <p class="seat"> 剩余坐席:<span>${gapNum}</span>位</p>
        <#else>
            <p style="margin-left:45px;">该活动已满员！</p>
        </#if>
    <#else>
        该活动已结束!
    </#if>
    </div>
</div>
<div class="auct-name">
    <div class="msgn">
        <p><span class="msgbold">承办人：</span><span>${banquet.author_nickname}</span></p>
    </div>
    <div class="msgn">
        <p><span class="msgbold">联系电话：</span><span>${banquet.author_telephone}</span></p>
    </div>
    <div class="msgn">
        <p><span class="msgbold">活动地址：</span><span>${banquet.author_address}</span></p>
    </div>
    <div class="msgn">
        <p><span class="msgbold">活动时间：</span><span>${banquet.banquet_time?string('yyyy-MM-dd HH:mm')}</span></p>
    </div>
    <div class="msgn">
        <p style="width: 100%;">
        <span class="msgbold">酒水礼品说明：<span>
        <span>
        <#if banquet.info??>
        ${banquet.info}
        <#else>
            无
        </#if>
        </span>
        </p>
    </div>
<#--<#if isjoin>-->
<#--<div class="msgn">-->
<#--<p><span class="msgbold">您的座位号：</span><span>${record.table_num}桌${record.seat_num}号</span></p>-->
<#--</div>-->
<#--</#if>-->

</div>

<#if banquet.status==2 && seconds gt 0>
<div style="height:30px;bottom:0;"></div>
<div class="footer">
    <input type="hidden" id="isjoin" value="${isjoin?c}">
    <input type="hidden" id="start_seconds" value="${start_seconds?c}">
    <input type="hidden" id="gapNum" value="${gapNum?c}">
    <#if isjoin>
        <div id="deposit">您已预约该饭局</div>
    <#else>
        <#if start_seconds gt 0>
            <div id="deposit">活动尚未开始</div>
        <#elseif gapNum lte 0>
            <div id="deposit">该活动已满员</div>
        <#else>
            <div id="deposit">我要约饭(<strong>${banquet.amount}</strong>元/人)</div>
        </#if>
    </#if>
</div>
</#if>
</body>
<script language="JavaScript">
    /**
     * 发送交定金请求
     * onclick="javascript:send_submit_order(${banquet.id});"
     */
//    function send_submit_order(aid) {
//        $.ajax({
//            url: "dopaybanquet.action?aid=" + aid,
//            dataType: "json",
//            success: function (data, textStatus) {
//                console.log(data);
//                if (data != null) {
//                    $.alert("您已成功预约该饭局");
//                    $("#deposit").html("您已预约");
//                    $("#deposit").click(function () {
//                        $.alert("您已参与过该活动");
//                    });
//                }
//            }
//        });
//    }

    var deposit = document.getElementById("deposit");
    var isjoin = document.getElementById("isjoin").value;
    var ss = document.getElementById("start_seconds").value;
    var gapNum = document.getElementById("gapNum").value;
    deposit.onclick = function () {
        if (isjoin == 'true') {
            $.alert("您已预约该饭局");
        } else if (ss > 0) {
            $.alert("活动尚未开始");
        }else if(gapNum<=0){
            $.alert("活动已满员");
        }
        else {
            window.location.href = "wechart_redirect.action?amount=${banquet.amount!}&aid=${banquet.id!}&title=${banquet.title!}&price=${banquet.amount!}";
        }
    }

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
        //倒计时总秒数量
        timer(seconds);
    });
</script>
</html>
