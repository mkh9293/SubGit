<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/css/bootstrap.min.css"
	integrity="sha384-y3tfxAZXuh4HwSYylfB+J125MxIs6mR5FOHamPBG064zB+AFeWH94NdvaCBm8qnd"
	crossorigin="anonymous">
	<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
	<link href="//netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/css/bootstrap.min.css" integrity="sha384-y3tfxAZXuh4HwSYylfB+J125MxIs6mR5FOHamPBG064zB+AFeWH94NdvaCBm8qnd" crossorigin="anonymous">
	
</head>
<body>
	<div id="wrapper">
		<%@include file="/WEB-INF/includes/gitTeamHeader.jsp"%>

		<div id="page-wrapper">

			<div class="container-fluid">
				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header"><span>팀명: </span><span>${teamName }</span></h3>
					</div>
				</div>
				<div class="container-fluid">
					<table class="table">
						<c:forEach var="file" items="${fileInfo}">
							<tbody>
								<tr>
									<td>
								 	<c:choose>
										<c:when test="${file.kind eq 'dir'}">
											<img src="<c:url value="/resources/img/team/dir.png"/>" style="width:30px; height:25px;"alt="dir" class="dirImg"/>
										</c:when>
										<c:when test="${file.kind eq 'file'}">
											<img src="<c:url value="/resources/img/team/file.png"/>" style="width:30px; height:25px;"alt="file" class="fileImg"/>
										</c:when>
									</c:choose>
									<span><a href="${pageContext.request.contextPath}/git/teampage/fileBrowse2/${uri }/${file.depth}/${file.path}?st=${plist.stName }&teamURL=${gitURL }&leaderNum_fk=${leaderNum_fk}&teamName=${teamName}&intro=${intro }&kind=${file.kind}">${file.fileName}</a></span>
									</td> 
								</tr>
							</tbody>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>