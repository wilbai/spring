<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>OES | Paper_Detail</title>

    <!-- css style -->
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <style>
        th {
            font-size: 18px;
        }
        td {
            font-size: 17px;
        }
    </style>



</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
   <%-- <jsp:include page="../student/include/sider.jsp">
        <jsp:param name="menu" value="exam"/>
    </jsp:include>--%>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">Java考试</h3>
                   <%-- <div class="box-tools">
                        <a href="/question" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>

                            <a href="/question/edit/${question.id}" class="btn bg-purple btn-sm"><i class="fa fa-pencil"></i> 编辑</a>
                            <button id="delBtn" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>
                    </div>--%>
                </div>
                <div class="box-body no-padding">
                    <form method="post">
                        <ul class="list-group">
                            <c:forEach items="${idList}" var="choice" varStatus="state" >
                                ${state.count }. 题目
                                <div class="checkbox form-group">
                                    <label>
                                        <input type="checkbox" name="${choice}" id="optionsRadios1" value="A">
                                            A. <span style="display: inline-block;">aaaa</span>
                                    </label>
                                </div>
                                <div class="checkbox form-group">
                                    <label>
                                        <input type="checkbox" name="${choice}" id="optionsRadios2" value="B">
                                            B. <span style="display: inline-block;">bbbbb</span>
                                    </label>
                                </div>
                                <div class="checkbox form-group">
                                    <label>
                                        <input type="checkbox"  name="${choice}" id="optionsRadios3" value="C">
                                            C. <span style="display: inline-block;">cccc</span>
                                    </label>
                                </div>
                                <div class="checkbox form-group">
                                    <label>
                                        <input type="checkbox" name="${choice}" id="optionsRadios4" value="D">
                                            D. <span style="display: inline-block;">ddddd</span>
                                    </label>
                                </div>
                            </c:forEach>
                    </ul>
                        <button class="btn btn-primary">提交</button>
                    </form>
                </div>
            </div>

        </section>
        <!-- /.content -->

    </div>
    <!-- /.content-wrapper -->


</div>
<!-- ./wrapper -->

<!-- js -->

<script src="/static/js/jquery-3.js"></script>
<script>

    $(function () {



    });
</script>
</body>
</html>

