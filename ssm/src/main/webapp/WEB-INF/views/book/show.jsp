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
            ${book.bookName}
                <a href="/book/${book.id}/edit" class="btn btn-info pull-right" >编辑</a>
                <a href="javascript:;" id="delLink" rel="${book.id}" class="btn btn-danger pull-right">删除</a>
        </h3>

        <ul class="list-group">
            <li class="list-group-item">作者:${book.authorName}</li>
            <li class="list-group-item">出版社:${book.publishName}</li>
            <li class="list-group-item">ISBN: ${book.isbn}</li>
            <li class="list-group-item">总数量: ${book.totalnum}</li>
            <li class="list-group-item">当前数量: ${book.currentnum}</li>
        </ul>
        <a class="btn btn-primary" href="/book">返回列表</a>
    </div>
    <script src="/static/js/jquery-3.js"></script>
    <script src="/static/layer/layer.js"></script>
    <script>
        $(function () {
            $("#delLink").click(function () {
                var id = $(this).attr("rel");
                layer.confirm("确定删除么？", function () {
                    window.location.href = "/book/"+id+"/delete";
                });
            });
        });
    </script>
</body>
</html>
