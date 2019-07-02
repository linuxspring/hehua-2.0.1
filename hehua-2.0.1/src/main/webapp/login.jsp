<%@ page language="java" import="java.util.*" contentType="text/html; chartset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="org.apache.shiro.subject.Subject" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <link rel="stylesheet" href="ref/bootstrap3/dist/css/bootstrap.min.css"/>
    <script src="ref/bootstrap3/dist/js/jquery.min.js"></script>
    <script src="ref/bootstrap3/dist/js/bootstrap.js"></script>
    <script src="js/component.js"></script>
    <script src="ref/security/tripledes.js"></script>
    <script src="ref/security/mode-ecb-min.js"></script>

    <style type="text/css">
        html, body {
            height: 100%;
        }

        .box {
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#6699FF', endColorstr='#6699FF'); /*  IE */
            background-image: linear-gradient(bottom, #040404 0%, #6699FF 100%);
            background-image: -o-linear-gradient(bottom, #040404 0%, #6699FF 100%);
            background-image: -moz-linear-gradient(bottom, #040404 0%, #6699FF 100%);
            background-image: -webkit-linear-gradient(bottom, #040404 0%, #6699FF 100%);
            background-image: -ms-linear-gradient(bottom, #040404 0%, #6699FF 100%);

            margin: 0 auto;
            position: relative;
            width: 100%;
            height: 100%;
        }

        .login-box {
            width: 100%;
            max-width: 500px;
            height: 200px;
            position: absolute;
            top: 50%;

            margin-top: -200px;
            /*设置负值，为要定位子盒子的一半高度*/
        }

        @media screen and (min-width: 500px) {
            .login-box {
                left: 50%;
                /*设置负值，为要定位子盒子的一半宽度*/
                margin-left: -250px;
            }
        }

        .form {
            width: 100%;
            max-width: 500px;
            height: 175px;
            margin: 25px auto 0px auto;
            padding-top: 25px;
        }

        .login-content {
            height: 270px;
            width: 100%;
            max-width: 500px;
            background-color: rgba(255, 255, 255, .6);
            float: left;
        }

        .input-group {
            margin: 0px 0px 30px 0px !important;
        }

        .form-control,
        .input-group {
            height: 40px;
        }

        .form-group {
            margin-bottom: 0px !important;
        }


        .login-title {
            padding: 20px 10px;
            background-color: rgba(0, 0, 0, .6);
        }

        .login-title h1 {
            margin-top: 10px !important;
        }

        .login-title small {
            color: #fff;
        }

        .link p {
            line-height: 20px;
            margin-top: 10px;
        }

        .btn-sm {
            padding: 8px 28px !important;
            font-size: 16px !important;
        }
        #myCanvas {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
        }
		.btn-info-login {
             color: #fff;
             background-color: 	 #20304F;
             border-color: 	#20304F;
        }
		.btn-info-login:hover {

	     color: #fff;
	     border-color: #fff;
        }
    </style>
    <title>登录页面</title>
    <script>
        $(document).ready(function () {
        	//debugger;
            var code ="${code}";
            var error3='';
            code = parseInt(code);
            if(code==0){
                error3="登录失败:用户与密码不一致";
            }
            if (code==1) {
                error3="登录失败:" + "用户不存在，请重新输入";
            }
            if (code==2) {
                error3="登录失败:" + "用户密码输入错误超过5次，用户被锁定";
            }
            $('#msgTip').html(error3);
        	$.getJSON('zq_todo/city.do', function(json){
        		var schema = json.schema;
        		$('#schema').text(schema);
        		$('#usernamePost').val(schema);
        		$('#cityName').text(json.city + "，版权所有");
        		$('#tel').text(json.tel);
        	})
            $('#myForm').on('submit',function (e) {
                setTimeout("$('#loginBtn').removeAttr('disabled')",3000);
                e.preventDefault();
                $('#loginBtn').attr({"disabled": "disabled"});
                var url = "";
                var ps = {
                    username: iwfTool.encryptByDES($('#username').val()),
                    usernamePost: '@mm.gpgc',
                    password: iwfTool.encryptByDES($('#password').val()),
                    status: ''
                }
                $.post(url, ps, function (json,scope) {
                    //if (json.success) {
                    //     window.location.href="adduser.jsp"
                    // }else{
                    //     alert(json.msg);
                    // }
                    //window.location.reload();
                });
            });
        });
    </script>
</head>
<body>
<%
    String error3 =(String) request.getAttribute("error");
    Integer a=(Integer) request.getAttribute("code");
    if (a == null) {
        a=7;
    }

%>
<div class="box"><canvas id="myCanvas"></canvas>
    <div class="login-box">
        <div class="login-title text-center">
            <h1>
                <small>梅州局IT集中运行监控系统</small>
            </h1>
        </div>
        <div class="login-content ">
            <div class="form">
                <form method="post">
                    <div class="form-group">
                        <div class="col-xs-12  ">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                                <input type="text" id="username" name="username" class="form-control" value="admin" placeholder="用户名"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12  ">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                                <input type="password" id="password" name="password" class="form-control" value="123456" placeholder="密码"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group form-actions">
                        <div class="col-xs-4 col-xs-offset-4 ">
                              <button id="loginBtn"  class="btn btn-sm btn-info-login"><span
                                    class="glyphicon glyphicon-off"></span> &nbsp;登&nbsp;&nbsp;录&nbsp;
                            </button>
                        </div>
                    </div>
                    <div class="form-group">

                        <div id="msgTip" class="col-xs-12" style="margin-top: 5px;color:red;">${error} </div>
                    </div>
                    <div class="form-group">
                        <%--<div class="col-xs-6 link">--%>
                            <%--<p class="text-center remove-margin">--%>
                                <%--<small id="cityName">XXX，版权所有</small>--%>
                            <%--</p>--%>
                        <%--</div>--%>
                        <%--<div class="col-xs-6 link">--%>
                            <%--<p class="text-center remove-margin">--%>
                                <%--<small>技术支持热线：<span id="tel">XXXXXX</span></small>--%>
                            <%--</p>--%>
                        <%--</div>--%>

                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var canvas = document.getElementById("myCanvas");
    canvas.width = document.documentElement.clientWidth;
    canvas.height = document.documentElement.clientHeight;
    var ctx = canvas.getContext("2d");
    //创建小球的构造函数
    function Ball() {
        this.x = randomNum(3, canvas.width - 3);
        this.y = randomNum(3, canvas.height - 3);
        this.r = randomNum(1, 3);
        this.color = randomColor();
        this.speedX = randomNum(-3, 3) * 0.2;
        this.speedY = randomNum(-3, 3) * 0.2;
    }
    Ball.prototype = {
        //绘制小球
        draw: function () {
            ctx.beginPath();
            ctx.globalAlpha = 1;
            ctx.fillStyle = this.color;
            ctx.arc(this.x, this.y, this.r, 0, Math.PI * 2);
            ctx.fill();
        },
        //小球移动
        move: function () {
            this.x += this.speedX;
            this.y += this.speedY;
            //为了合理性,设置极限值
            if (this.x <= 3 || this.x > canvas.width - 3) {
                this.speedX *= -1;
            }
            if (this.y <= 3 || this.y >= canvas.height - 3) {
                this.speedY *= -1;
            }
        }
    }
    //存储所有的小球
    var balls = [];
    //创建200个小球
    for (var i = 0; i < 150; i++) {
        var ball = new Ball();
        balls.push(ball);
    }
    main();

    function main() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        //鼠标移动绘制线
        mouseLine();
        //小球与小球之间自动画线
        drawLine();
        //使用关键帧动画，不断的绘制和清除
        window.requestAnimationFrame(main);
    }
    //添加鼠标移动事件
    //记录鼠标移动时的mouseX坐标
    var mouseX;
    var mouseY;
    canvas.onmousemove = function (e) {
        var ev = event || e;
        mouseX = ev.offsetX;
        mouseY = ev.offsetY;
    }
    //判断是否划线

    function drawLine() {
        for (var i = 0; i < balls.length; i++) {
            balls[i].draw();
            balls[i].move();
            for (var j = 0; j < balls.length; j++) {
                if (i != j) {
                    if (Math.sqrt(Math.pow((balls[i].x - balls[j].x), 2) + Math.pow((balls[i].y - balls[j].y), 2)) < 80) {
                        ctx.beginPath();
                        ctx.moveTo(balls[i].x, balls[i].y);
                        ctx.lineTo(balls[j].x, balls[j].y);
                        ctx.strokeStyle = "white";
                        ctx.globalAlpha = 0.2;
                        ctx.stroke();
                    }
                }
            }
        }
    }
    //使用鼠标移动划线

    function mouseLine() {
        for (var i = 0; i < balls.length; i++) {
            if (Math.sqrt(Math.pow((balls[i].x - mouseX), 2) + Math.pow((balls[i].y - mouseY), 2)) < 80) {
                ctx.beginPath();
                ctx.moveTo(balls[i].x, balls[i].y);
                ctx.lineTo(mouseX, mouseY);
                ctx.strokeStyle = "white";
                ctx.globalAlpha = 0.8;
                ctx.stroke();
            }
        }
    }
    //随机函数

    function randomNum(m, n) {
        return Math.floor(Math.random() * (n - m + 1) + m);
    }
    //随机颜色

    function randomColor() {
        return "rgb(" + randomNum(0, 255) + "," + randomNum(0, 255) + "," + randomNum(0, 255) + ")";
    }
</script>
</html>