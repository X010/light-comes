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
                    <form action="/admin/save_backlist.action" id="backlist_form" name="backlist_form" method="post">
                        <div class="box-header with-border">
                            <div class="col-md-12">
                                <div class="col-md-4">
                                    <select id="ctype" name="ctype" class="form-control">
                                        <option value="1">抽奖</option>
                                        <option value="2">拍卖</option>
                                        <option value="3">砍价</option>
                                        <option value="4">约饭</option>
                                    </select>
                                </div>
                                <div class="col-md-4">
                                    <input type="text" class="form-control" name="phone" id="phone" placeholder="如:1868499730">
                                </div>
                                <div class="col-md-4">
                                    <button type="submit" class="btn btn-primary"><i class="fa fa-envelope-o"></i>发送</button>
                                </div>
                            </div>
                        </div>
                    </form>

                    <div class="box-body">
                        <table class="table table-bordered">
                            <tr>
                                <th>编号</th>
                                <th>类别</th>
                                <th>加入时间</th>
                                <th>用户手机号</th>
                                <th>操作</th>
                            </tr>
                        <#if  backLists??>
                            <#if backLists.data??>
                                <#list backLists.data as backList>
                                    <tr>
                                        <td>${backList.id}</td>
                                        <td>
                                            <#if backList.ctype==1>
                                                抽奖
                                        </#if>
                                       <#if backList.ctype==2>
                                                拍卖
                                        </#if>
                                        <#if backList.ctype==3>
                                                砍价
                                        </#if>
                                        <#if backList.ctype==4>
                                                约饭
                                        </#if>
                                        </td>
                                        <td>${backList.createtime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                        <td>${backList.phone}</td>
                                        <td>
                                            <a class="badge  bg-red" href="javascript:if(confirm('您是否恢复该用户')){window.location.href='/admin/delete_backlist.action?id=${backList.id}';}">停用
                                            </a>
                                        </td>
                                    </tr>
                                </#list>
                            </#if>
                        </#if>
                        </table>
                    </div>
                </div>
            </div>
            <div class="box-footer clearfix">
                <ul class="pagination pagination-sm no-margin pull-right">
                <#if backLists??>
                    <#if (backLists.pages>0) >
                        <#list 1..backLists.pages as i>
                            <li><a href="/admin/backlist_list.action?page=${i}">${i}</a></li>
                        </#list>
                    </#if>
                </#if>
                </ul>
            </div>
        </div>
    </section>
</div>
<script lanuage="javascript">
    $(function () {
        document.title = "抽奖";
        $("#user").addClass("active");
        setNav("设置", "黑名单设置");

        $("#backlist_form").validate({
            rules:{
                phone: {
                    required: true,
                    minlength: 11,
                    maxlength:11
                }
            },
            messages: {
                phone: {
                    required: "请输入用户手机号码",
                    minlength: "您的用户名不能少于11位字符",
                    maxlength: "您的用户不能多于11位字符"
                }
            }
        });
    })
</script>
<#include "in_footer.ftl">