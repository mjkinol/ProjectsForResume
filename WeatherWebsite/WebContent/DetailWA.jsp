<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html>
<%

	String FinalCityData = (String)request.getAttribute("FinalCityData");
	String[] data = FinalCityData.split("\\|");
	String header = data[0];
	request.setAttribute("FinalCityDataString", data);
%>

<html>
	<head>
		<meta charset="UTF-8">
		<title>Search Results Page</title>
		<link rel="stylesheet" type="text/css" href="WAHeader.css" />
		<link rel="stylesheet" type="text/css" href="SearchResults.css" />
		<link rel="stylesheet" type="text/css" href="DetailWA.css" />
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
			function swap(arg1, arg2, data1, data2, img)
			{
				var button = document.getElementById(img);
			    if(button.value == "clicked"){
			    	if(img == "loc1"){	
				 		document.getElementById(arg1).value = data1 + ",";
				 		document.getElementById(arg2).value = data2;
			    	}
			    	else if(img == "pin1"){
			   			document.getElementById(arg1).value = data1 + ",";
				 		document.getElementById(arg2).value = data2;
			   		}
			    	else if(img == "rise1"){
			    		document.getElementById(arg1).value = data1 + ",";
				 		document.getElementById(arg2).value = data2;
			    	}
			 		button.value = "NotClicked";
			 		button.style.display = 'none';
			 		document.getElementById(arg1).style.color = "white";
			 		document.getElementById(arg1).style.background = "Transparent";
			 		document.getElementById(arg1).style.border = "none";
			 		document.getElementById(arg2).style.color = "white";
			 		document.getElementById(arg2).style.background = "Transparent";
			 		document.getElementById(arg2).style.border = "none";
			 		document.getElementById(arg2).style.fontSize = "28px";
			 		document.getElementById(arg1).style.fontSize = "28px";
			 		
			 	}
			    else{
			    	button.style.display = 'block';
			 		button.value = "clicked";
			 		document.getElementById(arg2).style.fontSize = "0px";
			 		document.getElementById(arg1).style.fontSize = "0px";
			 		
			    }
			}
			function swap2(arg, img, words)
			{
				var button = document.getElementById(img);
			    if(button.value == "clicked"){
			    	if(img == "low1" || img == "high1" || img == "temp1") document.getElementById(words).value = arg + " °F";
			    	else if(img == "wind1") document.getElementById(words).value = arg + " miles/hour";
			    	else if(img == "hum1") document.getElementById(words).value = arg + "%";
			 		button.value = "NotClicked";
			 		button.style.display = 'none';
			 		document.getElementById(words).style.color = "white";
			 		document.getElementById(words).style.background = "Transparent";
			 		document.getElementById(words).style.border = "none";
			 		document.getElementById(words).style.fontSize = "28px";
			 	}
			    else{
			    	button.style.display = 'block';
			 		button.value = "clicked";
			 		document.getElementById(words).style.fontSize = "0px";		
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
			<h5><%= header %></h5>
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
			<div id="mapAPI">
				<input type="image" src="A3Images/MapIcon.png" id="gmap" style="display: none;" onclick="showMap()"/><br/>
			</div>
			<div class=imgGlobe>
				<input type="image" id="loc1" value="clicked" src="A3Images/planet-earth.png" height="16%" width="26%" onclick="swap('state1', 'country1', '<%= data[0] %>', '<%= data[1] %>', 'loc1')"/>
				<h3>Location</h3>
				<input type="submit" id="state1" style="color: transparent; background-color: transparent; border-color: transparent;" onclick="swap('state1', 'country1', '<%= data[0] %>', '<%= data[1] %>', 'loc1')"></input>
				<input type="submit" id="country1" style="color: transparent; background-color: transparent; border-color: transparent;" onclick="swap('state1', 'country1','<%= data[0] %>', '<%= data[1] %>', 'loc1')"></input>
			</div>
			
			<div class=imgSnow>
				<input type="image" id="low1" value="clicked" src="A3Images/snowflake.png" height="16%" width="26%" onclick="swap2('<%= data[2] %>', 'low1', 'lowTemp')"/>
				<h3>Temp Low</h3>
				<input type="submit" id="lowTemp" style="color: transparent; background-color: transparent; border-color: transparent;" onclick="swap2('<%= data[2] %>', 'low1', 'lowTemp')"></input>
			</div>
			
			<div class=imgSun>
				<input type="image" id="high1" value="clicked" src="A3Images/sun.png" height="16%" width="26%" onclick="swap2('<%= data[3] %>', 'high1', 'highTemp')"/>
				<h3>Temp High</h3>
				<input type="submit" id="highTemp" style="color: transparent; background-color: transparent; border-color: transparent;" onclick="swap2('<%= data[3] %>', 'high1', 'highTemp')"></input>
			</div>
			
			<div class=imgWind>
				<input type="image" id="wind1" value="clicked" src="A3Images/wind.png" height="25%" width="35%" onclick="swap2('<%= data[4] %>', 'wind1', 'windSpeed')"/>
				<h3>Wind</h3>
				<input type="submit" id="windSpeed" style="color: transparent; background-color: transparent; border-color: transparent;" onclick="swap2('<%= data[4] %>', 'wind1', 'windSpeed')"></input>
			</div>
			
			<div class=imgDrop>
				<input type="image" id="hum1" value="clicked" src="A3Images/drop.png" height="16%" width="26%" onclick="swap2('<%= data[5] %>', 'hum1', 'humidity')"/>
				<h3>Humidity</h3>
				<input type="submit" id="humidity" style="color: transparent; background-color: transparent; border-color: transparent;" onclick="swap2('<%= data[5] %>', 'hum1', 'humidity')"></input>
			</div>
			
			<div class=imgPin>
				<input type="image" id="pin1" value="clicked" src="A3Images/LocationIcon.png" height="18%" width="28%" onclick="swap('pinLat', 'pinLong', '<%= data[7] %>', '<%= data[6] %>', 'pin1')"/>
				<h3>Coordinates</h3>
				<input type="submit" id="pinLat" style="color: transparent; background-color: transparent; border-color: transparent;" onclick="swap('pinLat', 'pinLong', '<%= data[7] %>', '<%= data[6] %>', 'pin1')"></input>
				<input type="submit" id="pinLong" style="color: transparent; background-color: transparent; border-color: transparent;" onclick="swap('pinLat', 'pinLong', '<%= data[7] %>', '<%= data[6] %>', 'pin1')"></input>
			</div>
			
			<div class=imgTemp>
				<input type="image" id="temp1" value="clicked" src="A3Images/thermometer.png" height="16%" width="26%" onclick="swap2('<%= data[8] %>', 'temp1', 'currTemp')"/>
				<h3>Current Temp</h3>
				<input type="submit" id="currTemp" style="color: transparent; background-color: transparent; border-color: transparent;" onclick="swap2('<%= data[8] %>', 'temp1', 'currTemp')"></input>
			</div>
			
			<div class=imgRise>
				<input type="image" id="rise1" value="clicked" src="A3Images/sunrise-icon.png" height="25%" width="35%" onclick="swap('sunRise', 'sunSet', '<%= data[9] %>', '<%= data[10] %>', 'rise1')"/>
				<h3>Sunrise/set</h3>
				<input type="submit" id="sunRise" style="color: transparent; background-color: transparent; border-color: transparent;" onclick="swap('sunRise', 'sunSet', '<%= data[9] %>', '<%= data[10] %>', 'rise1')"></input>
				<input type="submit" id="sunSet" style="color: transparent; background-color: transparent; border-color: transparent;" onclick="swap('sunRise', 'sunSet', '<%= data[9] %>', '<%= data[10] %>', 'rise1')"></input>
			</div> 
		</div>
	</body>
</html>