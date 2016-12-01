<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>签到</title>
    <link href="/css/sign.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/js/jquery.min.js" ></script>
    <script type="text/javascript" src="/js/laytpl.js"></script>
</head>
<body>
<div class="container" id="cont">
    <div class="mid">
        <div class="mid-top">
            <div class="mid-left">
                <p class="drink">今天喝掉</p>
                <p class="ml"><span id="td_drunk"></span>ml</p>
            </div>
            <div class="mid-right">
                <p class="drink">当前喝掉</p>
                <p class="ml"><span id="cy_drunk"></span>ml</p>
            </div>
        </div>
        <div class="bottle">
            <svg id="fillgauge" width="22%" height="120"></svg>
        </div>
    </div>
    <div class="bottom">
        <div class="bottom-box">
            <div class="bottom-d">
                <p class="drink">今日您已喝掉</p>
                <p class="mll" ><span id="tdu_drunk"></span>ml</p>
            </div>
            <div class="bottom-d">
                <p class="drink">朋友已帮你喝掉</p>
                <p class="mll"><span id="tdo_drunk"></span>ml</p>
            </div>
        </div>
    </div>
    <input type="button" value="我也要干杯" class="chess" onclick="changeNum();" />
</div>
<script src="/js/d3.v3.min.js" language="JavaScript"></script>
<script src="/js/liquidFillGauge.js" language="JavaScript"></script>
<script language="JavaScript" type="text/javascript">
    $(document).ready(function(){
    	  var maxValue;
	  maxValue=loadAjax();
    });
	var config = liquidFillGaugeDefaultSettings();
        config.circleThickness = 0.1;
        config.circleColor = "#ED1E37";
        config.textColor = "#ED1E37";
        config.waveTextColor = "#FD8F94";
        config.waveColor = "#FFDDDD";
        config.textVertPosition = 0.52;
        config.waveAnimateTime = 2000;
        config.waveHeight = 0.1;
        config.waveAnimate = true;
        config.waveCount = 2;
        config.waveOffset = 0.5;
        config.textSize = 1;
        config.minValue = 0;
        config.displayPercent = false;
        var gauge; 
    function changeNum(){
		$.ajax({
		    type:'GET',
		    url:'/pt/self_past.action',
		    timeout:10000,
		    dataType:'json',
		    success:function(re_json){
			data = re_json.data;
			$("#td_drunk").text(data.today_drunk);
    			$("#cy_drunk").text(data.cycle_drunk);
   		        $("#tdu_drunk").text(data.today_drunk);
   		        $("#tdo_drunk").text(data.today_other_drunk);
   		        var drunk = data.total_drunk-data.cycle_drunk;
   		        var gauge = loadLiquidFillGauge("fillgauge", 120, config);
		        gauge.update(drunk);
		    },
		    complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
                if(status=='timeout'){//超时,status还有success,error等值的情况
                    //ajaxTimeoutTest.abort();
                    alert("超时");
                }
                if(status=="parsererror"){
                    isload = false;
                    console.log("pasererror");
                }
            }
		});
	}

    function loadAjax(){
        $.ajax({
            type:'GET',
            url:'/pt/info.action',
            timeout : 10000, //超时时间设置，单位毫秒
            data:"ac=index_data",
            dataType:'json',
            async:false,
            success : function(re_json){
            		data = re_json.data;
   	 $("#td_drunk").text(data.today_drunk);
    $("#cy_drunk").text(data.cycle_drunk);
    $("#tdu_drunk").text(data.today_drunk);
    $("#tdo_drunk").text(data.today_other_drunk);
    var drunk = data.total_drunk-data.cycle_drunk;
    maxValue = data.total_drunk;
    console.log(maxValue);
    config.maxValue = maxValue;//总容量
    var gauge = loadLiquidFillGauge("fillgauge", 120, config);
    gauge.update(drunk);
    	    },
            complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
                if(status=='timeout'){//超时,status还有success,error等值的情况
                    //ajaxTimeoutTest.abort();
                    alert("超时");
                }
                if(status=="parsererror"){
                    isload = false;
                    console.log("pasererror");
                }
            }
        });
     return maxValue;
    }
</script>
</body>
</html>
