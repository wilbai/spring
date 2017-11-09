<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM | CUS_DETAIL</title>
    
    <%@include file="../include/css.jsp"%>
    <style>
        .td_title {
            font-weight: bold;
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

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户资料</h3>
                    <div class="box-tools">
                        <button class="btn btn-primary btn-sm" id="goList"><i class="fa fa-arrow-left"></i> 返回列表</button>
                        <button class="btn bg-purple btn-sm" id="editBtn"><i class="fa fa-pencil"></i> 编辑</button>
                        <button class="btn bg-orange btn-sm"><i class="fa fa-exchange"></i> 转交他人</button>
                        <button class="btn bg-maroon btn-sm" id="publicBtn"><i class="fa fa-recycle"></i> 放入公海</button>
                        <button class="btn btn-danger btn-sm" id="deleteBtn"><i class="fa fa-trash-o"></i> 删除</button>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table">
                        <tr>
                            <td class="td_title" id="customer" rel="${customer.id}">姓名</td>
                            <td>${customer.customerName}</td>
                            <td class="td_title">职位</td>
                            <td>${customer.jobTitle}</td>
                            <td class="td_title">联系电话</td>
                            <td>${customer.mobile}</td>
                        </tr>
                        <tr>
                            <td class="td_title">所属行业</td>
                            <td>${customer.trade}</td>
                            <td class="td_title">客户来源</td>
                            <td>${customer.source}</td>
                            <td class="td_title">级别</td>
                            <td>
                                <c:choose>
                                    <c:when test="${customer.level == 1}">★</c:when>
                                    <c:when test="${customer.level == 2}">★★</c:when>
                                    <c:when test="${customer.level == 3}">★★★</c:when>
                                    <c:when test="${customer.level == 4}">★★★★</c:when>
                                    <c:when test="${customer.level == 5}">★★★★★</c:when>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_title">地址</td>
                            <td colspan="5">${customer.address}</td>
                        </tr>
                        <tr>
                            <td class="td_title">备注</td>
                            <td colspan="5">${customer.mark}</td>
                        </tr>
                    </table>
                </div>
                <div class="box-footer">
                    <span style="color: #ccc" class="pull-right">
                        创建日期：<fmt:formatDate value="${customer.createTime}" pattern="MM月dd日"/>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        最后修改日期：<fmt:formatDate value="${customer.updateTime}" pattern="MM月dd日"/></span>
                </div>
            </div>

            <div class="row">
                <div class="col-md-8">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">跟进记录</h3>
                        </div>
                        <div class="box-body">
                            ${customer.record}
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">日程安排</h3>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">相关资料</h3>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <%@include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../include/js.jsp"%>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/bootstrap/js/jquery.validate.js"></script>
<script>
    $(function () {
        $("#goList").click(function () {
            window.location.href="/customer/my";
        });
        $("#editBtn").click(function () {
            var id = $("#customer").attr("rel");
            window.location.href="/customer/"+id+"/edit";
        });
        $("#publicBtn").click(function () {
            var id = $("#customer").attr("rel");
            $.get("/customer/publicSea.json",{id:id}).done(function (data) {
                if(data.state == 'success') {
                    layer.msg("客户放入公海成功");//延时处理
                    window.location.href="/customer/my";
                } else {
                    layer.msg("客户放入公海失败");
                }
            }).error(function () {
                layer.msg("服务器异常");
            });
        });
        $("#deleteBtn").click(function () {
            var id = $("#customer").attr("rel");

            $.get("/customer/delCus.json",{id:id}).done(function (data) {
                if(data.state == 'success') {
                    layer.msg("删除客户成功");//延时处理
                    window.location.href="/customer/my";
                } else {
                    layer.msg("删除客户失败");
                }
            }).error(function () {
                layer.msg("服务器异常");
            });
        });

    });
</script>
</body>
</html>

