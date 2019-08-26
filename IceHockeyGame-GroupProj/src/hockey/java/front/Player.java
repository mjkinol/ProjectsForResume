package hockey.java.front;

public class Player {
	
	
	private String username;
	private PVector mouse;
	
	private int score;
	private int playerID; // 1 or 2
	public Player(int id) {
		
		playerID = id;
		mouse = new PVector(0, 0);
		score = 0;
	}
	
	public void score() {
		score++;
	}
	
	public String getUsername() {
		return username;
	}
	
	public PVector getMouse() {
		return mouse;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getPlayerID() {
		return playerID;
	}

	public void setScore(int i) {
		score = i;
		
	}
	
}
