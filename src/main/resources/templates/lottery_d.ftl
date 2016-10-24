<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>抽奖活动</title>
    <link href="/css/header.css" type="text/css" rel="stylesheet">
    <link href="/css/lottery.css" type="text/css" rel="stylesheet">
</head>
<body>
<header>
    <a class="left" onclick="window.history.back();">
        <img src="/images/back.png"/>
    </a>

    <p>抽奖活动</p>
</header>
<div id="container">
    <img src="/images/bg.png"/>

    <div class="top">
        <p>剩余抽奖次数:<span id="count" class="count">${rCount!}</span><span>次</span></p>
    </div>
    <table id="center">
        <tr>
            <td id="lot1"><span></span></td><td id="lot2"><span></span></td><td id="lot3"><span></span></td>
        </tr>
        <tr>
            <td id="lot8"><span></span></td><td onclick="StartGame()" id="click"></td><td id="lot4"><span></span></td>
        </tr>
        <tr>
            <td id="lot7"><span></span></td><td id="lot6"><span></span></td><td id="lot5"><span></span></td>
        </tr>
    </table>
    <div id="floatimg" onclick="closeFloat()">
        <img src="/images/float.png"/>
    </div>
    <div id="down">
        <p>获奖人名单</p>

        <div class="downarrow"></div>
    </div>
</div>
<div id="downlist">
    <p>获取人名单</p>

    <div class="list">
    <#if records??>
        <#list records as record>
            <div class="person">
                <img src="/images/header.png"/>
                <span class="name">${record.phone!}|</span>
                <span class="project">${record.title!}</span>
            </div>
        </#list>
    </#if>
    </div>
</div>
<script src="/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript">
    window.onload = function () {
        setTimeout(function () {
            window.scrollTo(0, 1)
        }, 0);
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
        var coupons =${coupons!};
        //初始化数组
        var arr = [];
        for (var i = 0; i < m; i++) {
            arr.push([]);
            for (var j = 0; j < n; j++) {
                var index = i * n + j;
                //arr[i][j] = i * n + j;
                //奖品放入数组中其他补充0
                if (index < coupons.length) {
                    index = coupons[index].id;
                } else {
                    index = 0;
                }
                console.log(index);
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
            Speed = 300,           //初始速度
            Time,            //定义对象
            arr = GetSide(3, 3),         //初始化数组
            EndIndex = 0,           //决定在哪一格变慢
            center = document.getElementById("center"),
            cycle = 1,           //转动圈数
            EndCycle = 0,           //计算圈数
            flag = false,           //结束转动标志
            quick = 0;           //加速
    span = document.getElementsByTagName("span");
    floatimg = document.getElementById("floatimg");
    number = 3;

    function StartGame() {

        flag = false;
        //EndIndex=Math.floor(Math.random()*7);
        //EndCycle=Math.floor(Math.random()*4);
        EndCycle = 1;
        Time = setInterval(Star, Speed);
    }

    function Star(num) {
        //跑马灯变速
        if (flag == false) {
            //跑N圈减速
            if (cycle == EndCycle + 1) {
                clearInterval(Time);
                //Speed=300;
                flag = true;         //触发结束
                //Time=setInterval(Star,Speed);
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
        center.rows[arr[index][0]].cells[arr[index][1]].style.background = "url(/images/lq.png)";
        center.rows[arr[index][0]].cells[arr[index][1]].style.backgroundSize = "100%";
        if (index > 0)
            prevIndex = index - 1;
        else {
            prevIndex = arr.length - 1;
        }
        index++;
    }


    for (i = 0; i < span.length; i++) {
        if (number > 0) {
            span[i].onclick = function () {
                var rc =${rCount};
                if (rc == 0) {
                    alert("今日抽奖次数已用完。")
                } else {
                    $.ajax({
                        url: "lottery_draw.action",
                        type: "POST",
                        success: function (result) {
                            var r = jQuery.parseJSON(result);
                            var rCount = r.rCount;
                            $('.count').each(function () {
                                $(this).text(rCount);
                            });
                            if (r.code == 1) {
                                floatimg.style.display = "block";
                            } else {
                                alert("没有中奖！");
                            }
                        }
                    });
                }
            }
        }
        else {
            alert("下次再来！")
        }
//            if(number>0){
//                floatimg.style.display = "block";
//                number--;
//                console.log(number);
//            }
//            else{alert("下次再来!")}
    }
    floatimg.onclick = function () {
        floatimg.style.display = 'none';
    }

    //
    //    $(function () {
    //        $("#center").click(function () {
    //            $.ajax({
    //                url: "lottery_draw.action",
    //                type: "POST",
    //                success: function (result) {
    //                    var r = jQuery.parseJSON(result);
    //                    if (r.code == 1) {
    //                        floatimg.style.display = "block";
    //                    } else {
    //                        alert("没有中奖！");
    //                    }
    //                }
    //            });
    //        });
    //    });

    function changeStr(allstr, start, end, changeStr) {
        //allstr:原始字符串，start,开始位置,end：结束位  置,str：要改变的字，changeStr:改变后的字
        return allstr.substring(0, start - 1) + changeStr + allstr.substring(end, allstr.length);
    }

</script>
</body>
</html>