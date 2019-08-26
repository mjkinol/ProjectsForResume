package hockey.java.database;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import hockey.java.Master;
import hockey.java.front.User;
import hockey.java.packet.Constants;
import hockey.java.packet.PacketReturn;
import hockey.java.packet.PacketStats;

public class SQLModel {
	
	// https://stackoverflow.com/questions/17213761/check-login-string-against-password-mysql-jdbc
	// hash password
	public static String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest encoder = MessageDigest.getInstance("SHA-256"); 
	    encoder.update(password.getBytes(), 0, password.length());
	    String sha = new BigInteger(1, encoder.digest()).toString(16); // Encrypted 
	    return sha;
	}
	
	//private boolean singlePlayerDebug = true;
	
	Connection connection = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String query = "";
	
	public SQLModel() {
		//if(singlePlayerDebug) return;
		connection = SQLConnection.Connector();
		if(connection == null) {
			System.out.println("connection failed");
			System.exit(1);
		} else {
			System.out.println("Server connected to DB!");
		}
		
	}
	
	public boolean isDbConnected() {
		//if(singlePlayerDebug) return true;
		try{
			return !(connection.isClosed());		
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public PacketReturn checkSignUp(String username, String pw, String cpw, com.esotericsoftware.kryonet.Connection c) {
		
		if(username == null || username.equals("") || pw == null || pw.equals("") || cpw == null || cpw.equals("")) { //they shouldn't be empty
			//check = false;
			System.out.println("username or password is empty");
			return new PacketReturn(Constants.SIGNUPFAILURE, "username or password is empty");
		}
		else if(!pw.equals(cpw)){ //password and confirm password should be the same
			//check = false;
			System.out.println("confirm password is different");
			return new PacketReturn(Constants.SIGNUPFAILURE, "confirm password is different");
		}
		else {
			try {
				System.out.println("testing user existence");
				ps = connection.prepareStatement("SELECT * FROM Player WHERE username=?");
				ps.setString(1, username);
				rs = ps.executeQuery();	
				if(rs.next()) {
				    //check = false; 
				    System.out.println("username is already taken");
					return new PacketReturn(Constants.SIGNUPFAILURE, "username is already taken");
				} else {
					System.out.println("inserting user to db");
					//no error, update new player in database
					ps = connection.prepareStatement("INSERT INTO Player (username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, username);
					ps.setString(2, hashPassword(pw)); // hash password
					ps.executeUpdate();
					
					//ps = connection.prepareStatement("SELECT * FROM Player WHERE username=?");
					//ps.setString(1, username);
					//ps.executeUpdate();
					rs = ps.getGeneratedKeys();	
					int id = -1;
					if(rs.next()) {
						id = (int)rs.getLong(1);
					}
					
					
					User tmp = new User(id);
					tmp.setUsername(username);
					//System.out.println("After setUser(), Hockey.getUser().getUsername() is "+ Hockey.getUser().getUsername());
					Master.getConnections().put(id,c);
					Master.getUsers().put(id, tmp);
					return new PacketReturn(Constants.LOGINSUCCESS, id, username);
				}
			}
			catch(SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
				//check = false;
				return new PacketReturn(Constants.SIGNUPFAILURE, "SQL error.");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new PacketReturn(Constants.SIGNUPFAILURE, "Hashing error.");
				
			} finally {
				try {
					if(rs != null) {rs.close();}
					if(ps != null) {ps.close();}
				}catch(SQLException sqle) {
					System.out.println("sqle closing stuff: " + sqle.getMessage());
				}		
			} 
		}
		
	}
	
	public PacketReturn checkLogin(String username, String pw, com.esotericsoftware.kryonet.Connection c) {
		System.out.println("starting checkLogin");
		if(username == null || username == "" || pw == null || pw == "") { //they shouldn't be empty
			return new PacketReturn(Constants.LOGINFAILURE, "username or password is empty");
		}
		else {
			try {
//				System.out.println("111111111");
				ps = connection.prepareStatement("SELECT * FROM Player WHERE username=?");
				ps.setString(1, username);
				rs = ps.executeQuery();
				if(!rs.next()) {
		            //check = false; //user does not exist
					return new PacketReturn(Constants.LOGINFAILURE, "user does not exist");
				}
				else {
					//p.username = username;
					//p.id = rs.getInt(1);

//					System.out.println("2222222");
					
					String dbpw = rs.getString(3);						
					if(!dbpw.equals(hashPassword(pw))) { //once found this name exists, look at pw in database
			            //check = false; //password does not match with database			
						return new PacketReturn(Constants.LOGINFAILURE, "password is incorrect");
					}
				}
				//check = true;
				//p.status = 3;
//				System.out.println("3333333");
				User u = new User(rs.getInt(1),username);
				
				Master.getConnections().put(rs.getInt(1), c);
				Master.getUsers().put(rs.getInt(1), u); //create a new user and put it in map
				return new PacketReturn(Constants.LOGINSUCCESS, rs.getInt(1), username);
			} catch (SQLException e) {
				System.out.println("sqle: " + e.getMessage());
				//check = false;
				return new PacketReturn(Constants.LOGINFAILURE, "SQL error.");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new PacketReturn(Constants.LOGINFAILURE, "Hashing error.");
			}		
		}		
	}
	
	public PacketReturn signAsGuest(com.esotericsoftware.kryonet.Connection c) {
		try {
			System.out.println("trying to insert a guest");
			
			ps = connection.prepareStatement("INSERT INTO Player (username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);			
			ps.setString(1, "GUEST");
			ps.setString(2, "GUEST");
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			int id = -1;
			if(rs.next()) {
				id = (int)rs.getLong(1);
			}
			//rs = ps.executeQuery();
			System.out.println("insertion done");
			System.out.println("guest id is " + id);
			
			User u = new User(rs.getInt(1),"GUEST");
			
			Master.getConnections().put(id, c);
			Master.getUsers().put(id, u); //put it in online users map
			return checkList(id);			
			
		}catch (SQLException e) {
			System.out.println("sqle: " + e.getMessage());
			return new PacketReturn(Constants.SIGNUPFAILURE, "SQL error.");
		}		
	}
	
	public PacketReturn loggedPlay(String username, com.esotericsoftware.kryonet.Connection c) {
		int id = -1;
		try {
			ps = connection.prepareStatement("SELECT * FROM Player WHERE username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getInt(1);
				System.out.println("user "+username +" has id: "+id);
			} else {
				System.out.println("NO user "+username +" has id: "+id);
			}
		}catch (SQLException e) {
			System.out.println("sqle: " + e.getMessage());
		}
		return checkList(id);
	}
	
	public PacketReturn checkList(int id) { //process all online users
//		String username = "";
//		try {
//			ps = connection.prepareStatement("SELECT * FROM Player WHERE playerID=?");
//			ps.setString(1, String.valueOf(id));
//			rs = ps.executeQuery();
//			if(rs.next()) {
//				username = rs.getString(2);
//			}
//		}catch (SQLException e) {
//			System.out.println("sqle: " + e.getMessage());
//		}
		
		if(Master.getPlayerlist().size() == 0 && Master.getWaitlist().size() == 0) {
			Master.getPlayerlist().add(id);
			return new PacketReturn(Constants.PLAYFAILUREFEW, id, "Not Enough Players.\n Please Wait.");
		}
		else if(Master.getPlayerlist().size() == 1 && Master.getWaitlist().size() == 0) {		
			Master.getPlayerlist().add(id);
			return new PacketReturn(Constants.PLAYSUCCESS, id);			
		}
		else{
			Master.getWaitlist().add(id);
			return new PacketReturn(Constants.PLAYFAILUREMANY, id, "Game already in progress.\n Please Wait.");
		}   
	}
	
	public PacketStats getStats(int id) {
		try {
			ps = connection.prepareStatement("SELECT * FROM Player WHERE playerID=?");
			ps.setString(1, Integer.toString(id));
			rs = ps.executeQuery();
			rs.next();
			int matchW = rs.getInt(4);
			int matchL = rs.getInt(5);
			int goalsFor = rs.getInt(6);
			int goalsAgainst = rs.getInt(7);
			return new PacketStats(matchW, matchL, goalsFor, goalsAgainst);
			
		} catch (SQLException e) {
			System.out.println("sqle: " + e.getMessage());
			return null;
		}		
	}
	
	public PacketReturn updateStats(int dbid, String result) {
		try {
			
			ps = connection.prepareStatement("SELECT * FROM Player WHERE playerID=?");
			ps.setInt(1, dbid);
//			ps.setString(1, Integer.toString(dbid));
			rs = ps.executeQuery();
			rs.next();
			String username = rs.getString(2);
//			int matchW = rs.getInt(4);
//			int matchL = rs.getInt(5);
			int matchW = rs.getInt(5);
			int matchL = rs.getInt(4);
			int goalsFor = rs.getInt(6);
			int goalsAgainst = rs.getInt(7);
			
			
//			String username = "";
//			ps = connection.prepareStatement("SELECT * FROM Player WHERE playerID=?");
//			ps.setString(1, Integer.toString(dbid));
//			if(rs.next()) {
				
			//}
			
			
			if(username.equals("GUEST")) {
				//does not update db, only show result message
				System.out.println("GUEST!!!!!!");
				return new PacketReturn(Constants.GAMEOVER, result, true);
			}
			else {
				ps = connection.prepareStatement("UPDATE Player SET matchesWon=?,  matchesLost=? "
						+ ", goalsFor=?, goalsAgainst=? WHERE playerID=?");
				ps.setString(5, Integer.toString(dbid));
				//rs = ps.executeQuery();
				//rs.next();
				if(result.equals("YOU WON!!!")){
					ps.setInt(1, matchW+1);
					ps.setInt(2, matchL);
				}
				else{
					ps.setInt(1, matchW);
					ps.setInt(2, matchL+1);
				}
				ps.setInt(3, goalsFor+Master.getGoalsFor(dbid));
				ps.setInt(4, goalsAgainst+Master.getGoalsAgainst(dbid));
				ps.executeUpdate();
				System.out.println("After update stats in db");
				

				System.out.println("LOGGGGGGED!!!!!!");
				return new PacketReturn(Constants.GAMEOVER, result, false);
			}
			
		}catch (SQLException e) {
			System.out.println("sqle: " + e.getMessage());
			return new PacketReturn(Constants.GAMEOVER, "SQL Error", false);
		}
	}
}
