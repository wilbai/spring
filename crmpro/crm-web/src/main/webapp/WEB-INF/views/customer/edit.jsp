<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM | CUSTOMER-EDIT</title>
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
                    <h3 class="box-title">编辑客户</h3>
                    <div class="box-tools pull-right">
                        <button class="btn btn-primary btn-sm" id="goList"><i class="fa fa-arrow-left"></i> 返回列表</button>
                    </div>
                </div>
                <div class="box-body">
                    <form method="post" id="editForm">
                        <div class="form-group">
                            <label>姓名</label>
                            <input type="text" name="customerName" class="form-control" value="${customer.customerName}">
                        </div>
                        <div class="form-group">
                            <label>职位</label>
                            <input type="text" name="jobTitle" class="form-control" value="${customer.jobTitle}">
                        </div>
                        <label class="radio-inline">
                            <input type="radio" name="sex" ${customer.sex == "男" ? 'checked' : ''} value="男"><i class="fa fa-male"></i>
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="sex" ${customer.sex == "女" ? 'checked' : ''} value="女"><i class="fa fa-female"></i>
                        </label>
                        <div class="form-group">
                            <label>联系方式</label>
                            <input type="text" name="mobile" class="form-control" value="${customer.mobile}">
                        </div>
                        <div class="form-group">
                            <label>地址</label>
                            <input type="text" name="address" class="form-control" value="${customer.address}">
                        </div>
                        <div class="form-group">
                            <label>所属行业</label>
                            <select class="form-control" name="trade">
                                <option value=""></option>
                                <option value="互联网" ${customer.trade == "互联网" ? 'selected':''}>互联网</option>
                                <option value="电力能源" ${customer.trade == "电力能源" ? 'selected':''}>电力能源</option>
                                <option value="其他" ${customer.trade == "其他" ? 'selected':''}>其他</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>客户来源</label>
                            <select name="source" class="form-control">
                                <option value=""></option>
                                <option value="DM广告" ${customer.source == "DM广告"?'selected':''}>DM广告</option>
                                <option value="电视媒体" ${customer.source == "电视媒体"?'selected':''}>电视媒体</option>
                                <option value="网络媒体" ${customer.source == "网络媒体"?'selected':''}>网络媒体</option>
                                <option value="顾客推荐" ${customer.source == "顾客推荐"?'selected':''}>顾客推荐</option>
                                <option value="主动上门" ${customer.source == "主动上门"?'selected':''}>主动上门</option>
                                <option value="其他" ${customer.source == "其他"?'selected':''}>其他</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>级别</label>
                            <select class="form-control" name="level">
                                <option value=""></option>
                                <option value="1" ${customer.level == "1"?'selected':''}>★</option>
                                <option value="2" ${customer.level == "2"?'selected':''}>★★</option>
                                <option value="3" ${customer.level == "3"?'selected':''}>★★★</option>
                                <option value="4" ${customer.level == "4"?'selected':''}>★★★★</option>
                                <option value="5" ${customer.level == "5"?'selected':''}>★★★★★</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>备注</label>
                            <input type="text" class="form-control" name="mark" value="${customer.mark}">
                        </div>
                        <button class="btn btn-primary" id="editBtn">保存</button>
                    </form>
                </div>
                <div class="box-footer">
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

<%@include file="../include/js.jsp"%>
<script>
    $(function () {
        $("#goList").click(function () {
            window.location.href="/customer/my";
        });

    });
</script>
</body>
</html>



