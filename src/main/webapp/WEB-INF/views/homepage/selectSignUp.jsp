<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/includes/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
	.select{
		height:600px;
	}
	.selectimg{
		border:1px solid gray;
		width:500px;
		height:400px;
		margin-top:10%;
	}
</style>
</head>
<body>
<div class="container">

	<div class="select">
		<div class="row">
			<div class="col-xs-6">
				<a href="svn/signup"><img src="<c:url value="/resources/img/signup/SVNImage.jpg"/>" onclick=""alt="SVN" class="selectimg"/></a>
			</div>
			<div class="col-xs-6">
				<a href="git/signup"><img src="<c:url value="/resources/img/signup/gitImage.png"/>" onclick=""alt="GIT" class="selectimg"/></a>
			</div>
		</div>
	</div>
	<hr />
	
	<!-- Footer -->
	<%@include file="/WEB-INF/includes/footer.jsp"%>
	<!-- End Footer -->
</div>
</body>
</html>