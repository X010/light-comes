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
                            <a href="/admin/create_raffle.action?action=new&id=0" class="btn btn-primary"><i class="fa fa-envelope-o"></i> 新建抽奖活动</a>
                        </div>
                    </div>
                    <div class="box-body">
                        <table class="table table-bordered">
                            <tr>
                                <th>编号</th>
                                <th>标题</th>
                                <th>创建时间</th>
                                <th>状态</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>操作</th>
                            </tr>
                        <#if raffles.data??>
                            <#list raffles.data as raffle>
                                <tr>
                                    <td>
                                    ${raffle.id}
                                    </td>
                                    <td>
                                    ${raffle.title}
                                    </td>
                                    <td>
                                    ${raffle.createtime?string("yyyy-MM-dd HH:mm:ss")}
                                    </td>
                                    <td>
                                        <#if raffle.status==1>
                                            正常
                                        </#if>
                                        <#if raffle.status==9>
                                            已删除
                                        </#if>
                                        <#if raffle.status==8>
                                            已结束
                                        </#if>
                                    </td>
                                    <td>
                                    ${raffle.start_time?string("yyyy-MM-dd HH:mm:ss")}
                                    </td>
                                    <td>
                                    ${raffle.end_time?string("yyyy-MM-dd HH:mm:ss")}
                                    </td>
                                    <td>
                                        <a class="badge  bg-green" href="/admin/create_raffle.action?action=edit&id=#{raffle.id}">编缉</a>
                                        &nbsp;&nbsp;
                                        <a class="badge  bg-red" href="javascript:if(confirm('您是否确定停用该抽奖活动')){window.location.href='/admin/delete_raffle.action?id=#{raffle.id}';}">停用
                                        </a>
                                        &nbsp;&nbsp;
                                        <a class="badge  bg-blue" href="/admin/raffle_list_detail.action?id=#{raffle.id}">参与者信息</a>
                                    </td>
                                </tr>
                            </#list>
                        </#if>
                        </table>
                    </div>
                </div>
            </div>
            <div class="box-footer clearfix">
                <ul class="pagination pagination-sm no-margin pull-right">
                <#if raffles??>
                    <#if (raffles.pages>0) >
                        <#list 1..raffles.pages as i>
                            <li><a href="/admin/raffle_list.action?page=${i}">${i}</a></li>
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
        document.title = "抽奖";
        $("#minizone").addClass("active");
        setNav("抽奖", "抽奖活动列表");
    })
</script>
<#include "in_footer.ftl">