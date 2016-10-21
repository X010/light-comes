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
                        <div class="col-md-12">
                            <div class="col-md-4">
                                <select id="ctype" name="ctype" class="form-control">
                                    <option value="1">抽奖</option>
                                    <option value="2">拍卖</option>
                                    <option value="3">砍价</option>
                                    <option value="4">约饭</option>
                                </select>
                            </div>
                            <div class="col-md-4">
                                <input type="text" class="form-control" name="title" id="title" placeholder="如:1868499730">
                            </div>
                            <div class="col-md-4">
                                <a href="/admin/create_raffle.action?action=new&id=0" class="btn btn-primary"><i class="fa fa-envelope-o"></i> 添加黑名单</a>
                            </div>
                        </div>
                    </div>
                    <div class="box-body">
                        <table class="table table-bordered">
                            <tr>
                                <th>编号</th>
                                <th>类别</th>
                                <th>加入时间</th>
                                <th>用户手机号</th>
                                <th>操作</th>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div class="box-footer clearfix">
                <ul class="pagination pagination-sm no-margin pull-right">
                <#if raffles??>
                    <#if (raffles.pages>0) >
                        <#list 1..raffles.pages as i>
                            <li><a href="/admin/raffle_list.action?page=${i}">${i}</a></li>
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
        $("#user").addClass("active");
        setNav("设置", "黑名单设置");
    })
</script>
<#include "in_footer.ftl">