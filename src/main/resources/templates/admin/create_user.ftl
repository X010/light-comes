<#include "in_head.ftl">
<!--  头部 -->
<#include "head.ftl">
<!-- 边侧栏 -->
<#include "sider.ftl">

<!-- 内容导航区 -->
<div class="content-wrapper">
<#include "navigation.ftl">
    <!-- 具体内容区域 -->
    <form action="${baseUrl}admin/save_user.action" id="create_user" method="post">
    <#if users??>
        <input type="hidden" id="editid" name="editid" value="${users.id}"/>
    </#if>
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
                                <input type="text" class="form-control"
                                <#if users??>

                                       value="${users.user_name}"

                                       disabled
                                </#if>
                                       name="user_name" id="user_name" placeholder="admin">
                            </div>

                            <div class="form-group">
                                <label for="title">密码</label>
                                <input type="password"
                                <#if users??>

                                       value="${users.password}"

                                </#if>
                                       class="form-control" name="password" id="password" placeholder="">
                            </div>

                            <div class="form-group">
                                <label for="title">真实姓名</label>
                                <input type="text" class="form-control"

                                <#if users??>

                                       value="${users.real_name}"

                                </#if>
                                       name="real_name" id="real_name" placeholder="">
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

        $("#create_user").validate({
            rules:{
                user_name: {
                    required: true,
                    minlength: 5,
                    maxlength:16
                },
                password: {
                    required: true,
                    minlength: 6,
                    maxlength:16
                },
                real_name: {
                    required: true,
                    minlength: 2,
                    maxlength:16
                }
            },
            messages: {
                user_name: {
                    required: "请输入用户名",
                    minlength: "您的用户名不能少于5位字符",
                    maxlength: "您的用户不能多于16位字符"
                },
                password: {
                    required: "请输入密码",
                    minlength: "您的用户名不能少于6位字符",
                    maxlength: "您的用户不能多于16位字符"
                },
                real_name: {
                    required: "请输入真实名称",
                    minlength: "您的用户名不能少于2位字符",
                    maxlength: "您的用户不能多于16位字符"
                }
            }
        });
    });


</script>
<#include "in_footer.ftl">