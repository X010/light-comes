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
                        <div class="form-group">
                            <label for="title">饭局名称</label>
                            <input type="text" class="form-control" name="title" id="title" placeholder="如：东来顺商务餐">
                        </div>
                        <div class="form-group">
                            <label for="title">活动说明:</label>

                            <input type="text" class="form-control" name="memo" id="memo" placeholder="填写介绍活动的内容">
                        </div>
                        <div class="form-group">
                            <label>活动起始时间:</label>

                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="fa fa-calendar"></i>
                                </div>
                                <input type="text" class="form-control pull-right" name="rang_time" id="rang_time"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>饭局人数（人）</label>

                            <div class="input-group">
                                <input type="number" class="form-control" name="outnumber" id="outnumber" placeholder="如：1">
                            </div>
                        </div>
                        <div class="form-group">
                            <label>参与价格（元）</label>

                            <div class="input-group">
                                <input type="number" class="form-control" name="amount" id="amount" placeholder="如：500">
                            </div>
                        </div>
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