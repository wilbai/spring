<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible">
    <title>save</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <br/>
        <c:if test="${not empty message}">
            <div class="alert alert-info">
                ${message}
            </div>
        </c:if>
        <form action="/save" method="post" enctype="multipart/form-data">
            <legend>注册帐号</legend>
            <div class="form-group">
                <label>帐号</label>
                <input type="text" name="name" class="form-control">
            </div>
            <div class="form-group">
                <label>密码</label>
                <input type="text" name="password" class="form-control">
            </div>
            <div class="form-group">
                <label>电子邮件</label>
                <input type="text" name="email" class="form-control">
            </div>
            <div class="form-group">
                <label>照片</label>
                <input type="file" name="photo" class="form-control">
            </div>
            <div class="form-group">
                <button class="btn btn-success">注册</button>
            </div>
        </form>
    </div>

</body>
</html>
