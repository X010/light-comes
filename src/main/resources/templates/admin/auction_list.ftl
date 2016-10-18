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
                                <th>活动开始时间</th>
                                <th>活动结束时间</th>
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
                                            <a class="badge  bg-green" href="#">编缉</a>
                                            &nbsp;&nbsp;
                                            <a class="badge  bg-red" href="javascript:if(confirm('您是否确定停用该拍卖活动')){window.location.href='#';}">停用
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
        document.title = "拍卖";
        $("#platform").addClass("active");
        setNav("拍卖", "拍卖商品列表");
    })
</script>
<#include "in_footer.ftl">