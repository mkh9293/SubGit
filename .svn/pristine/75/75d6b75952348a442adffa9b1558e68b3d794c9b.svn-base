<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file ="/WEB-INF/includes/header.jsp" %>
<%@ page import="subgit.util.*" %>
<!DOCTYPE html>
<html>
<body>

<!-- Page Content -->
	<div class="container">

		<!-- Introduction Row -->
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">팀 목록</h1>
			</div>
		</div>
		
		<!-- Team Members Row -->
		<div class="row">
			<c:forEach var="teamList" items="${teamList }" varStatus="i">
			<div class="col-lg-4 col-sm-6 text-center">
				<c:url var="imageUrl" value="downloadImage?id=${i.index }"/>
				<img class="img-circle img-responsive img-center" alt="image" src="${imageUrl }" style="width: 200px; height: 200px" onerror="this.src='${pageContext.request.contextPath}/resources/img/main/logo2.jpg';">
				<h3>
					<a href="${teamList.section }/teampage/teamEnter?teamURL=${teamList.teamURL }&leaderNum_fk=${teamList.leaderNum_fk}" target="_blank">${teamList.teamName }</a>
				</h3>
				<p>${teamList.intro }</p>
			</div>
			</c:forEach>
		</div>
		
		<%-- <!-- db에서 가져온 팀 목록 시작 -->
		<c:forEach var="team" items="${teamList }">
			<div class="col-lg-4 col-sm-6 text-center" style="display:inline-block">
				<img class="img-circle img-responsive img-center" src="<c:url value="/resources/img/main/logo2.jpg"/>"
					alt="" style="width: 200px; height: 200px">
				<h3>
					<a href="teampage/teamEnter?gitURL=${team.gitURL }&leaderNum_fk=${team.leaderNum_fk}" target="_blank">${team.idx }</a>
				</h3>
				<p>팀 설명 작성</p>
			</div>
		</c:forEach> 
		<!-- db에서 가져온 팀 목록 끝 -->--%>
		
	<%-- 	<!-- Team Members Row -->
		<div class="row">
			<div class="col-lg-4 col-sm-6 text-center">
				<img class="img-circle img-responsive img-center" src="<c:url value="/resources/img/main/logo2.jpg"/>"
					alt="" style="width: 200px; height: 200px">
				<h3>
					<a href="teampage/">SUBGIT</a>
				</h3>
				<p>프로젝트 평가 시스템</p>
			</div>
			<div class="col-lg-4 col-sm-6 text-center">
				<img class="img-circle img-responsive img-center" src="<c:url value="/resources/img/main/hub.png"/>"
					alt="" style="width: 200px; height: 200px">
				<h3>
					<a href="teampage/">SLANG</a>
				</h3>
				<p>교내 협업 시스템</p>
			</div>
			<div class="col-lg-4 col-sm-6 text-center">
				<img class="img-circle img-responsive img-center" src="<c:url value="/resources/img/main/sky.jpg"/>"
					alt="" style="width: 200px; height: 200px">
				<h3>
					<a href="teampage/">MTOM</a>
				</h3>
				<p>상담 관리 시스템</p>
			</div>
		</div> --%>

		<hr/>
		<!-- Footer -->
			<%@include file ="/WEB-INF/includes/footer.jsp" %>	
		<!-- End Footer -->
	</div>
	<!-- /.container -->

</body>
</html>