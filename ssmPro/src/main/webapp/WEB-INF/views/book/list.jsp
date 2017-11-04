<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>书籍列表</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3>查询</h3>
            </div>
            <div class="panel-body">
                <form class="form-inline">
                    <input type="text" name="bookName" placeholder="书籍名称" value="${param.bookName}" class="form-control">
                    <select name="authorName" class="form-control">
                        <option value="">--选择作者--</option>
                        <c:forEach items="${authorList}" var="author">
                            <option value="${author}" ${param.authorName == author ? 'selected' : ''}>${author}</option>
                        </c:forEach>
                    </select>
                    <select name="typeId" class="form-control">
                        <option value="">--选择分类--</option>
                        <c:forEach items="${typeList}" var="type">
                            <option value="${type.id}" ${param.typeId == type.id ? 'selected' : ''}>${type.typeName}</option>
                        </c:forEach>
                    </select>
                    <button class="btn btn-default">搜索</button>
                </form>
            </div>
        </div>

        <c:if test="${not empty message}">
            <div class="alert alert-info">
                ${message}
            </div>
        </c:if>
        <br/>
        <a href="/book/new" class="btn btn-success">添加书籍</a>
        <br/>
        <table class="table">
            <thread>
                <tr>
                    <th>书籍名称</th>
                    <th>类型</th>
                    <th>作者</th>
                    <th>出版社</th>
                    <th>ISBN</th>
                    <th>总数量</th>
                    <th>当前数量</th>
                </tr>
            </thread>
            <tbody>
                <c:forEach items="${pageInfo.list}" var="book">
                    <tr>
                        <td><a href="/book/${book.id}">${book.bookName}</a></td>
                        <td>${book.bookType.typeName}</td>
                        <td>${book.authorName}</td>
                        <td>${book.publishName}</td>
                        <td>${book.isbn}</td>
                        <td>${book.totalnum}</td>
                        <td>${book.currentnum}</td>
                    </tr>
                </c:forEach>
                
            </tbody>
        </table>
        <ul id="pagination-demo" class="pagination-sm"></ul>
    </div>
    <script src="/static/js/jquery-3.js"></script>
    <script src="/static/js/bootstrap.min.js"></script>
    <script src="/static/js/jquery.twbsPagination.min.js"></script>

    <script>
        $(function () {
            //分页
            $('#pagination-demo').twbsPagination({
                totalPages: "${pageInfo.pages}",
                visiblePages: 3,
                first:'首页',
                last:'末页',
                prev:'上一页',
                next:'下一页',
                href:"?bookName="+encodeURIComponent('${param.bookName}')+
                "&authorName="+encodeURIComponent('${param.authorName}')+
                "&typeId=${param.typeId}&p={{number}}"
            });
        });
    </script>
</body>
</html>
