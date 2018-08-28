<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
    
    <%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
	%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>"></base>
		<meta charset="gbk">
		<title>SgExam���߿���ϵͳ|��ʦ��</title>
		<link rel="stylesheet" type="text/css" href="css/teacher_common.css" />
		<link rel="stylesheet" type="text/css" href="css/teacher_main.css" />
	</head>

	<body>
		<div class="topbar-wrap white">
			<div class="topbar-inner clearfix">
				<div class="topbar-logo-wrap clearfix">
					<ul class="navbar-list clearfix">
						<li>
							<a class="on" href="jsp/teacher/index.jsp">SgExam���߿���ϵͳ</a>
						</li>
					</ul>
				</div>
				<div class="top-info-wrap">
					<ul class="top-info-list clearfix">
						<li>
							<a href="#">��ã�${tea_name }</a>
						</li>
						<li>
							<a href="jsp/teacher/modify_password.jsp">�޸�����</a>
						</li>
						<li>
							<a href="QuitServlet?status=teacher">�˳�</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="container clearfix">
			<div class="sidebar-wrap">
				<div class="sidebar-title">
					<h1>�˵�</h1>
				</div>
				<div class="sidebar-content">
					<ul class="sidebar-list">
						<li>
							<ul class="sub-menu">
								<li class="on">
									<a href="jsp/teacher/index.jsp"><i class="icon-font">&#xe012;</i>��������</a>
								</li>
								<li>
									<a href="jsp/teacher/news_list.jsp"><i class="icon-font">&#xe012;</i>��Ϣ����</a>
								</li>
								<li>
									<a href="servlet/ManageQuestion.do?flag=find&type=0&pager=1&manager=teacher"><i class="icon-font">&#xe008;</i>�������</a>
								</li>
								<li>
									<a href="PaperManageServlet?operation=update"><i class="icon-font">&#xe005;</i>���Թ���</a>
								</li>
								<li>
									<a href="Class_List_BrowseServlet?updateclassform=updateClassForm"><i class="icon-font">&#xe005;</i>�༶����</a>
								</li>
								<li>
									<a href="servlet/ScoreCorrectServlet.do?operation=toScoreCorrect"><i class="icon-font">&#xe005;</i>�����Ծ�</a>
								</li>
								<li>
									<a href="CountServlet"><i class="icon-font">&#xe005;</i>�ɼ�ͳ��</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
			<!--/sidebar-->
			<div class="main-wrap">
				<div class="crumb-wrap">
					<div class="crumb-list"><i class="icon-font">&#xe06b;</i><span>��ӭʹ��SgExam���߿���ϵͳ��ʦ��</span></div>
				</div>
				<div class="result-wrap">
					<div class="result-title">
						<h1>��ݲ���</h1>
					</div>
					<div class="result-content">
						<div class="short-wrap">
							<a href="jsp/teacher/news_add.jsp"><i class="icon-font">��</i>������Ϣ</a>
							<a href="jsp/teacher/subject_add.jsp"><i class="icon-font">��</i>�������</a>
							<a href="jsp/teacher/test_add.jsp"><i class="icon-font">��</i>��������</a>
						</div>
					</div>
				</div>
				<div class="result-wrap">
					<div class="result-title">
						<h1>������Ϣ</h1>
					</div>
					<div class="result-content">
						<ul class="sys-info-list">
							<li>
								<label class="res-lab">ְ����</label><span class="res-info">${tea_id }</span>
							</li>
							<li>
								<label class="res-lab">����</label><span class="res-info">${tea_name }</span>
							</li>
							<li>
								<label class="res-lab">Ժϵ</label><span class="res-info">��ѧԺ</span>
							</li>
							<li>
								<label class="res-lab">ְ��</label><span class="res-info">Ժ��</span>
							</li>
							<li>
								<label class="res-lab"></label>
								<a href="jsp/teacher/modify_password.jsp"><input class="btn btn-primary btn6 mr10" value="��������" type="button"></a>
							</li>
						</ul>
					</div>
				</div>

			</div>
			<!--/main-->
		</div>
	</body>

</html>