<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>登陆${baseUrl}</title>
    <link href="${baseUrl}css/header.css" type="text/css" rel="stylesheet">
    <link href="${baseUrl}css/lottery.css" type="text/css" rel="stylesheet">
    <link rel="stylesheet" href="${baseUrl}ratchet/weui.css" type="text/css">
    <script src="${baseUrl}plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <script type="text/javascript" src="${baseUrl}ratchet/jquery-weui.js"></script>
</head>
<body style="background-color: #f3f3f3;">
<div class="container">
    <img src="/images/loginbg.png"/>

    <form action="/qblk/login.action" method="post" id="loginForm" name="loginForm">
        <div class="loginbox">
            <input id="redirect" type="hidden" value="${redirect!}">
            <input id="username" type="text" name="username" class="username" placeholder="请输入手机号/邮箱"
                   style="border-bottom: 1px solid #ccc;">
            <input id="password" type="password" name="password" class="pw" placeholder="请输入密码">
        </div>
        <input id="loginBtn" type="button" value="登录" class="login">

        <div class="btm">
            <a class="register">注册账号</a>
            <a class="forgot">找回密码</a>
        </div>
    </form>
</div>
</body>
<!-- Bootstrap 3.3.5 -->
<script src="/bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="/plugins/iCheck/icheck.min.js"></script>
<script>
    $(function () {
        $("#loginBtn").click(function () {
            var userName = Trim($("#username").val(), 'g');
            var userPwd = Trim($("#password").val(), 'g');
            var redirect = $("#redirect").val();
            if (userName == '') {
                $.alert("手机/邮箱不能为空!");
                $("#username").focus();
                return;
            }
            if (userPwd == '') {
                $.alert("密码不能为空!");
                $("#password").focus();
                return;
            }
            $.ajax({
                url: "/qblk/login.action?username=" + userName + "&password=" + userPwd + "&redirect=" + redirect,
                type: "POST",
                success: function (result) {
                    var r = jQuery.parseJSON(result);
                    if (r.code == 0) {
                        $.alert(r.msg);
                        $("#username").focus();
                    } else {
                        if (redirect == null || redirect == 'undefined' || redirect == '') {
                            window.self.location = "/raffle/lottery.action";
                        } else {
                            window.self.location = redirect;
                        }
                    }
                }
            });
        });
    });
    function Trim(str, is_global) {
        var result;
        result = str.replace(/(^\s+)|(\s+$)/g, "");
        if (is_global.toLowerCase() == "g")
            result = result.replace(/\s/g, "");
        return result;
    }
</script>
</html>
