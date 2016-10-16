<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>抽奖</title>
    <link type="text/css" rel="stylesheet" href="/css/header.css" />
    <link type="text/css" rel="stylesheet" href="/css/lottery.css" />
    <link rel="stylesheet" href="/css/swiper.min.css">
    <script type="text/javascript" src="/js/jquery.min.js" ></script>
    <script src="/js/swiper-3.3.1.jquery.min.js"></script>
</head>

<body>
<div id="banner">
    <div class="swiper-wrapper">
        <div class="swiper-slide"><img src="/images/img2.jpg" /></div>
        <div class="swiper-slide"><img src="/images/img3.jpg" /></div>
        <div class="swiper-slide"><img src="/images/img4.jpg" /></div>
        <div class="swiper-slide"><img src="/images/img5.jpg" /></div>
    </div>
    <div class="pagination"></div>
</div>
<ul id="msglist">
    <li class="msg" onclick="location.href='/raffle/lottery_d.action'">
        <div class="msg-left">
            <img src="/images/img2.jpg" />
        </div>
        <div class="msg-right">
            <p>微信群分享:13天带你穿越丝路大话西游</p>
            <div class="msg-author">
                <img src="/images/header.png" />
                <p>by<span class="author">小强</span></p>
            </div>
        </div>
        <div class="clear"></div>
    </li>
    <li class="msg" onclick="location.href='/raffle/lottery_d.action'">
        <div class="msg-left">
            <img src="/images/img2.jpg" />
        </div>
        <div class="msg-right">
            <p>微信群分享:13天带你穿越丝路大话西游</p>
            <div class="msg-author">
                <img src="/images/header.png" />
                <p>by<span class="author">小强</span></p>
            </div>
        </div>
        <div class="clear"></div>
    </li>
    <li class="msg" onclick="location.href='/raffle/lottery_d.action'">
        <div class="msg-left">
            <img src="/images/img2.jpg" />
        </div>
        <div class="msg-right">
            <p>微信群分享:13天带你穿越丝路大话西游</p>
            <div class="msg-author">
                <img src="images/header.png" />
                <p>by<span class="author">小强</span></p>
            </div>
        </div>
        <div class="clear"></div>
    </li>
    <li class="msg" onclick="location.href='/raffle/lottery_d.action'">
        <div class="msg-left">
            <img src="/images/img2.jpg" />
        </div>
        <div class="msg-right">
            <p>微信群分享:13天带你穿越丝路大话西游</p>
            <div class="msg-author">
                <img src="/images/header.png" />
                <p>by<span class="author">小强</span></p>
            </div>
        </div>
        <div class="clear"></div>
    </li>
    <div style="height: 180px; width:100%;"></div>
</ul>

<footer>
    <a href="/" class="on">
        <img  src="/images/ticket.png"/>
        <p class="on">抽奖券</p>
    </a>
    <a href="/">
        <img class="" src="/images/auction.png"/>
        <p>拍卖</p>
    </a>
    <a href="/">
        <img class="" src="/images/date.png"/>
        <p>约饭</p>
    </a>
    <a href="/">
        <img class="" src="/images/discount.png"/>
        <p>砍价</p>
    </a>
    <a href="/">
        <img class="" src="/images/mine.png"/>
        <p>我的</p>
    </a>
</footer>
<script type="text/javascript">
    window.onload = function() {
        var mySwiper1 = new Swiper('#header',{
            freeMode : true,
            slidesPerView : 'auto',
        });
        var mySwiper2 = new Swiper('#banner',{
            autoplay:5000,
            visibilityFullFit : true,
            loop:true,
            pagination : '.pagination',
        });

        var tabsSwiper = new Swiper('#tabs-container',{
            speed:500,
            onSlideChangeStart: function(){
                $(".tabs .active").removeClass('active')
                $(".tabs a").eq(tabsSwiper.activeIndex).addClass('active')
            }
        })
        $(".tabs a").on('touchstart mousedown',function(e){
            e.preventDefault()
            $(".tabs .active").removeClass('active')
            $(this).addClass('active')
            tabsSwiper.slideTo( $(this).index() )
        })
        $(".tabs a").click(function(e){
            e.preventDefault()
        })

    }
</script>
</body>
</html>
