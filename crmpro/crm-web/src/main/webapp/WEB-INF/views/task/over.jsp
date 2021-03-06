<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM-待办事项</title>
    <!-- css style -->
    <%@ include file="../include/css.jsp"%>
    <style>
        .table>tbody>tr:hover {
            cursor: pointer;
        }
        .table>tbody>tr>td {
            vertical-align: middle;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- 顶部导航栏部分 -->
    <%@ include file="../include/header.jsp"%>

    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="task_over"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">逾期事项</h3>

                    <div class="box-tools pull-right">
                        <a href="/task/new" class="btn btn-success btn-sm"><i class="fa fa-plus"></i> 新增任务</a>

                        <a href="/task/all" class="btn btn-primary btn-sm"><i class="fa fa-eye"></i> 显示所有任务</a>
                    </div>
                </div>
                <div class="box-body">

                    <ul class="todo-list">
                        <c:forEach items="${taskList}" var="task" >
                            <li class="" >
                            <input type="checkbox" class="task_checkbox" ${task.done == 1?'checked':''} value="${task.id}">
                                    <span class="text">${task.title}</span>
                                    <c:choose>
                                    <c:when test="${not empty task.customerId and not empty task.customer}">
                                        <a href="/customer/my/${task.customerId}"><i class="fa fa-user-o"></i> ${task.customer.customerName}</a>
                                    </c:when>
                                    <c:when test="${not empty task.saleId and not empty task.saleChance}">
                                        <a href="/sales/my/${task.saleId}"><i class="fa fa-money"></i> ${task.saleChance.name}</a>
                                     </c:when>
                                    </c:choose>
                                    <small class="label label-danger"><i class="fa fa-clock-o"></i> ${task.finishTime}</small>
                                    <div class="tools">
                                        <%--<i class="fa fa-edit"></i>--%>
                                        <i class="fa fa-trash-o delTask" rel="${task.id}"></i>
                                    </div>
                                </li>
                        </c:forEach>

                    </ul>
                </div>
                <!-- /.box-body -->
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
<script src="/static/plugins/layer/layer.js"></script>
<script>
    $(function () {

        $(".delTask").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要删除么？",function () {
                window.location.href = "/task/"+id+"/del";
            });
        });



    });
</script>
</body>
</html>
