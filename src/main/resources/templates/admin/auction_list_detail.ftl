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
                                <th>拍卖时间</th>
                                <th>出价(元)</th>
                            </tr>
                            <#if ars??>
                                <#if ars.data??>
                                    <#list ars.data as ar>
                                        <tr>
                                            <td>${ar.id}</td>
                                            <td>${ar.phone}</td>
                                            <td>${ar.create_time?string("MM月dd日 HH:mm:ss")}</td>
                                            <td><span style="color: red">${ar.price}</span></td>
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

                </ul>
            </div>
        </div>
    </section>
</div>
<script lanuage="javascript">
    $(function () {
        document.title = "拍卖";
        $("#platform").addClass("active");
        setNav("拍卖", "拍卖商品列表");
    })
</script>
<#include "in_footer.ftl">