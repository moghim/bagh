<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html">
<html>
<head>
<style>
.btn-custom {
	width: 100px;
	height: 30px;
	background-color: hsl(196, 100%, 4%) !important;
	background-repeat: repeat-x;
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#28c5ff",
		endColorstr="#000e14" );
	background-image: -khtml-gradient(linear, left top, left bottom, from(#28c5ff),
		to(#000e14) );
	background-image: -moz-linear-gradient(top, #28c5ff, #000e14);
	background-image: -ms-linear-gradient(top, #28c5ff, #000e14);
	background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #28c5ff),
		color-stop(100%, #000e14) );
	background-image: -webkit-linear-gradient(top, #28c5ff, #000e14);
	background-image: -o-linear-gradient(top, #28c5ff, #000e14);
	background-image: linear-gradient(#28c5ff, #000e14);
	border-color: #000e14 #000e14 hsl(196, 100%, -9.5%);
	color: #fff !important;
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.89);
	-webkit-font-smoothing: antialiased;
}

body {

}
</style>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Student Login Page</title>
</head>

<body>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<h3 align="center">Please enter your ID to login :</h3>
	<br>
	<form style="text-align:center" action='<%= response.encodeURL("j_security_check") %>' method="POST">
		user name : <input type="text" name="j_username"><br>
		password  : <input type="password" name="j_password"><br>
		<input class="btn-custom" type="submit" value="Sign in">
	</form>
	<c:if test="${hasError == '1'}">
		<h4 align="center">error : ${errMessage}</h4>
	</c:if>
</body>
</html>