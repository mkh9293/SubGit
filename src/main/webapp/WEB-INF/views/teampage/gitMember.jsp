<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
		<%@include file="/WEB-INF/includes/gitTeamHeader.jsp"%>
		
		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header" id="author" data-name="${author }">${author }</h3>
					</div>
				</div>
				<div class="container-fluid">
						<h4>기간 설정</h4>
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
							<button type="submit" class="btn btn-default" id="searchDate">검색</button>
						</div>
						<br/>
						
						<div class="panel-group" id="accordion">
							<c:forEach var="commit" items="${commitList}" varStatus="i">
  								<div class="panel panel-default" id=${i.index }>
    								<div class="panel-heading">
     									 <h4 class="panel-title">
      										  <a data-toggle="collapse" id="${i.index }" data-parent="#accordion" data-date="${commit.commitDate }" href="#collapse${i.index }">
										         <c:choose>
											         <c:when test="${fn:length(commit.commitMessage) > 50}">
											         	<span>${fn:substring(commit.commitMessage, 0, 47)}...</span>
											         </c:when>
											         <c:otherwise>
														${commit.commitMessage}
													 </c:otherwise>  
										         </c:choose>
										         <span class="pull-right">${commit.commitDate }</span>
										         <span class="badge">${changedFileCount[commit.commitId]}</span>
										         <span class="label label-success">+${lineList[commit.commitId][0]}</span>
										         <span class="label label-danger">-${lineList[commit.commitId][1]}</span>
										      </a>
										 </h4>
									</div>
								    <div id="collapse${i.index }" class="panel-collapse collapse">
								      <div class="panel-body">
								      	<table class="table table-hover entryTable">
								      		<thead>
								      			<tr>
								      				<th>File Status</th>
								      				<th>File List</th>
								      				<!-- <th>add Line</th>
								      				<th>delete Line</th> -->
								      			</tr>
								      		</thead>
								      		<tbody>
								      			<c:forEach var="diffEntry" items="${changedFileList[commit.commitId]}" varStatus="j">
									      			<tr>
										      			<c:set var="entryName" value="${diffEntry }"/>
										      			<c:choose>
										      				<c:when test="${fn:containsIgnoreCase(entryName, 'ADD')}">
														       <td class="status" style="color:green;">ADD<%-- ${j.index } --%></td>
														    </c:when>
														    <c:when test="${fn:containsIgnoreCase(entryName, 'MODIFY')}">
														       <td class="status" style="color:yellow;">MODIFY<%-- ${j.index } --%></td>
														    </c:when>
														    <c:when test="${fn:containsIgnoreCase(entryName, 'DELETE')}">
														       <td class="status" style="color:red;'">DELETE<%-- ${j.index } --%></td>
														    </c:when>
											      			 <c:otherwise>
														        <td class="status" style="color:green;">ADD<%-- ${j.index } --%></td>
														    </c:otherwise>
														</c:choose>
														<c:choose>
															<c:when test="${fn:containsIgnoreCase(entryName, 'DIFFENTRY')}">
																<c:set var="entryName" value="${diffEntry }"/>
																<c:set var="entryName1" value="${fn:substringAfter(entryName,' ') }"/>
																<c:set var="entryName2" value="${fn:substringBefore(entryName1,']') }"/>
											      				<td><a href="fileView?teamRepo=${uri}&teamURL=${gitURL }&filePath=${diffEntry }&commitId=${commit.commitId}&leaderNum_fk=${leaderNum_fk}">${entryName2 }</a></td>
											      			</c:when>
											      			<c:otherwise>
											      				<td><a href="fileView?teamRepo=${uri}&filePath=${diffEntry }&commitId=${commit.commitId}&leaderNum_fk=${leaderNum_fk}">${diffEntry }</a></td>
											      			</c:otherwise>
									      				</c:choose>
									      				<%-- <td>${lineList[commit.commitId][0]}</td>
									      				<td>${lineList[commit.commitId][1]}</td> --%>
									      			</tr>
								      			</c:forEach>
								      		</tbody>
								      	</table>
								      	<!-- 페이지 네이션 시도 -->
								        <%-- <ul class="pagination">	 
								        <fmt:parseNumber var="pageCount" value="${(changedFileCount[commit.commitId]/10)+1}" integerOnly="true" />
											<c:forEach var="paging" items="<c:out ">
												 <li><a href="#">${paging }</a></li>
											</c:forEach>
										</ul> --%>
								      </div>
								    </div>
 								 </div>
							</c:forEach>
							</div>
					<%-- 	<div style="position:relative; width:45%; display:inline-block;">
						<ul class="list-group">
							<c:forEach var="commit" items="${commitList}">
								<li class="list-group-item">
								<span class="badge">${changedFileCount[commit.commitId]}</span>
									${commit.commitDate}
								</li>
							</c:forEach>
						</ul>
						</div>
						<div style="position:relative; width:45%; border:1px solid green; display:inline-block;">
							
							<ul class="list-group">
								<c:forEach var="commit" items="${commitList}">
									<li class="list-group-item toggleItem toggle">
										${changedFileList[commit.commitId]}
									</li>
								</c:forEach>
							</ul>
							
	
						</div> --%>
					</div>
				</div>
			</div>
		</div>
</body>
<script type="text/javascript">

$('#searchDate').click(function(){
	var commitListSize = ${commitListSize};
	
	var startDate1 = document.getElementById("startInput").value;
	var endDate1 = document.getElementById("endInput").value;
	
	for(var i=0;i<commitListSize;i++){
		var commitDate = $('#accordion a#'+i).attr("data-date"); 
		var splitDate = commitDate.split(" ");
		if(splitDate[0] >= startDate1 && splitDate[0] <= endDate1){
			$('#accordion div#'+i).css('display','block');
		}else{
			$('#accordion div#'+i).css('display','none');
		}
	}
});

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
</script>
</html>