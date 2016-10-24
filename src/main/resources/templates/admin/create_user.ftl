<#include "in_head.ftl">
<!--  头部 -->
<#include "head.ftl">
<!-- 边侧栏 -->
<#include "sider.ftl">

<!-- 内容导航区 -->
<div class="content-wrapper">
<#include "navigation.ftl">
    <!-- 具体内容区域 -->
    <form action="/admin/save_user.action" method="post">
        <section class="content">
        <div class="row">
            <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <h3 class="box-title">创建管理员</h3>
                    </div>
                    <div class="box-body">
                        <div class="form-group">
                            <label for="title">登录名称</label>
                            <input type="text" class="form-control" name="user_name" id="user_name" placeholder="admin">
                        </div>

                        <div class="form-group">
                            <label for="title">密码</label>
                            <input type="password" class="form-control" name="password" id="password" placeholder="">
                        </div>

                        <div class="form-group">
                            <label for="title">真实姓名</label>
                            <input type="text" class="form-control" name="real_name" id="real_name" placeholder="">
                        </div>
                    </div>
                    <div class="box-footer">
                        <div class="pull-right">
                            <button type="submit" class="btn btn-primary"><i class="fa fa-envelope-o"></i>发送</button>
                        </div>
                        <button type="reset" class="btn btn-default"><i class="fa fa-times"></i>取消</button>
                    </div>
                </div>
            </div>
        </div>
    </section>
    </form>
</div>
<script lanuage="javascript">
    $(function () {
        document.title = "新建用户";
        $("#user").addClass("active");
        setNav("设置", "新建用户");
    })
</script>
<#include "in_footer.ftl">