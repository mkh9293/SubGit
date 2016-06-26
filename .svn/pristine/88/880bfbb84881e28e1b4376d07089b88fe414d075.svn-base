<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@include file="/WEB-INF/includes/src.jsp" %>
<title>SUBGIT</title>
<!-- Navigation -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath}/">SubGit</a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="${pageContext.request.contextPath}/about">About</a></li>
					
					<sec:authorize access="not authenticated">
						<li><a href="${pageContext.request.contextPath}/signup">SignUp</a></li>
					</sec:authorize>
					<sec:authorize access="authenticated">
						<li><a href="${pageContext.request.contextPath}/myTeam" target="_blank">teamPage</a></li>
					</sec:authorize>
					
					<sec:authorize access="not authenticated">
						<li><a href="${pageContext.request.contextPath}/login">LogIn</a></li>
					</sec:authorize>
					<sec:authorize access="authenticated">
						<li><a href="${pageContext.request.contextPath}/logout">LogOut</a></li>
					</sec:authorize>
					
					<li><a href="${pageContext.request.contextPath}/help">Help</a></li>
					<li><a href="codeDiff/main">diffTest</a></li>
					<sec:authorize access="authenticated">
						<li><a href="${pageContext.request.contextPath}/edit">Edit</a></li>
					</sec:authorize>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container -->
	</nav>
	<br/><br/>