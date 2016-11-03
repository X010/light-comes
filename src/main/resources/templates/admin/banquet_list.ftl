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
                            <a href="/admin/create_banquet.action" class="btn btn-primary"><i class="fa fa-envelope-o"></i> 新建饭局</a>
                        </div>
                    </div>
                    <div class="box-body">
                        <table class="table table-bordered">
                            <tr>
                                <th>编号</th>
                                <th>名称</th>
                                <th>报名人数</th>
                                <th>状态</th>
                                <th>创建时间</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>承办人</th>
                                <th>承办人电话</th>
                                <th>每桌人数</th>
                                <th>单价</th>
                                <th>操作</th>
                            </tr>
                        <#if banquets??>
                            <#if banquets.data??>
                                <#list banquets.data as banquet>
                                    <tr>
                                        <td>${banquet.id}</td>
                                        <td>${banquet.title}</td>
                                        <td></td>
                                        <td>
                                            <#if banquet.status==1>
                                                创建
                                            </#if>
                                            <#if banquet.status==2>
                                                正常
                                            </#if>
                                            <#if banquet.status==9>
                                                停用
                                            </#if>
                                            <#if banquet.status==8>
                                                已结束
                                            </#if>
                                        </td>
                                        <td>
                                        ${banquet.create_time?string("MM月dd日 HH:mm:ss")}
                                        </td>
                                        <td>
                                        ${banquet.start_time?string("MM月dd日 HH:mm:ss")}
                                        </td>
                                        <td>
                                        ${banquet.end_time?string("MM月dd日 HH:mm:ss")}
                                        </td>
                                        <td>${banquet.author_nickname}</td>
                                        <td>${banquet.author_telephone}</td>
                                        <td>${banquet.outnumber}</td>
                                        <td>${banquet.amount}</td>
                                        <td>
                                            <a class="badge  bg-green" href="/admin/create_banquet.action?action=edit&id=${banquet.id}">编缉</a>
                                            &nbsp;&nbsp;
                                            <a class="badge  bg-red" href="javascript:if(confirm('您是否确定停用该饭局')){window.location.href='/admin/delete_banquet.action?id=${banquet.id}';}">停用
                                            </a>&nbsp;&nbsp;
                                            <a class="badge  bg-blue" href="/admin/banquet_list_detail.action?id=#{banquet.id}">参与者信息</a>
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
                <#if banquets??>
                    <#if (banquets.pages>0) >
                        <#list 1..banquets.pages as i>
                            <li><a href="/admin/banquet_list.action?page=${i}">${i}</a></li>
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
        document.title = "约饭";
        $("#banquet").addClass("active");
        setNav("约饭", "饭局列表");
    })
</script>
<#include "in_footer.ftl">