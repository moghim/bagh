<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <style>
        	.btn-custom {
        	width: 100px; height : 30px;
  background-color: hsl(196, 100%, 4%) !important;
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#28c5ff", endColorstr="#000e14");
  background-image: -khtml-gradient(linear, left top, left bottom, from(#28c5ff), to(#000e14));
  background-image: -moz-linear-gradient(top, #28c5ff, #000e14);
  background-image: -ms-linear-gradient(top, #28c5ff, #000e14);
  background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #28c5ff), color-stop(100%, #000e14));
  background-image: -webkit-linear-gradient(top, #28c5ff, #000e14);
  background-image: -o-linear-gradient(top, #28c5ff, #000e14);
  background-image: linear-gradient(#28c5ff, #000e14);
  border-color: #000e14 #000e14 hsl(196, 100%, -9.5%);
  color: #fff !important;
  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.89);
  -webkit-font-smoothing: antialiased;
}
	  body {background-image:url('asb.gif');}
	</style>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
      <title>Student Login Page</title>
  </head>
  
  <body > 
  
  	<br><br><br><br><br><br><br><br><br><br>
  	<h3 align="center">Please enter your ID to login :</h3><br>
	<form style="text-align:center" action="StudentLogin.action" method="POST">
		student id : <input type="text" name="sid">
		
		<input class="btn-custom" type="submit" value="Sign in">
	</form>
	<c:if test="${err == '1'}">
		<h4 align="center">Student ID is wrong .</h4>
	</c:if>
	<c:if test="${err == '2'}">
		<h4 align="center">Your enrollment time is passed or not came yet .</h4>
	</c:if>
	<c:if test="${err == '3'}">
		<h4 align="center">Current term is not added in database .</h4>
	</c:if>		
 
  </body>
</html>