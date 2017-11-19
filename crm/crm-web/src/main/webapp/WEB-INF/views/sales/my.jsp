<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM-我的销售机会</title>
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
        <jsp:param name="menu" value="chance_my"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">我的销售机会</h3>

                    <div class="box-tools pull-right">
                        <a href="/sales/my/new" type="button" class="btn btn-box-tool">
                            <i  class="fa fa-plus"></i> 添加机会
                        </a>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table table-hover">
                        <thead>
                            <tr >
                                <td>机会名称</td>
                                <td>关联客户</td>
                                <td>机会价值</td>
                                <td>当前进度</td>
                                <td>最后跟进时间</td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${page.list}" var="chance">
                                <tr class="rowDetail" rel="${chance.id}">
                                    <td>${chance.name}</td>
                                    <td>${chance.customer.customerName}</td>
                                    <td><fmt:formatNumber value="${chance.worth}"/></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${chance.progress == '成交'}">
                                                <span class="label label-success">${chance.progress}</span>
                                            </c:when>
                                            <c:when test="${chance.progress == '暂时搁置'}">
                                                <span class="label label-danger">${chance.progress}</span>
                                            </c:when>
                                            <c:otherwise>${chance.progress}</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><fmt:formatDate value="${chance.lastTime}"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </section>
            <c:if test="${page.pages > 1}">
                <ul id="pagination-demo" class="pagination-sm pull-right"></ul>
            </c:if>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <%@ include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

<!-- js -->
<%@ include file="../include/js.jsp"%>
<script src="/static/bootstrap/js/jquery.twbsPagination.min.js"></script>
<script>
    $(function () {
        <c:if test="${page.pages > 1}" >
        //分页
        $('#pagination-demo').twbsPagination({
            totalPages: ${page.pages},
            visiblePages: 3,
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"?p={{number}}"
        });
        </c:if>

        $(".rowDetail").click(function () {
            var id = $(this).attr("rel");
            window.location.href = "/sales/my/"+id;
        });
    });
</script>
</body>
</html>
