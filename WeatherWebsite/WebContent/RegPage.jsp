<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="hw3.WeatherInfo2"%>
<!DOCTYPE html>
<%
	String userRegErrorEmpty = (String)request.getAttribute("userRegErrorEmpty");
	String passRegErrorEmpty = (String)request.getAttribute("passRegErrorEmpty");
	String passMatchError = (String)request.getAttribute("passMatchError");
	String usernameTaken = (String)request.getAttribute("usernameTaken");
	if(userRegErrorEmpty == null){
		userRegErrorEmpty="";
	}
	if(passRegErrorEmpty == null){
		passRegErrorEmpty="";
	}
	if(passMatchError == null){
		passMatchError="";
	}
	if(usernameTaken == null){
		usernameTaken="";
	}
%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>WeatherMeister</title>
		<link rel="stylesheet" type="text/css" href="WAHeader.css" />
		<link rel="stylesheet" type="text/css" href="RegPage.css" />
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
			<div id="error1"><%= userRegErrorEmpty %></div>
			<div id="error2"><%= passRegErrorEmpty %></div>
			<div id="error3"><%= passMatchError %></div>
			<div id="error4"><%= usernameTaken %></div>
			<div id="Newworduser">Username</div>
			<div id="Newwordpass">Password</div>
			<div id="confrimWordpass">Confirm Password</div>
			<div class="keys">
				<img id="keys" src="A3Images/add.png">
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
				<form id="logForm"action="RegServlet" method="POST">
					<input type="text" id="NewuserInput" name="Newuser">
					<input type="text" id="NewpassInput" name="Newpass">
					<input type="text" id="NewConfirmInput" name="confirmPass">
					<input type="submit" id="regButton" value="Register">
				</form>
			</div>
		</div>
	</body>
</html>