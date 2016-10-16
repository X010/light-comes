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
        <p>抽奖次数:<span class="count">3</span><span>次</span></p>
    </div>
    <div id="center">
    </div>
    <div id="floatimg" onclick="closeFloat()">
        <img src="/images/float.png"/>
    </div>
    <div id="down">
        <p>获取人名单</p>
        <div class="downarrow"></div>
    </div>
</div>
<div id="downlist">
    <p>获取人名单</p>
    <div class="list">
        <div class="person">
            <img src="/images/header.png"/>
            <span class="name">青岛啤酒|</span>
            <span class="project">穿越丝路</span>
        </div>
        <div class="person">
            <img src="/images/header.png"/>
            <span class="name">青岛啤酒|</span>
            <span class="project">穿越丝路</span>
        </div>
        <div class="person">
            <img src="/images/header.png"/>
            <span class="name">青岛啤酒|</span>
            <span class="project">穿越丝路</span>
        </div>
        <div class="person">
            <img src="/images/header.png"/>
            <span class="name">青岛啤酒|</span>
            <span class="project">穿越丝路</span>
        </div>
        <div class="person">
            <img src="/images/header.png"/>
            <span class="name">青岛啤酒|</span>
            <span class="project">穿越丝路</span>
        </div>
        <div class="person">
            <img src="/images/header.png"/>
            <span class="name">青岛啤酒|</span>
            <span class="project">穿越丝路</span>
        </div>

    </div>
</div>
<script type="text/javascript">
    var cont = document.getElementById("container");
    var list = document.getElementById("downlist");
    var downbtn = document.getElementById("down");
    var center = document.getElementById("center");
    var floatimg = document.getElementById("floatimg");
    downbtn.onclick = function () {
        cont.style.display = "none";
        list.style.display = "block";
    }
    center.onclick = function () {
        floatimg.style.display = "block";
    }
    function closeFloat() {
        floatimg.style.display = "none";
    }
    list.onclick = function () {
        list.style.display = "none";
        cont.style.display = "block";
    }
</script>
</body>
</html>