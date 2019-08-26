<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="hw3.WeatherInfo2"%>
<%
	String userLogError = (String)request.getAttribute("userLogError");
	String passLogError = (String)request.getAttribute("passLogError");
	if(userLogError == null){
		userLogError="";
	}
	if(passLogError == null){
		passLogError="";
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>WeatherMeister</title>
		<link rel="stylesheet" type="text/css" href="WAHeader.css" />
		<link rel="stylesheet" type="text/css" href="LoginPage.css" />
		<script>
			function signOut(){
				sessionStorage.setItem("currentUser", null);
			}
		</script>
	</head>
	<body>	
		<div class="page-bk"></div>
		<div class="behind"></div>
		<div class="wrapper">
			<div id=banner>
				<h1>here</h1>		
			</div>
			<div id="error1"><%= userLogError %></div>
			<div id="error2"><%= passLogError %></div>
			<div id="worduser">Username</div>
			<div id="wordpass">Password</div>
			<div class="keys">
				<img id="keys" src="A3Images/Keychain_Locked@2x.png">
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
			<form action="index.jsp">
				<input type="submit" id="title" value="WeatherMeister"/><br/>
			</form>
			<div class="input">
				<form action="LogServlet" method="POST" id="logForm">
					<input type="text" id="userInput" name="username">
					<input type="text" id="passInput" name="password">
					<input type="submit" id="logButton" value="Login">
				</form>
			</div>
		</div>
	</body>
</html>