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
	background-image: url('asb.gif');
}
</style>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Student Main Page</title>
</head>

<body>
	<br>
	<br>
	<br>
	<br>
	<h3 align="center">Welcome ${name} !</h3>
	<br>
	<h4 align="center">Term In Progress Offerings :</h4>
	<table align="center">
		<tr class="success">
			<th style="width: 10px">ID</th>
			<th style="width: 240px">Course</th>
			<th style="width: 30px">Units</th>
			<th style="width: 30px">Time</th>
			<th style="width: 200px">Teacher Name</th>
			<th style="width: 140px">Exam Date</th>
			<th style="width: 140px">Remain Capacity</th>
			<th style="width: 100px">Capacity</th>
		</tr>
		<c:forEach var="offer" items="${inProgressOffers}">
			<tr>
				<c:forEach var="off" items="${offer}">
					<td>${off}</td>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>
	<br>
	<br>
	<br>
	<form style="text-align:center" action="StudentMain.action"
		method="POST">
		<input type="hidden" name="sid" value="${sid}"> <input
			type="hidden" name="choice" value="CourseSelect"> <input
			class="btn-custom" type="submit" value="Course select">
	</form>
	<br>
	<form style="text-align:center" action="StudentMain.action"
		method="POST">
		<input type="hidden" name="sid" value="${sid}"> <input
			type="hidden" name="choice" value="Withdraw"> <input
			class="btn-custom" type="submit" value="Withdraw">
	</form>

	<c:if test="${hasError == '1'}">
		<h4 align="center">${errMessage}</h4>
	</c:if>
</body>
</html>