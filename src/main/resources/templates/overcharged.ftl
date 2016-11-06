<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>砍价</title>
    <link type="text/css" rel="stylesheet" href="/css/header.css"/>
    <link type="text/css" rel="stylesheet" href="/css/lottery.css"/>
    <link rel="stylesheet" href="/css/swiper.min.css">
    <script type="text/javascript" src="/js/laytpl.js"></script>
        <script type="text/javascript" src="/js/spin.min.js"></script>
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script src="/js/swiper-3.3.1.jquery.min.js"></script>
    <script id="post_list" type="text/html">
                {{# for(var i = 0, len = d.length; i < len; i++){ }}
                <li class="msg" onclick="location.href='/oc/overcharged_d.action?aid={{d[i].id}}'">
                    <div class="msg-left">
                        <img src="{{d[i].good_photo}}" />
                    </div>
                    <div class="msg-right">
                        <h3>{{d[i].title}}</h3>
                        <p>{{d[i].good_name}}</p>
                    </div>
                    <div class="clear"></div>
                </li>
                {{# } }}
            </script>
</head>

<body>
<div id="banner">
    <div class="swiper-wrapper">
    <#if focus??>
        <#list focus as focu>
            <div class="swiper-slide"><a href="${focu.link}"><img src="${focu.image}"/></a></div>
        </#list>
    </#if>
    </div>
    <div class="pagination"></div>
</div>
<ul id="msglist">
</ul>
<div style="height:70px;bottom:0;"></div>
<div id="firstDiv"></div>

<footer>
    <a href="/raffle/lottery.action">
        <img src="/images/ticket.png"/>

        <p>抽奖券</p>
    </a>
    <a href="/auction/auction.action">
        <img class="" src="/images/auction.png"/>

        <p >拍卖</p>
    </a>
    <a href="/banquet/banquet.action">
        <img class="" src="/images/date.png"/>

        <p>约饭</p>
    </a>
    <a href="/oc/overcharged.action" class="on">
        <img class="" src="/images/discount_on.png"/>

        <p class="on">砍价</p>
    </a>
    <a href="/my/mine.action">
        <img class="" src="/images/mine.png"/>

        <p>我的</p>
    </a>
</footer>
<script type="text/javascript" src="/js/common.js"></script>
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
            console.log('+++'+pages)
            $.ajax({
                type:'GET',
                url:'/oc/overcharged_list.action?page='+pages,
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
                    if(re_json != " "){
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
        function appendHtml(json){
            for(var i = 0, len = json.length; i < len; i++)
            {
                if(json[i].good_name.length>32){
                    json[i].good_name = json[i].good_name.substring(0,32)+"...";
                }
                if(json[i].title.length>16){
                    json[i].title = json[i].title.substring(0,16)+"...";
                }
            }
            var gettpl = document.getElementById('post_list').innerHTML;
            laytpl(gettpl).render(json, function(html){
                $("#msglist").append(html);
            });
        }
</script>
</body>
</html>
