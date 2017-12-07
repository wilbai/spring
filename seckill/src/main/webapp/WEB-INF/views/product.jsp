<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <style>
        .btn {
            margin-top: 35px;
            width: 400px;
        }
        .clock {
            margin-top: 40px;
            font-size: 24px;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-default navbar-static-top">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="/">
                    <i class="fa fa-shopping-basket"></i> ProductStore
                </a>
            </div>
        </div>
    </nav>
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <img src="http://ozp3uwn5y.bkt.clouddn.com/${product.productImage}?imageView2/1/w/300/h/300" alt="">
            </div>
            <div class="col-md-8">
                <h4>${product.productName}</h4>
                <p>${product.productTitle}</p>
                <h3 class="text-danger">抢购价:￥${product.productPrice} <small style="text-decoration: line-through">市场价:￥ ${product.productMarketPrice}</small></h3>
                <c:choose>
                    <c:when test="${product.productInventory == 0}">
                        <button class="btn btn-default btn-lg" disabled>已售罄</button>
                    </c:when>
                    <c:when test="${product.end}">
                        <button class="btn btn-default btn-lg" disabled>已结束</button>
                    </c:when>
                    <c:when test="${product.start and not product.end}">
                        <button id="secKillBtn" class="btn btn-danger btn-lg">立即抢购</button>
                    </c:when>
                    <c:otherwise>
                        <button id="secKillBtn" class="btn btn-danger btn-lg" disabled>等待抢购</button>
                        <div class="clock">距离抢购时间: <span id="clock">xx分xx秒</span></div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <br/>
        <div class="row">
            <div class="col-md-offset-4 col-md-8">
                <div>
                    ${product.productDesc}
                </div>
            </div>
        </div>
    </div>

    <script src="/static/js/jquery.min.js"></script>
    <script src="/static/js/layer/layer.js"></script>
    <script src="/static/js/moment.js"></script>
    <script src="/static/js/jquery.countdown.min.js"></script>
    <script>
        $(function () {
            $("#clock").countdown(${product.startTimeTs}, function (event) {
                $(this).html(event.strftime('%D 天 %H小时 %M分钟 %S秒'));
            }).on("finish.countdown", function () {
                $("#secKillBtn").text("立即抢购").removeAttr("disabled");
            });

            $("#secKillBtn").click(function () {
                $.get("/product/seckill/${product.id}").done(function (res) {
                    if(res.state == "success") {
                        layer.alert("抢购成功");
                    } else {
                        layer.alert(res.message);
                    }
                }).error(function (res) {
                    layer.msg("服务器异常");
                });
            });
        });
    </script>
</body>
</html>
