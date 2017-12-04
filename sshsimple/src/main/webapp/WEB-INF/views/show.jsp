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
    <div class="container">
        <h3>${product.productName}<a href="/product" class="btn btn-success pull-right">返回列表</a></h3>
        <ul class="list-group">
            <li class="list-group-item">商品价格：${product.price}</li>
            <li class="list-group-item">市场价格：${product.marketPrice}</li>
            <li class="list-group-item">产地：${product.place}</li>
            <li class="list-group-item">评论数量：${product.commentNum}</li>
            <li class="list-group-item">所属分类：${product.productType.typeName}</li>
        </ul>
        <a class="btn btn-primary" href="/product/${product.id}/edit">修改</a>
        <a class="btn btn-danger" id="delBtn" href="javascript:;" rel="${product.id}">删除</a>
    </div>
</div>
<script src="https://cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
<script>
    $(function () {
        $("#delBtn").click(function () {
            var id = $(this).attr("rel");
            if(confirm("确定删除么？")){
                window.location.href = "/product/"+ id +"/delete";
            }
        });

    });
</script>
</body>
</html>
