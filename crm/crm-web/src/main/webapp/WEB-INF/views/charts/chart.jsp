<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-客户统计</title>
    <%@ include file="../include/css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <%@include file="../include/header.jsp"%>
    <!-- =============================================== -->

    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="charts_customer"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户级别数量统计</h3>
                </div>
                <div class="box-body">
                    <div id="bar" style="height: 300px;width: 100%"></div>
                </div>
            </div>



            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">每月新增客户数量统计</h3>
                </div>
                <div class="box-body">
                    <div id="customer" style="height: 300px;width: 100%"></div>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../include/js.jsp"%>
<script src="/static/dist/js/echarts.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>

<script>
    $(function () {
        var myChart = echarts.init(document.getElementById('bar'));

        var cusChart = echarts.init($("#customer")[0]);

        myChart.setOption({
            title: {
                text: '客户级别数量统计',
                left: 'center'
            },
            tooltip: {},
            legend: {
                data:['人数'],
                left:'right'
            },
            xAxis: {
                name:'星级',
                data: []
            },
            yAxis: {},
            series: [{
                name: '人数',
                type: 'bar',
                data: []
            }]
        });

        $.get("/charts/level").done(function (json) {
            if(json.state == 'success') {

                var keyArray = [];
                var valueArray = [];
                var data = json.data;
                for(var i = 0; i < data.length; i++) {
                    var obj = data[i];
                    keyArray.push(obj.level);
                    valueArray.push(obj.count);
                }

                myChart.setOption({
                    xAxis: {
                        data: keyArray
                    },
                    series: {
                        data: valueArray
                    }
                });

            } else {
                layer.msg("json.message");
            }
        }).error(function () {
            layer.msg("服务器异常");
        });




        cusChart.setOption({
            title: {
                text: '每月新增客户数量统计',
                left: 'center'
            },
            tooltip: {},
            legend: {
                data:['人数'],
                left:'right'
            },
            xAxis: {
                name:'月份',
                data: []
            },
            yAxis: {},
            series: [{
                name: '人数',
                type: 'line',
                data: []
            }]
        });

        $.get("/charts/newCus").done(function (json) {
            if(json.state == 'success') {

                var keyArray = [];
                var valueArray = [];
                var data = json.data;
                for(var i = 0; i < data.length; i++) {
                    var obj = data[i];
                    keyArray.push(obj.month + '月');
                    valueArray.push(obj.count);
                }

                cusChart.setOption({
                    xAxis: {
                        data: keyArray
                    },
                    series: {
                        data: valueArray

                    }
                });

            } else {
                layer.msg("json.message");
            }
        }).error(function () {
            layer.msg("服务器异常");
        });






    });
</script>
</body>
</html>