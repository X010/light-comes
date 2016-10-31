<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <title>拍卖</title>
    <link type="text/css" rel="stylesheet" href="/css/header.css"/>
    <link type="text/css" rel="stylesheet" href="/css/lottery.css"/>
    <link rel="stylesheet" href="/css/swiper.min.css">
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script src="/js/swiper-3.3.1.jquery.min.js"></script>
</head>

<body>
<div id="banner">
    <div class="swiper-wrapper">
    <#if focus??>
        <#list focus as focu>
            <div class="swiper-slide"><a href="${focu.link}"><img src="${focu.image}"/></a></div>
        </#list>
    </#if>
    </div>
    <div class="pagination"></div>
</div>
<ul id="msglist">
<#if bs??>
    <#list bs as b>
        <li class="msg" onclick="location.href='/banquet/banquet_d.action?aid=${b.id}'">
            <div class="msg-left">
                <#if b.photo??>
                    <img src="${b.photo}" />
                </#if>
            </div>
            <div class="msg-right">
                <p><span class="author">${b.title!}</span></p>

                <div class="msg-author">
                    <p>${b.memo!}</p>
                </div>
            </div>
            <div class="clear"></div>
        </li>
    </#list>
</#if>
    <div style="height: 180px; width:100%;"></div>
</ul>

<footer>
    <a href="/raffle/lottery.action">
        <img src="/images/ticket.png"/>

        <p>抽奖券</p>
    </a>
    <a href="/auction/auction.action">
        <img class="" src="/images/auction.png"/>

        <p >拍卖</p>
    </a>
    <a href="/banquet/banquet.action" class="on">
        <img class="" src="/images/date.png"/>

        <p  class="on">约饭</p>
    </a>
    <a href="/oc/overcharged.action">
        <img class="" src="/images/discount.png"/>

        <p>砍价</p>
    </a>
    <a href="/my/mine.action">
        <img class="" src="/images/mine.png"/>

        <p>我的</p>
    </a>
</footer>
<script src="/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript">
    window.onload = function () {
        var mySwiper1 = new Swiper('#header', {
            freeMode: true,
            slidesPerView: 'auto',
        });
        var mySwiper2 = new Swiper('#banner', {
            autoplay: 5000,
            visibilityFullFit: true,
            loop: true,
            pagination: '.pagination',
        });

        var tabsSwiper = new Swiper('#tabs-container', {
            speed: 500,
            onSlideChangeStart: function () {
                $(".tabs .active").removeClass('active')
                $(".tabs a").eq(tabsSwiper.activeIndex).addClass('active')
            }
        })
        $(".tabs a").on('touchstart mousedown', function (e) {
            e.preventDefault()
            $(".tabs .active").removeClass('active')
            $(this).addClass('active')
            tabsSwiper.slideTo($(this).index())
        })
        $(".tabs a").click(function (e) {
            e.preventDefault()
        })

    }
</script>
</body>
</html>
