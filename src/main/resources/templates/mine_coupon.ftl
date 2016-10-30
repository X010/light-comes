<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">
    <title></title>
    <link rel="stylesheet" href="/ratchet/css/ratchet.css" type="text/css">
    <link rel="stylesheet" href="/ratchet/weui.css" type="text/css">
    <link rel="stylesheet" href="/ratchet/app.css" type="text/css">
</head>
<body>

<header class="bar bar-nav">
    <a class="icon icon-left-nav pull-left" onclick="window.history.back();" id="navBackBtn"></a>

    <h1 class="title">我的优惠劵</h1>
</header>
<div class="content">
    <div class="mui-order-cate">
        <div class="segmented-control" id="orderStatusList">
            <a class="control-item active" id="order_status_0" href="">全部</a>
            <a class="control-item" id="order_status_1" href="">未使用</a>
            <a class="control-item" id="order_status_2" href="">已使用</a>
            <a class="control-item" id="order_status_3" href="">过期</a>
        </div>
    </div>
    <div class="mui-order-list" id="containerList">
        <div class="items">
            <div class="mui-panel item-order">
                <div class="item-order-info">
                    <div id="item-group"><span class="item-code">优惠劵号:</span></div>
                </div>
                <div class="item-goods-list">
                    <div class="items">
                        <div class="item-col">
                            <div class="item-goods">
                                <div class="item-gname">
                                    <span class="item-flag">单品类</span>
                                </div>
                                <div class="item-total"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="item-order-ext clearfix">
                    <div class="pull-left item-price-total">金额：<strong></strong> 元</div>
                    <div class="pull-right">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="mui-empty">
        <div class="item-icon">
            <span class="icon micon-empty"></span>
        </div>
        <div class="item-title">该状态下没有订单~</div>
    </div>
</div>
</body>
</html>