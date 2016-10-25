<#include "in_head.ftl">
<!--  头部 -->
<#include "head.ftl">
<!-- 边侧栏 -->
<#include "sider.ftl">

<!-- 内容导航区 -->
<div class="content-wrapper">
<#include "navigation.ftl">
    <!-- 具体内容区域 -->
    <form action="/admin/save_banquet.action" id="banquet_form" name="banquet_form" method="post">
    <#if banquet??>
        <input id="editid" name="editid" value="${banquet.id}" type="hidden"/>
    </#if>

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
                                <input type="text" class="form-control"
                                <#if banquet??>
                                       value="${banquet.title}"
                                </#if>

                                       name="title" id="title" placeholder="如：东来顺商务餐">
                            </div>
                            <div class="form-group">
                                <label for="title">活动说明</label>

                                <input type="text" class="form-control"
                                <#if banquet??>
                                       value="${banquet.memo}"
                                </#if>
                                       name="memo" id="memo" placeholder="填写介绍活动的内容">
                            </div>
                            <div class="form-group">
                                <label>活动起始时间</label>

                                <div class="input-group">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <input type="text" class="form-control pull-right"
                                    <#if banquet??>
                                           value="${banquet.rang_time}"
                                    </#if>
                                           name="rang_time" id="rang_time"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>饭局人数（人）</label>

                                <div class="input-group">
                                    <input type="number" class="form-control" name="outnumber"
                                    <#if banquet??>
                                           value="${banquet.outnumber}"
                                    </#if>
                                           id="outnumber" placeholder="如：1">
                                </div>
                            </div>
                            <div class="form-group">
                                <label>参与价格（元）</label>

                                <div class="input-group">
                                    <input type="number" class="form-control"
                                    <#if banquet??>
                                           value="${banquet.amount}"
                                    </#if>
                                           name="amount" id="amount" placeholder="如：500">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="title">承办人</label>

                                <input type="text" class="form-control"
                                <#if banquet??>
                                       value="${banquet.author_nickname}"
                                </#if>
                                       name="author_nickname" id="author_nickname" placeholder="如：张三">
                            </div>
                            <div class="form-group">
                                <label for="title">承办人电话</label>

                                <input type="text" class="form-control"
                                <#if banquet??>
                                       value="${banquet.author_telephone}"
                                </#if>
                                       name="author_telephone" id="author_telephone" placeholder="如：18684332100">
                            </div>
                            <div class="form-group">
                                <label for="title">承办地址</label>

                                <input type="text" class="form-control"
                                <#if banquet??>
                                       value="${banquet.author_address}"
                                </#if>
                                       name="author_address" id="author_address" placeholder="如：江东">
                            </div>
                            <div class="form-group">
                                <label for="title">酒水礼品说明</label>
                                <textarea id="info" name="info" class="form-control" style="height: 300px"><#if banquet??>${banquet.info}</#if></textarea>
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
        document.title = "约饭";
        $("#banquet").addClass("active");
        setNav("约饭", "创建饭局");
        $("#info").wysihtml5();
        $('#rang_time').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'YYYY/MM/DD HH:mm:ss'});

        //JS验证
        $("#banquet_form").validate({
            rules: {
                title: {
                    required: true,
                    minlength: 4,
                    maxlength: 16
                },
                memo: {
                    required: true,
                    minlength: 5,
                    maxlength: 48
                },
                rang_time: {
                    required: true
                },
                number: {
                    required: true
                },
                amount: {
                    required: true
                },
                author_nickname: {
                    required: true
                },
                author_telephone: {
                    required: true
                },
                info: {
                    required: true
                }
            },
            messages: {
                title: {
                    required: "请输入名称",
                    minlength: "您的用户名不能少于4位字符",
                    maxlength: "您的用户不能多于16位字符"
                },
                memo: {
                    required: "请输入说明信息",
                    minlength: "您的用户名不能少于5位字符",
                    maxlength: "您的用户不能多于48位字符"
                },
                rang_time: {
                    required: "请选择时间范围"
                },
                number: {
                    required: "请输入每桌人数"
                },
                amount: {
                    required: "请输入每个需要交的钱"
                },
                author_nickname: {
                    required: "请输入承办人"
                },
                author_telephone: {
                    required: "请输入承办人电话"
                },
                author_address: {
                    required: "请输入承办地址"
                },
                info: {
                    required: "请输入酒水礼品说明"
                }
            }
        });
    })
</script>
<#include "in_footer.ftl">