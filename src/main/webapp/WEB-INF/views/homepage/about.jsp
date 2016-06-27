<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<%@include file="/WEB-INF/includes/header.jsp"%>
<!DOCTYPE html>
<html>
<style type="text/css">
.aboutMain {
	height: 600px;
}
</style>
<body>
	<div class="container">
		<div class="aboutMain">
			<img src="<c:url value="/resources/img/main/tree.jpg"/>" alt="tree"
				style="width: 90%; height: 400px;">
			<h3>프로젝트 평가 시스템</h3>
			<p><b>SubGit</b>은 형상관리 시스템을 쉽게 보여지도록 도우며 프로젝트 평가를 수월하고 확실하게 평가 할 수 있도록돕습니다</p>
		</div>

		<hr />
		<!-- Footer -->
		<%@include file="/WEB-INF/includes/footer.jsp"%>
		<!-- End Footer -->
	</div>
</body>
</html>
