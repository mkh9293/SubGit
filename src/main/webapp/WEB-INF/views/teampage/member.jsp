<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular-animate.js"></script>
    <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.3.2.js"></script>
    <link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
	<link href="//netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/css/bootstrap.min.css" integrity="sha384-y3tfxAZXuh4HwSYylfB+J125MxIs6mR5FOHamPBG064zB+AFeWH94NdvaCBm8qnd" crossorigin="anonymous">
	
</head>

<body ng-app="calendar">
	<div id="wrapper">
		<%@include file="/WEB-INF/includes/teamHeader.jsp"%>
		
		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header" id="author" data-name="${author }">${p_name }(${author }) </h3>
					</div>
				</div>
				<div class="container-fluid">
						<h4>기간 설정</h4>
						<form action="" method="POST">
						<div class="row">
							<div class="col-md-5" ng-controller="StartCtrl">
								<p class="input-group">
									<input id="startInput" data-startDate="${startDate }" name="startDate" placeholder="startDate" type="text" class="form-control" ng-model="startDate"
										uib-datepicker-popup is-open="popup1.opened" datepicker-options="dateOptions"
										ng-required="true" close-text="Close"/> <span
										class="input-group-btn">
										<button type="button" class="btn btn-default" ng-click="date()">
											<i class="glyphicon glyphicon-calendar"></i>
										</button>
									</span>
								</p>
							</div>
							<div class="col-md-5" ng-controller="EndCtrl">
								<p class="input-group">
									<input id="endInput" placeholder="endDate" data-endDate="${endDate }" type="text" class="form-control" name="endDate" 
									uib-datepicker-popup ng-model="endDate" is-open="popup1.opened"
										datepicker-options="dateOptions" ng-required="true"
										close-text="Close" /> <span class="input-group-btn" >
										<button type="submit" class="btn btn-default" ng-click="date()">
											<i class="glyphicon glyphicon-calendar"></i>
										</button>
									</span>
								</p>
							</div>
							<button type="submit" class="btn btn-default">검색</button>
						</div>
						</form>
						<div>
							<h3>그래프</h3>
							<!--막대 그래프 -->
							<div id="bar-example"></div><button onclick="getChartByDate();">차트</button>
						</div>
						<div class="panel-group" id="accordion">
						
							<c:forEach var="plog" items="${p_log}" varStatus="i">
  								<div class="panel panel-default">
    								<div class="panel-heading">
     									 <h4 class="panel-title">
      										  <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse${i.index }">
										        <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${plog.date}" />
										      	<span class="badge">${p_endtryPath_size[i.index] }</span>
										      	<div style="width:400px; display:inline-block; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">${plog.message }</div>
										      </a>
										 </h4>
									</div>
								    <div id="collapse${i.index }" class="panel-collapse collapse">
								      <div class="panel-body">
								      <table class="table table-hover entryTable">
								        <c:forEach var="pendtryPath" items="${p_endtryPath}">
										 	<c:if test="${pendtryPath.key eq i.index}">
										 	<c:forEach var="list" items="${pendtryPath.value}">
												<tr>
													<td>
													<c:choose>
														<c:when test="${list.type eq 'A'.charAt(0)}">
															<img src="<c:url value="/resources/img/team/add.png"/>" style="width:30px; height:35px;"alt="add" class="addImg"/>
														</c:when>
														<c:when test="${list.type eq 'M'.charAt(0)}">
															<img src="<c:url value="/resources/img/team/modify.png"/>" style="width:30px; height:35px;"alt="modify" class="modifyImg"/>
														</c:when>
														<c:when test="${list.type eq 'D'.charAt(0)}">
															<img src="<c:url value="/resources/img/team/delete.png"/>" style="width:30px; height:35px;"alt="delete" class="deleteImg"/>
														</c:when>
													</c:choose>
													</td>
													<td>${list.path }</td>
												</tr>
											</c:forEach>
											</c:if>
										</c:forEach>
									</table>
								    </div>
								    </div>
 								 </div>
							</c:forEach>
							</div>
					</div>
				</div>
			</div>
		</div>
</body>

<script type="text/javascript">
/* function getChartByDate(){
	var author = document.getElementById("author").getAttribute("data-name");
	var startDate1 = document.getElementById("startInput").getAttribute("data-startDate");
	var endDate1 = document.getElementById("endInput").getAttribute("data-endDate");
	
	$.ajax({
		type: "GET",
		url: "./member.json?startDate="+startDate1+"&"+"endDate="+endDate1+"&"+"st="+author,
		dataType : 'json',
		success : function(datas) {
			$.getScript('http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js',function(){
			$.getScript('http://cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.0/morris.min.js',function(){
			
			var dataList = datas;
			
			Morris.Donut({
		   	 	element: 'bar-example',
		   	 	data: dataList,
		   	});
			})
			})
		},
		error: function(jqXHR, textStatus, ex){
			alert("error");
			console.log(textStatus + "," + ex + "," + jqXHR.responseText);
		}
	});
}; */

var app = angular.module('calendar', ['ngAnimate', 'ui.bootstrap']);

app.controller('StartCtrl', function ($scope) {
	
	  $scope.today = function() {
	      $scope.startDate = new Date();
	  };
	  //$scope.today();

	  $scope.clear = function() {
		  $scope.startDate = null;
	  };

	  $scope.inlineOptions = {
	    customClass: getDayClass,
	    minDate: new Date(),
	    showWeeks: true
	  };

	  $scope.dateOptions = {
	    //dateDisabled: disabled,
	    formatYear: 'yyyy-MM',
	    maxDate: new Date(2020, 5, 22),
	    minDate: new Date(),
	    showWeeks:true,
	    startingDay: 1
	  };

	  // Disable weekend selection
	  function disabled(data) {
	    var date = data.date,
	      mode = data.mode;
	    return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
	  }
	  
	  $scope.toggleMin = function() {
	    $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
	    $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
	  };

	  $scope.toggleMin();

	  $scope.date = function() {
	    $scope.popup1.opened = true;
	  };

	  $scope.setDate = function(year, month, day) {
	    $scope.startDate = new Date(year, month, day);
	  };

	  $scope.popup1 = {
	    opened: false
	  };

	  var tomorrow = new Date();
	  tomorrow.setDate(tomorrow.getDate() + 1);
	  var afterTomorrow = new Date();
	  afterTomorrow.setDate(tomorrow.getDate() + 1);
	  $scope.events = [
	    {
	      date: tomorrow,
	      status: 'full'
	    },
	    {
	      date: afterTomorrow,
	      status: 'partially'
	    }
	  ];

	  function getDayClass(data) {
	    var date = data.date,
	      mode = data.mode;
	    if (mode === 'day') {
	      var dayToCheck = new Date(date).setHours(0,0,0,0);

	      for (var i = 0; i < $scope.events.length; i++) {
	        var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

	        if (dayToCheck === currentDay) {
	          return $scope.events[i].status;
	        }
	      }
	    }

	    return '';
	  }
	});
	
app.controller('EndCtrl', function ($scope) {
	
	  $scope.today = function() {
	      $scope.endDate = new Date();
	  };
	  //$scope.today();

	  $scope.clear = function() {
		  $scope.endDate = null;
	  };

	  $scope.inlineOptions = {
	    customClass: getDayClass,
	    minDate: new Date(),
	    showWeeks: true
	  };

	  $scope.dateOptions = {
	    //dateDisabled: disabled,
	    formatYear: 'yyyy-MM',
	    maxDate: new Date(2020, 5, 22),
	    minDate: new Date(),
	    showWeeks:true,
	    startingDay: 1
	  };

	  // Disable weekend selection
	  function disabled(data) {
	    var date = data.date,
	      mode = data.mode;
	    return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
	  }
	  
	  $scope.toggleMin = function() {
	    $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
	    $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
	  };

	  $scope.toggleMin();

	  $scope.date = function() {
	    $scope.popup1.opened = true;
	  };

	  $scope.setDate = function(year, month, day) {
	    $scope.endDate = new Date(year, month, day);
	  };

	  $scope.altInputFormats = ['yyyy-MM-dd'];

	  $scope.popup1 = {
	    opened: false
	  };

	  var tomorrow = new Date();
	  tomorrow.setDate(tomorrow.getDate() + 1);
	  var afterTomorrow = new Date();
	  afterTomorrow.setDate(tomorrow.getDate() + 1);
	  $scope.events = [
	    {
	      date: tomorrow,
	      status: 'full'
	    },
	    {
	      date: afterTomorrow,
	      status: 'partially'
	    }
	  ];

	  function getDayClass(data) {
	    var date = data.date,
	      mode = data.mode;
	    if (mode === 'day') {
	      var dayToCheck = new Date(date).setHours(0,0,0,0);

	      for (var i = 0; i < $scope.events.length; i++) {
	        var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

	        if (dayToCheck === currentDay) {
	          return $scope.events[i].status;
	        }
	      }
	    }

	    return '';
	  }
	});
	
app.controller('LogCtrl',function($scope, $sce, $attrs){
	
	$scope.getLogByDate = function(i){
		var indexId = angular.element(document.querySelector('#date'+i));
		alert(indexId.attr("data-whendate"));
		
		var whenDate = indexId.attr("data-whendate");
		
		var testMap = "<c:out value="${p_endtryPath}"/>";
		var indexNum = parseInt(i);
		
		var logMap = "<c:out value="${logMap}"/>";
		alert(testMap);
		
		$scope.logTable = i;
		alert($scope.logTable);
		
		
		$scope.logContent = $sce.trustAsHtml("<h1>abc</h1>");		
	}
});
</script>
</html>