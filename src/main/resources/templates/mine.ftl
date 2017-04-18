<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone = no"/>
    <title>个人资料</title>
    <link href="${baseUrl}css/header.css" type="text/css" rel="stylesheet">
    <link href="${baseUrl}css/lottery.css" type="text/css" rel="stylesheet">
    <style>
        .container {
            position: absolute;
            width: 100%;
            height: 100%;
            top: 0;
            left: 0;
            background: #f3f3f3;
        }

        .head {
            background: url("${baseUrl}images/mine-bg.png") no-repeat;
            background-size: 100% 100%;
            width: 100%;
            height: 30%;
        }

        .head img {
            position: absolute;
            width: 100px;
            height: 100px;
            left: 36%;
            top: 5%;
        }

        .head p {
            text-align: center;
            font-size: 16px;
            color: #fff;
            padding-top: 135px;
        }

        .mainlist {
            margin-top: 5%;
            width: 100%;
            background: #fff;
        }

        .mainlist li {
            height: 16px;
            border-bottom: 1px solid #ccc;
            padding: 4%;
        }

        .mainlist img, .mainlist p {
            float: left;
            font-size: 16px;
        }

        .mainlist img {
            width: 20px;
            height: 20px;
            margin: 0 5%;
        }

        .mainlist a {
            float: right;
            font-size: 24px;
            color: #ccc;
            margin-right: 10px;
            line-height: 20px;
        }

        .quit {
            width: 40%;
            height: 6%;
            margin-top: 6%;
            margin-left: 30%;
            border-radius: 20px;
            background: #ff6375;
            color: #fff;
            text-align: center;
            line-height: 36px;
        }
    </style>
</head>
<body style="background-color: #f3f3f3;">
<div class="container">
    <div class="head">
        <img src="${baseUrl}images/portrait.png"/>

        <p>${um.phone!}</p>
    </div>
    <ul class="mainlist">
        <li onclick="window.location.href='${baseUrl}pt/past.action'">
            <img src="${baseUrl}images/ticket-b.png"/>

            <p>我要干杯</p>
            <a>&gt;</a>
        </li>

        <li onclick="window.location.href='${baseUrl}my/mine_coupon.action'">
            <img src="${baseUrl}images/ticket-b.png"/>

            <p>优惠劵</p>
            <a>&gt;</a>
        </li>


        <li onclick="window.location.href='${baseUrl}my/mine_auction.action'">
            <img src="${baseUrl}images/auction-b.png"/>

            <p>拍卖</p>
            <a>&gt;</a>
        </li>


        <li onclick="window.location.href='${baseUrl}my/mine_overcharged.action'">
            <img src="${baseUrl}images/date-b.png"/>

            <p>砍价</p>
            <a>&gt;</a>
        </li>


        <li onclick="window.location.href='${baseUrl}my/mine_banquet.action'" style="border-bottom:none;">
            <img src="${baseUrl}images/discount-b.png"/>

            <p>约饭</p>
            <a>&gt;</a>
        </li>

    </ul>
    <div class="quit" id="logout">
        退出登录
    </div>
</div>
<footer>
    <#--<a href="http://www.qubulikou.com/yeshizuileweixin/index.html">-->
        <a href="http://120.55.241.127/index.html">
        <img src="${baseUrl}images/home.png"/>
        <p>首页</p>
    </a>
    <a href="${baseUrl}raffle/lottery.action">
        <img src="${baseUrl}images/ticket.png"/>

        <p>抽奖券</p>
    </a>
    <a href="${baseUrl}auction/auction.action">
        <img class="" src="${baseUrl}images/auction.png"/>

        <p>拍卖</p>
    </a>
    <a href="${baseUrl}banquet/banquet.action">
        <img class="" src="${baseUrl}images/date.png"/>

        <p>约饭</p>
    </a>
    <a href="${baseUrl}oc/overcharged.action">
        <img class="" src="${baseUrl}images/discount.png"/>

        <p>砍价</p>
    </a>
    <#--<a class="on" href="${baseUrl}my/mine.action">-->
    <#--<a class="on" href="http://www.qubulikou.com/yeshizuileweixin/mine.html">-->
    <a class="on" href="http://120.55.241.127/mine.html"
        <img class="" src="${baseUrl}images/mine_on.png"/>
        <p class="on">我的</p>
    </a>
</footer>
</body>
<script src="${baseUrl}plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript">
    var logout = document.getElementById("logout");
    logout.onclick = function () {
        if(confirm("确定要退出登录吗？")){
            window.location.href="${baseUrl}qblk/logout.action";
        }
    }
</script>
</html>
