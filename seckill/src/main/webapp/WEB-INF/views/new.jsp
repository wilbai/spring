<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/js/datetimepicker/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" type="text/css" href="/static/js/editer/styles/simditor.css" />
</head>
<body>
    <div class="container">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label>商品名称</label>
                <input type="text" name="productName" class="form-control">
            </div>
            <div class="form-group">
                <label>商品副标题</label>
                <input type="text" name="productTitle" class="form-control">
            </div>
            <div class="form-group">
                <label>商品库存数量</label>
                <input type="text" name="productInventory" class="form-control">
            </div>
            <div class="form-group">
                <label>商品秒杀价</label>
                <input type="text" name="productPrice" class="form-control">
            </div>
            <div class="form-group">
                <label>商品市场价</label>
                <input type="text" name="productMarketPrice" class="form-control">
            </div>
            <div class="form-group">
                <label>商品图片</label>
                <input type="file" name="image" class="form-control">
            </div>
            <div class="form-group">
                <label>开始时间</label>
                <input type="text" name="sTime" class="form-control timePicker">
            </div>
            <div class="form-group">
                <label>结束时间</label>
                <input type="text" name="eTime" class="form-control timePicker">
            </div>
            <div class="form-group">
                <label>商品详情</label>
                <textarea type="text" id="editor" name="productDesc" class="form-control"></textarea>
            </div>
            <div class="form-group">
                <button class="btn btn-primary">保存</button>
            </div>
        </form>
    </div>
    <script src="/static/js/jquery.min.js"></script>
    <script src="/static/js/bootstrap.min.js"></script>
    <script src="/static/js/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script src="/static/js/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="/static/js/editer/scripts/module.js"></script>
    <script src="/static/js/editer/scripts/hotkeys.js"></script>
    <script src="/static/js/editer/scripts/uploader.js"></script>
    <script src="/static/js/editer/scripts/simditor.js"></script>
    <script>
        $(function () {
            var timepicker = $('.timePicker').datetimepicker({
                format: "yyyy-mm-dd hh:ii",
                language: "zh-CN",
                autoclose: true,
                todayHighlight: true
            });
            var editor = new Simditor({
                textarea: $('#editor')
                //optional options
            });
        });
    </script>
</body>
</html>
