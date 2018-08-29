<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM-修改员工信息</title>
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
        <jsp:param name="menu" value="employee"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">修改员工信息</h3>

                    <div class="box-tools pull-right">
                        <a href="javascript:history.back()" type="button" class="btn btn-box-tool">
                            <i class="fa fa-plus"></i> 返回列表
                        </a>
                    </div>
                </div>
                <div class="box-body">
                    <form id="editEmployeeForm" method="post">
                        <div class="form-group">
                            <label for="userName">姓名</label>
                            <input id="userName" type="text" class="form-control" name="userName" value="${account.userName}">
                        </div>
                        <div class="form-group">
                            <label for="mobile">手机号码</label>
                            <input id="mobile" type="text" class="form-control" name="mobile" value="${account.mobile}">
                        </div>
                        <div class="form-group">
                            <label for="password">新密码</label>
                            <input id="password" type="password" class="form-control" name="password" >
                        </div>
                        <div class="form-group">
                            <label >所属部门</label>
                            <div>
                                <c:forEach items="${deptList}" var="dept">
                                    <input type="checkbox" name="deptIdArray" checked value=${dept.id}> ${dept.deptName}
                                </c:forEach>
                                <c:forEach items="${restDepts}" var="dept">
                                    <input type="checkbox" name="deptIdArray" value=${dept.id}> ${dept.deptName}
                                </c:forEach>
                            </div>
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
<script src="/static/bootstrap/js/jquery.validate.js"></script>
<script>
    $(function () {

        $("#editBtn").click(function () {
            $("#editEmployeeForm").submit();
        });

        //jqValidate验证添加员工表单
        $("#editEmployeeForm").validate({
            errorClass : "text-danger",
            errorElement : "span",
            rules : {
                userName:{
                    required:true
                },
                mobile:{
                    required:true,
                    digits:true
                },
                deptIdArray:{
                    required:true
                }
            },
            messages : {
                userName:{
                    required:" 请输入姓名"
                },
                mobile:{
                    required:" 请输入手机号",
                    digits:"请输入整数"
                },
                deptIdArray:{
                    required:"请选择部门"
                }
            }
        });
    });
</script>
</body>
</html>
