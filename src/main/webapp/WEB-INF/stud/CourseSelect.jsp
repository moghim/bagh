<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Course Selection</title>
<style>
.btn-custom {
	width: 150px;
	height: 50px;
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
</style>
<style>
table,td,th {
	border: 1px solid #8A2BE2;
	background-color: #D8BFD8;
	text-align: center;
	vertical-align: middle;
	height: 50px;
}

th {
	text-align: center;
	height: 50px;
	text-align: center;
	vertical-align: middle;
	background-color: #8A2BE2;
	color: white;
}

textarea {
	height: 25px;
}

body {
	background-image: url('bluepap.jpg');
}
</style>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
</head>

<body>
	<c:if test="${err == '1'}">
		<h4 align="center">error : ${errMessage}</h4>
	</c:if>
	<h3 align="center">
		Student Name :<%=request.getSession().getAttribute("name")%></h3>
	<br>

	<h4 align="center">Selected Offerings</h4>
	<table align="center" class="success">
		<tr>
			<th style="width: 10px">ID</th>
			<th style="width: 240px">Course</th>
			<th style="width: 30px">Units</th>
			<th style="width: 30px">Time</th>
			<th style="width: 200px">Teacher Name</th>
			<th style="width: 140px">Exam Date</th>
			<th style="width: 140px">Remain Capacity</th>
			<th style="width: 100px">Capacity</th>
			<th style="width: 150px">Action</th>
		</tr>
		<c:forEach var="offer" items="${inProgressOffers}">
			<tr>
				<c:forEach var="off" items="${offer}">
					<td>${off}</td>
				</c:forEach>
				<td>
					<form style="text-align: center"
						action="/bagh/stud/CourseSelect.jsp" method="POST">
						<input class="btn-custom" type="submit" value="drop" /> <input
							type="hidden" name="drop" value="${offer}">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
	<br>

	<h4 align="center">Term Offerings</h4>
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
			<th style="width: 20px">Action</th>
		</tr>
		<c:forEach var="offer" items="${otherOffers}">
			<tr>
				<c:forEach var="off" items="${offer}">
					<td>${off}</td>
				</c:forEach>
				<td>
					<form style="text-align: center" action="/bagh/stud/CourseSelect.jsp" method="POST">
						<input class="btn-custom" type="submit" value="take" />
						<input type="hidden" name="take" value="${offer}">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<form style="text-align: center" action="/bagh/stud/StudentMain.jsp" method="POST">
		<input class="btn-custom" type="submit" value="home" />
	</form>
</body>
</html>