<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Weibo</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <div id="alert" class="alert alert-warning" style="display: none">
            <a href="javascript:;" id="text"></a>
        </div>
        <ul id="list" class="list-group">
            <c:forEach items="${userList}" var="user">
                <li class="list-group-item">${user.name}</li>
            </c:forEach>
        </ul>
    </div>
    <script src="/static/js/jquery-3.js"></script>
    <script>
        $(function () {

            var maxId = ${userList.get(userList.size()-1).id};

            var data;
            setInterval(function () {
                $.getJSON("/weibo", {"maxId":maxId}).done(function (json) {
                    if(json.length) {
                        data = json;
                        $("#alert").show();
                        $("#text").text("您有"+json.length+"条新微博,请查看");
                    }
                });
            }, 1000);

            $("#text").click(function () {
                $("#alert").hide();
                maxId = data[data.length-1].id;
                for(var i in data) {
                    var li = "<li class='list-group-item'>"+data[i].name+"</li>";
                    $("#list").prepend(li);
                }
            });


        });
    </script>
</body>
</html>
