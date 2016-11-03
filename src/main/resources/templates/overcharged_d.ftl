<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>约饭</title>
    <link href="/css/header.css" type="text/css" rel="stylesheet">
    <link href="/css/auction.css" type="text/css" rel="stylesheet">
    <script src="/plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <link rel="stylesheet" href="/ratchet/weui.css" type="text/css">
    <script type="text/javascript" src="/ratchet/jquery-weui.js"></script>
</head>
<body style="background-color: #f3f3f3;">
<header>
    <a class="left" onclick="window.history.back();">
        <img src="/images/back.png"/>
    </a>

    <p>${oc.title}</p>
</header>
<div id="banner">
    <img src="${oc.good_photo}"/>
</div>

<div class="auction">
    <div class="msg-auct">
        <img src="/images/auction_on.png"/>

        <div class="title">
            <h3>${oc.title!}</h3>

            <p>${oc.good_name}</p>
        </div>


    </div>
    <div class="msg-time">
        <img src="/images/clock.png"/>
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

<div class="auct-name" style="padding-bottom:20px;">
    <div class="msgt">
        <p><strong>砍价者信息</strong></p>
    </div>
    <div class="msgt">
        <p>
        <#if orms??>
            <#list orms as orm>
            ${orm.createtime?string("MM月dd日 HH:mm:ss")}&nbsp;&nbsp;
            ${orm.phone}&nbsp;&nbsp;
                砍掉:<strong style="color: red">${oc.subtract_price}</strong> 元
            </#list>
        <#else>
            无人参与砍价!
        </#if>
        </p>
    </div>
</div>

<div class="footer">
<#if oc.status==2>
    <#if join>
        <div id="deposit">您已砍过一刀</div>
    <#else>
        <div id="deposit" onclick="send_overcharged(${oc.id})">我要砍一刀</div>
    </#if>
</#if>
</div>
</body>

<script language="JavaScript">
    function send_overcharged(aid) {
        $.ajax({
            url: "/oc/send_overcharged.action?aid=" + aid,
            dataType: "json",
            success: function (data, textStatus) {
                if (data != null) {
                    if (data.status == 1) {
                        $.alert("您成功砍了一刀,但未获取该商品");

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
