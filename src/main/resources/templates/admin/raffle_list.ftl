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
                            <a href="/fun/minizone/worko/worko_list.shtml" class="btn btn-primary"><i class="fa fa-envelope-o"></i> 新建工单</a>
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
                                        </td>
                                        <td>
                                            ${raffle.start_time?string("yyyy-MM-dd HH:mm:ss")}
                                        </td>
                                        <td>
                                            ${raffle.end_time?string("yyyy-MM-dd HH:mm:ss")}
                                        </td>
                                        <td>

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
                    <li><a href="#">&laquo;</a></li>
                    <li><a href="#">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">&raquo;</a></li>
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