<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<script src="/subgit/resources/js/diff.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.4.0/highlight.min.js"></script>
<script src="<c:url value="/resources/syntaxHighlighter/js/highlightjs-line-numbers.min.js"/>"></script>
<script src="<c:url value="/resources/js/plugins/jquery-linedtextarea.js"/>"></script>
<link href="<c:url value="/resources/css/plugins/jquery-linedtextarea.css"/>" rel="stylesheet" type="text/css">
<style>
.hljs-line-numbers {
	text-align: right;
	border-right: 1px solid #ccc;
	color: #999;
	-webkit-touch-callout: none;
	-webkit-user-select: none;
	-khtml-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}
.lines {
	display: inline-block;
}
.linedtextarea {
	display: inline-block;
}
</style>
<script>
	hljs.initHighlightingOnLoad();

/* 	hljs.initLineNumbersOnLoad(); */
</script>
 <script>
	$(document).ready(function() {
		$('code.hljs').each(function(i, block) {
			hljs.lineNumbersBlock(block);
		});
	});
</script> 
<script>
	$(function() {
		var dmp = new diff_match_patch();

		var text1 = document.getElementById('text1').value;
		 if (text1 != "새로 생성된 파일") { 
			var text2 = document.getElementById('text2').value;

			var d = dmp.diff_main(text2, text1);

			dmp.diff_cleanupSemantic(d);

			var ds = dmp.diff_prettyHtml(d);
			var pat = /&para;/gi;
			var te = ds.replace(pat, " ");
			document.getElementById('resultText').innerHTML = te;

		 } 
	});
</script>


</head>
<body>
	<div id="wrapper">
		<%@include file="/WEB-INF/includes/gitTeamHeader.jsp"%>
		<div id="page-wrapper">

			<div class="container-fluid">
				<h3>${fileName }</h3>
				<textarea id="text1" style="display: none;">${currentFileContent}</textarea>
				<textarea id="text2" style="display: none;">${searchFileContent }</textarea>
				
				<c:if test="${newContent eq true }">
				<span style="font-weight:bold;">${searchFileContent }</span>
					<pre><code class="html"><c:out value="${currentFileContent }" escapeXml="true"></c:out></code></pre>
				</c:if>
				<c:if test="${newContent eq false}">
					<pre><code id="resultText"></code></pre>
				</c:if>
				
				
			</div>
		</div>
	</div>
</body>
</html>