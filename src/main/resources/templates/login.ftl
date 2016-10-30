<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登陆</title>
    <link href="/css/header.css" type="text/css" rel="stylesheet">
    <link href="/css/lottery.css" type="text/css" rel="stylesheet">
</head>
<body style="background-color: #f3f3f3;">
<header>
    <a class="left" onclick="window.history.back();">
        <img src="/images/back.png"/>
    </a>
    <p>请先登录</p>
</header>
<div id="container">
    <img src="/images/loginbg.png"/>
    <form action="/qblk/login.action" method="post" id="loginForm" name="loginForm">
        <div class="loginbox">
            <input id="username" type="text" name="username" class="username" placeholder="请输入手机号/邮箱">
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
<!-- jQuery 2.1.4 -->
<script src="/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<!-- Bootstrap 3.3.5 -->
<script src="/bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="/plugins/iCheck/icheck.min.js"></script>
<script>
    $(function () {
        $("#loginBtn").click(function () {
            var userName = Trim($("#username").val(), 'g');
            var userPwd = Trim($("#password").val(), 'g');
            if (userName == '') {
                alert("手机/邮箱不能为空!");
                $("#username").focus();
                return;
            }
            if (userPwd == '') {
                alert("密码不能为空!");
                $("#password").focus();
                return;
            }
            $.ajax({
                url: "login.action?username=" + userName + "&password=" + userPwd,
                type: "POST",
                success: function (result) {
                    var r = jQuery.parseJSON(result);
                    if (r.code == 0) {
                        alert(r.msg);
                        $("#username").focus();
                    } else {
                        window.self.location = "/raffle/lottery.action";
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