<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Withdraw Response</title>
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
	<br>
	<br>
	<br>
	<br>
	<h3 align="center">Welcome <%=request.getSession().getAttribute("name")%> !</h3>
	<br>
	<h4 align="center">Students In Offerings :</h4>
	<table align="center">
		<tr class="success">
			<th style="width: 10px">ID</th>
			<th style="width: 200px">Name</th>
			<th style="width: 200px">Grade</th>
			<th style="width: 200px">Status</th>
			<th style="width: 300px">Action</th>
		</tr>
		<c:forEach var="row" items="${students}">
			<tr>
				<c:forEach var="cell" items="${row}">
					<td>${cell}</td>
				</c:forEach>
				<td>
					<form style="text-align: center" action="/bagh/prof/WithdrawResponse.jsp" method="POST">
						<input name="accept" class="btn-custom" type="submit" value="accept" />
						<input name="reject" class="btn-custom" type="submit" value="reject" />
						<input type="hidden" name="student" value="${row}">
						<input type="hidden" name="offering" value="${offering}">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<br>
	<br>
	<br>
	<form style="text-align: center" action="/bagh/prof/ProfessorMain.jsp" method="POST">
		<input class="btn-custom" type="submit" value="home" />
	</form>
	
	<c:if test="${err == '1'}">
		<h4 align="center">${errMessage}</h4>
	</c:if>
</body>
</html>