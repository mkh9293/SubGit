<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!--  -<style>
	.file-header {
	padding: 5px 10px;
	background-color: #f7f7f7;
	border-bottom: 1px solid #d8d8d8;
	border-top-left-radius: 2px;
	border-top-right-radius: 2px;
}
	.main {
	position: relative;
	margin-top: 20px;
	margin-bottom: 15px;
	border: 1px solid #ddd;
	border-radius: 3px;
	margin-left: auto;
	margin-right: auto;
}
span.block{ width:800px;}
 main pre{font-size:12px}
</style>-->

<script src="/subgit/resources/js/diff.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- syntaxHighlighter.js -->
<script src="<c:url value="/resources/syntaxHighlighter/js/shCore.js"/>"></script>
<script src="<c:url value="/resources/syntaxHighlighter/js/shBrushJava.js"/>"></script>
<script src="<c:url value="/resources/syntaxHighlighter/js/shBrushXml.js"/>"></script>
<script src="<c:url value="/resources/syntaxHighlighter/js/shBrushPython.js"/>"></script>

<!-- syntaxHighlighter.css -->
<link href="<c:url value="/resources/syntaxHighlighter/css/shCore.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/syntaxHighlighter/css/shThemeDefault.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/syntaxHighlighter/css/shThemeEclipse.css"/>" rel="stylesheet" type="text/css">
<%-- <link href="<c:url value="/resources/syntaxHighlighter/css/shThemeDjango.css"/>" rel="stylesheet" type="text/css"> --%>
 <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/8.7/styles/default.min.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/8.7/highlight.min.js"></script>
<script>hljs.initHighlightingOnLoad();</script>

<script src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
<!-- diff.js -->
<script src="<c:url value="/resources/js/diff.js"/>"></script>
<script>
	$(function() {
		var dmp = new diff_match_patch();

		var text1 = document.getElementById('text1').value;
		var text2 = document.getElementById('text2').value;

		var d = dmp.diff_main(text1, text2);

		dmp.diff_cleanupSemantic(d);

		var ds = dmp.diff_prettyHtml(d);
		var pat = /&para;/gi;
		var te = ds.replace(pat, " ");
		/* $('#source-code').html(te); */
		 document.getElementById('source-code').innerHTML = te; 

	});
</script>

</head>

<body>
	<script>
	$(document).ready(function(){ // 미적용
	 $("#source-code").addClass("brush: "+"${fileExtension}"); 

  SyntaxHighlighter.all();
 
	});

</script>	
	
	<div class="file-header">
	<p>파일명 : ${fileName} 확장자 : ${fileExtension} </p>
	</div>
	<div style="width: 950px; height: auto; overflow:scroll; display:inline-block">
	<pre class="brush:${fileExtension}" style="display: inline-block; width:auto overflow-x:scroll">
	${currentFileContent}
	</pre>
	</div>
	<textarea id="text1" style="display:none">

	${currentFileContent }

	</textarea>	
	
	
	<textarea id="text2" style="display:none">

	${searchFileContent }

	</textarea>	
		

	<!-- <div style="padding-top:30px;" class="well-white">
		 	<pre id="source-code" > 
		 	</pre> 
	</div> -->
	<pre><code class="html" id="source-code"></code></pre>
	
</body>

</html>