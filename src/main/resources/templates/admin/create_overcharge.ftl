<#include "in_head.ftl">
<!--  头部 -->
<#include "head.ftl">
<!-- 边侧栏 -->
<#include "sider.ftl">

<!-- 内容导航区 -->
<div class="content-wrapper">
<#include "navigation.ftl">
    <!-- 具体内容区域 -->
    <form id="create_overcharge_form" name="create_overcharge_form" action="${baseUrl}admin/save_overcharge.action" method="post" enctype="multipart/form-data">

    <#if overcharged??>
        <input type="hidden" id="editid" name="editid" value="${overcharged.id}"/>
    </#if>
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-primary">

                        <div class="box-header with-border">
                            <h3 class="box-title">砍价商品</h3>
                        </div>
                        <div class="box-body">
                            <div class="form-group">
                                <label for="title">砍价活动名称</label>
                                <input type="text" class="form-control"
                                <#if overcharged??>
                                       value="${overcharged.title}"
                                </#if>
                                       name="title" id="title" placeholder="砍价活动名">
                            </div>
                            <div class="form-group">
                                <label>活动起始时间:</label>

                                <div class="input-group">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <input type="text" class="form-control pull-right"
                                    <#if overcharged??>
                                           value="${overcharged.rang_time}"
                                    </#if>
                                           name="rang_time" id="rang_time"/>
                                </div>
                                <!-- /.input group -->
                            </div>

                            <div class="form-group">
                                <label for="subtract_price">砍价力度(共需砍多少刀)</label>
                                <input type="number" name="subtract_count"
                                <#if overcharged??>
                                       value="${overcharged.subtract_count}"
                                </#if>
                                       class="form-control" id="subtract_count"
                                       placeholder="如：1">
                            </div>
                            <#--<div class="form-group">-->
                                <#--<label for="subtract_price">砍价幅度</label>-->
                                <#--<input type="number" name="subtract_price"-->
                                <#--<#if overcharged??>-->
                                       <#--value="${overcharged.subtract_price}"-->
                                <#--</#if>-->
                                       <#--class="form-control" id="subtract_price"-->
                                       <#--placeholder="如：1">-->
                            <#--</div>-->
                            <div class="form-group">
                                <label for="amount">活动价</label>
                                <input type="number" name="amount"
                                      <#if overcharged??>
                                       value="${overcharged.amount}"
                                    </#if>
                                       class="form-control" id="amount"
                                       placeholder="如：2">
                            </div>
                            <div class="form-group">
                                <label for="over_amount">活动底价</label>
                                <input type="number" name="over_amount"
                                <#if overcharged??>
                                       value="${overcharged.over_amount}"
                                </#if>
                                       class="form-control" id="over_amount"
                                       placeholder="如：2">
                            </div>
                            <div class="form-group">
                                <label>绑定商品</label>
                                <div class="input-group input-group-sm">
                                    <input type="text" name="searchKeyword"
                                    <#if overcharged??>
                                           value="${overcharged.good_name}"
                                           disabled
                                    </#if>
                                           id="searchKeyword" class="form-control">
                                        <span class="input-group-btn">
                                            <button class="btn btn-info btn-flat"
                                            <#if overcharged??>
                                                    disabled
                                            </#if>
                                                    onclick="loadSearchCommodity();" type="button">搜索
                                            </button>
                                        </span>
                                </div>
                                <table id="goods_list" class="table table-striped">
                                    <thead>
                                    <th style="width: 10%"></th>
                                    <th style="width: 40%">名称</th>
                                    <th style="width: 10%">条码</th>
                                    <th style="width: 10%">商品编码</th>
                                    <th style="width: 10%">价格</th>
                                    <th style="width: 10%">规格</th>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                            <div class="form-group">
                                <label for="inventory">商品库存</label>
                                <input type="number" name="inventory"
                                <#if overcharged??>
                                       value="${overcharged.inventory}"
                                </#if>
                                       class="form-control" id="inventory"
                                       placeholder="如：2">
                            </div>
                            <div class="form-group">
                                <label for="share_title">分享标题</label>
                                <input type="text" class="form-control"
                                <#if overcharged??>
                                       value="${overcharged.share_title}";
                                </#if>
                                       name="share_title" id="share_title" placeholder="签到标题">
                            </div>
                            <div class="form-group">
                                <label for="share_title">分享描述</label>
                                <input type="text" class="form-control"
                                <#if overcharged??>
                                       value="${overcharged.share_desc}";
                                </#if>
                                       name="share_desc" id="share_desc" placeholder="签到标题">
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="share_photo">分享图标(300*300)</label>
                                <div class="controls">
                                    <input class="input-file uniform_on" id="share_photo" name="share_photo_file" type="file">
                                    <input id="old_share_photo" name="old_share_photo_file" type="hidden" <#if overcharged??> value="${overcharged.share_photo!""}</#if>">
                                    <div id="image-div2" class="overflow">
                                    <#if overcharged??>
                                        <img id="focus_image_preview2" name="focus_image_preview2"
                                             src="http://www.qubulikou.com/${overcharged.share_photo!""}" width="300" height="300">
                                    </#if>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="title">活动规则说明</label>
                                <textarea id="info" name="info" class="form-control"
                                          style="height: 300px"><#if overcharged??>${overcharged.info!""}</#if></textarea>
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
        </section>
    </form>
</div>
<script lanuage="javascript">
    $(function () {
        $("#info").wysihtml5({
            language:'zh-CN'
        });
        document.title = "砍价";
        $("#estate").addClass("active");
        setNav("砍价", "创建砍价商品");

        $('#rang_time').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'YYYY/MM/DD HH:mm:ss'});

        $("#create_overcharge_form").validate({
            rules: {
                title: {
                    required: true,
                    minlength: 4,
                    maxlength: 16
                },
                rang_time: {
                    required: true
                },
                subtract_price: {
                    required: true
                },
                amount: {
                    required: true
                },
                over_amount: {
                    required: true
                }
            },
            messages: {
                title: {
                    required: "请输入名称",
                    minlength: "您的用户名不能少于4位字符",
                    maxlength: "您的用户不能多于16位字符"
                },
                rang_time: {
                    required: "请选择时间范围"
                },
                subtract_price: {
                    required: "请输入每次砍价幅度"
                },
                amount: {
                    required: "请输入活动初始价"
                },
                over_amount: {
                    required: "请输入底价"
                }
            },
            submitHandler: function (form) {
                var editid = $("#editid").val();
                if (editid != null && editid != '' && editid != 'undefined') {

                } else {
                    var cate2 = $("#goodsid").val();
                    if (cate2 == null || cate2 == 'undefined' || cate2 == '') {
                        alert("请选择对应的商品");
                        return false;
                    }
                }
                form.submit();
            }
        });
    });

    /**
     * 搜索商品信息
     */
    function loadSearchCommodity() {
        var searchKeyword = $("#searchKeyword").val();

        $.ajax({
            url: "${baseUrl}admin/search_commodity.action?keyword=" + searchKeyword,
            dataType: "json",
            success: function (data, textStatus) {
                $("#goods_list tbody").empty();
                if (data != null && data.length > 0) {
                    $.each(data, function (i, val) {
                        $("#goods_list tbody").append("<tr>" +
                                "<td><input type='radio' id='goodsid' name='goodsid' value='" + val.id + "' /></td>" +
                                "<td>" + val.name + "</td>" +
                                "<td>" + val.barcode + "</td>" +
                                "<td>" + val.goodscode + "</td>" +
                                "<td>" + val.price + "</td>" +
                                "<td>" + val.specification + "</td>" +
                                "</tr>");
                    });
                }
            }
        });
    }
</script>
<#include "in_footer.ftl">