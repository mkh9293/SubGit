<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<title>Insert title here</title>
<script>
$(function(){
	$("tr[data-url]").click(function() {
        location.href = $(this).attr("data-url");
    });
});
</script>
</head>
<body>
	<h2>Commit List</h2>

	<table style="padding:5px;">
		<thead>
			<tr>
				<th>commitId</th>
				<th>commitDate</th>
				<th>email</th>
				<th>name</th>
				<th>commitMessage</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="commit" items="${commitInfo }">
				<tr data-url="commitCheck?commitId=${commit.commitId }">
					<td>${commit.commitId }</td>
					<td>${commit.commitDate }</td>
					<td>${commit.email }</td>
					<td>${commit.name }</td>
					<td>${commit.commitMessage }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>