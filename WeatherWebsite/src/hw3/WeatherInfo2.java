package hw3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WeatherInfo2 {
	
	public ArrayList<String> getLatLongData(String lat, String lng, String realPath) {
		ArrayList<String> cityData = new ArrayList<String>();
		try {
			FileReader reader = new FileReader(realPath);
			BufferedReader buff = new BufferedReader(reader);
			String line = buff.readLine();
			String[] retrievedCity = line.split("\\|");
			while(line != null) {
				if(retrievedCity[3].trim().equals(lat.trim()) && retrievedCity[4].trim().equals(lng.trim())) {
					break;
				}
				line = buff.readLine();
				if(line == null) break;
				retrievedCity = line.split("\\|");
			}
			buff.close();
			reader.close();
			if(line == null && !retrievedCity[3].trim().equals(lat.trim())) {
				return null;
			}
			if(line == null && !retrievedCity[4].trim().equals(lng.trim())) {
				return null;
			}
			else {
				cityData.add(line);
				return cityData;
			}
		} catch(FileNotFoundException fnfe) {
			return null;
		} catch(IOException ioe) {
			return null;
		}
		
	}
	
	public ArrayList<String> getAllData(String realPath) {
		ArrayList<String> cityData = new ArrayList<String>();
		try {
			FileReader reader = new FileReader(realPath);
			BufferedReader buff = new BufferedReader(reader);
			String line = buff.readLine();
			while(line != null) {
				cityData.add(line);
				line = buff.readLine();
			}
			buff.close();
			reader.close();
			return cityData;
		} catch(FileNotFoundException fnfe) {
			return null;
		} catch(IOException ioe) {
			return null;
		}
	}			
	
	public ArrayList<String> getCityData(String cityName, String realPath) {
		ArrayList<String> cityData = new ArrayList<String>();
		try {
			FileReader reader = new FileReader(realPath);
			BufferedReader buff = new BufferedReader(reader);
			String line = buff.readLine();
			String[] retrievedCity = line.split("\\|");
			while(line != null && !retrievedCity[0].toLowerCase().trim().equals(cityName.toLowerCase().trim())) {
				line = buff.readLine();
				if(line == null) break;
				retrievedCity = line.split("\\|");
			}
			buff.close();
			reader.close();
			if(line == null && !retrievedCity[0].toLowerCase().trim().equals(cityName.toLowerCase().trim())) {
				return null;
			}
			else {
				cityData.add(line);
				return cityData;
			}
		} catch(FileNotFoundException fnfe) {
			return null;
		} catch(IOException ioe) {
			return null;
		}
	}
	
	//figure how to output these to html correctly
	public boolean formatCheck(String realPath) {
		try {
			FileReader reader = new FileReader(realPath);
			BufferedReader buff = new BufferedReader(reader);
			String line = buff.readLine();
			if(line == null) {
				//System.out.println("This file is empty.");
				buff.close();
				reader.close();
				return false;
			}
			while(line != null){
				String[] data = line.split("\\|");
				if(data.length < 16) {
					//System.out.println("Theres not enough parameters on line '" + line +"'.");
					buff.close();
					reader.close();
					return false;
				}
				try {
					Float.parseFloat(data[3]);
				} catch(Exception a) {
					//System.out.println("Unable to convert the '" + data[3] + "' to an float.");
					buff.close();
					reader.close();
					return false;
				}
				try {
					Float.parseFloat(data[4]);
				} catch(Exception a) {
					//System.out.println("Unable to convert the '" + data[4] + "' to an float.");
					buff.close();
					reader.close();
					return false;
				}
				try {
					Integer.parseInt(data[7]);
				} catch(Exception b) {
					//System.out.println("Unable to convert the '" + data[7] + "' to an int.");
					buff.close();
					reader.close();
					return false;
				}
				try {
					Integer.parseInt(data[8]);
				} catch(Exception c) {
					//System.out.println("Unable to convert the '" + data[8] + "' to an int.");
					buff.close();
					reader.close();
					return false;
				}
				try {
					Integer.parseInt(data[9]);
				} catch(Exception d) {
					//System.out.println("Unable to convert the '" + data[9] + "' to an int.");
					buff.close();
					reader.close();
					return false;
				}
				try {
					Integer.parseInt(data[10]);
				} catch(Exception e) {
					//System.out.println("Unable to convert the '" + data[10] + "' to a int.");
					buff.close();
					reader.close();
					return false;
				}
				try {
					Float.parseFloat(data[11]);
				} catch(Exception f) {
					//System.out.println("Unable to convert the '" + data[11] + "' to a float.");
					buff.close();
					reader.close();
					return false;
				}
				try {
					Float.parseFloat(data[12]);
				} catch(Exception g) {
					//System.out.println("Unable to convert the '" + data[12] + "' to a float.");
					buff.close();
					reader.close();
					return false;
				}
				try {
					Float.parseFloat(data[13]);
				} catch(Exception g) {
					//System.out.println("Unable to convert the '" + data[13] + "' to a float.");
					buff.close();
					reader.close();
					return false;
				}
				try {
					Integer.parseInt(data[14]);
				} catch(Exception h) {
					//System.out.println("Unable to convert the '" + data[14] + "' to an int.");
					buff.close();
					reader.close();
					return false;
				}
				line = buff.readLine();
			}
			buff.close();
			reader.close();
		} catch(FileNotFoundException fnfe) {
			System.out.println("The file could not be found.");
			return false;
		} catch(IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
			return false;
		}
		finally {}
		return true;
	}
}
