<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
    <%@ page import="java.util.*"%>    
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>SgExam���߿���ϵͳ|ѧ����ҳ</title>
		<link href="css/common.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script src="js/js.js" type="text/javascript"></script>
	</head>

	<body>
		<div class="wrap psRe">
			<jsp:include page="../top.jsp"></jsp:include>
			<div class="wrap_main span8 psAb">
				<ul class="wrap_main_ul">
					<div class="lf griditem box1" gridw="1" gridh="1" >
						<span class="main_nav psAb cupo">�ҵĿ���</span>
						<ul class="psAb main_ul">
							<h5 class="lh35 clear"><a class="lf main_a fb" href="javascript:void(0)">�ҵĿ���</a></h5>
							<p class="bk"></p>
							<fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM-dd HH:mm" var="today"/>
							<c:if test="${! empty papers }">
								<c:forEach var="paper" items="${papers.getDateList()}" varStatus="status">
								<fmt:parseDate value="${paper.endTime}:00" var="testday" type="both"/>
								<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${testday}" var="testDay"/>
								<c:if test="${today<=testDay}">
									<li class="lf wid3  main_pic" style="width:200px">
										<a class="main_rc_a disin" href="servlet/StudentServlet.do?operation=toMyTest"><img src="images/exam.png" /><span class="fontsize">${paper.p_name}</span><br>���Կ�ʼʱ�䣺<br>${paper.startTime}</a>
									</li>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${empty papers}">
								<li class="lf wid3  main_pic">
									<a class="main_rc_a disin" href="#"><img src="images/no.png" /><span class="fontsize">���޿���</span></a>
								</li>
							</c:if>
						</ul>
						<p class="blue_bg psAb"></p>
					</div>
					<div class="lf griditem zoom" gridw="4" gridh="2">
						<span class="main_nav psAb cupo">��������</span>
						<ul class="psAb main_ul">
							<h5 class="lh35 clear"><a class="lf main_a fb" href="#">��������</a></h5>
							<p>ͬѧ�ǣ�����п��ԣ��������ߵ��ҵĿ��ԣ��μӿ���Ŷ~</p>
						</ul>
						<p class="blue_bg psAb"></p>
					</div>
					<div class="lf griditem box1" gridw="1" gridh="1">
						<span class="main_nav psAb cupo">�ҵĳɼ�</span> 
						<ul class="psAb main_ul">
							<h5 class="lh35 clear"><a class="lf main_a fb" href="#">�ҵĳɼ�</a><a class="rt more" href="servlet/StudentServlet.do?operation=showScore">����</a></h5>
							<p class="bk5"></p>
							<div class="wrap_cont">
								<table class="myscoretable">
									<thead>
										<tr>
											<th width="3%">��������</th>
											<th width="3%">����ʱ��</th>
											<th width="2%">�÷�</th>
											<th width="5%">�ɼ�״̬</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="sco" items="${scores}" varStatus="status">
											<tr>
												<td>${sco.p_name}</td>
												<td>${fn:split(sco.sc_date, " ")[0]}</td>
												<td>${sco.ifdone=='0'?'(�͹���)':'' }${sco.sc_score}</td>
												<td>${sco.ifdone=='0'?'δ�������':'�������' }</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</ul>
						<p class="blue_bg psAb"></p>
					</div>
					<div class="lf griditem box1" gridw="1" gridh="1">
						<span class="main_nav psAb cupo">��Ϣ����</span>
						<ul class="psAb main_ul">
							<h5 class="lh35 clear"><a class="lf main_a fb" href="#">��Ϣ����</a><a class="rt more" href="jsp/student/student_news.jsp">����</a></h5>
							<p class="bk5"></p>
							<div class="wrap_cont">
								<c:forEach var="ann" items="${anns}" varStatus="status">
									<h6 class="fn"><a class="clear" href="#" title="${ann.a_title}"><span class="main_ul_span lf">${ann.a_content}</span></a></h6>
								</c:forEach>
							</div>
						</ul>
						<p class="blue_bg psAb"></p>
					</div>
					<div class="lf griditem box1" gridw="1" gridh="1">
						<span class="main_nav psAb cupo">������Ϣ</span>
						<ul class="psAb main_ul">
							<h5 class="lh35 clear">
								<a class="lf main_a fb" href="#">������Ϣ</a>
           					</h5>
							<p class="bk10"></p>
							<div class="wrap_cont">
								<div class="wid2 lf">
									<h3 class="clear fn">
										<img class="lf padr10" src="images/clownf.png" height="80"/>
            								<a class="cor1 f14 lh20">����:${stu_name}</a>
           								<br/>
            				<p class="cor3 f12 fn lh20 marr5">ѧ��:${stu_id}</p>			
						            <p class="cor3 f12 fn lh20 marr5">�༶:${cls_name}</p>
						            </h3>
								</div>
								<div class="wid2 lf">
									<a href="jsp/student/student_modify_password.jsp">�޸�����</a>
									<br /> 
									<a href="jsp/student/login.jsp">�˳�</a>
								</div>
								<p class="bk5"></p>
							</div>
						</ul>
						<p class="blue_bg psAb"></p>
					</div>
				</ul>
			</div>
			<!--/main-->
			<div class="wrap_bg psRe" id="box">
				<ul class="psAb">
					<li><img src="images/index_background_student_img.png" /></li>
				</ul>
				<div class="grd_bg psAb"></div>
			</div>
			<div id="footer" class="psAb">
				<p class="padt10 textc white lh20">
					<a class="disin padlr10" href="#"> </a>
				</p>
				<p class="padt5 textc white lh20">��Ȩ����&nbsp;&copy;&nbsp;SgExam���߿���ϵͳ&nbsp;&nbsp;��ICP��&nbsp;00000001��&nbsp;�湫������000000000001</p>
				<div class="foot_bg psAb"></div>
			</div>
		</div>
		<!--/wrap-->
	</body>

</html>