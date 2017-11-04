<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <title>user</title>
</head>
<body>
    <div class="container">
        <table class="table">
            <thread>
                <tr>
                    <th>userId</th>
                    <th>userName</th>
                    <th>userAddressId</th>
                </tr>
            </thread>
            <tbody>
                <c:forEach items="${userList}" var="user">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.addressId}</td>
                    </tr>
                </c:forEach>
            </tbody>

        </table>




        <ul class="list-group ">
            <li class="list-group-item">userName</li>
            <li class="list-group-item">userAddressId</li>
        </ul>
        <ul class="list-group ">
            <c:forEach items="${userList}" var="user">
                <li class="list-group-item">${user.name}</li>
                <li class="list-group-item">${user.addressId}</li>
            </c:forEach>
        </ul>

    </div>
</body>
</html>
