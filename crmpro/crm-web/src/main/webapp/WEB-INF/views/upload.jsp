<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>

    <form action="http://up-z1.qiniup.com" method="post" enctype="multipart/form-data">
        <input type="hidden" name="token" value="${upToken}">
        <input type="file" name="file">
        <input type="text" name="key" value="">
        <button>上传</button>
    </form>

</body>
</html>
