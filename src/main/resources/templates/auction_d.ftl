<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>抽奖活动</title>
    <link href="css/header.css" type="text/css" rel="stylesheet">
    <link href="css/auction.css" type="text/css" rel="stylesheet">
</head>
<body style="background-color: #f3f3f3;">
<header>
    <a class="left" onclick="window.history.back();">
        <img src="images/back.png"/>
    </a>
    <p>拍卖活动一</p>
</header>
<div id="banner">
        <img src="images/caro1.png" />
</div>
<div class="auction">
    <div class="msg-auct">
        <img src="images/auction_on.png"/>
        <div class="title">
            <h3>天之蓝经典酒</h3>
            <p>微信群分享:13天带你穿越丝路大话西游</p>
        </div>
    </div>
    <div class="msg-time">
       <img src="images/clock.png"/>
        剩余时间<span>01</span>时<span>26</span>分<span>36</span>秒
    </div>
</div>
<div class="auct-name">
    <div class="msgname">
        <img src="images/auct-header.png"/>
        <p>拍卖者信息</p>
    </div>
</div>
<div id="bg-auct">
    <div class="auct-success">
        <div class="succ-top">
            <p class="title-succ">拍卖成功!</p>
            <p id="close">x</p>
        </div>
        <div class="succ-main">
            <div class="msg-succ">
                <img src="images/auct.png"/>
                <div class="msg-r">
                    <h4>天之蓝经典酒</h4>
                    <p>数量 x1</p>
                    <p>成交价格 389元</p>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="footer">
    <div class="footer-left"><p>出价:200</p></div>
    <div class="footer-right" id="auction"><p>拍下来</p></div>
</div>
<script type="text/javascript">
    var auct = document.getElementById("auction");
    var floatbg = document.getElementById("bg-auct");
    var closebtn = document.getElementById("close");
    auct.onclick = function () {
        floatbg.style.display = "block";
    }
    closebtn.onclick = function () {
        floatbg.style.display = "none";
    }
</script>
</body>
</html>