package hockey.java.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import hockey.java.network.NetworkHelper;

public class SQLConnection {

	public static Connection Connector() { //trying to connect to database
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hockeymaster?user=" + NetworkHelper.username + "&password=" + NetworkHelper.password + "&serverTimezone=UTC");
			return conn;
		} catch (SQLException e) {
			System.out.println("sqle: "+e.getMessage());
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println("e: "+e.getMessage());
			return null;
		}
	}
}
