<#include "in_head.ftl">
<!--  头部 -->
<#include "head.ftl">
<!-- 边侧栏 -->
<#include "sider.ftl">

<!-- 内容导航区 -->
<div class="content-wrapper">

<#include "navigation.ftl">
    <!-- 具体内容区域 -->
    <form action="/admin/save_auction.action" method="post">
    <#if auction??>
        <input type="hidden" id="editid" name="editid" value="${auction.id}"/>
    </#if>
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
                                <input type="text" class="form-control"

                                <#if auction??>
                                       value="${auction.title}"
                                </#if>

                                       name="title" id="title" placeholder="拍卖活动：长城干红500ml">

                            </div>
                            <div class="form-group">
                                <label for="title">活动说明:</label>

                                <input type="text" class="form-control"

                                <#if auction??>
                                       value="${auction.memo}"
                                </#if>

                                       name="memo" id="memo" placeholder="填写介绍活动的内容">
                            </div>
                            <div class="form-group">
                                <label>活动起始时间:</label>

                                <div class="input-group">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <input type="text" class="form-control pull-right"

                                           <#if auction??>
                                           value="${auction.rang_time}"
                                           </#if>

                                           name="rang_time" id="rang_time"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>最低保证金（元）</label>

                                <div class="input-group">
                                    <input type="number" class="form-control"

                                           <#if auction??>
                                               value="${auction.deposit}"
                                           </#if>

                                           name="deposit" id="deposit" placeholder="如：1">
                                </div>
                            </div>
                            <div class="form-group">
                                <label>起拍价格（元）</label>

                                <div class="input-group">
                                    <input type="number" class="form-control"

                                    <#if auction??>
                                           value="${auction.amount}"
                                    </#if>


                                           name="amount" id="amount" placeholder="如：20">
                                </div>
                            </div>

                            <div class="form-group">
                                <label>加价幅度（元）</label>

                                <div class="input-group">
                                    <input type="number" class="form-control"

                                    <#if auction??>
                                           value="${auction.setp_amount}"
                                    </#if>

                                           name="setp_amount" id="setp_amount" placeholder="如：5">
                                </div>
                            </div>

                            <div class="form-group">
                                <label>提前读秒时间数（分钟）</label>

                                <div class="input-group">
                                    <input type="number" class="form-control"

                                    <#if auction??>
                                           value="${auction.time_second}"
                                    </#if>

                                           name="time_second" id="time_second" placeholder="如：30">
                                </div>
                            </div>

                            <div class="form-group">
                                <label>绑定商品</label>

                                <div class="input-group input-group-sm">
                                    <input type="text" name="searchKeyword"
                                    <#if auction??>
                                           value="${auction.good_name}"

                                            disabled
                                    </#if>

                                           id="searchKeyword" class="form-control">
                                        <span class="input-group-btn">
                                            <button class="btn btn-info btn-flat"
                                                <#if auction??>
                                                disabled
                                                </#if>
                                                    onclick="loadSearchCommodity();" type="button">搜索</button>
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
        document.title = "拍卖";
        $("#platform").addClass("active");
        setNav("拍卖", "创建拍卖商品");

        $('#rang_time').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'YYYY/MM/DD HH:mm:ss'});
    })

    /**
     * 搜索商品信息
     */
    function loadSearchCommodity() {
        var searchKeyword = $("#searchKeyword").val();

        $.ajax({
            url: "/admin/search_commodity.action?keyword=" + searchKeyword,
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