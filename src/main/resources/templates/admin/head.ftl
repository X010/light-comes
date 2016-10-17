<header class="main-header">
    <!-- Logo -->
    <a href="#" class="logo">
        <span class="logo-mini"><b>物</b></span>
        <span class="logo-lg"><b>管理后台</b></span>
    </a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>
        <!-- Navbar Right Menu -->
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <#---->
                <#--<li class="dropdown messages-menu">-->
                    <#--<a href="#" class="dropdown-toggle" data-toggle="dropdown">-->
                        <#--<i class="fa fa-envelope-o"></i>-->
                        <#--<span class="label label-success">4</span>-->
                    <#--</a>-->
                    <#--<ul class="dropdown-menu">-->
                        <#--<li class="header">您有4条待处理工单</li>-->
                        <#--<li>-->
                            <#--<ul class="menu">-->
                                <#--<li>-->
                                    <#--<a href="#">-->
                                        <#--<p>[工单标题]</p>-->
                                    <#--</a>-->
                                <#--</li>-->
                            <#--</ul>-->
                        <#--</li>-->
                        <#--<li class="footer"><a href="#">查看所有工单</a></li>-->
                    <#--</ul>-->
                <#--</li>-->
                <!-- /.messages-menu -->

                <!-- User Account Menu -->
                <li class="dropdown user user-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="<#if Session.sys_user.head_img??>${Session.sys_user.head_img}</#if>/dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
                        <span class="hidden-xs">[${Session.sys_user.real_name!}]</span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="user-header">
                            <img src="<#if Session.sys_user.head_img??>${Session.sys_user.head_img}</#if>/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
                            <p>

                            </p>
                        </li>
                        <!-- Menu Footer-->
                        <li class="user-footer">
                            <div class="pull-right">
                                <a href="javascript:logout()" class="btn btn-default btn-flat">退出</a>
                            </div>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
</header>

<script type="text/javascript">
    function logout(){
        var flag = confirm("确定退出系统吗?");
        if(flag){
            var url = 'logout.action';
            $.ajax({
                url: url ,
                type: 'POST',
                success: function (returndata) {
                    window.location.href = "to_login.action";
                },
                error: function (returndata) {
                    alert(returndata);
                }
            });
        }
    }
</script>
