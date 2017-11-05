<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>修改商品</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <form method="post">
            <div class="form-group">
                <label>所属类型</label>
                <select name="typeId" class="form-control" >
                    <option value="0">--请选择类型--</option>
                    <c:forEach items="${typeList}" var="type">
                        <option value="${type.id}" ${type.id == product.typeId? 'selected' : ''}>${type.typeName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>商品名称</label>
                <input type="text" name="productName" value="${product.productName}" class="form-control">
            </div>
            <div class="form-group">
                <label>考拉价</label>
                <input type="text" name="price" value="${product.price}" class="form-control">
            </div>
            <div class="form-group">
                <label>市场价</label>
                <input type="text" name="publishName" value="${product.marketPrice}" class="form-control">
            </div>
            <div class="form-group">
                <label>产地</label>
                <input type="text" name="isbn" value="${product.place}" class="form-control">
            </div>
            <div class="form-group">
                <button class="btn btn-primary">保存</button>
            </div>
        </form>
    </div>
</body>
</html>
