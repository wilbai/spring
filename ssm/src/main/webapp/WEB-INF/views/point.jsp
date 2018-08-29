<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>地图展示</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=zOhaQLnNfm9NPvmvVnl48ZmNGs3ZQZHY"></script>
</head>
<body>


    <div class="container">
        <form action="">
            <div class="form-group">
                <label>要查询的地址：</label>
                <input id="text_" type="text" class="form-control"/>
            </div>

            <div class="form-group">
                <label>查询结果(经度)：</label>
                <input id="result_l" type="text" />
            </div>
            <div class="form-group">
                <label>查询结果(纬度)：</label>
                <input id="result_a" type="text" />
            </div>
            <input type="button" value="查询" onclick="searchByStationName();"/>
        </form>
    </div>

</body>

<script type="text/javascript">

    var map = new BMap.Map();
    var localSearch = new BMap.LocalSearch(map);

    function searchByStationName() {

        var keyword = document.getElementById("text_").value;
        localSearch.setSearchCompleteCallback(function (searchResult) {
            var poi = searchResult.getPoi(0);
            document.getElementById("result_l").value = poi.point.lng;
            document.getElementById("result_a").value = poi.point.lat;

        });
        localSearch.search(keyword);
    }



</script>

</html>
