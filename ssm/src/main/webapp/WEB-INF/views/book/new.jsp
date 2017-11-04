<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>添加书籍</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <form action="/book/new" method="post">
            <div class="form-group">
                <label>书籍类型</label>
                <select name="typeId" class="form-control" >
                    <option value="0">--请选择类型--</option>
                    <c:forEach items="${typeList}" var="type">
                        <option value="${type.id}">${type.typeName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>书籍名称</label>
                <input type="text" name="bookName" class="form-control">
            </div>
            <div class="form-group">
                <label>作者</label>
                <input type="text" name="authorName" class="form-control">
            </div>
            <div class="form-group">
                <label>出版社</label>
                <input type="text" name="publishName" class="form-control">
            </div>
            <div class="form-group">
                <label>ISBN</label>
                <input type="text" name="isbn" class="form-control">
            </div>
            <div class="form-group">
                <label>总数量</label>
                <input type="text" name="totalnum" class="form-control">
            </div>
            <div class="form-group">
                <button class="btn btn-primary">保存</button>
            </div>
        </form>
    </div>
</body>
</html>
