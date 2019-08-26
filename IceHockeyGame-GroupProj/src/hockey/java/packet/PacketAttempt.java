package hockey.java.packet;

public class PacketAttempt {
	public int attempt;	

	public int id = -1;
	public String username = null;

	public String password = null;
	public String confirm = null;
	
	public PacketAttempt() {} // default
	public PacketAttempt(int attempt) { // play guest
		this(attempt,-1,null,null,null);
	}
	public PacketAttempt(int attempt, int id) { // signout, get stats, playLogged
		this(attempt,id,null,null,null);
	}
	public PacketAttempt(int attempt, int id, String username) { // signout, get stats, playLogged
		this(attempt,id,username,null,null);
	}
	public PacketAttempt(int attempt, String username, String password) { // login
		this(attempt,-1,username,password,null);
	}				
	public PacketAttempt(int attempt, String username, String password, String confirm) { // signup
		this(attempt,-1,username,password,confirm);
	}
	public PacketAttempt(int attempt, int id, String username, String password, String confirm) { // signup
		this.attempt = attempt;
		this.id = id;
		this.username = username;
		this.password = password;
		this.confirm = confirm;
	}
}
