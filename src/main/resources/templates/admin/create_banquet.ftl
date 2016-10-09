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
                        <h3 class="box-title">约饭</h3>
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
        document.title = "约饭";
        $("#banquet").addClass("active");
        setNav("约饭", "创建饭局");
    })
</script>
<#include "in_footer.ftl">