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
                        </div>
                    </div>
                    <div class="box-body">
                        <table class="table table-bordered">
                            <tr>
                                <th>编号</th>
                                <th>会员手机号码</th>
                                <th>今天喝掉次数</th>
                                <th>今天喝掉(ml)</th>
                                <th>本周期喝掉次数</th>
                                <th>本周期喝掉(ml)</th>
                                <th>最后更新时间</th>
                                <th>操作</th>
                            </tr>
                        <#if prs??>
                            <#if prs.data??>
                                <#list prs.data as rs>
                                    <tr>
                                        <td>${rs.id}</td>
                                        <td>${rs.phone}</td>
                                        <td>${rs.today_times}</td>
                                        <td>${rs.today_drunk}</td>
                                        <td>${rs.cycle_times}</td>
                                        <td>${rs.cycle_drunk}</td>
                                        <td>${rs.update_time?string("yyyy-MM-dd HH:mm:ss")}</td>
                                        <td>
                                            <a class="badge  bg-red" href="/admin/clear_user_past.action?status=2&phone=${rs.phone}">清空本期签到</a>
                                            <a class="badge  bg-blue" href="/admin/clear_user_past.action?status=1&phone=${rs.phone}">清空今天签到</a>
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
                <#if prs??>
                    <#if (prs.pages>0) >
                        <#list 1..prs.pages as i>
                            <li><a href="/admin/past_detail.action?page=${i}">${i}</a></li>
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
        document.title = "签到";
        $("#check").addClass("active");
        setNav("签到", "签到详情");
    })
</script>
<#include "in_footer.ftl">