<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM-新增机会</title>
    <!-- css style -->
    <%@ include file="../include/css.jsp"%>
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

                    <%--<div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool">
                            <i class="fa fa-plus"></i> 添加机会
                        </button>
                    </div>--%>
                </div>
                <div class="box-body">
                    <form method="post" id="addForm">
                        <div class="form-group">
                            <label>机会名称</label>
                            <input name="accountId" type="hidden" value="${sessionScope.currentAccount.id}">
                            <input name="name" type="text" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>关联客户</label>
                            <select name="customerId" id="" class="form-control">
                                <option value=""></option>
                                <c:forEach items="${customerList}" var="customer">
                                    <option value="${customer.id}">${customer.customerName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>机会价值</label>
                            <input name="worth" type="text" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>当前进度</label>
                            <select name="progress" class="form-control">
                                <c:forEach items="${progressList}" var="progress">
                                    <option value="${progress}">${progress}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>详细内容</label>
                            <textarea name="content" class="form-control"></textarea>
                        </div>
                    </form>
                </div>
                <!-- /.box-body -->
                <div class="box-footer">
                    <button class="btn btn-primary" id="addBtn">保存</button>
                    <a href="javascript:history.back()" class="btn btn-default" >取消</a>
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
<script src="/static/bootstrap/js/jquery.validate.js"></script>
<script>
    $(function () {
        $("#addBtn").click(function () {
            $("#addForm").submit();
        });

        $("#addForm").validate({
            errorClass:"text-danger",
            errorElement:"span",
            rules:{
                name:{
                    required:true
                },
                customerId:{
                    required:true
                },
                worth:{
                    required:true,
                    number:true,
                    min:1
                }
            },
            messages:{
                name:{
                    required:"请输入机会名称"
                },
                customerId:{
                    required:"请选择机会客户"
                },
                worth:{
                    required:"请输入机会价值",
                    number:"请输入有效的价值",
                    min:"请输入大于1的价值"
                }
            }
        });
    });
</script>
</body>
</html>
