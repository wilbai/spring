<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/static/plugins/font-awesome/css/font-awesome.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">

    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="/static/dist/css/skins/_all-skins.min.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <a href="../../index2.html"><b>凯盛软件</b></a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg" id="message" hidden></p>

        <form method="post" id="loginForm">
            <div class="form-group has-feedback">
                <input type="text" name="mobile" id="mobile" class="form-control" placeholder="Mobile">
                <span class="glyphicon glyphicon-phone form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" name="password" id="password" class="form-control" placeholder="Password">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                        <label>
                            <a href="#">忘记密码</a><br>
                        </label>
                    </div>
                </div>
                <!-- /.col -->
                <div class="col-offset-8 col-xs-4">
                    <button type="submit" id="loginBtn" class="btn btn-primary btn-block btn-flat">登录</button>
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
<script src="/static/plugins/layer/layer.js"></script>
<script>
    $(function () {

        $("#loginBtn").click(function () {
            $("#loginForm").submit();
        });

        $(document).keydown(function (event) {
            if(event.keyCode == 13) {
                $("#loginForm").submit();
            }
        });

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
                    required : "请输入号码"
                },
                password : {
                    required : "请输入密码"
                }
            },
            submitHandler : function (form) {
                $.ajax({
                    url : '/employee/login',
                    type : 'post',
                    data : $(form).serialize(),
                    beforeSend : function () {
                        $("#loginBtn").text("努力加载中").attr("disabled", true);
                    },
                    success : function (json) {
                        if(json.state=="success") {
                            window.location.href="/employee";
                        } else {
                            $("#message").show().text(json.message);
                        }
                    },
                    error : function () {
                        layer.msg("服务器异常");
                    },
                    complete : function () {
                        $("#loginBtn").text("进入系统").removeAttr("disabled");
                    }
                });
            }
        });



    });

</script>
</body>
</html>

