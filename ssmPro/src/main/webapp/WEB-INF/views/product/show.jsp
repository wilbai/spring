<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>show</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/layer.css">
</head>
<body>
    <div class="container">
        <c:if test="${not empty message}">
            <div class="alert alert-info">
                    ${message}
            </div>
        </c:if>
        <h3>
            ${product.productName}
                <a href="/product/${product.id}/edit" class="btn btn-info pull-right" >编辑</a>
                <a href="javascript:;" id="delLink" rel="${product.id}" class="btn btn-danger pull-right">删除</a>
        </h3>

        <ul class="list-group">
            <li class="list-group-item">类型:${product.kaolaType.typeName}</li>
            <li class="list-group-item">产地:${product.place}</li>
            <li class="list-group-item">考拉价:${product.price}</li>
            <li class="list-group-item">市场价: ${product.marketPrice}</li>
            <li class="list-group-item">评论数量: ${product.commentNum}</li>
        </ul>
        <a class="btn btn-primary" href="/product">返回列表</a>
    </div>
    <script src="/static/js/jquery-3.js"></script>
    <script src="/static/layer/layer.js"></script>
    <script>
        $(function () {
            $("#delLink").click(function () {
                var id = $(this).attr("rel");
                layer.confirm("确定删除么？", function () {
                    window.location.href = "/product/"+id+"/delete";
                });
            });
        });
    </script>
</body>
</html>
