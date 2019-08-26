<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="hw3.WeatherInfo2"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>WeatherMeister</title>
		<link rel="stylesheet" type="text/css" href="WAHeader.css" />
		<link rel="stylesheet" type="text/css" href="index.css" />
		<script>
			function signOut(){
				sessionStorage.setItem("currentUser", null);
				var e = document.getElementById("currentUser");
				var b;
			}
		</script>
	</head>
	<body>	
		<div class="page-bk"></div>
		<div class="wrapper">
			<div id=banner>
				<h1>here</h1>		
			</div>
			<div class=img>
				<img src="A3Images/logo.png" height="27%" width="37%">
			</div>
			<div id=mid>
				<form action="IndexServlet" method="POST">
					<input id="bar" type="text" name="cityName" placeholder=" Los Angeles"/><br/>
					<input type="image" src="A3Images/magnifying_glass.jpeg" id="search" alt="Submit"/>
				</form>
			</div>
			<%if(session.getAttribute("currentUser") == null){ %>
				<form action="LoginPage.jsp" method="POST">
					<input type="submit" id="log" name="Login" Value="Login"/><br/>
				</form>
				<form action="RegPage.jsp" method="POST">
					<input type="submit" id="reg" name="Register" Value="Register"/><br/>
				</form>
			<%}
			else{%>
				<form action="Profile.jsp" method="POST">
					<input type="submit" id="profile" name="profile" Value="Profile"/><br/>
				</form>
				<form action="IndexServlet" method="POST">
					<input type="submit" id="SignO" name="SignO" Value="Sign Out"/><br/>
				</form>
			<%}%>
			</div>
			<form action="index.jsp">
				<input type="submit" id="title" value="WeatherMeister"/><br/>
			</form>
			<div class="bubbles">
				<h2>City</h2><br/>
				<h3>Location (Lat/Long)</h3>
				<form>
					<input type="radio" name="location" value="city" id="bubb1" checked="checked"/>
					<input type="radio" name="location" value="lat" id="bubb2" onchange="window.location.replace('WALatLong.jsp')"/>
				</form>
			</div>
	</body>
</html>