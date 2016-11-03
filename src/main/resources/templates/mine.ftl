<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone = no"/>
    <title>个人资料</title>
    <link href="/css/header.css" type="text/css" rel="stylesheet">
    <link href="/css/lottery.css" type="text/css" rel="stylesheet">
    <style>
        .container {
            position: absolute;
            width: 100%;
            height: 100%;
            top:0;
            left: 0;
            background: #f3f3f3;
        }

        .head {
            background: url("/images/mine-bg.png") no-repeat;
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
        <img src="/images/portrait.png"/>

        <p>${um.phone}</p>
    </div>
    <ul class="mainlist">

        <li onclick="window.location.href='/my/mine_coupon.action'">
            <img src="/images/ticket-b.png"/>

            <p>优惠劵</p>
            <a>&gt;</a>
        </li>


        <li onclick="window.location.href='/my/mine_auction.action'">
            <img src="/images/auction-b.png"/>

            <p>拍卖</p>
            <a>&gt;</a>
        </li>


        <li onclick="window.location.href='/my/mine_overcharged.action'">
            <img src="/images/date-b.png"/>

            <p>砍价</p>
            <a>&gt;</a>
        </li>


        <li onclick="window.location.href='/my/mine_banquet.action'" style="border-bottom:none;">
            <img src="/images/discount-b.png"/>

            <p>约饭</p>
            <a>&gt;</a>
        </li>

    </ul>
    <div class="quit">
        退出登录
    </div>
</div>
<footer>
    <a href="/raffle/lottery.action">
        <img src="/images/ticket.png"/>

        <p>抽奖券</p>
    </a>
    <a href="/auction/auction.action" >
        <img class="" src="/images/auction.png"/>

        <p >拍卖</p>
    </a>
    <a href="/banquet/banquet.action">
        <img class="" src="/images/date.png"/>

        <p>约饭</p>
    </a>
    <a href="/oc/overcharged.action">
        <img class="" src="/images/discount.png"/>

        <p>砍价</p>
    </a>
    <a class="on" href="/my/mine.action">
        <img class="" src="/images/mine_on.png"/>

        <p class="on">我的</p>
    </a>
</footer>
</body>
</html>
