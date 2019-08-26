<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="hw3.WeatherInfo2"%>
<!DOCTYPE html>

<%	
	String LatLongError = (String)request.getAttribute("LatLongError");
	if(LatLongError == null){
		LatLongError="";
	}
	
	String lat = request.getParameter("lat");
	String lng = request.getParameter("lng");
	
	if(lat == null){
		lat = " Latitude";
	}
	if(lng == null){
		lng = " Longitude";
	}
%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>WeatherMeister</title>
		<link rel="stylesheet" type="text/css" href="WALatLong.css" />
		<link rel="stylesheet" type="text/css" href="WAHeader.css" />		
		<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&callback=initialize"></script>
		<script>
			var map;
			function initMap() {
				map = new google.maps.Map(document.getElementById('map'), {
				  center: {lat: 39.0119, lng: -98.4842},
				  zoom: 4
				});
				google.maps.event.addListener(map, "click", function (event) {
					 document.getElementById('lat').value = Math.round(event.latLng.lat()*100)/100.0
	                 document.getElementById('long').value =  Math.round(event.latLng.lng()*100)/100.0
	                 document.getElementById("map").style.display = "none";
				});
			}
			function showMap() {
				document.getElementById("map").style.display = "block";
			}
		</script>
		<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCgoXaKPc9-BPy1h_VlJNS0E7aBDPMzS2o&callback=initMap">
		</script>
	</head>
	<body>
		<div id="map" style="display: none;">
			<div id="map"></div>
		</div>
		<div class="page-bk"></div>
		<div class="wrapper">
			<div id=banner>
				<h1>here</h1>		
			</div>
			<div class=img>
				<img src="A3Images/logo.png" height="27%" width="37%">
			</div>
			<div id=mid>
				<form action="LatLongServlet" method="POST">
					<input type="text" id="lat" name="lat" placeholder=" Latitude"/>
					<input type="text" id="long" name="lng" placeholder=" Longitude"/><br/>
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
			<div id="mapAPI">
				<input type="image" src="A3Images/MapIcon.png" id="gmap" onclick="showMap()"/><br/>
			</div>

			<form action="index.jsp">
				<input type="submit" id="title" value="WeatherMeister"/><br/>
			</form>
			<div class="bubbles">
				<h2>City</h2><br/>
				<h3>Location (Lat/Long)</h3>
				<form>
					<input type="radio" name="location" value="city" id="bubb1" onchange="window.location.replace('index.jsp')"/>
					<input type="radio" name="location" value="lat" id="bubb2"  checked="checked"/>
				</form>
			</div>
		</div>
		<div class="error">
			<h1><font color="red"><%= LatLongError %></font></h1>
		</div>
	</body>
</html>