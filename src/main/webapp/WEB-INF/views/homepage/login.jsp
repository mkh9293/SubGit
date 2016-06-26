<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/includes/header.jsp" %>
<!DOCTYPE html>
<html>
<body>
<div class="container">
 	<div id="login">
        <form action="login_processing" method="post">
            <div>
                <p>Team Id</p>
                <input type="text" name="leaderNum"/>
            </div><br />
            <div>
                <p>Password</p>
                <input type="password" name="loginPw"/>
            </div><br/>
            
    		<input type="submit" class="btn btn-primary" name="login" value="Login">
        </form>
    </div>
    
    <hr/>
    <!-- Footer -->
			<%@include file ="/WEB-INF/includes/footer.jsp" %>	
		<!-- End Footer -->
</div>
</body>
</html>