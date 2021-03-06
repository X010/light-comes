<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>${raffle.title!}</title>
    <link href="${baseUrl}css/header.css" type="text/css" rel="stylesheet">
    <link href="${baseUrl}css/lottery.css" type="text/css" rel="stylesheet">
    <link rel="stylesheet" href="${baseUrl}ratchet/weui.css" type="text/css">
    <style>
        #nothit {
            position: absolute;
            background-color: rgba(149, 147, 148, .8);
            width: 100%;
            height: 93%;
            top: 120px;
            z-index: 99;
            display: none;
        }
    </style>
</head>
<body>
<header>
    <a class="left" onclick="window.history.back();">
        <img src="${baseUrl}images/back.png"/>
    </a>

    <p>${raffle.title!}</p>
</header>
<div id="container">
    <img src="${baseUrl}images/bg.png"/>

    <div class="top">
        <p>剩余抽奖次数:<span id="count" class="count">${rCount!}</span><span>次</span></p>
    </div>
    <table id="center">
        <tr>
            <td id="lot1"><span class="lot" id="1"></span></td>
            <td id="lot2"><span class="lot" id="2"></span></td>
            <td id="lot3"><span class="lot" id="3"></span></td>
        </tr>
        <tr>
            <td id="lot8"><span class="lot" id="8"></span></td>
            <td id="click"></td>
            <td id="lot4"><span class="lot" id="4"></span></td>
        </tr>
        <tr>
            <td id="lot7"><span class="lot" id="7"></span></td>
            <td id="lot6"><span class="lot" id="6"></span></td>
            <td id="lot5"><span class="lot" id="5"></span></td>
        </tr>
    </table>
    <div id="floatimg" onclick="closeFloat()">
        <img src="${baseUrl}images/float.png"/>
    </div>
    <div id="down">
        <p>获奖人名单</p>

        <div class="downarrow"></div>
    </div>
</div>
<div id="downlist">
    <p>获奖人名单</p>

    <div class="list">
    <#if records??>
        <#list records as record>
            <div class="person">
                <span class="name">${record.phone!}</span>
                <span class="project">${record.title!}</span>
            </div>
        </#list>
    </#if>
    </div>
</div>
<div id="layer">
    <div class="layer-msg">
        <div class="suc-top">
            <p class="title-suc">抽奖次数已用完!</p>

            <p id="close-btn" name="close-btn">x</p>
        </div>
        <button type="button" id="ok-btn" name="ok-btn">好的</button>
    </div>
</div>

<div id="nothit">
    <div class="layer-msg">
        <div class="suc-top">
            <p class="title-suc">很遗憾，没有中奖!</p>

            <p id="nothit-close-btn">x</p>
        </div>
        <button type="button" id="nothit-ok-btn">好的</button>
    </div>
</div>

<script src="${baseUrl}plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript">
    var result_num;
    var raffle_data;
    //下面两个变量需要渲染模板的时候填充
    //1. 活动id
    var rid =${raffle.id!};
    //2. 剩余可抽奖次数
    var rcount =${rCount!};
    window.onload = function () {
        setTimeout(function () {
            window.scrollTo(0, 1)
        }, 0);
        $.ajax({
            type: "GET",
            url: "lottery_raffle.action?rid=" + rid,
            dataType: "json",
            success: function (data) {
                raffle_data = data.raffleCouponModels
                result_num = rand(data.raffleCouponModels.length);
            }
        })
    };
    var click = document.getElementById("click");
    /*
     * 删除左右两端的空格
     */
    function Trim(str) {
        return str.replace(/(^\s*)|(\s*$)/g, "");
    }

    /*
     * 定义数组
     */
    function GetSide(m, n) {
        //var coupons =${coupons!};
        //初始化数组
        var arr = [];
        for (var i = 0; i < m; i++) {
            arr.push([]);
            for (var j = 0; j < n; j++) {
                var index = i * n + j;
                //奖品放入数组中其他补充0
                /*if (index < coupons.length) {
                    if (i != 1 && j != 1) {
                        index = coupons[index].id;
                    } else {
                        index = 0;
                    }
                } else {
                    index = 0;
                }*/
                //console.log(i + '  ' + j + '  ' + index);
                arr[i][j] = index;
            }
        }
//        for(var a in coupons){
//            alert(coupons[a].id);
//        }
        //获取数组最外圈
        var resultArr = [];
        var tempX = 0,
                tempY = 0,
                direction = "Along",
                count = 0;
        while (tempX >= 0 && tempX < n && tempY >= 0 && tempY < m && count < m * n) {
            count++;
            resultArr.push([tempY, tempX]);
            if (direction == "Along") {
                if (tempX == n - 1)
                    tempY++;
                else
                    tempX++;
                if (tempX == n - 1 && tempY == m - 1)
                    direction = "Inverse"
            }
            else {
                if (tempX == 0)
                    tempY--;
                else
                    tempX--;
                if (tempX == 0 && tempY == 0)
                    break;
            }
        }
        return resultArr;
    }


    var index = 0,           //当前亮区位置
            prevIndex = 0,          //前一位置
            Speed = 200,           //初始速度
            Time,            //定义对象
            arr = GetSide(3, 3),         //初始化数组
            EndIndex = 0,           //决定在哪一格变慢
            center = document.getElementById("center"),
            cycle = 1,           //转动圈数
            EndCycle = 0,           //计算圈数
            flag = false,           //结束转动标志
            quick = 0;           //加速
    floatimg = document.getElementById("floatimg");
    closebtn = document.getElementById("close-btn");
    okbtn = document.getElementById("ok-btn");
    close_btn = document.getElementById("close-btn");
    ok_btn = document.getElementById("ok-btn");
    layer = document.getElementById("layer");
    over = document.getElementById("over");
    nothit = document.getElementById("nothit");
    nothitclosebtn = document.getElementById("nothit-close-btn");
    nothitokbtn = document.getElementById("nothit-ok-btn");
    lotclick = document.getElementById("click");

    function StartGame() {

        flag = false;
        EndCycle = 1;
        Time = setInterval(Star, Speed);
        boxflag = true;
    }

    function Star(num) {
        //跑马灯变速
        if (flag == false) {
            //跑N圈减速
            if (cycle == EndCycle + 1) {
                clearInterval(Time);
                flag = true;         //触发结束
            }
        }

        if (index >= arr.length) {
            index = 0;
            cycle++;
        }

        //结束转动并选中号码
        if (flag == true) {
            quick = 0;
            clearInterval(Time);
            //click.setAttribute("value","请抽奖");
        }
        center.rows[arr[index][0]].cells[arr[index][1]].style.background = "url(${baseUrl}images/lq.png)";
        center.rows[arr[index][0]].cells[arr[index][1]].style.backgroundSize = "100% 100%";
        //alert(center.rows[arr[index][0]].cells[arr[index][1]].innerHTML);
        if (index > 0)
            prevIndex = index - 1;
        else {
            prevIndex = arr.length - 1;
        }
        index++;
        boxflag = true;
    }

    function getByClass(sClass) {
        var aResult = [];
        var aEle = document.getElementsByTagName('*');
        for (var i = 0; i < aEle.length; i++) {
            /*当className相等时添加到数组中*/
            if (aEle[i].className == sClass) {
                aResult.push(aEle[i]);
            }
        }
        return aResult;
    }
    ;
    var lot = getByClass("lot");
    function rand(num) {
        //中奖宝箱存放位置
        var count = 8;
        if (num > count){
            count = num;
        }
        var rand_num = new Array;//新数组
        var originalArray = new Array;//原数组
        //给原数组originalArray赋值
        for (var i = 0; i < count; i++) {
            originalArray[i] = i + 1;
        }
        originalArray.sort(function () {
            return 0.5 - Math.random();
        });
        for (var i = 0; i < num; i++) {
            console.log(originalArray[i] + " , ");
            rand_num.push(originalArray[i]);
        }
        return (rand_num);
    }
    ;
    function success_function(data) {
        //do what you want do
        var data = data
    }
    function post_lo(id, rid) {
        urls = "lottery_draw.action?id=" + id + "&rid=" + rid
        var data;
        $.ajax({
            type: "GET",
            url: urls,
            async: false,
            dataType: "json",
            success: function (data) {
                result = data;
            },
            error: function (error) {
                result = error;
            }
        })
        return result;

    }
    lotclick.onclick = function () {
        if (rcount == 0) {
            $.alert("抽奖次数已用完");
        }
        else {
            StartGame();
            if (boxflag) {
                for (var i = 0; i < lot.length; i++) {
                    lot[i].onclick = function () {
                        if (rcount > 0) {
                            console.log("this id" + this.id)
                            if (result_num.indexOf(parseInt(this.id)) != -1) {
                                raffle = raffle_data[Math.floor(Math.random() * raffle_data.length)];
                                data = post_lo(raffle.id, rid);
                                $("#count").text(data.rCount);
                                $.alert(data.msg, function () {
                                    window.location.reload();
                                });
                            }
                            else {
                                data = post_lo("0", rid)
                                console.log(data)
                                $("#count").text(data.rCount)
                                $.alert(data.msg, function () {
                                    window.location.reload();
                                });
                            }
                            rcount--;
                        }
                        else {
                            $.alert("抽奖次数已用完", function () {
                                window.location.reload();
                            });
                        }
                    }
                }
            }
        }
    }

    function changeStr(allstr, start, end, changeStr) {
        //allstr:原始字符串，start,开始位置,end：结束位  置,str：要改变的字，changeStr:改变后的字
        return allstr.substring(0, start - 1) + changeStr + allstr.substring(end, allstr.length);
    }

</script>
<script type="text/javascript" src="${baseUrl}ratchet/jquery-weui.js"></script>
</body>
</html>
