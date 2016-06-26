<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %> 

<%@include file="/WEB-INF/includes/header.jsp" %>
<!DOCTYPE html>
<html>
<body>
<div class="container">
	<img src="<c:url value="/resources/img/main/tree.jpg"/>" alt="tree" style="width:90%;height:40%;">
	<h3>프로젝트 평가 시스템</h3>
	<p><b>SubGit</b>은 형상관리 시스템을 쉽게 보여지도록 도우며  프로젝트 평가를 수월하고 확실하게 평가 할 수 있도록 돕습니다</p>
	
	<h3>팀원리스트</h3>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>깃이름</th>
				<th>학번</th>
				<th>팀번호</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="userList" items="${userList }">
				<tr>
					<td>${userList.stName}</td>
					<td>${userList.stNum}</td>
					<td>${userList.teamNum}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<h3>팀리스트</h3>
	<table class="table table-bordered">
			<thead>
				<tr>
					<th>팀번호</th>
					<th>팀이름</th>
					<th>teamURL</th>
					<th>팀소개</th>
					<th>팀장번호</th>
					<th>비밀번호</th>
					<th>방식구분</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="teamList" items="${teamList }">
					<tr>
						<td>${teamList.idx }</td>
						<td>${teamList.teamName }</td>
						<td>${teamList.teamURL}</td>
						<td>${teamList.intro }</td>
						<td>${teamList.leaderNum_fk}</td>
						<td>${teamList.password}</td>
						<td>${teamList.section}</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	
	<h3>리더리스트</h3>
	<table class="table table-bordered">
			<thead>
				<tr>
					<th>번호</th>
					<th>팀장학번</th>
					<th>깃네임</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="leaderList" items="${leaderList }">
					<tr>
						<td>${leaderList.idx}</td>
						<td>${leaderList.leaderNum}</td>
						<td>${leaderList.gitName}</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	<hr/>
	<!-- Footer -->
			<%@include file ="/WEB-INF/includes/footer.jsp" %>	
	<!-- End Footer -->
</div>
</body>
</html> 