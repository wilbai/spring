<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM | CUSTOMER-MY</title>
    <!-- Tell the browser to be responsive to screen width -->
    <%@include file="../include/css.jsp"%>
    <style>
        .name-avatar {
            display: inline-block;
            width: 50px;
            height: 50px;
            background-color: #ccc;
            border-radius: 50%;
            text-align: center;
            line-height: 50px;
            font-size: 24px;
            color: #FFF;
        }
        .table>tbody>tr:hover {
            cursor: pointer;
        }
        .table>tbody>tr>td {
            vertical-align: middle;
        }
        .star {
            font-size: 20px;
            color: #ff7400;
        }
    </style>

</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- 顶部导航栏部分 -->
    <%@include file="../include/header.jsp"%>

    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="customer"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">我的客户</h3>
                    <div class="box-tools pull-right">
                        <button class="btn btn-success btn-sm" id="addCustomer"><i class="fa fa-plus"></i> 新增客户</button>
                        <button class="btn btn-primary btn-sm"><i class="fa fa-file-excel-o"></i> 导出Excel</button>
                    </div>
                </div>
                <c:if test="${not empty suc_message}" >
                    <div class="alert alert-success">${suc_message}</div>
                </c:if>
                <c:if test="${not empty err_message}" >
                    <div class="alert alert-danger">${err_message}</div>
                </c:if>
                <div class="box-body no-padding">
                    <table class="table table-hover">
                        <tbody>
                        <tr>
                            <th width="80"></th>
                            <th>姓名</th>
                            <th>职位</th>
                            <th>跟进时间</th>
                            <th>级别</th>
                            <th>联系方式</th>
                        </tr>
                        <c:forEach items="${customerList}" var="cus">
                        <tr>
                            <td><span class="name-avatar"><a href="/customer/${cus.id}">${fn:substring(cus.customerName, 0, 1)}</a></span></td>
                            <td><a href="/customer/${cus.id}">${cus.customerName}</a></td>
                            <td>${cus.jobTitle}</td>
                            <td><fmt:formatDate value="${cus.lastContactTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
                            <td class="star">
                                <c:choose>
                                    <c:when test="${cus.level == 1}">★</c:when>
                                    <c:when test="${cus.level == 2}">★★</c:when>
                                    <c:when test="${cus.level == 3}">★★★</c:when>
                                    <c:when test="${cus.level == 4}">★★★★</c:when>
                                    <c:when test="${cus.level == 5}">★★★★★</c:when>
                                </c:choose>
                            </td>
                            <td><i class="fa fa-phone"></i> ${cus.mobile} <br></td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <%@include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

<!-- jQuery 2.2.3 -->
<%@include file="../include/js.jsp"%>
<script>
    $(function () {
        $("#addCustomer").click(function () {
            window.location.href = "/customer/new";
        });
    });
</script>
</body>
</html>

