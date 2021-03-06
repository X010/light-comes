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
                            <a href="${baseUrl}admin/create_overcharge.action" class="btn btn-primary"><i class="fa fa-envelope-o"></i> 新建砍价活动</a>
                        </div>
                    </div>
                    <div class="box-body">
                        <table class="table table-bordered">
                            <tr>
                                <th>编号</th>
                                <th>名称</th>
                                <th>状态</th>
                                <th>现价</th>
                                <th>剩余时间</th>
                                <th>初始价(元)</th>
                                <th>底价(元)</th>
                                <th>砍价幅度(元)</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>创建时间</th>
                                <th>商品名称</th>
                                <th>操作</th>
                            </tr>
                        <#if overchargeds??>
                            <#if overchargeds.data??>
                                <#list overchargeds.data as overcharged>
                                    <tr>
                                        <td>${overcharged.id}</td>
                                        <td>${overcharged.title}</td>
                                        <td>
                                            <#if overcharged.status==1>
                                                正常
                                            </#if>
                                            <#if overcharged.status==2>
                                                正常
                                            </#if>
                                            <#if overcharged.status==9>
                                                已删除
                                            </#if>
                                            <#if overcharged.status==8>
                                                已结束
                                            </#if>
                                        </td>
                                        <td>
                                        <span style="color: red">${overcharged.now_price}</span></td>
                                        <td>
                                            ${overcharged.free_time}天
                                        </td>
                                        <td>${overcharged.amount}</td>
                                        <td>${overcharged.over_amount}</td>
                                        <td>${overcharged.subtract_price}</td>
                                        <td>${overcharged.start_time?string("MM月dd日 HH:mm:ss")}</td>
                                        <td>${overcharged.end_time?string("MM月dd日 HH:mm:ss")}</td>
                                        <td>${overcharged.create_time?string("MM月dd日 HH:mm:ss")}</td>
                                        <td>${overcharged.good_name}</td>
                                        <td>
                                            <a class="badge  bg-green" href="${baseUrl}admin/create_overcharge.action?action=edit&id=${overcharged.id}">编缉</a>
                                            &nbsp;&nbsp;
                                            <a class="badge  bg-red"
                                               href="javascript:if(confirm('您是否确定停用该砍价')){window.location.href='${baseUrl}admin/delete_overcharged.action?id=#{overcharged.id}';}">停用
                                            </a>&nbsp;&nbsp;
                                            <a class="badge  bg-blue" href="${baseUrl}admin/overcharged_list_detail.action?id=#{overcharged.id}">参与者信息</a>
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
                <#if overchargeds??>
                    <#if (overchargeds.pages>0) >
                        <#list 1..overchargeds.pages as i>
                            <li><a href="${baseUrl}admin/overcharge_list.action?page=${i}">${i}</a></li>
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