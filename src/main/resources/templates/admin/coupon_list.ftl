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
                            <a href="/admin/create_coupon.action" class="btn btn-primary"><i class="fa fa-envelope-o"></i> 新建优惠劵</a>
                        </div>
                    </div>
                    <div class="box-body">
                        <table class="table table-bordered">
                            <tr>
                                <th>编号</th>
                                <th>名称</th>
                                <th>状态</th>
                                <th>类型</th>
                                <th>数量(张)</th>
                                <th>金额(元)</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>创建时间</th>
                                <th>操作</th>
                            </tr>
                        <#if coupons??>
                            <#if coupons.data??>
                                <#list coupons.data as coupon>
                                    <tr>
                                        <td>${coupon.id}</td>
                                        <td>${coupon.title}</td>
                                        <td>
                                            <#if coupon.status==1>
                                                已创建
                                            </#if>
                                            <#if coupon.status==2>
                                                已生成
                                            </#if>
                                            <#if coupon.status==9>
                                                已删除
                                            </#if>
                                        </td>
                                        <td>
                                            <#if coupon.ctype==1>
                                                全局型
                                            </#if>
                                            <#if coupon.ctype==2>
                                                单类型
                                            </#if>
                                            <#if coupon.ctype==3>
                                                单品型
                                            </#if>
                                        </td>
                                        <td>${coupon.num}</td>
                                        <td>${coupon.price}</td>
                                        <td>${coupon.use_start_time?string("yyyy-MM-dd")}</td>
                                        <td>${coupon.use_end_time?string("yyyy-MM-dd")}</td>
                                        <td>${coupon.createtime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                        <td>
                                            <#if coupon.status==1>
                                                <a class="badge  bg-green"
                                                   href="javascript:if(confirm('确认是否生成该类优惠劵')){window.location.href='/admin/produce_coupon.action?id=${coupon.id}';}">生成</a>
                                                &nbsp;&nbsp;
                                            </#if>
                                            <a class="badge  bg-red"
                                               href="javascript:if(confirm('您是否确认停用该类优惠劵')){window.location.href='/admin/delete_coupon.action?id=${coupon.id}';}">
                                                停用</a>
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
                <#if coupons??>
                    <#if (coupons.pages>0) >
                        <#list 1..coupons.pages as i>
                            <li><a href="/admin/coupon_list.action?page=${i}">${i}</a></li>
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