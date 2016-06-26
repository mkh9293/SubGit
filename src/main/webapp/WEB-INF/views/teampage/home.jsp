<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<!-- home JvavScript,CSS -->
<!-- home JvavScript -->
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular.js"></script>
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
<script src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
<script src="http://cdn.oesmith.co.uk/morris-0.4.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-morris-chart/1.2.0/angular-morris-chart.min.js"></script>
<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
	<link href="//netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/css/bootstrap.min.css" integrity="sha384-y3tfxAZXuh4HwSYylfB+J125MxIs6mR5FOHamPBG064zB+AFeWH94NdvaCBm8qnd" crossorigin="anonymous">
	
</head>
<body>
		<!--내용  -->
		<div id="wrapper">
		<%@include file="/WEB-INF/includes/teamHeader.jsp"%>
		
		<div id="page-wrapper">
			<h3>
				<span>전체 커밋 수 :</span> <span>${count }</span>
			</h3>

			<!-- Page Heading -->
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">
						${teamInfo.teamName } <small>${teamInfo.intro }</small>
					</h1>
				</div>
			</div>
			<div class="container-fluid">
				<div class="row">
					<c:forEach var="plist" items="${p_list }" varStatus="i">
						<c:choose>
							<c:when test="${i.index==3 }">
								<div class="col-lg-3 col-md-6">
									<div class="panel panel-red">
										<div class="panel-heading">
											<div class="row">
												<div class="col-xs-5">
													<span>${plist.stName }</span>
													<p>(${plist.stNum })</p>
												</div>
												<div class="col-xs-7 text-right">
													<div class="huge">${p_count[plist.stNum] }</div>
													<div>Commits</div>
												</div>
											</div>
										</div>
										<!-- 개인 기여도 화면으로 이동-->
										<a href="member?st=${plist.stNum }">
											<div class="panel-footer">
												<span class="pull-left">View Details</span> <span
													class="pull-right"><i
													class="fa fa-arrow-circle-right"></i></span>
												<div class="clearfix"></div>
											</div>
										</a>
									</div>
								</div>
							</c:when>
							<c:when test="${i.index==2 }">
								<div class="col-lg-3 col-md-6">
									<div class="panel panel-yellow">
										<div class="panel-heading">
											<div class="row">
												<div class="col-xs-5">
													<span>${plist.stName }</span>
													<p>(${plist.stNum })</p>
												</div>
												<div class="col-xs-7 text-right">
													<div class="huge">${p_count[plist.stNum] }</div>
													<div>Commits</div>
												</div>
											</div>
										</div>
										<!-- 개인 기여도 화면으로 이동-->
										<a href="member?st=${plist.stNum }">
											<div class="panel-footer">
												<span class="pull-left">View Details</span> <span
													class="pull-right"><i
													class="fa fa-arrow-circle-right"></i></span>
												<div class="clearfix"></div>
											</div>
										</a>
									</div>
								</div>
							</c:when>
							<c:when test="${i.index==1 }">
								<div class="col-lg-3 col-md-6">
									<div class="panel panel-green">
										<div class="panel-heading">
											<div class="row">
												<div class="col-xs-5">
													<span>${plist.stName }</span>
													<p>(${plist.stNum })</p>
												</div>
												<div class="col-xs-7 text-right">
													<div class="huge">${p_count[plist.stNum] }</div>
													<div>Commits</div>
												</div>
											</div>
										</div>
										<!-- 개인 기여도 화면으로 이동-->
										<a href="member?st=${plist.stNum }">
											<div class="panel-footer">
												<span class="pull-left">View Details</span> <span
													class="pull-right"><i
													class="fa fa-arrow-circle-right"></i></span>
												<div class="clearfix"></div>
											</div>
										</a>
									</div>
								</div>
							</c:when>
							<c:when test="${i.index==0 }">
								<div class="col-lg-3 col-md-6">
									<div class="panel panel-primary">
										<div class="panel-heading">
											<div class="row">
												<div class="col-xs-5">
													<span>${plist.stName }</span>
													<p>(${plist.stNum })</p>
												</div>
												<div class="col-xs-7 text-right">
													<div class="huge">${p_count[plist.stNum] }</div>
													<div>Commits</div>
												</div>
											</div>
										</div>
										<!-- 개인 기여도 화면으로 이동-->
										<a href="member?st=${plist.stNum }">
											<div class="panel-footer">
												<span class="pull-left">View Details</span> <span
													class="pull-right"><i
													class="fa fa-arrow-circle-right"></i></span>
												<div class="clearfix"></div>
											</div>
										</a>
									</div>
								</div>
							</c:when>
						</c:choose>
					</c:forEach>
				</div>

				<!-- 그래프 -->
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-bar-chart-o fa-fw"></i> 기여도
								</h3>
							</div>
							<!--나중에 그래프 넣을 것(팀원을 색으로 구분) -->
							<div class="panel-body">
								<div id="donut-example"></div>
							</div>
						</div>
					</div>
				</div>
				<!-- /.row -->
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var fk = "<c:out value="${leaderNum_fk}"/>";
var url = "<c:out value="${teamURL}"/>";
var leaderNum = "<c:out value="${leaderNum}"/>";
var teamData = {"idx":fk,"teamURL":url,"leaderNum_fk":fk};

 $.ajax({
	type: "GET",
	url: "./home.json",
	data: teamData,
	dataType : 'json',
	success : function(datas) {
		$.getScript('http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js',function(){
		$.getScript('http://cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.0/morris.min.js',function(){
		
	    var myData = datas;
	   // var finalData = JSON.stringify(jsons);
	    
		Morris.Donut({
	   	 	element: 'donut-example',
	   	 	data: myData,
	   	 	colors: ['#4374D9','#2F9D27','#FFBB00','#F15F5F']
	   	 	
		});
		})
		})
	}
}); 
</script>
</html>