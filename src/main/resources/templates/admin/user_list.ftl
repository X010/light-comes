<#include "in_head.ftl">
<!--  头部 -->
<#include "head.ftl">
<!-- 边侧栏 -->
<#include "sider.ftl">

<!-- 内容导航区 -->
<div class="content-wrapper">
<#include "navigation.ftl">
    <!-- 具体内容区域 -->
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <div class="box">
                    <div class="box-header with-border">
                        <div class="pull-right">
                            <a href="/admin/create_user.action" class="btn btn-primary"><i class="fa fa-envelope-o"></i>新建管理员</a>
                        </div>
                    </div>
                    <div class="box-body">
                        <table class="table table-bordered">
                            <tr>
                                <th>编号</th>
                                <th>真实姓名</th>
                                <th>登录帐号</th>
                                <th>操作</th>
                            </tr>
                            <#if users??>
                                <#if users.data??>
                                    <#list users.data as user>
                                        <tr>
                                            <td>${user.id}</td>
                                            <td>${user.real_name}</td>
                                            <td>${user.user_name}</td>
                                            <td>
                                                <a class="badge  bg-red" href="javascript:if(confirm('您是否确定删除该用户')){window.location.href='/admin/delete_user.action?id=#{user.id}';}">停用</a>
                                            </td>
                                        </tr>
                                    </#list>
                                </#if>
                            </#if>
                        </table>
                    </div>
                </div>
            </div>
            <div class="box-footer clearfix">
                <ul class="pagination pagination-sm no-margin pull-right">
                <#if users??>
                    <#if (users.pages>0) >
                        <#list 1..users.pages as i>
                            <li><a href="/admin/user_list.action?page=${i}">${i}</a></li>
                        </#list>
                    </#if>
                </#if>
                </ul>
            </div>
        </div>
    </section>
</div>
<script lanuage="javascript">
    $(function () {
        document.title = "用户列表";
        $("#user").addClass("active");
        setNav("设置", "管理员用户列表");
    })
</script>
<#include "in_footer.ftl">