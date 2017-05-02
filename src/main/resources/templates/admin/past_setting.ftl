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
        <form action="${baseUrl}admin/past_setting.action" id="past_setting" name="past_setting" method="post"  enctype="multipart/form-data">
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">签到设置</h3>
                        </div>
                        <div class="box-body">
                            <div class="form-group">
                                <label>签到周期开始时间</label>

                                <div class="input-group">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <input type="text" class="form-control pull-right" name="start_time" id="start_time"
                                            <#if pr??>
                                           value="${pr.start_time?string("yyyy-MM-dd HH:mm:ss")}"
                                           </#if>
                                            />
                                </div>
                            </div>
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
                            <div class="form-group">
                                <label for="title">奖品优惠券</label>
                                <select id="coupon_id" name="coupon_id" class="form-control">
                                    <option value="0">不设优惠劵</option>
                                <#if coupons??>
                                    <#list coupons as coupon>
                                        <option value="${coupon.id}" <#if pr.coupon_id==coupon.id>selected</#if>>${coupon.title}</option>
                                    </#list>
                                </#if>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="title">签到标题</label>
                                <input type="text" class="form-control"
                                <#if pr??>
                                    value="${pr.title}";
                                </#if>
                                       name="title" id="title" placeholder="签到标题">
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="photo_up">活动图片(建议使用360*100JPG图片)</label>
                                <div class="controls">
                                    <input class="input-file uniform_on" id="photo" name="photo_up" type="file">
                                </div>
                                <div id="image-div" class="overflow">
                                <#if pr.photo??>
                                    <img id="focus_image_preview" name="focus_image_preview"
                                         src="http://www.qubulikou.com/${pr.photo!""}" width="360" height="100">
                                </#if>
                                    <input id="old_photo" name="old_photo" type="hidden" <#if pr??> value="${pr.photo!""}</#if>">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="share_title">分享标题</label>
                                <input type="text" class="form-control"
                                <#if pr??>
                                       value="${pr.share_title}";
                                </#if>
                                       name="share_title" id="share_title" placeholder="签到标题">
                            </div>
                            <div class="form-group">
                                <label for="share_title">分享描述</label>
                                <input type="text" class="form-control"
                                <#if pr??>
                                       value="${pr.share_desc}";
                                </#if>
                                       name="share_desc" id="share_desc" placeholder="签到标题">
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="share_photo">分享图标（300*300）</label>
                                <div class="controls">
                                    <input class="input-file uniform_on" id="share_photo" name="share_photo_file" type="file">
                                <#if pr.share_photo??>
                                    <img id="focus_image_preview" name="focus_image_preview"
                                         src="http://www.qubulikou.com/${pr.share_photo!""}" width="300" height="300">
                                </#if>
                                    <input id="old_share_photo" name="old_share_photo" type="hidden" <#if pr??> value="${pr.share_photo!""}</#if>">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="title">活动规则说明</label>
                                <textarea id="info" name="info" class="form-control"
                                          style="height: 300px"><#if pr??>${pr.info}</#if></textarea>
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
    $.fn.datetimepicker.dates['zh-CN'] = {
        days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
        daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
        daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
        months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        today: "今天",
        suffix: [],
        meridiem: ["上午", "下午"]
    };
    $(function () {
        document.title = "签到";
        $("#check").addClass("active");
        setNav("签到", "签到设置");
        $("#info").wysihtml5({
            language:'zh-CN'
        });
//        var editor = new wysihtml5.Editor('editor', {
//            toolbar: 'toolbar',
//            parserRules:  wysihtml5ParserRules
//        });
        $('#start_time').datetimepicker({
            format: "yyyy-mm-dd hh:ii:ss",
            autoclose: true,
            todayBtn: true,
            language:'zh-CN',
            pickerPosition:"bottom-left"
        });
    })
</script>
<#include "in_footer.ftl">