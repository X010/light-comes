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
                        <h3 class="box-title">拍卖活动</h3>
                    </div>
                    <div class="box-body">
                        <div class="form-group">
                            <label for="title">拍卖商品名称</label>
                            <input type="text" class="form-control" name="title" id="title" placeholder="拍卖活动：长城干红500ml">
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
                            <label>最低保证金（元）</label>

                            <div class="input-group">
                                <input type="number" class="form-control" name="deposit" id="deposit" placeholder="如：1">
                            </div>
                        </div>
                        <div class="form-group">
                            <label>起拍价格（元）</label>

                            <div class="input-group">
                                <input type="number" class="form-control" name="amount" id="amount" placeholder="如：20">
                            </div>
                        </div>

                        <div class="form-group">
                            <label>加价幅度（元）</label>

                            <div class="input-group">
                                <input type="number" class="form-control" name="setp_amount" id="setp_amount" placeholder="如：5">
                            </div>
                        </div>

                        <div class="form-group">
                            <label>提前读秒时间数（分钟）</label>

                            <div class="input-group">
                                <input type="number" class="form-control" name="time_second" id="time_second" placeholder="如：30">
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
        document.title = "拍卖";
        $("#platform").addClass("active");
        setNav("拍卖", "创建拍卖商品");
    })
</script>
<#include "in_footer.ftl">