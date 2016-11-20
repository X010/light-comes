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
        <form action="/admin/past_setting.action" id="past_setting" name="past_setting" method="post">
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">签到设置</h3>
                        </div>
                        <div class="box-body">
                            <div class="form-group">
                                <label>签到周期天数（天）</label>

                                <div class="input-group">
                                    <input type="number" class="form-control" name="interval_day"
                                    <#if pr??>
                                           value="${pr.interval_day?c}"
                                    </#if>
                                           id="interval_day" placeholder="如：10">
                                </div>
                            </div>

                            <div class="form-group">
                                <label>每日可以签到次数（次数）</label>

                                <div class="input-group">
                                    <input type="number" class="form-control" name="past_times"
                                    <#if pr??>
                                           value="${pr.past_times?c}"
                                    </#if>
                                           id="past_times" placeholder="如：1">
                                </div>
                            </div>

                            <div class="form-group">
                                <label>总量（ml）</label>

                                <div class="input-group">

                                    <input type="number" class="form-control" name="total_drunk"
                                             <#if pr??>
                                           value="${pr.total_drunk?c}"
                                            </#if>
                                           id="total_drunk" placeholder="如：500">
                                </div>
                            </div>


                            <div class="form-group">
                                <label>随机值设定</label>
                                <select id="past_type" name="past_type" class="form-control">
                                    <option value="1" <#if pr??> <#if pr.past_type==1> selected</#if> </#if> >固定值</option>
                                    <option value="2" <#if pr??> <#if pr.past_type==2> selected</#if> </#if> >随机值</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label>每次获得量（ml）</label>

                                <div class="input-group">
                                    <input type="number" class="form-control" name="fix_drunk"
                                    <#if pr??>
                                           value="${pr.fix_drunk?c}"
                                    </#if>
                                           id="fix_drunk" placeholder="如：50">
                                </div>
                            </div>

                            <div class="form-group">
                                <label>每次获得量下限（ml）</label>

                                <div class="input-group">
                                    <input type="min_drunk" class="form-control" name="min_drunk"
                                    <#if pr??>
                                           value="${pr.min_drunk?c}"
                                    </#if>
                                           id="min_drunk" placeholder="如：50">
                                </div>
                            </div>


                            <div class="form-group">
                                <label>每次获得量上限（ml）</label>

                                <div class="input-group">
                                    <input type="max_drunk" class="form-control" name="max_drunk"
                                    <#if pr??>
                                           value="${pr.max_drunk?c}"
                                    </#if>
                                           id="max_drunk" placeholder="如：100">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="title">奖品名称</label>
                                <input type="text" class="form-control"

                                <#if pr??>
                                       value="${pr.prizes_name}"
                                </#if>

                                       name="prizes_name" id="prizes_name" placeholder="奖品名称">
                            </div>
                        </div>
                        <div class="box-footer">
                            <div class="pull-right">
                                <button type="submit" class="btn btn-primary"><i class="fa fa-envelope-o"></i>发送
                                </button>
                            </div>
                            <button type="reset" class="btn btn-default"><i class="fa fa-times"></i>取消</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </section>
</div>
<script lanuage="javascript">
    $(function () {
        document.title = "签到";
        $("#check").addClass("active");
        setNav("签到", "签到设置");
    })
</script>
<#include "in_footer.ftl">