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
                                <th>内容</th>
                                <th>状态</th>
                                <th>类型</th>
                                <th>预约时间</th>
                                <th>联系人</th>
                                <th>联系电话</th>
                                <th>评价</th>
                                <th>操作</th>
                            </tr>
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