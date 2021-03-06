<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>优惠券</title>
    <link rel="stylesheet" href="${baseUrl}ratchet/css/ratchet.css" type="text/css">
    <link rel="stylesheet" href="${baseUrl}ratchet/weui.css" type="text/css">
    <link rel="stylesheet" href="${baseUrl}ratchet/app.css" type="text/css">
    <script type="text/javascript" src="${baseUrl}js/jquery.min.js" ></script>
    <script type="text/javascript" src="${baseUrl}js/laytpl.js"></script>
    <script type="text/javascript" src="${baseUrl}js/jquery.qrcode.min.js"></script>
    <style type="text/css">
        .panel{ border: 1px dotted #c7c7c7; margin:5px; background: #fff;}
        .top{ margin: 5px; border-radius: 5px; border: 1px solid #f3f3f3; padding: 10px;}
        .top-left{ width: 40px; margin-left: 10px;}
        .top-left img{ width: 100%; float: left;}
        .top-right{ margin-right: 20%;}
        .qrcode{ margin:20px auto; width: 220px; padding: 10px; border: 1px solid #f3f3f3;}
    </style>
</head>
<body>
<header class="bar bar-nav">
    <a class="icon icon-left-nav pull-left" onclick="javascript:history.back();" id="navBackBtn"></a>
    <h1 class="title">优惠劵</h1>
</header>
<div class="content">
    <div class="panel">
        <div class="top clearfix">
            <div class="pull-left top-left">
                <img src="${baseUrl}images/qr-coupon.png"/>
            </div>
            <div class="pull-right top-right">
                <p>${coupon.title}</p>
                <p>到期日:${coupon.use_end_time?string('yyyy-MM-dd')}</p>
                <p>金额:${coupon.price}元</p>
                <p>类目:
                <#if coupon.ctype==1>
                    全品类
                <#elseif coupon.ctype==2>
                    商品栏目类
                <#else>
                    商品类
                </#if>
                </p>
            </div>
        </div>
        <div class="qrcode">
            <div id="code"></div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $("#code").qrcode({
        render: "table", //table方式
        width: 200, //宽度
        height:200, //高度
        text: "${qrcode_url!}" //任意内容
    });
</script>
</body>
</html>