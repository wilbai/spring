<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

    <div class="container">
        <%--<div class="panel panel-default">--%>
        <div class="panel-body">
        <a href="/product/new" class="btn btn-primary pull-right inline">添加商品</a>
                <form class="form-inline">
                    <input type="text" placeholder="商品名称" name="q_like_s_productName" value="${param.q_like_s_productName}" class="form-control">
                    <input type="text" placeholder="考拉价 or 市场价" name="q_eq_bd_price_or_marketPrice" value="${param.q_eq_bd_price_or_marketPrice}" class="form-control">
                    <input type="text" placeholder="产地" name="q_like_s_place" value="${param.q_like_s_place}" class="form-control">
                    <button class="btn btn-default">搜索</button>
                </form>
            </div>
        <%--</div>--%>
        <table class="table table-striped table-hover">
            <thead>
                <tr style="font-weight: 800" class="success">
                    <td>商品名称</td>
                    <td>考拉价</td>
                    <td>市场价</td>
                    <td>产地</td>
                    <td>评论数量</td>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.items}" var="product">
                    <tr class="rowClick" rel="${product.id}">
                        <td>${product.productName}</td>
                        <td>${product.price}</td>
                        <td>${product.marketPrice}</td>
                        <td>${product.place}</td>
                        <td>${product.commentNum}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
            <ul id="pagination" class="pagination  pull-right"></ul>
    </div>
        <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
        <script src="/static/jquery.twbsPagination.min.js"></script>
        <script>
            $(function () {
                $(".rowClick").click(function () {
                    var id = $(this).attr("rel");
                    window.location.href = "/product/"+id;
                });

                $("#pagination").twbsPagination({
                    totalPages:"${page.totalPageSize}",
                    visiblePages:5,
                    href:"?p={{number}}",
                    first: "首页",
                    prev: "上一页",
                    next:"下一页",
                    last:"末页"
                });
            });
        </script>
</body>
</html>
