<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
td{border:1px solid black;}
</style>
</head>
<body>
	<h1>팀 로그</h1>
	<hr>
		<h3>팀 전체 커밋 수:${allCommitCount }</h3>
		
		<hr>
		
		<h3>팀 개인 커밋 수</h3>
		<table>
		<thead>
			<tr>
				<td>메일</td>
				<td>커밋수</td>
			</tr>
		</thead>
			<tbody>
				<c:forEach var="personalList" items="${personalCommitCount }">
					<tr>
						<td>${personalList.name }</td>
						<td>${personalList.commitCount }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<hr>
		
		<h3>팀 개인 추가/삭제 라인 수</h3>
		<table>
		<thead>
			<tr>
				<td>메일</td>
				<td>추가 라인 수</td>
				<td>삭제 라인 수</td>
			</tr>
		</thead>
			<tbody>
				<c:forEach var="lineslist" items="${lineslist }">
					<tr>
						<td>${lineslist.name }</td>
						<td>${lineslist.addLine }</td>
						<td>${lineslist.deleteLine }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<hr>
</body>
</html>