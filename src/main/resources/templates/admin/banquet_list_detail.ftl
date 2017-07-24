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

                    </div>
                    <div class="box-body">
                        <table class="table table-bordered">
                            <tr>
                                <th>编号</th>
                                <th>手机号</th>
                                <th>报名时间</th>
                                <th>订单号</th>
                                <th>商户订单号</th>
                                <th>订单状态</th>
                                <th>操作</th>
                            </tr>
                        <#if brs??>
                            <#if brs.data??>
                                <#list brs.data as br>
                                    <tr>
                                        <td>${br.id}</td>
                                        <td>${br.phone}</td>
                                        <td>${br.createtime?string("MM月dd日 HH:mm:ss")}</td>
                                        <td>${br.orderNo}</td>
                                        <td>${br.tradeno}</td>
                                        <#if br.status==2>
                                            <td>已付款</td>
                                            <#else>
                                            <td>未付款</td>
                                        </#if>
                                        <td>
                                            <a class="badge  bg-red" href="javascript:if(confirm('您是否确定针对该用户进行退款')){}">退款</a>
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
                <#if brs??>
                    <#if (brs.pages>0) >
                        <#list 1..brs.pages as i>
                            <li><a href="${baseUrl}admin/banquet_list_detail.action?id=${aid}&page=${i}">${i}</a></li>
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