<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>约饭</title>
    <link href="/css/header.css" type="text/css" rel="stylesheet">
    <link href="/css/auction.css" type="text/css" rel="stylesheet">
    <script src="/plugins/jQuery/jQuery-2.1.4.min.js"></script>
</head>
<body style="background-color: #f3f3f3;">
<header>
    <a class="left" onclick="window.history.back();">
        <img src="/images/back.png"/>
    </a>

    <p>${banquet.title}</p>
</header>
<div id="banner">
    <img src="${banquet.photo}"/>
</div>
<div class="auction">
    <div class="msg-auct">
        <img src="/images/auction_on.png"/>

        <div class="title">
            <h3>${banquet.title!}</h3>

            <p>${banquet.memo}</p>
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
<div class="auct-name">
    <div class="msgname">
        <p>承办人： <span style="font-weight: normal;">${banquet.author_nickname}</span></p>
    </div>
</div>
<div class="auct-name">
    <div class="msgname">
        <p>联系电话：<span style="font-weight: normal;"> ${banquet.author_telephone}</span></p>
    </div>
</div>
<div class="auct-name">
    <div class="msgname">
        <p >活动地址：<span style="font-weight: normal;">${banquet.author_address}</span> </p>
    </div>
</div>
<div class="auct-name" style="height: auto;">
    <div class="msgname">
        <p style="width: 100%;">酒水礼品说明：</p>
    </div>
    <div class="msgname" style="padding-left:30px;margin-top: 10px;font-size: 36px;line-height:60px;font-weight: normal;">
    <#if banquet.info??>
    ${banquet.info}
    <#else>
        无
    </#if>
    </div>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
</div>
<div class="footer">
    <div id="deposit">我要约饭</div>
</div>
</body>
<script language="JavaScript">
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

    $(function(){
        var seconds =${seconds?c};
        //var intDiff = parseInt(${seconds});//倒计时总秒数量
        timer(seconds);
    });
</script>
</html>