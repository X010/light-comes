<#include "in_head.ftl">
<!--  头部 -->
<#include "head.ftl">
<!-- 边侧栏 -->
<#include "sider.ftl">

<!-- 内容导航区 -->
<div class="content-wrapper">
<#include "navigation.ftl">
    <!-- 具体内容区域 -->
    <form action="${baseUrl}admin/save_coupon.action" id="coupon_form" name="coupon_form" method="post">
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
                                <select id="ctype" name="ctype" onchange="changeCtype();" class="form-control">
                                    <option value="1">全局类</option>
                                    <option value="2">商品栏目类</option>
                                    <option value="3">商品类</option>
                                </select>
                            </div>
                            <div style="display: none;" id="goodscate" class="form-group">
                                <input type="hidden" name="commoditycateoryid" id="commoditycateoryid" value="0"/>


                                <div class="col-md-6">
                                    <select id="cate1" name="cate1" class="form-control" onchange="changeSubCategories()">
                                    <#if categories??>
                                        <#list categories as categorie>
                                            <option value="${categorie.category1}">${categorie.category1}</option>
                                        </#list>
                                    </#if>
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <select id="cate2" name="cate2" class="form-control">
                                        <option value="0">qua</option>
                                    <#if subCategories??>
                                        <#list subCategories as subCategorie>
                                            <option value="${subCategorie.id}">${subCategorie.category2}</option>
                                        </#list>
                                    </#if>
                                    </select>
                                </div>

                            </div>
                            <div style="display: none;" id="goods" class="form-group">
                                <input type="hidden" name="commodityid" id="commodityid" value="0"/>

                                <div class="input-group input-group-sm">
                                    <input type="text" name="searchKeyword" id="searchKeyword" class="form-control">
                                        <span class="input-group-btn">
                                            <button class="btn btn-info btn-flat" onclick="loadSearchCommodity();" type="button">搜索</button>
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


        $("#coupon_form").validate({
            rules: {
                title: {
                    required: true,
                    minlength: 4,
                    maxlength: 16
                },
                rang_time: {
                    required: true
                },
                num: {
                    required: true
                },
                price: {
                    required: true
                }
            },
            messages: {
                title: {
                    required: "请输入名称",
                    minlength: "您的用户名不能少于4位字符",
                    maxlength: "您的用户不能多于16位字符"
                }
                , rang_time: {
                    required: "请选择时间范围"
                },
                num: {
                    required: "请输入数量"
                },
                price: {
                    required: "输入优惠劵价格"
                }
            },
            submitHandler: function (form) {
                var selectType = parseInt($("#ctype").val());
                if (selectType == 3) {
                    var cate2 = $("#cate2").val();
                    if (cate2 == null || cate2 == 'undefined' || cate2 == '') {
                        alert("请选择对应的商品");
                        return false;
                    }
                }
                form.submit();
            }
        });
    });


    function changeCtype() {
        var goodscate = $("#goodscate");
        var goods = $("#goods");
        var ctype = $("#ctype").val();

        switch (parseInt(ctype)) {
            case 1:
                goodscate.hide();
                goods.hide();
                break;
            case 2:
                goodscate.show();
                goods.hide();
                break;
            case 3:
                goodscate.hide();
                goods.show();
                break;
        }
    }


    //获取子栏目
    function changeSubCategories() {
        var parentName = $("#cate1").val();

        $.ajax({
            url: "${baseUrl}admin/search_commodity_category.action?category=" + parentName,
            dataType: "json",
            success: function (data, textStatus) {
                $("#cate2").empty();
                if (data != null && data.length > 0) {
                    $.each(data, function (i, val) {
                        $("#cate2").append("<option value='" + val.id + "'>" + val.category2 + "</option>")
                    });
                }
            }
        });
    }


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