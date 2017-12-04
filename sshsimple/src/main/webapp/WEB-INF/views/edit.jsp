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
        <form method="post" >
            <div class="form-group">
                <label>商品名称</label>
                <input type="text" class="form-control" name="productName" value="${product.productName}">
            </div>
            <div class="form-group">
                <label>考拉价</label>
                <input type="text" class="form-control" name="price" value="${product.price}">
            </div>
            <div class="form-group">
                <label>市场价</label>
                <input type="text" class="form-control" name="marketPrice" value="${product.marketPrice}">
            </div>
            <div class="form-group">
                <label>产地</label>
                <input type="text" class="form-control" name="place" value="${product.place}">
            </div>
            <div class="form-group">
                <label>评论数量</label>
                <input type="text" class="form-control" name="commentNum" value="${product.commentNum}">
            </div>
            <div class="form-group">
                <label>产品类型</label>
                <select class="form-control" name="typeId">
                    <option value=""></option>
                    <c:forEach items="${typeList}" var="type">
                        <option ${type.id == product.productType.id?'selected':''} value="${type.id}">${type.typeName}</option>
                    </c:forEach>
                </select>
            </div>
            <button class="btn btn-success">保存</button>
        </form>
    </div>
</body>
</html>
