<aside class="main-sidebar">
    <section class="sidebar">
        <div class="user-panel">
            <div class="pull-left image">

                <img src="<#if Session.sys_user.head_img??>${Session.sys_user.head_img}</#if>${baseUrl}dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p>[${Session.sys_user.real_name!}]</p>
            </div>
        </div>

        <ul class="sidebar-menu">
            <li class="header">[曲不离口]</li>
            <li id="fouces" class="treeview">
                <a href="#"><i class="fa   fa-columns"></i> <span>焦点图</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="${baseUrl}admin/focus_manage.action"> <i class="fa  fa-newspaper-o"></i> <span>焦点图管理</span></a></li>
                </ul>
            </li>
            <li id="check" class="treeview">
                <a href="#"><i class="fa   fa-check-circle"></i> <span>签到</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="${baseUrl}admin/past_setting.action"> <i class="fa   fa-gift"></i> <span>会员签到设置</span></a></li>
                    <li><a href="${baseUrl}admin/past_detail.action"> <i class="fa  fa-calendar"></i> <span>会员签到详情</span></a></li>
                </ul>
            </li>
            <li id="minizone" class="treeview">
                <a href="#"><i class="fa  fa-indent"></i> <span>抽奖</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <!-- 信息发布 -->
                    <li><a href="${baseUrl}admin/create_raffle.action?action=new&id=0"> <i class="fa  fa-asterisk"></i> <span>新建抽奖</span></a></li>
                    <li><a href="${baseUrl}admin/create_coupon.action"> <i class="fa   fa-credit-card"></i> <span>创建优惠劵</span></a></li>
                    <li><a href="${baseUrl}admin/raffle_list.action"><i class="fa   fa-bars"></i> <span>抽奖活动列表</span></a></li>
                    <li><a href="${baseUrl}admin/coupon_list.action"> <i class="fa  fa-briefcase"></i> <span>优惠劵列表</span></a></li>
                    <li><a href="${baseUrl}admin/coupon_balance.action"> <i class="fa   fa-external-link-square"></i> <span>优惠劵结算</span></a></li>
                    <li><a href="${baseUrl}admin/coupon_balance_list.action"> <i class="fa   fa-barcode"></i> <span>优惠劵结算表</span></a></li>
                </ul>
            </li>
            <li id="estate" class="treeview">
                <a href="#"><i class="fa fa-area-chart"></i> <span>砍价</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="${baseUrl}admin/create_overcharge.action"> <i class="fa   fa-book"></i> <span>新建砍价商品</span></a></li>
                    <li><a href="${baseUrl}admin/overcharge_list.action"><i class="fa  fa-barcode"></i> <span>砍价商品列表</span></a></a></li>
                </ul>
            </li>
            <li id="platform" class="treeview">
                <a href="#"><i class="fa  fa-gavel"></i> <span>拍卖</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="${baseUrl}admin/create_auction.action"> <i class="fa fa-book"></i> <span>新建拍卖商品</span></a></li>
                    <li><a href="${baseUrl}admin/auction_list.action"><i class="fa  fa-barcode"></i> <span>拍卖商品列表</span></a></li>
                </ul>
            </li>
            <li id="banquet" class="treeview">
                <a href="#"><i class="fa fa-cutlery"></i> <span>约饭</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="${baseUrl}admin/create_banquet.action"> <i class="fa fa-book"></i> <span>新建饭局</span></a></li>
                    <li><a href="${baseUrl}admin/banquet_list.action"><i class="fa   fa-barcode"></i> <span>约局列表</span></a></li>
                </ul>
            </li>
            <li id="user" class="treeview">
                <a href="#"><i class="fa fa-cogs"></i> <span>设置</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="${baseUrl}admin/create_user.action"> <i class="fa  fa-user"></i> <span>新建管理员</span></a></li>
                    <li><a href="${baseUrl}admin/user_list.action"><i class="fa   fa-users"></i> <span>管理员管理</span></a></li>
                    <li><a href="${baseUrl}admin/backlist_list.action"><i class="fa    fa-warning"></i> <span>黑名单管理</span></a></li>
                </ul>
            </li>
        </ul>
    </section>
</aside>
