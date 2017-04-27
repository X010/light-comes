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
                                <th>序号</th>
                                <th>优惠名称</th>
                                <th>优惠序号</th>
                                <th>使用者手机号</th>
                                <th>优惠金额</th>
                                <th>使用时间</th>
                            </tr>
                        <#if curs??>
                            <#list curs as cur>
                                <tr>
                                    <td>${cur.id}</td>
                                    <td>${cur.coupon_title}</td>
                                    <td>${cur.cardno}</td>
                                    <td>${cur.source_phone}</td>
                                    <td>${cur.price}</td>
                                    <td>${cur.used_time?string("yyyy-MM-dd HH:mm:ss")}</td>
                                </tr>
                            </#list>
                        </#if>
                        </table>
                    </div>
                </div>
            </div>
            <div class="box-footer clearfix">
                <ul class="pagination pagination-sm no-margin pull-right">

                </ul>
            </div>
        </div>
    </section>
</div>
<script lanuage="javascript">
    $(function () {
        document.title = "抽奖";
        $("#coupon").addClass("active");
        setNav("抽奖", "优惠劵结算列表");
    })
</script>
<#include "in_footer.ftl">