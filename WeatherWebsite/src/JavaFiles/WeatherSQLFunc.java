package hw3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class WeatherSQLFunc {
	
	public String loginValidation(String user, String pass) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/WeatherAssignment3?user=root&password=doghot&useSSL=false");
			st = conn.createStatement();
			rs = st.executeQuery("Select * from Account Where username='" + user + "'");
			if(!rs.next()) {
				conn.close();
				return "userWrong";
			}
			rs = st.executeQuery("Select * from Account Where username='" + user + "' and password='" + pass + "'");
			if(rs.next()) {
				conn.close();
				return "valid";
			}
			else {
				conn.close();
				return "passWrong";
			}
		}catch (SQLException sqle) {
			System.out.println (sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println (cnfe.getMessage());
		} finally {}
		return "userWrong";
	}

	public boolean registerUser(String user, String pass) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/WeatherAssignment3?user=root&password=doghot&useSSL=false");
			st = conn.createStatement();
			rs = st.executeQuery("Select * from Account Where username='" + user + "'");
			if(rs.next()) {
				conn.close();
				return false;
			}
			else {
				st.executeUpdate("INSERT INTO Account (username, password) VALUES ('" + user + "', '" + pass +"')");
				conn.close();
				return true;
			}
		}catch (SQLException sqle) {
			System.out.println (sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println (cnfe.getMessage());
		} finally {}
		return false;
	}
	
	public void insertProfileHistory(String searched, String currUser) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int userID = -1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/WeatherAssignment3?user=root&password=doghot&useSSL=false");
			st = conn.createStatement();
			rs = st.executeQuery("Select * from Account Where username='" + currUser + "'");
			if(rs.next()) userID = rs.getInt("userID");
			rs = st.executeQuery("Select * from History Where pageName='" + searched + "'");
			if(rs.next()) {
				st.executeUpdate("DELETE FROM History WHERE pageName='" + searched + "';");
			}
			st.executeUpdate("INSERT INTO History (userID, pageName) VALUES ('" + userID + "', '" + searched +"')");
			conn.close();
		
		}catch (SQLException sqle) {
			System.out.println (sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println (cnfe.getMessage());
		} finally {}
	}
	
	public ArrayList<String> searchHistory(String currUsername){
		ArrayList<String> history = new ArrayList<String>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int userID = -1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/WeatherAssignment3?user=root&password=doghot&useSSL=false");
			st = conn.createStatement();
			rs = st.executeQuery("Select * from Account Where username='" + currUsername + "'");
			if(rs.next()) userID = rs.getInt("userID");
			rs = st.executeQuery("Select pageName from History Where userID='" + userID + "'");
			while(rs.next()) {
				String temp = rs.getString("pageName");
				history.add(temp);
			}
			conn.close();
			return history;
		}catch (SQLException sqle) {
			System.out.println (sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println (cnfe.getMessage());
		} finally {}
		return null;
		
	}
	
}
