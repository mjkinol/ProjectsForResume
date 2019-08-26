<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="hw3.WeatherInfo2" %>
<!DOCTYPE html>

<%
	//This sets up the title based on if its a display all search or not
	ArrayList<String> cityData = new ArrayList<String>();
	cityData = (ArrayList<String>)request.getAttribute("CurrentcityData");
	String city = "City";
	String header = "No city matches the search.";
	String tempL = "";
	String tempH = "";
	if(cityData != null && cityData.size() > 1){
		header = "All Cities";
	}
%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Search Results Page</title>
		<link rel="stylesheet" type="text/css" href="WAHeader.css" />
		<link rel="stylesheet" type="text/css" href="SearchResults.css" />
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
		<script>
		 function splitBar(){
			 if(document.getElementById("bubb1").value == "checked"){
				document.getElementById("bubb1").value = "notChecked";
				document.getElementById("search").style.display = "none";
				document.getElementById("mag1").style.display = "none";
				document.getElementById("lat").style.display = "block";
		 		document.getElementById("long").style.display = "block";
		 		document.getElementById("mag2").style.display = "block";
		 		document.getElementById("gmap").style.display = "block";
			 }
			 else{
				document.getElementById("bubb1").value = "checked";
				document.getElementById("search").style.display = "block";
				document.getElementById("mag1").style.display = "block";
				document.getElementById("lat").style.display = "none";
				document.getElementById("long").style.display = "none";
				document.getElementById("mag2").style.display = "none";
				document.getElementById("gmap").style.display = "none";
			 }
		 }
		 
		 function sortTable(){
			 var e = document.getElementById("sort");
			 var sortType = e.options[e.selectedIndex].value;
			 var dTable = document.getElementById("sortData");
			 var array =[];
			 for(var x = 1; x < dTable.rows.length; x++){
				 array.push({
					 button: dTable.rows[x].cells[0].innerHTML,
					 name: dTable.rows[x].cells[0].innerText,
					 lowTemp: dTable.rows[x].cells[1].innerHTML,
					 highTemp: dTable.rows[x].cells[2].innerHTML,
				 });
			 }
			 if(sortType == "----------") return;
			 if(sortType == "AZ" || sortType == "ZA"){
				 for(var x = 1; x < dTable.rows.length-1; x++){
					 for(var y = x+1; y < dTable.rows.length; y++){
						var first = array[x-1].name;
						var second = array[y-1].name;
					 	if(first.localeCompare(second) == 1){
					 		var temp = array[x-1];
					 		array[x-1] = array[y-1];
					 		array[y-1] = temp;
					 	}
					 }
				 }
				 if(sortType == "ZA") array.reverse();
			 }
			 if(sortType == "TLA" || sortType == "TLD"){
				 for(var x = 1; x < dTable.rows.length-1; x++){
					 var minIndex = x-1;
					 for(var y = x+1; y < dTable.rows.length; y++){
						var first = parseFloat(array[minIndex].lowTemp);
						var second = parseFloat(array[y-1].lowTemp);
					 	if(first > second){
							minIndex = y-1;
					 	}
					 }
			 		var temp = array[x-1];
			 		array[x-1] = array[minIndex];
			 		array[minIndex] = temp;
				 }
				 if(sortType == "TLD") array.reverse();
			 }
			 if(sortType == "THA" || sortType == "THD"){
				 for(var x = 1; x < dTable.rows.length-1; x++){
					 var minIndex = x-1;
					 for(var y = x+1; y < dTable.rows.length; y++){
						var first = parseFloat(array[minIndex].highTemp);
						var second = parseFloat(array[y-1].highTemp);
					 	if(first > second){
					 		minIndex = y-1;
					 	}
					 }
			 		var temp = array[x-1];
			 		array[x-1] = array[minIndex];
			 		array[minIndex] = temp;
				 }
				 if(sortType == "THD") array.reverse();
			 }
			 
			 for(var x = 1; x < dTable.rows.length; x++){
				 dTable.rows[x].cells[0].innerHTML = array[x-1].button;
				 dTable.rows[x].cells[1].innerHTML = array[x-1].lowTemp;
				 dTable.rows[x].cells[2].innerHTML = array[x-1].highTemp;
			 }
		 }
		</script>
	</head>
	<body>
		<div id="map" style="display: none;">
			<div id="map"></div>
		</div>
		<div class="page-bk"></div>
		<div class="wrapper">
		<% if(cityData == null || cityData.size() > 1){ %>
				<h5><%= header %></h5>
		<% } %>
			<div id=banner>
				<h1>here</h1>		
			</div>
			<div id=mid>
				<form action="IndexServlet" method="POST">
					<input id="search" type="text" name="cityName" placeholder=" Los Angeles"/><br/>
					<input type="image" src="A3Images/magnifying_glass.jpeg" id="mag1" alt="Submit"/>
				</form>
				<form action="LatLongServlet" method="POST">
					<input type="text" id="lat" name="lat" placeholder=" Latitude" style="display: none;"/>
					<input type="text" id="long" name="lng" placeholder=" Longitude" style="display: none;"/><br/>
					<input type="image" src="A3Images/magnifying_glass.jpeg" id="mag2" style="display: none;"/>
				</form>
			</div>
			<form action="index.jsp">
				<input type="submit" id="title" value="WeatherMeister"/><br/>
			</form>
			<div class="bubbles">
				<h2>City</h2><br/>
				<h3>Location (Lat/Long)</h3>
				<form>
					<input type="radio" name="location" value="checked" id="bubb1" checked="checked" onchange="splitBar()"/>
					<input type="radio" name="location" value="notChecked" id="bubb2" onchange="splitBar()"/>
				</form>
			</div>
			<input type="image" src="A3Images/MapIcon.png" id="gmap" style="display: none;" onclick="showMap()"/><br/>
			<% if(cityData != null && cityData.size() > 1){ %>
				<h6>Sort by:</h6>
				<form class="sorted" onchange="sortTable()">
					<select name="sort" id="sort">
					  <option selected="selected" value="none">----------</option>
					  <option value="AZ">City Name A-Z</option>
					  <option value="ZA">City Name Z-A</option>
					  <option value="TLA">Temp. Low ASC</option>
					  <option value="TLD">Temp. Low DESC</option>
					  <option value="THA">Temp. High ASC</option>
					  <option value="THD">Temp. High DESC</option>	  
					</select>
				</form>
			<% } %>
			<div class="dataTable">
			<% if(cityData != null){ %>
				<table id="sortData">
					<tr>
						<th>City Name</th>
						<th>Temp. Low</th>
						<th>Temp. High</th>
					</tr>
						<%
							for(int i = 0; i < cityData.size(); i++){
								String dataline = cityData.get(i);
								String[] data = dataline.split("\\|");
								//pass in the id of the location rather than city name here
								city = data[0]+", "+data[11];
								tempL = data[2];
								tempH = data[3];
						%>
								<tr <%if(i != cityData.size() - 1){%>style="outline: 0.5px solid"<%}%> >
									<td>
										<form action="SRServlet" method="POST">
											<button type="submit" name="name" id="detailSub" value="<%= data[11] %>"><%= city %></button><br/>
										</form>
									</td>
									<td><%= tempL %></td>
									<td><%= tempH %></td>
								</tr>	
						<% } %>				
				</table>
			<% } %>
			</div>
		</div>
	</body>
</html>