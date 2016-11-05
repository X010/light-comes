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
                                <th>序号</th>
                                <th>创建时间</th>
                                <th>优惠序号</th>
                                <th>分配手机号</th>
                                <th>优惠金额</th>
                                <th>使用时间</th>
                                <th>状态</th>
                            </tr>
                        <#if crs??>
                            <#if crs.data??>
                                <#list crs.data as cr>
                                    <tr>
                                        <td>${cr.id}</td>
                                        <td>${cr.createtime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                        <td>${cr.cardno}</td>
                                        <td>
                                            <#if cr.phone??>
                                                ${cr.phone}
                                            </#if>
                                        </td>
                                        <td>${cr.price}</td>
                                        <td>
                                            <#if cr.updatetime??>
                                                ${cr.updatetime?string("yyyy-MM-dd HH:mm:ss")}
                                            </#if>
                                        </td>
                                        <td><#if cr.status==2>
                                            正常
                                        </#if>
                                            <#if cr.status==3>
                                                已使用
                                            </#if></td>
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
                <#if crs??>
                    <#if (crs.pages>0) >
                        <#list 1..crs.pages as i>
                            <li><a href="/admin/coupon_list_detail.action?id=${aid}&page=${i}">${i}</a></li>
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
        setNav("抽奖", "优惠劵列表");
    })
</script>
<#include "in_footer.ftl">