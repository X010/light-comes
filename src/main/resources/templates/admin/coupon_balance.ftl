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
                <div class="box">
                    <div class="box-header with-border">
                        <div class="pull-right">
                            <div class="input-group">
                                <input type="text" name="phone" id="phone" class="form-control" placeholder="请输出店家老板的手机号..."/>
                            <span class="input-group-btn">
                                <button type='button' name='seach' onclick="search_coupon();" id='search-btn' class="btn btn-flat"><i class="fa fa-search"></i>
                                </button>
                            </span>
                            </div>
                        </div>
                    </div>
                    <div class="box-body">
                        <table id="coupon_list" class="table table-bordered">

                            <thead>
                            <tr>
                                <th>是否结算</th>
                                <th>序号</th>
                                <th>优惠名称</th>
                                <th>优惠序号</th>
                                <th>使用者手机号</th>
                                <th>优惠金额</th>
                                <th>使用时间</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="box-footer clearfix">
                <ul class="pagination pagination-sm no-margin pull-right">
                    <div class="pull-right">
                        <button type="button" onclick="coupon_balance();" class="btn btn-primary"><i class="fa fa-envelope-o"></i>结算</button>
                    </div>
                </ul>
            </div>
        </div>
    </section>
</div>
<script lanuage="javascript">
    $(function () {
        document.title = "抽奖";
        $("#minizone").addClass("active");
        setNav("抽奖", "优惠劵结算");
    });

    /**
     * 对选中的优惠劵进行兼容
     */
    function coupon_balance() {
        var phone = $("#phone").val();
        var ids = "";
        $("input[type='checkbox']:checked").each(function () {
            ids += $(this).attr('value') + ',';
        });

        //发给服务端进行数据校验并生成结算单
        if (ids != "" && ids != null && ids != 'undefined') {
            $.ajax({
                url: "coupon_balance_submit.action?phone=" + phone + "&ids=" + ids,
                dataType: "json",
                success: function (data, textStatus) {
                    window.location.href="${baseUrl}admin/coupon_balance_list.action";
                }
            });

        }
    }

    function search_coupon() {
        var phone = $("#phone").val();


        if (phone == null || phone == 'undefined' || phone.length < 11) {
            alert("请输入正确的手机号码");
            return false;
        }

        $.ajax({
            url: "coupon_balance_search.action?phone=" + phone,
            dataType: "json",
            success: function (data, textStatus) {
                console.log(data);
                if (data != null && data.length > 0) {
                    $("#coupon_list tbody").empty();
                    $.each(data, function (i, val) {
                        var content = "<tr>";
                        content += "<td><input type='checkbox' name='isbalance' value='" + val.id + "' /></td>";
                        content += "<td>" + val.id + "</td>";
                        content += "<td>" + val.coupon_title + "</td>";
                        content += "<td>" + val.cardno + "</td>";
                        content += "<td>" + val.source_phone + "</td>";
                        content += "<td>" + val.price + "</td>";
                        content += "<td>" + val.used_time + "</td>";
                        content += "</tr>";
                        $("#coupon_list tbody").append(content);
                    });
                } else {
                    alert("暂无可结算优惠劵");
                }
            }
        });
    }
</script>
<#include "in_footer.ftl">