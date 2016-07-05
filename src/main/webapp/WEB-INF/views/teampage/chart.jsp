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
	<script src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
    <script src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
    <script src="http://cdn.oesmith.co.uk/morris-0.4.1.min.js"></script>
<script>
var options ={
        chart: {
            type: 'column',
            renderTo: 'container'
        },
        title: {
            text: '월별 라인 추가 현황'
        },
        subtitle: {
            text:''
        },
        xAxis: {
            categories: [
                'Jan',
                'Feb',
                'Mar',
                'Apr',
                'May',
                'Jun',
                'Jul',
                'Aug',
                'Sep',
                'Oct',
                'Nov',
                'Dec'
            ],
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Lines add'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} Lines</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: []
    }; 
    
$.ajax({
	type: "GET",
	url: "./jsonChart",
	dataType:"json",
	data: {"teamRepo":"<c:out value="${uri}"/>"},
	success : function(data) {
		var names = [];
		var datas = [];
		var jsonInfo = data;
		for(var i in jsonInfo.chartData){
			datas.push(jsonInfo.chartData[i].data);
			/* datas.push(jsonInfo.chartData[i].data);
			alert(datas.pop) */
			 
			/* myNames.push(jsonInfo.chartData[i].name);
			for(var j in myNames){
				
			} */
		}
		for(var j in datas){
			var even = datas[j]%2;
			alert(even);
		}	
/* 		var arr1 = JSON.stringify(jsonInfo.chartData).slice(1);
		var arr2 = arr1.slice(0,-1); */


	/* 	var arr2 = arr1[1].slice(0,-1);
		alert(arr2); */
		/* alert(JSON.stringify(jsonInfo.chartData)); */
		/* for(var i in jsonInfo.chartData){
			alert(jsonInfo.chartData[i].name) 
			alert(jsonInfo)
		} */
			/* for(var g in v.chartData){
				alert(g.name);
			} */
		
		/*  var st = JSON.stringify(data);
		foo = st.split('chartData":');
		var foo2 = foo[1].slice(0,-1);
		jsonType = JSON.parse(foo2);
		options.series = jsonType;
		chart = new Highcharts.Chart(options); */
	}
});   

/* var options2 ={
        chart: {
            type: 'column',
            renderTo: 'container2'
        },
        title: {
            text: '월별 라인 삭제 현황'
        },
        subtitle: {
            text:''
        },
        xAxis: {
            categories: [
                'Jan',
                'Feb',
                'Mar',
                'Apr',
                'May',
                'Jun',
                'Jul',
                'Aug',
                'Sep',
                'Oct',
                'Nov',
                'Dec'
            ],
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Lines delete'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} Lines</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: []
    };
$.ajax({
	type: "GET",
	url: "./jsonChart2",
	dataType:"json",
	data: {"teamRepo":"<c:out value="${uri}"/>"},
	success : function(data) {
		for(var v in data){
			alert(JSON.stringify(v));
			 var st = JSON.stringify(data);
			 foo = st.split('chartData":');
			var foo2 = foo[1].slice(0,-1);
			jsonType = JSON.parse(foo2);
			options2.series = jsonType;
			chart = new Highcharts.Chart(options2);	 
		}
		
		 var st = JSON.stringify(data);
		foo = st.split('chartData":');
		var foo2 = foo[1].slice(0,-1);
		jsonType = JSON.parse(foo2);
		options2.series = jsonType;
		chart = new Highcharts.Chart(options2); 
	}
});  */
</script>

</head>

<body>
	<div id="wrapper">
		<%@include file="/WEB-INF/includes/gitTeamHeader.jsp"%>
		
		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h3>chart</h3>
					</div>
				</div>
			
			<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>				
			<div id="container2" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
			<script src="https://code.highcharts.com/highcharts.js"></script>
			<script src="https://code.highcharts.com/modules/exporting.js"></script>
							
				
				
		</div>
		
		</div>
		</div>
</body>

</html>