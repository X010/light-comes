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
                            <a href="/admin/create_auction.action" class="btn btn-primary"><i class="fa fa-envelope-o"></i> 新建拍卖活动</a>
                        </div>
                    </div>
                    <div class="box-body">
                        <table class="table table-bordered">
                            <tr>
                                <th>编号</th>
                                <th>标题</th>
                                <th>活动开始时间</th>
                                <th>活动结束时间</th>
                                <th>状态</th>
                                <th>创建时间</th>
                                <th>最低保证金(元)</th>
                                <th>超拍价格(元)</th>
                                <th>拍卖商品名称</th>
                                <th>加价(元)</th>
                                <th>操作</th>
                            </tr>
                        <#if auctions??>
                            <#if auctions.data??>
                                <#list auctions.data as auction>
                                    <tr>
                                        <td>${auction.id}</td>
                                        <td>${auction.title}</td>
                                        <td> ${auction.start_time?string("yyyy-MM-dd HH:mm:ss")}</td>
                                        <td>
                                        ${auction.end_time?string("yyyy-MM-dd HH:mm:ss")}
                                        </td>
                                        <td>
                                            <#if auction.status==1>
                                                正常
                                            </#if>
                                            <#if auction.status==2>
                                                正常
                                            </#if>
                                            <#if auction.status==9>
                                                已删除
                                            </#if>
                                            <#if auction.status==8>
                                                已结束
                                            </#if>
                                        </td>
                                        <td>
                                        ${auction.create_time?string("yyyy-MM-dd HH:mm:ss")}
                                        </td>
                                        <td>
                                        ${auction.deposit}
                                        </td>
                                        <td>
                                        ${auction.amount}
                                        </td>
                                        <td>
                                        ${auction.good_name}
                                        </td>
                                        <td>
                                        ${auction.setp_amount}
                                        </td>
                                        <td>
                                            <a class="badge  bg-green" href="/admin/create_auction.action?action=edit&id=${auction.id}">编缉</a>
                                            &nbsp;&nbsp;
                                            <a class="badge  bg-red" href="javascript:if(confirm('您是否确定停用该拍卖活动')){window.location.href='/admin/delete_auction.action?id=${auction.id}';}">停用
                                            </a>
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
                <#if auctions??>
                    <#if (auctions.pages>0) >
                        <#list 1..auctions.pages as i>
                            <li><a href="/admin/auction_list.action?page=${i}">${i}</a></li>
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
        document.title = "拍卖";
        $("#platform").addClass("active");
        setNav("拍卖", "拍卖商品列表");
    })
</script>
<#include "in_footer.ftl">