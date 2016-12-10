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
                    <div class="box-body">
                        <table class="table table-bordered">
                            <tr>
                                <th>编号</th>
                                <th>结算金额(元)</th>
                                <th>结算手机号</th>
                                <th>结算时间</th>
                                <th>操作</th>
                            </tr>
                            <#if brs??>
                                <#if brs.data??>
                                    <#list brs.data as br>
                                        <tr>
                                            <td>${br.id}</td>
                                            <td>${br.total_price}</td>
                                            <td>${br.phone}</td>
                                            <td>${br.create_time?string("yyyy-MM-dd HH:mm:ss")}</td>
                                            <td> <a class="badge  bg-blue" href="${baseUrl}admin/coupon_balance_list_detail.action?bill_id=${br.id}">查看详单</a></td>
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
                            <li><a href="${baseUrl}admin/coupon_balance_list.action?page=${i}">${i}</a></li>
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
        setNav("抽奖", "优惠劵结算列表");
    })
</script>
<#include "in_footer.ftl">