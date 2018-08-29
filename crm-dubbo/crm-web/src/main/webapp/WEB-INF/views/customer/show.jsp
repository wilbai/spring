<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM | 客户资料</title>

    <!-- css style -->
    <%@ include file="../include/css.jsp"%>

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
    <%@ include file="../include/header.jsp"%>

    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="customer_my"/>
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
                        <a href="/customer/my/" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                        <c:if test="${customer.accountId != 0}">
                            <a href="/customer/my/${customer.id}/edit" class="btn bg-purple btn-sm"><i class="fa fa-pencil"></i> 编辑</a>
                            <button id="transBtn" class="btn bg-orange btn-sm"><i class="fa fa-exchange"></i> 转交他人</button>
                            <button id="publicBtn" class="btn bg-maroon btn-sm"><i class="fa fa-recycle"></i> 放入公海</button>
                            <button id="delBtn" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>
                        </c:if>
                        <c:if test="${customer.accountId == 0}">
                            <button id="getCus" class="btn bg-maroon btn-sm"><i class="fa fa-recycle"></i> 接手客户</button>
                        </c:if>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table">
                        <tr>
                            <td class="td_title">姓名</td>
                            <td>${customer.customerName}(${customer.sex == '女士'?'<i class="fa fa-female"></i>' : '<i class="fa fa-male"></i>'})</td>
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
                            <td>${customer.level}</td>
                        </tr>
                        <c:if test="${not empty customer.address}">
                            <tr>
                                <td class="td_title">地址</td>
                                <td colspan="5">${customer.address}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty customer.mark}">
                            <tr>
                                <td class="td_title">备注</td>
                                <td colspan="5">${customer.mark}</td>
                            </tr>
                        </c:if>
                    </table>
                </div>
                <div class="box-footer">
                    <span style="color: #ccc" class="pull-right">
                        创建日期：<span title="<fmt:formatDate value="${customer.createTime}"/>"><fmt:formatDate value="${customer.createTime}" pattern="MM月dd日"/></span> &nbsp;&nbsp;&nbsp;&nbsp;
                        最后修改日期：<span title="<fmt:formatDate value="${customer.updateTime}"/>"><fmt:formatDate value="${customer.updateTime}" pattern="MM月dd日"/></span>
                    </span>
                </div>
            </div>

            <div class="row">
                <div class="col-md-8">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">跟进记录</h3>
                        </div>
                        <div class="box-body">
                            <c:forEach items="${saleChanceList}" var="chance">
                                <a href="/sales/my/${chance.id}" target="_blank">${chance.content}</a>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">日程安排</h3>
                            <div class="box-tools">
                            <a href="/task/new?customerId=${customer.id}" class="btn btn-success btn-sm pull-right"><i class="fa fa-plus"></i> 新增任务</a>
                            </div>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">相关资料</h3>
                            <p>${customer.record}</p>
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
    <%@ include file="../include/footer.jsp"%>

    <!-- 转交他人模态框 -->
    <div class="modal fade" id="transModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">请选择要转交的同事</h4>
                </div>
                <div class="modal-body">
                    <select id="selectedAcc" class="form-control">
                        <option value="">-- 请选择 --</option>
                        <c:forEach items="${accountList}" var="account">
                            <c:if test="${account.id != customer.accountId}">
                                <option value="${account.id}">${account.userName} (${account.mobile})</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="saveTransBtn">确定</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

</div>
<!-- ./wrapper -->

<!-- js -->
<%@ include file="../include/js.jsp"%>
<script src="/static/plugins/layer/layer.js"></script>

<script>

    $(function () {
        var customerId = ${customer.id};

        //转交他人
        $("#transBtn").click(function () {
            $("#transModal").modal({
                show : true,
                backdrop : 'static'
            });
        });
        $("#saveTransBtn").click(function () {
            var accountId = $("#selectedAcc").val();
            var accountName = $("#selectedAcc option:selected").text();
            layer.confirm("确定要将客户转交给"+accountName+"么?", function () {
                window.location.href = "/customer/my/"+customerId+"/trans/"+accountId;
            });
        });

        //放入公海
        $("#publicBtn").click(function () {
            layer.confirm("确定要将该客户放入公海么？",function () {
                window.location.href = "/customer/my/"+customerId+"/public";
            })
        });

        //删除客户
        $("#delBtn").click(function () {
            layer.confirm("删除客户将删除与该客户有关的所有信息，确定要删除么？",function () {
                window.location.href = "/customer/my/"+customerId+"/delete";
            })
        });

        //接手客户
        $("#getCus").click(function () {
            layer.confirm("确定要接收该客户么？",function () {
                window.location.href = "/customer/public/"+customerId+"/get";
            })
        });



    });
</script>
</body>
</html>

