<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM-修改待办事项</title>
    <!-- css style -->
    <%@ include file="../include/css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="/static/plugins/datepicker/datepicker3.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- 顶部导航栏部分 -->
    <%@ include file="../include/header.jsp"%>

    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="task"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">修改待办任务</h3>

                    <div class="box-tools pull-right">
                        <a href="javascript:history.back()" type="button" class="btn btn-box-tool">
                            <i class="fa fa-plus"></i> 返回列表
                        </a>
                    </div>
                </div>
                <div class="box-body">
                    <form method="post" id="editForm">
                        <div class="form-group">
                            <label>任务名称</label>
                            <input type="hidden" name="accountId" value="<shiro:principal property="id"/>">
                            <input type="hidden" name="saleId" value="${task.saleId}">
                            <input type="hidden" name="customerId" value="${task.customerId}">
                            <input type="text" name="title" value="${task.title}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>完成日期</label>
                            <input type="text" name="finishTime" value="${task.finishTime}" class="form-control" id="datepicker" >
                        </div>
                        <div class="form-group">
                            <label>提醒时间</label>
                            <input type="text" name="remindTime" value="${task.remindTime}" class="form-control" id="datepicker2" >
                        </div>
                    </form>
                </div>
                <!-- /.box-body -->
                <div class="box-footer">
                    <button id="editBtn" class="btn btn-primary">保存</button>
                </div>
            </div>
            <!-- /.box -->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <%@ include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

<!-- js -->
<%@ include file="../include/js.jsp"%>
<script src="/static/plugins/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="/static/plugins/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="/static/plugins/moment/moment.js"></script>
<script src="/static/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="/static/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script src="/static/bootstrap/js/jquery.validate.js"></script>
<script>
    $(function () {

        var picker = $("#datepicker").datepicker({
            format:"yyyy-mm-dd",
            language:"zh-CN",
            autoclose:true,
            todayHighlight:true,
            startDate:moment().format("yyyy-MM-dd")
        });
        picker.on("changeDate", function (e) {
            var today = moment().format("YYYY-MM-DD");
            $('#datepicker2').datetimepicker('setStartDate', today);
            $('#datepicker2').datetimepicker('setEndDate', e.format('yyyy-mm-dd'));
        });

        var timepicker = $('#datepicker2').datetimepicker({
            format:"yyyy-mm-dd hh:ii",
            language:"zh-CN",
            autoclose:true,
            todayHighlight:true,
        });

        $("#editBtn").click(function () {
            $("#editForm").submit();
        });

        $("#editForm").validate({
            errorClass:"text-danger",
            errorElement:"span",
            rules:{
                title:{
                    required:true
                },
                finishTime:{
                    required:true
                }
            },
            messages:{
                title:{
                    required:"请输入任务内容"
                },
                finishTime:{
                    required:"请选择完成时间"
                }

            }
        });
    });
</script>
</body>
</html>
