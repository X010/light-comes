<html>
<head>
    <script src="${baseUrl}plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <script type="text/javascript" charset="UTF-8" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script language="JavaScript">
        wx.config({
            debug:false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId:"${appId}", // 必填，公众号的唯一标识" +
            timestamp:"${timeStamp}", // 必填，生成签名的时间戳
            nonceStr:"${nonceStr}", // 必填，生成签名的随机串
            signature:"${signature}",// 必填，签名，见附录1
            jsApiList:['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });

        wx.ready(function (res) {
            // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
            wx.chooseWXPay({
                timestamp: "${timeStamp}", // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                nonceStr: "${nonceStr}", // 支付签名随机串，不长于 32 位
                package: "${package}", // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
                signType: "MD5", // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
                paySign: "${paySign}", // 支付签名
                success: function (res) {
                    alert("支付成功${redirectUrl}");
                    window.location.href="${redirectUrl}";
                },
                cancel: function (res) {
                    alert("取消失败");
                    window.location.href="${redirectUrl}";
                },
                fail: function (res) {
                    alert("支付失败");
                    window.location.href="${redirectUrl}";
                }
            });
        });

        wx.error(function (res) {
            alert("error:" + JSON.stringify(res));
        });
        <#--function onBridgeReady() {-->
            <#--WeixinJSBridge.invoke(-->
                    <#--'getBrandWCPayRequest', {-->
                        <#--"appId": "${appId}",     //公众号名称，由商户传入-->
                        <#--"timeStamp": "${timeStamp}",         //时间戳，自1970年以来的秒数-->
                        <#--"nonceStr": "${nonceStr}", //随机串-->
                        <#--"package": "${package}",-->
                        <#--"signType": "MD5",         //微信签名方式：-->
                        <#--"paySign": "${paySign}" //微信签名-->
                    <#--},-->
                    <#--function (res) {-->
                        <#--alert("err_msg:" + res.err_msg);-->
                        <#--WeixinJSBridge.log(res.err_msg);-->
                        <#--if (res.err_msg == "get_brand_wcpay_request:ok") {-->
                            <#--alert("支付成功");-->
                        <#--} else if (res.err_msg == "get_brand_wcpay_request:cancel") {-->
                            <#--alert("取消支付!");-->
                        <#--} else {-->
                            <#--alert("支付失败!");-->
                            <#--//WeixinJSBridge.call('closeWindow');-->
                        <#--}-->
                        <#--// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。-->
                    <#--}-->
            <#--);-->
        <#--}-->

        <#--if (typeof WeixinJSBridge == "undefined") {-->
            <#--if (document.addEventListener) {-->
                <#--document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);-->
            <#--} else if (document.attachEvent) {-->
                <#--document.attachEvent('WeixinJSBridgeReady', onBridgeReady);-->
                <#--document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);-->
            <#--}-->
        <#--} else {-->
            <#--onBridgeReady();-->
        <#--}-->
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