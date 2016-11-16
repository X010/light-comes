<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>优惠券兑换</title>
    <link rel="stylesheet" href="/ratchet/css/ratchet.css" type="text/css">
    <link rel="stylesheet" href="/ratchet/weui.css" type="text/css">
    <link rel="stylesheet" href="/ratchet/app.css" type="text/css">
    <script type="text/javascript" src="/js/jquery.min.js" ></script>
    <script type="text/javascript" src="/js/laytpl.js"></script>
    <script type="text/javascript" src="/js/jquery.qrcode.min.js"></script>
    <script type="text/javascript" src="/ratchet/jquery-weui.js"></script>
    <style type="text/css">
        .panel{ border: 1px dotted #c7c7c7; margin:5px; background: #fff;}
        .top{ margin:20px 5px; border-radius: 5px; border: 1px solid #f3f3f3; padding: 10px;}
        .top-left{ width: 40px; margin-left: 10px;}
        .top-left img{ width: 100%; float: left;}
        .top-right{ margin-right: 20%; line-height: 40px;}
        .exchange{ margin:20px auto; width: 220px; padding: 10px; border: 1px solid #cc0000; color: #cc0000; border-radius: 5px; }
    </style>
</head>
<body>
<header class="bar bar-nav">
    <h1 class="title">优惠劵兑换</h1>
</header>
<div class="content">
    <div class="panel">
        <div class="top clearfix">
            <div class="pull-left top-left">
                <img src="/images/qr-coupon.png"/>
            </div>
            <div class="pull-right top-right">
                <p>${coupon.title}</p>
            </div>
        </div>
        <div class="top clearfix">
            <div class="pull-right">
                <p>优惠劵号:${coupon.cardno}</p>
            </div>
            <#--<div class="pull-right">-->
                <#--<p>优惠金额:${coupon.price}</p>-->
            <#--</div>-->
            <#--<div class="pull-right">-->
                <#--<p>过期时间:${coupon.use_end_time?string('yyyy-MM-dd')}</p>-->
            <#--</div>-->
        </div>
        <div class="top clearfix">
            <div class="pull-right">
                <p>过期时间:${coupon.use_end_time?string('yyyy-MM-dd')}</p>
            </div>
        </div>
        <div class="top clearfix">
            <div class="pull-right">
                <p>优惠金额:${coupon.price}</p>
            </div>
        </div>
        <input type="hidden" id="couponRecordId" value="${coupon.id}"/>
        <div class="exchange">
            <input id="transferBtn" value="确认兑换" type="button"/>
        </div>
    </div>
</div>
</body>
<script>
$(function (){
    $("#transferBtn").click(function(){
        var id=$("#couponRecordId").val();
        $.ajax({
            url: "transferCoupon.action?id=" + id,
            type: "POST",
            success: function (result) {
                var r = jQuery.parseJSON(result);
                if (r.code < 0) {
                    $.alert(r.msg);
                } else {
                    $.alert(r.msg);
                    //window.self.location = "/raffle/lottery.action";
                }
            }
        });
    });
});
//    $(function () {
//        $("#loginBtn").click(function () {
//            var userName = Trim($("#username").val(), 'g');
//            var userPwd = Trim($("#password").val(), 'g');
//            if (userName == '') {
//                $.alert("手机/邮箱不能为空!");
//                $("#username").focus();
//                return;
//            }
//            if (userPwd == '') {
//                $.alert("密码不能为空!");
//                $("#password").focus();
//                return;
//            }
//            $.ajax({
//                url: "login.action?username=" + userName + "&password=" + userPwd,
//                type: "POST",
//                success: function (result) {
//                    var r = jQuery.parseJSON(result);
//                    if (r.code == 0) {
//                        $.alert(r.msg);
//                        $("#username").focus();
//                    } else {
//                        window.self.location = "/raffle/lottery.action";
//                    }
//                }
//            });
//        });
//    });
</script>
</html>