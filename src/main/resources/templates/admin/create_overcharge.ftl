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
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <h3 class="box-title">砍价商品</h3>
                    </div>
                    <div class="box-body">
                    </div>
                    <div class="box-footer">
                        <div class="pull-right">
                            <button type="submit" class="btn btn-primary"><i class="fa fa-envelope-o"></i>发送</button>
                        </div>
                        <button type="reset" class="btn btn-default"><i class="fa fa-times"></i>取消</button>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<script lanuage="javascript">
    $(function () {
        document.title = "砍价";
        $("#estate").addClass("active");
        setNav("砍价", "创建砍价商品");
    })
</script>
<#include "in_footer.ftl">