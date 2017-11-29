<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM | Login</title>
    <!-- css style -->
    <%@ include file="include/css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/iCheck/square/blue.css">
</head>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <a href="../../index2.html"><b>凯盛软件</b></a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg">${message}</p>

        <form method="post" id="loginForm">
            <div class="form-group has-feedback">
                <input type="tel" class="form-control" name="mobile" placeholder="手机号码" value="187">
                <span class="glyphicon glyphicon-phone form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" name="password" class="form-control" placeholder="密码" value="123">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                        <label class="">
                            <div class="icheckbox_square-blue">
                                <input type="checkbox" value="true" name="rememberMe">
                            </div>
                            自动登录
                        </label>
                    </div>
                </div>
                <!-- /.col -->
                <div class="col-offset-8 col-xs-4">
                    <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
                </div>
                <!-- /.col -->
            </div>
        </form>



    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 2.2.3 -->
<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/bootstrap/js/jquery.validate.js"></script>
<script src="/static/plugins/iCheck/icheck.min.js"></script>
<script>
    $(function () {
        $("#loginForm").validate({
            errorClass : "text-danger",
            errorElement : "span",
            rules : {
                mobile : {
                    required : true
                },
                password : {
                    required : true
                }
            },
            messages : {
                mobile : {
                    required : "请输入手机号",
                },
                password : {
                    required : "请输入密码"
                }
            }
        });

        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
    });
</script>
</body>
</html>
