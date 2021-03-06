<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script>
 $(document).ready(function() {
		$(".teamName").keyup(function() {
			$("#vals").html($(this).val());
		});

		var file = document.querySelector('#getfile');
		file.onchange = function() {
			var fileList = file.files;

			var reader = new FileReader();
			reader.readAsDataURL(fileList[0]);

			//로드 한 후
			reader.onload = function() {
				document.querySelector('#preview').src = reader.result;
			};
		};
	}); 
</script>
</head>
<body>
	<div class="container">
		<form action="${pageContext.request.contextPath}/edit" method="post" enctype="multipart/form-data">
				<div style="height:600px;">
				<div>
					<div class="col-lg-3 col-sm-7 text-center">
						<c:url var="imageUrl" value="downloadTeamImage" />
						<img class="img-circle img-responsive img-center" alt="image"
							id="preview" src="${imageUrl }"
							style="width: 200px; height: 200px"><br />
						<p style="padding-left: 5px;">Team Name</p>
						<div style="color: red" id="vals">${teamInfo.teamName }</div>
						<br />
						<p style="padding-left: 5px;">Team Introduce</p>
						<textarea name="intro" style="width: 100%; height: 100px;">${teamInfo.intro }</textarea>
					</div>
				</div>
				<div class="col-lg-8 col-sm-7" id="edit">
					<div>
						<h3>SVN URL</h3>
						<input type="text" class="url" name="teamURL"
							value="${teamInfo.teamURL }" style="width: 600px;" />
					</div>
					<hr />
					<div>
						<table>
							<tr>
								<td>Student Number</td>
								<td>Student Name</td>
							</tr>
							<tr>
								<td><input type="text" name="leaderNum"
									value="${leader.leaderNum }" placeholder="팀장 학번" /></td>
								<td><input type="text" name="gitName"
									value="${leader.gitName }" placeholder="팀장 이름" /></td>
								<td><i class="glyphicon glyphicon-user" style="color:#4641D9"></i></td>
							</tr>
							<c:forEach var="person" items="${personList }" varStatus="i">
								<tr>
									<td><input type="text" name="stNum" value="${person.stNum }"/></td>
									<td><input type="text" name="stName" value="${person.stName }"/></td>
									<td><input type="hidden" name="p_idx" value="${person.idx }"/></td>
								</tr>
							</c:forEach>
							<%-- 
							<tr ng-repeat="person in people">
								<td><input type="text" name="stNum" ng-model="person.stNum"
									placeholder="팀원 학번" /></td>
								<td><input type="text" name="stName"
									ng-model="person.stName" placeholder="팀원 이름" /></td>
								<td><input type="hidden" name="p_idx" ng-model="person.idx"
									ng-value="person.idx" /></td>
							</tr>--%>
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
					<br/>
					<div>
						<p style="padding-left: 5px;">파일</p>
						<input type="file" id="getfile" name="getfile" accept="image/*" value="${teamInfo.teamImage }" />
					</div><br/>
					<div>
						<button type="submit" class="btn btn-primary" name="edit" >Edit</button>
						<a type="button" class="btn btn-danger" href="delete">Delete</a>
					</div><br/>
				
				</div>
				
				</div>
				<hr />
				<!-- Footer -->
				<%@include file="/WEB-INF/includes/footer.jsp"%>
				<!-- End Footer -->
		</form>
	</div>
</body>
<!-- <script type="text/javascript">
 	var app = angular.module('myapp', []);
	app.controller('EditController', function($scope, $http) {

		$scope.people = [ {
			idx : '',
			stNum : '',
			stName : '',
			teamNum : ''
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
</script> -->
</html>