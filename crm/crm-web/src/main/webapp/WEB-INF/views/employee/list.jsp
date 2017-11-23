<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM | 员工管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <%@include file="../include/css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/tree/css/metroStyle/metroStyle.css">
    <link rel="stylesheet" href="/static/plugins/datatables/jquery.dataTables.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../include/header.jsp"%>

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

            <div class="row">
                <div class="col-md-2">
                    <div class="box">
                        <div class="box-body">
                            <button class="btn btn-default" id="addDept">添加部门</button>
                            <input type="hidden" id="deptId">
                            <ul id="ztree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-10">
                    <!-- Default box -->
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">员工管理</h3>
                            <div class="box-tools pull-right">
                                <button type="button" id="addEmployee" class="btn btn-box-tool"  title="Collapse">
                                    <i class="fa fa-plus"></i> 添加员工</button>
                            </div>
                        </div>
                        <div class="box-body">
                            <table class="table" id="dataTable">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>姓名</th>
                                    <th>部门</th>
                                    <th>手机</th>
                                    <th>#</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                    <!-- /.box -->
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

<!-- 添加员工模态框 --!>
<!-- Modal -->
<div class="modal fade" id="addEmployeeModal" >
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加员工</h4>
            </div>
            <div class="modal-body">
                <form id="addEmployeeForm">
                    <div class="form-group">
                        <label>姓名</label>
                        <input type="text" class="form-control" name="userName">
                    </div>
                    <div class="form-group">
                        <label>手机号码</label>
                        <input type="text" class="form-control" name="mobile">
                    </div>
                    <div class="form-group">
                        <label>密码(默认000000)</label>
                        <input type="password" class="form-control" name="password" value="000000">
                    </div>
                    <div class="form-group">
                        <label>所属部门</label>
                        <div id="checkboxList">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="addEmployeeFormBtn">保存</button>
            </div>
        </div>
    </div>
</div>
<%----------------------------------%>



<%@include file="../include/js.jsp"%>
<script src="/static/plugins/tree/js/jquery.ztree.all.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/plugins/datatables/jquery.dataTables.js"></script>
<script src="/static/bootstrap/js/jquery.validate.js"></script>


<script>
    $(function(){

        //dataTable信息设置
        var dataTable = $("#dataTable").DataTable({
            "processing" : true,
            "serverSide" : true,
            "ajax" : {
                "url" : "/employee/load.json",
                "data" : function (data) {
                    data.deptId = $("#deptId").val();
                }
            },
            "lengthChange" : false,
            "pageLength" : 15,
            "columns" : [
                {"data" :"id"},
                {"data" : "userName"},
                {"data" : function (row) {
                    var deptArray = row.deptList;
                    var str = "";
                    for(var i = 0; i < deptArray.length; i++) {
                        str += deptArray[i].deptName + " ";
                    }
                    return str;
                }},
                {"data" : "mobile"},
                {"data" : function (row) {
                    return "<a href='javascript:;' class='delEmployee' rel='"+ row.id +"'>删除</a>";
                }},
            ],
            "columnDefs" : [
                {
                    "targets": [2,3,4],
                    "orderable": false
                },
                {
                    "targets": [0],
                    "visible": false
                }
            ],
            language:{
                "search":"姓名:",
                "info": "显示从 _START_ 到 _END_ 条数据，共 _TOTAL_ 条",
                "infoEmpty":"未找到数据",
                "emptyTable":"暂无数据",
                "processing":"加载中...",
                "paginate": {
                    "first":      "首页",
                    "last":       "末页",
                    "next":       "下一页",
                    "previous":   "上一页"
                }
            }

        });

        //添加员工
        $("#addEmployee").click(function () {
            $("#checkboxList").html("");
            $.get("/employee/dept.json").done(function (data) {
                for(var i = 0; i < data.length; i++) {
                    var dept = data[i];
                    if(dept.id != 1) {
                        var html = '<label class="checkbox-inline"><input type="checkbox" name="deptIdArray" value="'+dept.id+'">'+dept.deptName+'</label>';
                        $("#checkboxList").append(html);
                    }
                }

               $("#addEmployeeModal").modal({
                   show : true,
                   backdrop : 'static'
               });
            }).error(function () {
                layer.msg("部门数据加载失败，服务器异常");
            });
        });

        $("#addEmployeeFormBtn").click(function () {
            $("#addEmployeeForm").submit();
        });

        //jqValidate验证添加员工表单
        $("#addEmployeeForm").validate({
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
                password:{
                    required:true
                },
                deptId:{
                    required:true
                }
            },
            messages : {
                userName:{
                    required:"请输入姓名"
                },
                mobile:{
                    required:"请输入手机号",
                    digits:"请输入整数"
                },
                password:{
                    required:"请输入密码"
                },
                deptId:{
                    required:"请选择部门"
                }
            },
            submitHandler : function () {
                $.post("/employee/new",$("#addEmployeeForm").serialize()).done(function (data) {
                    if(data.state == "success"){
                        $("#addEmployeeModal").modal("hide");
                        layer.msg("添加成功");
                        //刷新dataTable
                        dataTable.ajax.reload();
                    } else {
                        layer.msg(data.message);
                    }
                }).error(function () {
                    layer.msg("服务器异常");
                });
            }
        });

        //删除员工(事件委托)异步加载时刚来到页面是没有删除按钮的
        $(document).delegate(".delEmployee", "click", function () {
            var id = $(this).attr("rel");
            layer.confirm("确定删除么？",function (index) {
                $.get("/employee/"+id+"/delEmployee").done(function (data) {
                    if(data.state == "success") {
                        layer.msg("删除成功");
                        dataTable.ajax.reload();
                    } else {
                        layer.msg(data.message);
                    }
                }).error(function () {
                    layer.msg("服务器异常");
                });
            })
        });



        //添加部门
        $("#addDept").click(function () {
            layer.prompt({title:"请输入部门名称"}, function (value, index) {
                layer.close(index);
                $.post("/employee/dept/new", {"deptName":value}).done(function (data) {
                    if(data.state == "success") {
                        layer.msg("添加成功");
                        //刷新zTree
                        var treeObj = $.fn.zTree.getZTreeObj("ztree");
                        treeObj.reAsyncChildNodes(null,"refresh");
                    } else {
                        layer.msg(data.message);
                    }
                }).error(function () {
                    layer.msg("服务器异常");
                });
            });
        });



        //ztreeSetting
        var setting = {
            view: {
                removeHoverDom: removeHoverDom,
                selectedMulti: false
            },
            edit: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                },
                key : {
                    name : "deptName"
                }
            },
            async : {
                enable : true,
                url : "/employee/dept.json",
                type : "get",
                dataFilter : ajaxDataFilter
            },
            callback:{
                onClick:function(event,treeId,treeNode,clickFlag){
                    //alert(treeNode.id + treeNode.deptName + treeNode.pId);
                    $("#deptId").val(treeNode.id);
                    dataTable.ajax.reload();
                },
                beforeRemove: beforeRemove,
                beforeRename: beforeRename,
                onRemove: zTreeOnRemove,
                onRename: zTreeOnRename
            }
        };
        function ajaxDataFilter(treeId, parentNode, responseData) {
            if(responseData) {
                for(var i = 0;i < responseData.length; i++) {
                    if(responseData[i].id == 1) {
                        responseData[i].open = true;
                        break;
                    }
                }
            }
            return responseData;
        };
        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_"+treeNode.id).unbind().remove();
        };
        /*删除与修改图标出现控制
        function showRemoveBtn(treeId, treeNode) {
            return !treeNode.isFirstNode;
        };
        function showRenameBtn(treeId, treeNode) {
            return treeNode;
        };*/
        function beforeRemove(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("ztree");
            zTree.selectNode(treeNode);
            return confirm("确认删除 节点 -- " + treeNode.deptName + " 吗？");
        }
        function beforeRename(treeId, treeNode, newName) {
            if (newName.length == 0) {
                setTimeout(function() {
                    var zTree = $.fn.zTree.getZTreeObj("ztree");
                    zTree.cancelEditName();
                    alert("节点名称不能为空.");
                }, 0);
                return false;
            }
            return true;
        }

        //删除部门
        function zTreeOnRemove(event, treeId, treeNode) {
            //alert(treeNode.id + ", " + treeNode.deptName);
            var treeObj = null;
            $.get("/employee/dept/"+treeNode.id+"/delDept").done(function (data) {
                if(data.state == "success") {
                    layer.msg("删除成功");
                    treeObj = $.fn.zTree.getZTreeObj("ztree");
                } else {
                    layer.msg(data.message);
                    treeObj = $.fn.zTree.getZTreeObj("ztree");
                }
            }).error(function () {
                layer.msg("服务器异常");
                treeObj = $.fn.zTree.getZTreeObj("ztree");
            });
            //刷新zTree
            treeObj.reAsyncChildNodes(null,"refresh");
        }

        //修改部门名称
        function zTreeOnRename(event, treeId, treeNode, isCancel) {
            var treeObj = null;
            $.get("/employee/dept/"+treeNode.id+"/editDept",{"deptName":treeNode.deptName}).done(function (data) {
                if(data.state == "success") {
                    layer.msg("修改成功");
                    treeObj = $.fn.zTree.getZTreeObj("ztree");
                } else {
                    layer.msg(data.message);
                    treeObj = $.fn.zTree.getZTreeObj("ztree");
                    isCancel = true;
                    return isCancel;
                }
            }).error(function () {
                layer.msg("服务器异常");
                treeObj = $.fn.zTree.getZTreeObj("ztree");
            });
            //刷新zTree
            treeObj.reAsyncChildNodes(null,"refresh");
            dataTable.ajax.reload();
        }


        /*var zNodes =[
         { id:1, pId:0, name:"凯盛软件", open:true},
         { id:11, pId:1, name:"开发部"},
         { id:111111, pId:11, name:"华北开发部"},
         { id:111, pId:1, name:"销售部"},
         { id:112, pId:1, name:"经理办公室"}
         ];*/
        $.fn.zTree.init($("#ztree"), setting);
    });
    </script>
</body>
</html>

