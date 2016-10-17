<aside class="main-sidebar">
    <section class="sidebar">
        <div class="user-panel">
            <div class="pull-left image">
                <img src="/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p>[员工姓名]</p>
            </div>
        </div>

        <ul class="sidebar-menu">
            <li class="header">[曲不离口]</li>
            <li id="fouces" class="treeview">
                <a href="#"><i class="fa   fa-columns"></i> <span>焦点图</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="/admin/focus_manage.action"> <i class="fa  fa-newspaper-o"></i> <span>焦点图管理</span></a></li>
                </ul>
            </li>
            <li id="minizone" class="treeview">
                <a href="#"><i class="fa  fa-indent"></i> <span>抽奖</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <!-- 信息发布 -->
                    <li><a href="/admin/create_raffle.action?action=new&id=0"> <i class="fa  fa-asterisk"></i> <span>新建抽奖</span></a></li>
                    <li><a href="/admin/create_coupon.action"> <i class="fa   fa-credit-card"></i> <span>创建优惠劵</span></a></li>
                    <li><a href="/admin/raffle_list.action"><i class="fa   fa-bars"></i> <span>抽奖活动列表</span></a></li>
                    <li><a href="/admin/coupon_list.action"> <i class="fa  fa-briefcase"></i> <span>优惠劵列表</span></a></li>
                </ul>
            </li>
            <li id="estate" class="treeview">
                <a href="#"><i class="fa fa-area-chart"></i> <span>砍价</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="/admin/create_overcharge.action"> <i class="fa   fa-book"></i> <span>新建砍价商品</span></a></li>
                    <li><a href="/admin/overcharge_list.action"><i class="fa  fa-barcode"></i> <span>砍价商品列表</span></a></a></li>
                </ul>
            </li>
            <li id="platform" class="treeview">
                <a href="#"><i class="fa  fa-gavel"></i> <span>拍卖</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="/admin/create_auction.action"> <i class="fa fa-book"></i> <span>新建拍卖商品</span></a></li>
                    <li><a href="/admin/auction_list.action"><i class="fa  fa-barcode"></i> <span>拍卖商品列表</span></a></li>
                </ul>
            </li>
            <li id="banquet" class="treeview">
                <a href="#"><i class="fa fa-cutlery"></i> <span>约饭</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="/admin/create_banquet.action"> <i class="fa fa-book"></i> <span>新建饭局</span></a></li>
                    <li><a href="/admin/banquet_list.action"><i class="fa   fa-barcode"></i> <span>约局列表</span></a></li>
                </ul>
            </li>
        </ul>
    </section>
</aside>
