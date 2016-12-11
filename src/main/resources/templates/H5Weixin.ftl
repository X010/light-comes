<html>
<head>
<script src="${baseUrl}plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script language="JavaScript">
    <#--wx.config({-->
    <#--debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。-->
    <#--appId: ${appId}, // 必填，公众号的唯一标识-->
    <#--timestamp: ${timeStamp}, // 必填，生成签名的时间戳-->
    <#--nonceStr: ${nonceStr}, // 必填，生成签名的随机串-->
    <#--signature: ${signature},// 必填，签名，见附录1-->
    <#--jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2-->
    <#--});-->
    function onBridgeReady() {
        WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId":"${appId!}",     //公众号名称，由商户传入
                    "timeStamp":" ${timeStamp!}",         //时间戳，自1970年以来的秒数
                    "nonceStr":"${nonceStr!}", //随机串
                    "package":"${package!}",
                    "signType":"MD5",         //微信签名方式：
                    "paySign":"${paySign!}" //微信签名
                },
                function (res) {
                    if (res.err_msg == "get_brand_wcpay_request:ok") {
                        alert("支付成功");
                    }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
                }
        );
    }

    if (typeof WeixinJSBridge == "undefined") {
        if (document.addEventListener) {
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        } else if (document.attachEvent) {
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    } else {
        onBridgeReady();
    }
</script>
</head>
<#--<body onload="onBridgeReady()">-->
<#--</body>-->
<body>
<h1>正在进行微信支付....
    <br/>
    <small>请不要关闭</small>
</h1>
</body>
</html>