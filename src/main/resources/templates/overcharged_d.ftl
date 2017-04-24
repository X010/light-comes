<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>${oc.title!}</title>
    <link rel="stylesheet" href="//cdn.bootcss.com/weui/1.1.1/style/weui.min.css">
    <link href="${baseUrl}css/header.css" type="text/css" rel="stylesheet">
    <link href="${baseUrl}css/auction.css" type="text/css" rel="stylesheet">
    <script src="${baseUrl}plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
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
    <p>已有${now_count}位朋友帮忙砍价，共砍掉${subtract_price}元，再砍${difference_price}元就成功了，加油！</p>
    <div class="friendList">
    <#if orms??>
        <#list orms as orm>
            <p><span style="width: 32%">${orm.createtime?string("MM月dd日 HH:mm:ss")}</span><span
                    style="width: 30%; padding-left:10px; ">
            ${orm.phone}</span><span style="width: 16%; padding-left: 10px;">
                砍掉:<strong style="color: red">${orm.amount}元</strong> </span>
            </p>
        </#list>
    </#if>
    </div>
</div>

<div class="auct-progress">
    <p>${oc.amount}</p>
    <div class="weui-progress">
      <div class="weui-progress__bar">
        <div class="weui-progress__inner-bar js_progress" id = "progress" style="width: 50%;"></div>
      </div>
    </div>
    <p>${oc.over_amount}</p>
</div>

<#--<div class="auct-name">-->
<#--<div class="msgt">-->
<#--<p style="color:#000; font-size:14px;"><strong>砍价者信息</strong></p>-->
<#--</div>-->
<#--<div class="msgt">-->
<#---->
<#--<#if orms??>-->
<#--<#list orms as orm>-->
<#--<p><span style="width: 32%">${orm.createtime?string("MM月dd日 HH:mm:ss")}</span><span style="width: 30%; padding-left:10px; ">-->
<#--${orm.phone}</span><span style="width: 16%; padding-left: 10px;">-->
<#--砍掉:<strong style="color: red">${oc.subtract_price}元</strong> </span>-->
<#--</p>-->
<#--</#list>-->
<#--已有${now_count}位朋友帮忙砍价，共砍掉${subtract_price}元，再砍${difference_price}元就成功了，加油！-->
<#--<#else>-->
<#--<p>-->
<#--无人参与砍价!-->
<#--</p>-->
<#--</#if>-->
<#--</div>-->
<#--</div>-->

<div class="manual">
    <div class="msgt">
        <p style="color:#000; font-size:14px;"><strong>活动说明</strong></p>
        ${oc.info!""}
        <#--<p><span>活动福利：</span>砍习酱纪念酒，夺茅台飞天酒！</p>-->
        <#--<p><span>活动内容：</span>砍习酱纪念酒，夺茅台飞天酒！砍习酱纪念酒，夺茅台飞天酒！砍习酱纪念酒，夺茅台飞天酒！砍习酱纪念酒，夺茅台飞天酒！砍习酱纪念酒，夺茅台飞天酒！砍习酱纪念酒，夺茅台飞天酒！</p>-->
        <#--<p><span>活动时间：</span>2017年3月22日14：30-2017年3月23日14：30</p>-->
    </div>
</div>

<!--<div class="help">
    <input type="button" value="我也要参与" class="otherchess" style="background-color: #FFB046;" onclick="sendOcBySponsor(${oc.id},$(sponsor))"/>
    <input type="button" value="帮TA砍一刀" class="otherchess" style="background-color: #FFB046;"/>
</div>-->

<div class="footer">
<#if oc.status==2>
    <#if join>
        <div id="deposit">您已砍过一刀</div>
    <#else>
        <div id="deposit" onclick="send_overcharged(${oc.id})">我要砍一刀</div>
    </#if>
</#if>
</div>
<div id="shareit" onclick="close_sharewx()">
    <img class="arrow" src='${baseUrl}images/share.jpg'/>
    <a href="#" id="follow"><p id="share-text"></p></a>
</div>
</body>

<script language="JavaScript">
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
            title: '${oc.share_title}', // 分享标题
            link: 'http://www.qubulikou.com/qblk/oc/overcharged_d.action?aid=${oc.id}&sponsor=' +${sponsor}, // 分享链接
            imgUrl: 'http://www.qubulikou.com/qblk/photo/${oc.share_photo!""}' // 分享图标
        });
        wx.onMenuShareAppMessage({
            title: '${oc.share_title}',
            desc: '${oc.share_desc}',
            link: 'http://www.qubulikou.com/qblk/oc/overcharged_d.action?aid=${oc.id}&sponsor=' +${sponsor}, // 分享链接
            imgUrl: 'http://www.qubulikou.com/qblk/photo/${oc.share_photo!""}'
        });
        wx.onMenuShareQQ({
            title: '${oc.share_title}',
            desc: '${oc.share_desc}',
            link: 'http://www.qubulikou.com/qblk/oc/overcharged_d.action?aid=${oc.id}&sponsor=' +${sponsor}, // 分享链接
            imgUrl: 'http://www.qubulikou.com/qblk/photo/${oc.share_photo!""}'
        });
        wx.onMenuShareWeibo({
            title: '${oc.share_title}',
            desc: '${oc.share_desc}',
            link: 'http://www.qubulikou.com/qblk/oc/overcharged_d.action?aid=${oc.id}&sponsor=' +${sponsor}, // 分享链接
            imgUrl: 'http://www.qubulikou.com/qblk/photo/${oc.share_photo!""}'
        });
        wx.onMenuShareQZone({
            title: '${oc.share_title}',
            desc: '${oc.share_desc}',
            link: 'http://www.qubulikou.com/qblk/oc/overcharged_d.action?aid=${oc.id}&sponsor=' +${sponsor}, // 分享链接
            imgUrl: 'http://www.qubulikou.com/qblk/photo/${oc.share_photo!""}'
        });
    });
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
                                {
                                    text: "取消", className: "default", onclick: function () {
                                }
                                },
                                {
                                    text: "找朋友帮我砍", onclick: function () {
                                    sharewx();
                                }
                                },
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
function sendOcBySponsor(aid,sponsor) {
        $.ajax({
            url: "send_overcharged.action?aid=" + aid +"&sponsor=" + sponsor,
            dataType: "json",
            success: function (data, textStatus) {
                if (data != null) {
                    if (data.status == 1) {
                        $.alert("已减5元,你已帮朋友砍了一刀，真给力!");
                                }
                               },
                            ]
                        });
                        $("#deposit").html("您已砍过一刀");
                        $("#deposit").click(function () {
                            $.alert("您已参与过该活动");
                        });
                    } else if (data.status == 5) {
                        $.alert("你已帮朋友拿下该商品！");
                    }
                }
            }
        });
    }
    var share = document.getElementById("shareit");
    function sharewx() {
        share.style.display = 'block';
    }
    ;
    function close_sharewx() {
        share.style.display = 'none';
    }
    ;

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
