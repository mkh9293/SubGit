<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>

<style>
.full button span {
	background-color: limegreen;
	border-radius: 32px;
	color: black;
}

.partially button span {
	background-color: orange;
	border-radius: 32px;
	color: black;
}
</style>
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular.js"></script>
<link
	href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css"
	rel="stylesheet">
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/css/bootstrap.min.css"
	integrity="sha384-y3tfxAZXuh4HwSYylfB+J125MxIs6mR5FOHamPBG064zB+AFeWH94NdvaCBm8qnd"
	crossorigin="anonymous">
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>	
<script type="text/javascript">
	$(document).ready(function() {
		$(".teamName").keyup(function() {
			$("#vals").html($(this).val());
		});
		
		var file = document.querySelector('#getfile');
		file.onchange = function(){
			var fileList = file.files;
			
			var reader = new FileReader();
		    reader.readAsDataURL(fileList[0]);

		    //로드 한 후
		    reader.onload = function  () {
		        document.querySelector('#preview').src = reader.result ;
		    }; 
		};	
	});
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
						<h3 class="page-header">팀 정보 수정</h3>
					</div>
				</div>
				<div class="container-fluid">
					<form action="${pageContext.request.contextPath}/svn/teampage/edit" method="post" enctype="multipart/form-data">
						<div>
							<div class="col-lg-3 col-sm-6 text-center">
								<c:url var="imageUrl" value="downloadImage"/>
								<img class="img-circle img-responsive img-center" alt="image" id="preview" src="${imageUrl }" style="width: 200px; height: 200px"><br/>
								<p style="padding-left: 5px;">Team Name</p>
								<div style="color: red" id="vals">${teamInfo.teamName }</div>
								<br />
								<p style="padding-left: 5px;">Team Introduce</p>
								<textarea name="intro" style="width: 100%; height: 100px;">${teamInfo.intro }</textarea>
							</div>
							<div id="edit">

								<div>
									<h3>SVN URL</h3>
									<input type="text" class="url" name="teamURL" value="${teamInfo.teamURL }" style="width: 600px;"  />
								</div>
								<hr />
								<div ng-app="myapp" ng-controller="EditController">
									<table>
										<tr>
											<td>Student Number</td>
											<td>Student Name</td>
										</tr>
										<tr>
											<td><input type="text" name="leaderNum" value="${leader.leaderNum }" placeholder="팀장 학번" /></td>
											<td><input type="text" name="gitName" value="${leader.gitName }" placeholder="팀장 이름" /></td>
											<td>팀장</td>
										</tr>
										
										<tr ng-repeat="person in people">
											<td><input type="text" name="stNum" ng-model="person.stNum" placeholder="팀원 학번" /></td>
											<td><input type="text" name="stName" ng-model="person.stName" placeholder="팀원 이름" /></td>
											<td><input type="hidden" name="p_idx" ng-model="person.idx" ng-value="person.idx" /></td>
										</tr>
									</table>
								</div>
								<div>
									<p style="padding-left: 5px;">Team Name</p>
									<input type="text" name="teamName" class="teamName" value="${teamInfo.teamName }">
								</div>
								<div>
									<p style="padding-left: 5px;">Password</p>
									<input type="password" name="password" placeholder="팀장 svn 비밀번호" /> 
								</div>
								<div>
									<p style="padding-left: 5px;">파일</p>
									<input type="file" id="getfile" name="getfile" accept="image/*" value="${teamInfo.teamImage }"/>
								</div>
								<div>
								<button type="submit" class="btn btn-primary" name="edit">Edit</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var app = angular.module('myapp', []);
	app.controller('EditController', function($scope, $http) {
		
		$scope.people = [ {
			idx :'',
			stNum : '',
			stName : '',
			teamNum:''
		} ];
		
		$http({
	        method : "GET",
	        url : "./edit.json"
	    }).then(function mySucces(response) {
	    	$scope.people = response.data;
	    }, function myError(response) {
	    	alert(error);
	    });
		
		//edit페이지에서 팀원을 추가 삭제가 가능 하려면?? 
		$scope.addPerson = function() {
			var newPerson = $scope.people.length + 1;
			$scope.people.push({});
		};

		$scope.removePerson = function(i) {
			$scope.people.splice(i, 1);
		};
	});
</script>
</html>