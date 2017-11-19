<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM-公司网盘</title>
    <!-- css style -->
    <%@ include file="../include/css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/uploader/webuploader.css">
    <style>
        tr{
            height: 50px;
            line-height: 50px;
        }
        .table>tbody>tr>td{
            vertical-align: middle;
        }
        .file_icon {
            font-size: 30px;
        }
        .table>tbody>tr:hover{
            cursor: pointer;
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
        <jsp:param name="menu" value="disk"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">公司网盘</h3>

                    <div class="box-tools pull-right">
                        <c:if test="${not empty requestScope.pId}">
                            <a href="javascript:history.back()"  class="btn btn-gray btn-sm"><i class="fa fa-arrow-left"></i> 返回上级</a>
                        </c:if>
                        <button  class="btn btn-primary btn-sm"><i class="fa fa-upload"></i> 上传文件</button>
                        <button id="newFolder" class="btn btn-success btn-sm"><i class="fa fa-plus"></i> 新建文件夹</button>
                    </div>
                </div>
                <div class="box-body no-padding">

                    <table class="table table-hover">
                        <c:forEach items="${diskList}" var="disk">
                        <tr class="rowDetail" rel="${disk.id}">
                            <td width="50" class="file_icon"><i class="fa fa-folder-o"></i></td>
                            <td>${disk.name}</td>
                            <td><fmt:formatDate value="${disk.createTime}"/></td>
                            <td width="100"></td>
                            <td width="150">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        <i class="fa fa-ellipsis-h"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="">打开</a></li>
                                        <li><a href="#">重命名</a></li>
                                        <li><a href="#">删除</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        </c:forEach>
                        <tr>
                            <td width="50" class="file_icon"><i class="fa fa-file-o"></i></td>
                            <td>销售合同.doc</td>
                            <td>2017-07-24</td>
                            <td width="100">23kB</td>
                            <td width="100">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        <i class="fa fa-ellipsis-h"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="">下载</a></li>
                                        <li><a href="#">重命名</a></li>
                                        <li><a href="#">删除</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td width="50" class="file_icon"><i class="fa fa-folder-o"></i></td>
                            <td>样板间图片</td>
                            <td>2017-07-24</td>
                            <td width="100"></td>
                            <td width="100">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        <i class="fa fa-ellipsis-h"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="">打开</a></li>
                                        <li><a href="#">重命名</a></li>
                                        <li><a href="#">删除</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td width="50" class="file_icon"><i class="fa fa-file-o"></i></td>
                            <td>3月销售排名.xls</td>
                            <td>2017-07-24</td>
                            <td width="100">125kB</td>
                            <td width="100">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        <i class="fa fa-ellipsis-h"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="">下载</a></li>
                                        <li><a href="#">重命名</a></li>
                                        <li><a href="#">删除</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td width="50" class="file_icon"><i class="fa fa-file-o"></i></td>
                            <td>购房合同模板.doc</td>
                            <td>2017-07-24</td>
                            <td width="100">23kB</td>
                            <td width="100">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        <i class="fa fa-ellipsis-h"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="">下载</a></li>
                                        <li><a href="#">重命名</a></li>
                                        <li><a href="#">删除</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>
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
    <%@ include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@ include file="../include/js.jsp"%>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/plugins/uploader/webuploader.js"></script>
<script>
    $(function () {

        var accountId = ${sessionScope.currentAccount.id};
        var pId = ${not empty requestScope.pId? requestScope.pId:0};

        $("#newFolder").click(function () {
            layer.prompt({title:"请输入文件夹名称"}, function (value, index) {
                layer.close(index);
                $.post("/disk/new", {"name":value,"pId":pId,"accountId":accountId}).done(function (data) {
                    if(data.state == "success") {
                        layer.msg("添加成功");
                        //刷新

                    } else {
                        layer.msg(data.message);
                    }
                }).error(function () {
                    layer.msg("服务器异常");
                });
            });
        });

        $(".rowDetail").click(function () {
            var id = $(this).attr("rel");
            window.location.href = "/disk?_="+id;
        });

        var uploader = WebUploader.create({
            auto:true,
            swf:'/static/plugins/uploader/Uploader.swf',
            server:'/disk/upload',
            pick:'#picker'
        });

    });
</script>
</body>
</html>

