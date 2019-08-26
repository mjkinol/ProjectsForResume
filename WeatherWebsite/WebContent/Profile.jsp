<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="hw3.WeatherSQLFunc" %>
<%@page import="java.util.ArrayList" %>
    
<!DOCTYPE html>
<%
	WeatherSQLFunc getData = new WeatherSQLFunc();
	ArrayList<String> history = new ArrayList<String>();
	String user = (String)session.getAttribute("currentUser");
	history = getData.searchHistory(user);
%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>WeatherMeister</title>
		<link rel="stylesheet" type="text/css" href="WAHeader.css" />
		<link rel="stylesheet" type="text/css" href="Profile.css" />
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
			<form action="Profile.jsp" method="POST">
				<input type="submit" id="profile" name="profile" Value="Profile"/><br/>
			</form>
			<form action="IndexServlet" method="POST">
				<input type="submit" id="SignO" name="SignO" Value="Sign Out"/><br/>
			</form>
			<form action="index.jsp">
				<input type="submit" id="title" value="WeatherMeister"/><br/>
			</form>
			<div id="userHistory"><%= user %>'s Search History</div>
		</div>
		<div class="dTable">
				<table id="historyTable">
					<tbody>
				<%
					if(history.size() != 0){
						for(int i = history.size()-1; i >= 0; i--){
				%>
							<tr <%if((i != history.size() - 1 && i != 0) || history.size() == 2){%>style="outline: 0.5px solid"<%}%> >
								<td><%= history.get(i) %></td>
							</tr>	
				<% 		}
					}
					%>
					</tbody>
				</table>
			</div>
	</body>
</html>