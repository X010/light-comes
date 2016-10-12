<#include "in_head.ftl">
<!--  头部 -->
<#include "head.ftl">
<!-- 边侧栏 -->
<#include "sider.ftl">

<!-- 内容导航区 -->
<div class="content-wrapper">
<#include "navigation.ftl">
    <!-- 具体内容区域 -->
    <form action="/admin/save_coupon.action" method="post">
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">抽奖活动</h3>
                        </div>
                        <div class="box-body">
                            <div class="form-group">
                                <label for="title">优惠劵名称</label>
                                <input type="text" class="form-control" name="title" id="title" placeholder="优惠劵名称">
                            </div>
                            <div class="form-group">
                                <label>优惠劵起始时间:</label>

                                <div class="input-group">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <input type="text" class="form-control pull-right" name="rang_time" id="rang_time"/>
                                </div>
                                <!-- /.input group -->
                            </div>
                            <div class="form-group">
                                <label>优惠券类型</label>
                                <select id="ctype" name="ctype" class="form-control">
                                    <option value="1">全局类</option>
                                    <option value="2">栏目类</option>
                                    <option value="3">单名类</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="num">生成数量</label>
                                <input type="number" name="num" class="form-control" id="num" placeholder="生成数量">
                            </div>
                            <div class="form-group">
                                <label for="num">优惠券金额</label>

                                <div class="input-group">
                                    <span class="input-group-addon">$</span>
                                    <input id="price" name="price" type="text" class="form-control">
                                    <span class="input-group-addon">.00</span>
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
    </form>
</div>
<script lanuage="javascript">
    $(function () {
        document.title = "抽奖";
        $("#minizone").addClass("active");
        setNav("抽奖", "创建优惠卷");

        $('#rang_time').daterangepicker();
    })
</script>
<#include "in_footer.ftl">