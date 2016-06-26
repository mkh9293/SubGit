<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/includes/header.jsp"%>
<!DOCTYPE html>
<html>
<script src="<c:url value="/resources/js/angular-min.js"/>"></script>
<script>
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
<body>
	<div class="container">
		<form action="${pageContext.request.contextPath}/signup" method="post" enctype="multipart/form-data">
			<div>
				<div class="col-lg-3 col-sm-6 text-center">
					<img class="img-circle img-responsive img-center" id="preview"  src="<c:url value="/resources/img/main/tree.jpg"/>" alt=""
						style="width: 200px; height: 200px"><br />
					<p style="padding-left: 5px;">Team Name</p>
					<div style="color: red" id="vals">ex)협업 관리 시스템</div>
					<br />
					<p style="padding-left: 5px;">Team Introduce</p>
					<textarea name="intro" style="width: 100%; height: 100px;"></textarea>
				</div>
				<div id="signup">

					<div>
						<h3>SVN URL</h3>
						<input type="text" class="url" name="teamURL" value="" placeholder="예)https://swlab.skhu.ac.kr/svn/project2016/201132026" style="width:600px;"/>
					</div>
					<hr />
					<div ng-app="myapp" ng-controller="SignUpController">
						<table>
							<tr>
								<td>Student Number</td>
								<td>Student Name</td>
							</tr>
							<tr>
								<td><input type="text" name="leaderNum" value=""
									placeholder="팀장 학번" /></td>
								<td><input type="text" name="gitName" value=""
									placeholder="팀장 이름" /></td>
								<td style="text-align: left;"><img
									src="<c:url value="/resources/img/signup/add.png"/>" alt="add"
									class="add" style="width: 30px; cursor: pointer"
									ng-click="addPerson()" /></td>
							</tr>

							<tr ng-repeat="person in people">
								<td><input type="text" name="stNum" ng-model="person.stNum"
									placeholder="팀원 학번" /></td>
								<td><input type="text" name="stName"
									ng-model="person.stName" placeholder="팀원 이름" /></td>
								<td style="text-align: left;"><img
									src="<c:url value="/resources/img/signup/remove.jpg"/>"
									alt="remove" class="remove"
									style="width: 30px; cursor: pointer"
									ng-click="removePerson($index)" /></td>
							</tr>
						</table>
					</div>
					<div>
						<p style="padding-left: 5px;">Team Name</p>
						<input type="text" name="teamName" class="teamName"
							placeHolder="팀 이름">
					</div>
					<div>
						<p style="padding-left: 5px;">Password</p>
						<input type="password" name="password" placeholder="팀장 svn 비밀번호"/> 
						<input type="hidden" name="section" value="svn" />
					</div>
					<div>
						<p style="padding-left: 5px;">파일: </p>
						<input type="file" id="getfile" name="getfile" accept="image/*">
					</div>
					<br />

					<button type="submit" class="btn btn-primary" name="signup">SignUp</button>

				</div>

				<hr />
				<!-- Footer -->
				<%@include file="/WEB-INF/includes/footer.jsp"%>
				<!-- End Footer -->
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
	var app = angular.module('myapp', []);

	app.controller('SignUpController', function($scope) {
		$scope.people = [ {
			stNum : '',
			stName : ''
		} ];

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