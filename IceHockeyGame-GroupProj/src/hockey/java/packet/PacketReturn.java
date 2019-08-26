package hockey.java.packet;

public class PacketReturn {
	public int status;
	public int dbid;
	public int playerNum;
	public String username;
	public String message;
	public boolean isGuest;
	//public int playerID;
	
	public PacketReturn(int status, int id) {
		this.status = status;
		this.dbid = id;
	}
	
	public PacketReturn() {} // for KryoNet; otherwise cannot be deserialized
	
	public PacketReturn(int status) {
		this.status = status;
	}
	public PacketReturn(int status, int id, String username, int playernNum) { //this is for sending p
		this.status = status;
		this.dbid = id;
		this.username = username;
		this.playerNum = playernNum;
	}
	public PacketReturn(int status, int id, String username) {
		this.status = status;
		this.dbid = id;
		this.username = username;
	}
	public PacketReturn(int status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public PacketReturn(int status, String message, boolean isGuest) {
		this.status = status;
		this.message = message;
		this.isGuest = isGuest;
	}
	
	
	
}
