<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<!-- syntaxHighlighter.js -->
<script src="<c:url value="/resources/syntaxHighlighter/js/shCore.js"/>"></script>
<script src="<c:url value="/resources/syntaxHighlighter/js/shBrushJava.js"/>"></script>
<script src="<c:url value="/resources/syntaxHighlighter/js/shBrushXml.js"/>"></script>


<!-- syntaxHighlighter.css -->
<link href="<c:url value="/resources/syntaxHighlighter/css/shCore.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/syntaxHighlighter/css/shThemeDefault.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/syntaxHighlighter/css/shThemeEclipse.css"/>" rel="stylesheet" type="text/css">

<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
<link href="//netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/css/bootstrap.min.css" integrity="sha384-y3tfxAZXuh4HwSYylfB+J125MxIs6mR5FOHamPBG064zB+AFeWH94NdvaCBm8qnd" crossorigin="anonymous">
<script type="text/javascript">
	SyntaxHighlighter.all();
</script>	
</head>
<body>
	<div id="wrapper">
		<%@include file="/WEB-INF/includes/teamHeader.jsp"%>

		<div id="page-wrapper">

			<div class="container-fluid">
				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header"><span>파일명: </span><span>${path }</span></h3>
					</div>
				</div>
				<div class="container-fluid">
					<pre class="brush:${fileExtension }"><c:out value="${str}"/></pre>	
				</div>
			</div>
		</div>
	</div>
</body>
</html>