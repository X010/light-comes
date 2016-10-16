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
                    <form id="create_overcharge_form" name="create_overcharge_form" action="create_overcharge_save.action" method="post">
                        <div class="box-header with-border">
                            <h3 class="box-title">砍价商品</h3>
                        </div>
                        <div class="box-body">
                            <div class="form-group">
                                <label for="title">砍价活动名</label>
                                <input type="text" class="form-control" name="title" id="title" placeholder="砍价活动名">
                            </div>
                            <div class="form-group">
                                <label>选择砍价商品</label>
                                <div class="input-group m-b">
                                    <input type="text" class="form-control" name="goodsName" id="goodsName"> <span
                                        class="input-group-btn">
                            <button type="button" class="btn btn-primary" onclick="queryGoods()">查询
                            </button> </span></div>
                                <select id="sku_id" name="sku_id" class="form-control">
                                </select>
                            </div>
                            <div class="form-group">
                                <label>活动起始时间:</label>

                                <div class="input-group">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <input type="text" class="form-control pull-right" name="end_time" id="end_time"/>
                                </div>
                                <!-- /.input group -->
                            </div>
                            <div class="form-group">
                                <label for="每次减少价格"></label>
                                <input type="number" name="subtract_price" class="form-control" id="subtract_price"
                                       placeholder="每次减少价格">
                            </div>
                            <div class="form-group">
                                <label for="底价"></label>
                                <input type="number" name="lower_price" class="form-control" id="lower_price"
                                       placeholder="底价">
                            </div>
                        </div>

                        <div class="box-footer">
                            <div class="pull-right">
                                <button type="submit" class="btn btn-primary"><i class="fa fa-envelope-o"></i>发送
                                </button>
                            </div>
                            <button type="reset" class="btn btn-default"><i class="fa fa-times"></i>取消</button>
                        </div>
                    </form>
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

        $('#end_time').daterangepicker();
    })

    function queryGoods() {
        var name = $("#goodsName").val();
        if (name == '') {
            alert("请输入商品名称");
            return;
        }
        $.ajax({
            type: "post",
            url: "query_goods.action",
            data: {"name": name},
            success: function (goods) {
                ;
                var data = $.parseJSON(goods);
                if (data) {
                    var html = "";
                    $.each(data, function (i, result) {
                        html += '<option value=' + result.goodsid + '>' + result.name + '</option>';
                    })
                    $("#sku_id").empty().append(html).trigger("chosen:updated");
                }
            }
        });
    }
</script>
<#include "in_footer.ftl">