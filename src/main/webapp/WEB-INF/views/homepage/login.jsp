<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/includes/header.jsp" %>
<!DOCTYPE html>
<html>
<style type="text/css">
	#login{
    	 height:500px;
    	 font-size:20px;
	}
	#login form{
		width:300px;
		margin:0 auto;
	}
	#Login input{
		width:300px;
	}
</style>
<body>
<div class="container" style="height:600px; padding-top:5%;">
 	<div id="login">
        <form action="login_processing" method="post">
            <div>
                <p>팀장 학번</p>
                <input type="text" name="leaderNum"/>
            </div><br />
            <div>
                <p>비밀번호</p>
                <input type="password" name="loginPw"/>
            </div><br/>
            
    		<input type="submit" class="btn btn-primary" name="login" value="Login">
        </form>
    </div>
    
    <hr/>
    
    <!-- Footer -->
	<%@include file="/WEB-INF/includes/footer.jsp"%>
	<!-- End Footer -->

</div>
</body>
</html>