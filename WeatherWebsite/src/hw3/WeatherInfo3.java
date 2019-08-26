package hw3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class WeatherInfo3 {
	
	public ArrayList<String> getAPIWeatherDataLoc2(String desiredCity, String realPath) {
		try {
			FileReader reader = new FileReader(realPath);
			BufferedReader buff = new BufferedReader(reader);
			ArrayList<String> Names = new ArrayList<String>();
			String line = buff.readLine();
			String prevLine = "";
			while(line != null) {
				String tempName = "\"name\": \""+desiredCity+"\",";
				if(line.trim().equals(tempName)) {
					Names.add(prevLine);
				}
				prevLine = line;
				line = buff.readLine();
			}
			buff.close();
			reader.close();
			return Names;
		} catch(FileNotFoundException fnfe) {
		} catch(IOException ioe) {
		}
		return null;
	}
	public String getAPIWeatherDataID(String idVal) {
		String url = "https://api.openweathermap.org/data/2.5/weather?id="+idVal+"&dt=UTC&units=imperial&APPID=60c9239a644872ad319e3e083583953e";
		StringBuilder result = new StringBuilder();
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
		         result.append(line);
		    }
			rd.close();
			String jsonString = result.toString();
			JsonObject jobj = new Gson().fromJson(jsonString, JsonObject.class);
			JsonObject getSys = jobj.getAsJsonObject("sys");
			JsonObject getMain = jobj.getAsJsonObject("main");
			JsonObject getWind = jobj.getAsJsonObject("wind");
			JsonObject getCoord = jobj.getAsJsonObject("coord");
			
			String name = jobj.get("name").toString();
			String id = jobj.get("id").toString();
			name = name.substring(1, name.length()-1);
			String country = getSys.get("country").toString();
			country = country.substring(1, country.length()-1);
			String temp_min = getMain.get("temp_min").toString();
			String temp_max = getMain.get("temp_max").toString();
			String Wspeed = getWind.get("speed").toString();
			String humidity = getMain.get("humidity").toString();
			String lon = getCoord.get("lon").toString();
			String lat = getCoord.get("lat").toString();
			String currTemp = getMain.get("temp").toString();
			long sunrise = getSys.get("sunrise").getAsInt();
			Date rise = new Date(sunrise*1000L); 
			SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
			String formattedrise = sdf.format(rise);
			formattedrise += " am";
			long sunset = getSys.get("sunset").getAsInt();
			Date set = new Date(sunset*1000L); 
			sdf = new SimpleDateFormat("h:mm");
			String formattedset = sdf.format(set);
			formattedset += " pm";
			String retVal = name+"|"+country+"|"+temp_min+"|"+temp_max+"|"+Wspeed+"|"+humidity+"|"+lon+"|"+lat+"|"+currTemp+"|"+formattedrise+"|"+formattedset+"|"+id;
			
			return retVal;
		}
		catch(Exception e) {
		}
		return null;
	}
	

	public String getAPIWeatherDataLoc(String desiredCity) {
		String url = "https://api.openweathermap.org/data/2.5/weather?q="+desiredCity+"&dt=UTC&units=imperial&APPID=60c9239a644872ad319e3e083583953e";
		StringBuilder result = new StringBuilder();
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
		         result.append(line);
		    }
			rd.close();
			String jsonString = result.toString();
			JsonObject jobj = new Gson().fromJson(jsonString, JsonObject.class);
			JsonObject getSys = jobj.getAsJsonObject("sys");
			JsonObject getMain = jobj.getAsJsonObject("main");
			JsonObject getWind = jobj.getAsJsonObject("wind");
			JsonObject getCoord = jobj.getAsJsonObject("coord");
			
			String name = jobj.get("name").toString();
			name = name.substring(1, name.length()-1);
			String country = getSys.get("country").toString();
			country = country.substring(1, country.length()-1);
			String temp_min = getMain.get("temp_min").toString();
			String temp_max = getMain.get("temp_max").toString();
			String Wspeed = getWind.get("speed").toString();
			String humidity = getMain.get("humidity").toString();
			String lon = getCoord.get("lon").toString();
			String lat = getCoord.get("lat").toString();
			String currTemp = getMain.get("temp").toString();
			long sunrise = getSys.get("sunrise").getAsInt();
			Date rise = new Date(sunrise*1000L); 
			SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
			String formattedrise = sdf.format(rise);
			formattedrise += " am";
			long sunset = getSys.get("sunset").getAsInt();
			Date set = new Date(sunset*1000L); 
			sdf = new SimpleDateFormat("h:mm");
			String formattedset = sdf.format(set);
			formattedset += " pm";
			String retVal = name+"|"+country+"|"+temp_min+"|"+temp_max+"|"+Wspeed+"|"+humidity+"|"+lon+"|"+lat+"|"+currTemp+"|"+formattedrise+"|"+formattedset;
			
			return retVal;
		}
		catch(Exception e) {
		}
		return null;
	}
	
	public ArrayList<String> getAPIWeatherDataLatLon(String lat, String lon) {
		String url = "https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&units=imperial&APPID=60c9239a644872ad319e3e083583953e";
		StringBuilder result = new StringBuilder();
		ArrayList<String> cityData = new ArrayList<String>();
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
		         result.append(line);
		    }
			rd.close();
			String jsonString = result.toString();
			JsonObject jobj = new Gson().fromJson(jsonString, JsonObject.class);
			JsonObject getSys = jobj.getAsJsonObject("sys");
			JsonObject getMain = jobj.getAsJsonObject("main");
			JsonObject getWind = jobj.getAsJsonObject("wind");
			JsonObject getCoord = jobj.getAsJsonObject("coord");
			
			String name = jobj.get("name").toString();
			String id = jobj.get("id").toString();
			name = name.substring(1, name.length()-1);
			String country = getSys.get("country").toString();
			country = country.substring(1, country.length()-1);
			String temp_min = getMain.get("temp_min").toString();
			String temp_max = getMain.get("temp_max").toString();
			String Wspeed = getWind.get("speed").toString();
			String humidity = getMain.get("humidity").toString();
			String lon1 = getCoord.get("lon").toString();
			String lat1 = getCoord.get("lat").toString();
			String currTemp = getMain.get("temp").toString();
			long sunrise = getSys.get("sunrise").getAsInt();
			Date rise = new Date(sunrise*1000L); 
			SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
			String formattedrise = sdf.format(rise);
			formattedrise += " am";
			long sunset = getSys.get("sunset").getAsInt();
			Date set = new Date(sunset*1000L); 
			sdf = new SimpleDateFormat("h:mm");
			String formattedset = sdf.format(set);
			formattedset += " pm";
			cityData.add(name+"|"+country+"|"+temp_min+"|"+temp_max+"|"+Wspeed+"|"+humidity+"|"+lon1+"|"+lat1+"|"+currTemp+"|"+formattedrise+"|"+formattedset+"|"+id);
			
			return cityData;
		}
		catch(Exception e) {
		}
		return null;
	}
}
