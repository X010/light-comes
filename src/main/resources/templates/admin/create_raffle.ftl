<#include "in_head.ftl">
<!--  头部 -->
<#include "head.ftl">
<!-- 边侧栏 -->
<#include "sider.ftl">

<!-- 内容导航区 -->
<div class="content-wrapper">
<#include "navigation.ftl">
    <!-- 具体内容区域 -->
    <form action="/admin/save_raffle.action" enctype="multipart/form-data" method="post">
    <#if editid??>
        <input type="hidden" name="editid" id="editid" value="${editid}"/>
    </#if>
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">抽奖活动</h3>
                        </div>
                        <div class="box-body">
                            <div class="form-group">
                                <label for="title">抽奖活动名称</label>
                                <input type="text" class="form-control"

                                <#if raffle??>
                                       value="${raffle.title}"
                                </#if>

                                       name="title" id="title" placeholder="注册送奖品活动">
                            </div>
                            <div class="form-group">
                                <label>活动起始时间:</label>

                                <div class="input-group">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <input type="text" class="form-control pull-right"

                                    <#if raffle??>
                                           value="${raffle.rang_time}"
                                    </#if>

                                           name="rang_time" id="rang_time"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label>每人每天抽奖次数:</label>
                                <select id="times" name="times" class="form-control">
                                    <option value="1">每人每天1次</option>
                                    <option value="2">每人每天2次</option>
                                    <option value="3">每人每天3次</option>
                                    <option value="4">每人每天4次</option>
                                    <option value="5">每人每天5次</option>
                                    <option value="6">每人每天6次</option>
                                    <option value="7">每人每天7次</option>
                                    <option value="8">每人每天8次</option>
                                    <option value="9">每人每天9次</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="title">活动说明:</label>

                                <input type="text" class="form-control"

                                <#if raffle??>
                                       value="${raffle.memo}"
                                </#if>

                                       name="memo" id="memo" placeholder="">
                            </div>

                            <div class="control-group">
                                <label class="control-label" for="photo_up">活动图片(建议使用JPG图片)</label>

                                <div class="controls">
                                    <input class="input-file uniform_on" id="photo" name="photo_up" type="file">
                                </div>
                            </div>

                            <div class="control-group">
                                <label class="control-label" for="coupon">奖劵列表</label>

                                <div class="controls">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 20%">序号</th>
                                            <th style="width: 50%">优惠劵</th>
                                            <th style="width: 30%">中奖率(百分比*100)</th>
                                        </tr>
                                        <tr>
                                            <td>1.</td>
                                            <td>
                                                <select id="cid_1" name="cid_1" class="form-control">
                                                    <option value="0">不设优惠劵</option>
                                                <#if coupons??>
                                                    <#list coupons as coupon>
                                                        <option value="${coupon.id}">${coupon.title}</option>
                                                    </#list>
                                                </#if>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="number" value="0" min="0" max="100" class="form-control" name="cid_rate_1" id="cid_rate_1">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>2.</td>
                                            <td>
                                                <select id="cid_2" name="cid_2" class="form-control">
                                                    <option value="0">不设优惠劵</option>
                                                <#if coupons??>
                                                    <#list coupons as coupon>
                                                        <option value="${coupon.id}">${coupon.title}</option>
                                                    </#list>
                                                </#if>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="number" value="0" min="0" max="100" class="form-control" name="cid_rate_2" id="cid_rate_2">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>3.</td>
                                            <td>
                                                <select id="cid_3" name="cid_3" class="form-control">
                                                    <option value="0">不设优惠劵</option>
                                                <#if coupons??>
                                                    <#list coupons as coupon>
                                                        <option value="${coupon.id}">${coupon.title}</option>
                                                    </#list>
                                                </#if>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="number" value="0" min="0" max="100" class="form-control" name="cid_rate_3" id="cid_rate_3">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>4.</td>
                                            <td>
                                                <select id="cid_4" name="cid_4" class="form-control">
                                                    <option value="0">不设优惠劵</option>
                                                <#if coupons??>
                                                    <#list coupons as coupon>
                                                        <option value="${coupon.id}">${coupon.title}</option>
                                                    </#list>
                                                </#if>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="number" value="0" min="0" max="100" class="form-control" name="cid_rate_4" id="cid_rate_4">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>5.</td>
                                            <td>
                                                <select id="cid_5" name="cid_5" class="form-control">
                                                    <option value="0">不设优惠劵</option>
                                                <#if coupons??>
                                                    <#list coupons as coupon>
                                                        <option value="${coupon.id}">${coupon.title}</option>
                                                    </#list>
                                                </#if>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="number" value="0" min="0" max="100" class="form-control" name="cid_rate_5" id="cid_rate_5">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>6.</td>
                                            <td>
                                                <select id="cid_6" name="cid_6" class="form-control">
                                                    <option value="0">不设优惠劵</option>
                                                <#if coupons??>
                                                    <#list coupons as coupon>
                                                        <option value="${coupon.id}">${coupon.title}</option>
                                                    </#list>
                                                </#if>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="number" value="0" min="0" max="100" class="form-control" name="cid_rate_6" id="cid_rate_6">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>7.</td>
                                            <td>
                                                <select id="cid_7" name="cid_7" class="form-control">
                                                    <option value="0">不设优惠劵</option>
                                                <#if coupons??>
                                                    <#list coupons as coupon>
                                                        <option value="${coupon.id}">${coupon.title}</option>
                                                    </#list>
                                                </#if>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="number" value="0" min="0" max="100" class="form-control" name="cid_rate_7" id="cid_rate_7">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>8.</td>
                                            <td>
                                                <select id="cid_8" name="cid_8" class="form-control">
                                                    <option value="0">不设优惠劵</option>
                                                <#if coupons??>
                                                    <#list coupons as coupon>
                                                        <option value="${coupon.id}">${coupon.title}</option>
                                                    </#list>
                                                </#if>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="number" value="0" min="0" max="100" class="form-control" name="cid_rate_8" id="cid_rate_8">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>9.</td>
                                            <td>
                                                <select id="cid_9" name="cid_9" class="form-control">
                                                    <option value="0">不设优惠劵</option>
                                                <#if coupons??>
                                                    <#list coupons as coupon>
                                                        <option value="${coupon.id}">${coupon.title}</option>
                                                    </#list>
                                                </#if>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="number" value="0" min="0" max="100" class="form-control" name="cid_rate_9" id="cid_rate_9">
                                            </td>
                                        </tr>
                                    </table>
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
        setNav("抽奖", "创建抽奖活动");

        $('#rang_time').daterangepicker();


    <#if raffle_str??>
        var coupon_items =${raffle_str};
        if (coupon_items != null && coupon_items.length > 0) {
            for (var i = 0; i < coupon_items.length; i++) {
                var item = coupon_items[i];
                var cid_rate = $("#cid_rate_" + item.cindex);
                var cid = $("#cid_" + item.cindex);


                cid_rate.val(item.winrate);
                cid.val(item.cid);
            }
        }

    </#if>
    })
</script>
<#include "in_footer.ftl">