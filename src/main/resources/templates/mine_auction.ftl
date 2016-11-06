<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">
    <title></title>
    <link rel="stylesheet" href="/ratchet/css/ratchet.css" type="text/css">
    <link rel="stylesheet" href="/ratchet/weui.css" type="text/css">
    <link rel="stylesheet" href="/ratchet/app.css" type="text/css">
    <script type="text/javascript" src="/js/laytpl.js"></script>
    <script type="text/javascript" src="/js/spin.min.js"></script>
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script id="post_list" type="text/html">
     {{# for(var i = 0, len = d.length; i < len; i++){ }}
     <div class = "mui-panel">
       <div class="item-order-info">
           <div id="item-group"><span class="item-code">活动名称:{{d[i].title}}</span></div>
       </div>
       <div class="item-goods-list">
         <div class="items">
             <div class="item-col">
                 <div class="item-goods">
                     <div class="item-gname">
                         <span class="item-flag">
                           {{d[i].status}}
                       </span>
                   </div>
                   <div class="item-total"></div>
               </div>
           </div>
       </div>
   </div>
   <div class="item-order-ext clearfix">
   <div class="pull-left item-price-total">最后出价：<strong>{{d[i].price}}元</strong></div>
      <div class="pull-right">
      </div>
  </div>
</div>
{{# } }}
</script>
</head>
<body>

    <header class="bar bar-nav">
        <a class="icon icon-left-nav pull-left" onclick="location.href='/my/mine.action'" id="navBackBtn"></a>

        <h1 class="title">我的拍卖</h1>
    </header>
    <div class="content">
        <div class="mui-order-cate">
            <div class="segmented-control" id="orderStatusList">
                <a class="control-item <#if status==0>active</#if>" id="order_status_0" href="mine_auction.action">全部</a>
                <a class="control-item <#if status==1>active</#if>" id="order_status_1" href="mine_auction.action?status=1">出价中</a>
                <a class="control-item <#if status==2>active</#if>" id="order_status_1" href="mine_auction.action?status=2">已拍得</a>
                <a class="control-item <#if status==3>active</#if>" id="order_status_2" href="mine_auction.action?status=3">未拍得</a>
            </div>
        </div>
        <div class="mui-order-list" id="containerList">
            <div class="items">
                <#if records??>
                <#list records as record>
                <div class="item-order" id="msglist">

                </div>
                </#list>
                <#else>
                <div class="mui-empty">
                    <div class="item-icon">
                        <span class="icon micon-empty"></span>
                    </div>
                    <div class="item-title">该状态下没有订单~</div>
                </div>
                </#if>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        var opts = {
                        lines: 10 // The number of lines to draw
                        , length: 5 // The length of each line
                        , width: 8 // The line thickness
                        , radius: 10 // The radius of the inner circle
                        , scale: 0.5 // Scales overall size of the spinner
                        , corners: 1 // Corner roundness (0..1)
                        , color: '#000' // #rgb or #rrggbb or array of colors
                        , opacity: 0.25 // Opacity of the lines
                        , rotate: 0 // The rotation offset
                        , direction: 1 // 1: clockwise, -1: counterclockwise
                        , speed: 1 // Rounds per second
                        , trail: 60 // Afterglow percentage
                        , fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
                        , zIndex: 2e9 // The z-index (defaults to 2000000000)
                        , className: 'spinner' // The CSS class to assign to the spinner
                        , top: '85%' // Top position relative to parent
                        , left: '50%' // Left position relative to parent
                        , shadow: false // Whether to render a shadow
                        , hwaccel: true // Whether to use hardware acceleration
                        , position: 'fixed' // Element positioning
                    }
                    var spinner = null;
                    var spinner_div = 0;
                    var pages = 1;
                    var isload = true;
                    var status=${status};
                    $(document).ready(function(){
                        if(isload){
                loadMore(); //加载所有瀑布流的数据
            }
        });
                    $(window).scroll(function(){
                        if ($(document).height() - $(this).scrollTop() - $(this).height()<50){
                            sentIt = false;
                            if (isload){
                                loadMore();
                            }
                            setTimeout(function(){sentIt = true;},1000);
                        }
                    });
                    function loadMore(){
                        var target = $('#firstDiv').get(0);
                        console.log('+++'+pages);
                        $.ajax({
                            type:'GET',
                            url:'/auction/mine_auction_list.action?status='+status+'&page='+pages+'&size=3',
                timeout : 10000, //超时时间设置，单位毫秒
                data:"ac=index_data",
                dataType:'json',
                beforeSend: function () {
                    if(spinner == null) {
                        spinner = new Spinner(opts).spin(target);
                    }
                    else {
                        spinner.spin(target);
                    }
                },
                success : function(re_json){
                    console.log('----'+pages)
                    if(re_json != ""){
                        if( re_json.length > 0){
                            pages = parseInt(pages) + parseInt(1);
                            appendHtml(re_json);
                            spinner.stop(target);
                            isload = true;
                        }
                        else {
                            spinner.stop(target);
                            isload = false;
                        }
                    }
                    else {
                        spinner.stop(target);
                        isload = false;
                    }
                },
                complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
                    if(status=='timeout'){//超时,status还有success,error等值的情况
                        //ajaxTimeoutTest.abort();
                        alert("超时");
                    }
                    if(status=="parsererror"){
                        spinner.stop(target);
                        isload = false;
                        console.log("pasererror");
                    }
                }
            });
                    }

                    function appendHtml(json_obj){
                        for(var i = 0, len = json_obj.length; i < len; i++)
                        {
                            if(json_obj[i].status==1){
                                json_obj[i].status = "未拍得";
                            }
                            else{
                                json_obj[i].status = "已拍得"}
                            }
                            var gettpl = document.getElementById('post_list').innerHTML;
                            laytpl(gettpl).render(json_obj, function(html){
                                $("#msglist").append(html);
                            });
                        }
                    </script>
                </body>
                </html>
