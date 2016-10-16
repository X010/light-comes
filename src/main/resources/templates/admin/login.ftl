<!--
@Author: jeffrey
@Date:   2015-10-23T22:50:49+08:00
@Last modified by:   jeffrey
@Last modified time: 2016-05-21T12:09:52+08:00
-->


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>管理后台</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/dist/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="/dist/css//ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/dist/css/AdminLTE.min.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="/plugins/iCheck/square/blue.css">

</head>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <a href="../../index2.html"><b>管理后台</b></a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg">登录</p>

        <form action="login.action" method="post" id="login_form" name="login_form">
            <div class="form-group has-feedback">
                <input id="userName" name="userName" type="text" class="form-control" placeholder="用户名">
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input id="password" name="password" type="password" class="form-control" placeholder="密码">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                        <label>
                            <input id="rememberMe" name="rememberMe" type="checkbox"> 记住我
                        </label>
                    </div>
                </div>
                <!-- /.col -->
                <div class="col-xs-4">
                    <button type="button" id="loginBtn" name="loginBtn" class="btn btn-primary btn-block btn-flat">登录
                    </button>
                </div>
                <!-- /.col -->
            </div>
        </form>

        <!-- /.social-auth-links -->
    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 2.1.4 -->
<script src="/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<!-- Bootstrap 3.3.5 -->
<script src="/bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="/plugins/iCheck/icheck.min.js"></script>
<script>

    $(function () {
        $("#loginBtn").click(function () {
            var userName = Trim($("#userName").val(), 'g');
            var userPwd = Trim($("#password").val(), 'g');
            if (userName == '') {
                swal({title: "用户名不能为空",});
                $("#userName").focus();
                return;
            }
            if (userPwd == '') {
                swal({title: "密码不能为空",});
                $("#password").focus();
                return;
            }
            $.ajax({
                url: "login.action?userName=" + userName + "&password=" + userPwd,
                type: "POST",
                success: function (result) {
                    var r = jQuery.parseJSON(result);
                    if (r.code == 0) {
                        alert(r.msg);
                        $("#userName").focus();
                    } else {
                        window.self.location = "to_index.action";
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

        <#--$(function() {-->
        <#--&lt;#&ndash;var msg=${msg};&ndash;&gt;-->
        <#--&lt;#&ndash;if(msg!=null||msg!=''){&ndash;&gt;-->
        <#--&lt;#&ndash;$("#userName").val(${userName});&ndash;&gt;-->
        <#--&lt;#&ndash;alert(msg);&ndash;&gt;-->
        <#--&lt;#&ndash;return;&ndash;&gt;-->
        <#--&lt;#&ndash;}&ndash;&gt;-->
            <#--$('#rememberMe').iCheck({-->
                <#--checkboxClass: 'icheckbox_square-blue',-->
                <#--radioClass: 'iradio_square-blue',-->
                <#--increaseArea: '20%' // optional-->
            <#--});-->
        <#--});-->
</script>
</body>
</html>
