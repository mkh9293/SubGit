<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link
	href="http://maxcdn.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<style>
thead tr {
	background: #eee;
}

tbody tr:hover {
	background-color: #ffa;
	cursor: pointer;
}
</style>
</head>

<body>
	<div class="container">

		<h1>git목록</h1>
		<hr />

		<table class="table table-bordered">
			<tbody>
				<c:forEach var="fileList" items="${fileInfo }">
					<tr>
						<td><a href="/mkh9293/SLANG?dir=${fileList }">${fileList }</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>