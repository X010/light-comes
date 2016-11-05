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
                                <th>砍价时间</th>
                                <th>出价（元）</th>
                            </tr>
                        <#if ors??>
                            <#if ors.data??>
                                <#list ors.data as or>
                                    <tr>
                                        <td>${or.id}</td>
                                        <td>${or.phone}</td>
                                        <td>${or.createtime?string("MM月dd日 HH:mm:ss")}</td>
                                        <td><span style="color:red;">${or.amount}</span></td>
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
                <#if ors??>
                    <#if (ors.pages>0) >
                        <#list 1..ors.pages as i>
                            <li><a href="/admin/overcharged_list_detail.action?id=${aid}&page=${i}">${i}</a></li>
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
        document.title = "砍价";
        $("#estate").addClass("active");
        setNav("砍价", "砍价商品列表");
    })
</script>
<#include "in_footer.ftl">