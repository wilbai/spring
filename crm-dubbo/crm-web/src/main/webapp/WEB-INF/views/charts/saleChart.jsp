<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-销售统计</title>
    <%@ include file="../include/css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <%@include file="../include/header.jsp"%>
    <!-- =============================================== -->

    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="charts_sale"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">



            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">销售进度统计</h3>
                </div>
                <div class="box-body">
                    <div id="chance" style="height: 300px;width: 100%"></div>
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

        var chanceChart = echarts.init($("#chance")[0]);






        chanceChart.setOption({
            title: {
                text: '销售进度统计'
                //subtext: '纯属虚构'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c}%"
            },
            toolbox: {
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            },
            legend: {
                //data: ['初访','意向','报价','成交','暂时搁置']
                data:[]
            },
            calculable: true,
            series: [
                {
                    name:'百分比',
                    type:'funnel',
                    left: '10%',
                    top: 60,
                    //x2: 80,
                    bottom: 60,
                    width: '80%',
                    // height: {totalHeight} - y - y2,
                    min: 0,
                    max: 100,
                    minSize: '0%',
                    maxSize: '100%',
                    sort: 'descending',
                    gap: 2,
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        },
                        emphasis: {
                            textStyle: {
                                fontSize: 20
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            length: 10,
                            lineStyle: {
                                width: 1,
                                type: 'solid'
                            }
                        }
                    },
                    itemStyle: {
                        normal: {
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    },
                    data: [
//                        {value: 60, name: '初访'},
//                        {value: 40, name: '意向'},
//                        {value: 20, name: '报价'},
//                        {value: 80, name: '成交'},
//                        {value: 100, name: '暂时搁置'}
                    ]
                }
            ]
        });

        $.get("/charts/process").done(function (json) {
            if(json.state == 'success') {

                var keyArray = [];
                var map = [];
                var data = json.data;
                for(var i = 0; i < data.length; i++) {
                    var obj = data[i];
                    keyArray.push(obj.progress);
                    var mapObj = {"name":obj.progress,"value":obj.count};
                    map.push(mapObj);

                }

                chanceChart.setOption({
                    legend: {
                        data: keyArray
                    },
                    series: {
                        data: map
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