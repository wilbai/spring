<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        .product_list {
            border-bottom: 1px dashed #ccc;
            padding: 25px 0px;
        }
        .product_list:last-child {
            border-bottom: none;
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
            <a href="/product/new" class="btn btn-success navbar-btn pull-right"><i class="fa fa-plus"></i> 添加商品</a>
        </div>
    </nav>
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-body">
                <c:forEach items="${productList}" var="product">
                    <div class="row product_list">
                        <div class="col-md-2">
                            <img src="http://ozp3uwn5y.bkt.clouddn.com/${product.productImage}?imageView2/1/w/150/h/150" alt="">
                        </div>
                        <div class="col-md-10">
                            <h4><a href="/product/${product.id}">${product.productName}</a></h4>
                            <h4 style="margin-top: 25px" class="text-danger">抢购价:￥${product.productPrice}</h4>
                            <h4 style="margin-top: 35px">开始时间: <fmt:formatDate value="${product.startTime}" pattern="MM月dd日 HH:mm分"/> </h4>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>

</body>
</html>
