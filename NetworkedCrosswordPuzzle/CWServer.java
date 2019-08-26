import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CWServer {
	
	private Vector<CWServerThread> cwServerThreads;
	private static Vector<Lock> locksVec;
	private static Vector<Condition> consVec;
	private static int[] scores;
	private int numPlayers;
	private static int turn;
	
	public CWServer(int port) {
		ServerInit(port);
	}
	
	public void ServerInit(int port) {
		ServerSocket ss = null;
		try {
			System.out.println("Trying to bind to port " + port);
			ss = new ServerSocket(port);
			System.out.println("Bound to port " + port);
			System.out.println("");
			System.out.println("Listening on port "+port);
			System.out.println("Waiting for players...");
			System.out.println("");
			cwServerThreads = new Vector<CWServerThread>();
			locksVec = new Vector<Lock>();
			consVec = new Vector<Condition>();
			numPlayers = 0;
			// you need to create the the thread class to be able to communicate to all of the clients
			while(true) {
				//accepts all connections initially. Reads info to server
				Socket s = ss.accept();
				System.out.println("Connection from " + s.getInetAddress());
				System.out.println("");
				Lock lock = new ReentrantLock();
				Condition con = lock.newCondition();
				CWServerThread st = new CWServerThread(s, this);
				cwServerThreads.add(st);
				locksVec.add(lock);
				consVec.add(con);
				
				//handles first connection
				if(cwServerThreads.size() == 1) {
					while(numPlayers == 0) {
						cwServerThreads.get(0).sendMessage("How many players will there be?");
						String temp = cwServerThreads.get(0).readMessage();
						cwServerThreads.get(0).sendMessage(" ");
						int caught = 0;
						try {
							numPlayers = Integer.parseInt(temp);
						} catch(NumberFormatException nfe) {
							numPlayers = 0;
							caught = 1;
							cwServerThreads.get(0).sendMessage("Please enter a valid number between 1 and 3.");
						}
						if((numPlayers < 1 || numPlayers > 3) && caught != 1) {
							cwServerThreads.get(0).sendMessage("Please enter a valid number between 1 and 3.");
							numPlayers = 0;
						}
					}
					System.out.println("Number of players: " + numPlayers);
					System.out.println("Reading random game file.");
					if(numPlayers == 1) {
						cwServerThreads.get(0).sendMessage("The game is beginning.");
						System.out.println("Game can now begin.");
						break;
					}
					cwServerThreads.get(0).sendMessage(" ");
					cwServerThreads.get(0).sendMessage("Wating for player 2.");
					System.out.println(" ");
					System.out.println("Wating for player 2.");
				}
				//handles second connection if needed
				else if(cwServerThreads.size() == 2) {
					cwServerThreads.get(0).sendMessage("Player 2 joined from " + s.getInetAddress());
					cwServerThreads.get(0).sendMessage(" ");
					cwServerThreads.get(1).sendMessage("There is already a game waiting for you");
					cwServerThreads.get(1).sendMessage(" ");
					cwServerThreads.get(1).sendMessage("Player 1 has already joined");
					cwServerThreads.get(1).sendMessage(" ");
					if(numPlayers == 2) {
						cwServerThreads.get(0).sendMessage(" ");
						cwServerThreads.get(0).sendMessage("The game is beginning.");
						cwServerThreads.get(1).sendMessage(" ");
						cwServerThreads.get(1).sendMessage("The game is beginning.");
						System.out.println("Game can now begin.");
						break;
					}
					cwServerThreads.get(0).sendMessage("Wating for player 3.");
					cwServerThreads.get(1).sendMessage("Wating for player 3.");
					System.out.println("Wating for player 3.");
				}
				//handles third connection if needed
				else if(cwServerThreads.size() == 3) {
					cwServerThreads.get(0).sendMessage("Player 3 joined from " + s.getInetAddress());
					cwServerThreads.get(0).sendMessage(" ");
					cwServerThreads.get(1).sendMessage("Player 3 joined from " + s.getInetAddress());
					cwServerThreads.get(1).sendMessage(" ");
					cwServerThreads.get(2).sendMessage("There is already a game waiting for you");
					cwServerThreads.get(2).sendMessage(" ");
					cwServerThreads.get(2).sendMessage("Player 1 has already joined");
					cwServerThreads.get(2).sendMessage("Player 2 has already joined");
					cwServerThreads.get(2).sendMessage(" ");
					for(CWServerThread cwst : cwServerThreads) {
						cwst.sendMessage("The game is beginning.");
					}
					System.out.println(" ");
					System.out.println("Game can now begin.");
					break;
				}
			}
			System.out.println(" ");
			System.out.println("Sending game board.");
	
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		} finally {
			try {
				if(ss != null) ss.close();
			} catch (IOException ioe) {
				System.out.println("ioe closing ss: " + ioe.getMessage());
			}
		}
	}
	
	//broadcast from a player to all other players 
	public void broadcastFromPlayer(String message, CWServerThread currentST) {
		if(message != null) {
			System.out.println(message);
			for(CWServerThread st : cwServerThreads) {
				if(st != currentST) {
					st.sendMessage(message);
				}
			}
		}
	}
	
	//broadcast to all connected players
	public void broadcastFromServer(String message) {
		for(CWServerThread st : cwServerThreads) {
			st.sendMessage(message);
		}
	}
	
	//send the game board to all current players
	public void sendCurrentboard(CrossWordFunc cwF) {
		for(CWServerThread st : cwServerThreads) {
			for(int i = 0; i < cwF.iSize; i++) {
				for(int j = 0; j < cwF.jSize; j++) {
					st.sendMessageNoPL(cwF.displayboard[i][j]);
				}
				st.sendMessage("");
			}
			st.sendMessage("");
			for(int i = 0; i < cwF.questions.size(); i++) {
				st.sendMessage(cwF.questions.get(i));
			}
		}
	}
	
	//get the current players guess
	public static void getPlayerGuess(CWServer cwS, CrossWordFunc cwFunc) {
		//synchronization 
		locksVec.get(turn-1).lock();
		consVec.get(turn-1).signal();
		locksVec.get(turn-1).unlock();
		boolean valid = false;
		String direction = "";
		int num = -1;
		while(!valid) {
			cwS.cwServerThreads.get(turn-1).sendMessage("Would you like to answer a question across (a) or down (d)?");
			//need to add a lock here so only player 1 can type
			direction = cwS.cwServerThreads.get(turn-1).readMessage();
			while(!direction.equalsIgnoreCase("a") && !direction.equalsIgnoreCase("d")) {
				cwS.cwServerThreads.get(turn-1).sendMessage("Invlaid direction. Re-enter a direction.");
				direction = cwS.cwServerThreads.get(turn-1).readMessage();
			}
			cwS.cwServerThreads.get(turn-1).sendMessage("Which number?");
			try {
				num = Integer.parseInt(cwS.cwServerThreads.get(turn-1).readMessage());
			} catch(NumberFormatException ie){
				cwS.cwServerThreads.get(turn-1).sendMessage("That is not a valid option");
			}

			if(num != -1) {
			//make sure the entered numbers are valid
				for(int i = 0 ; i < cwFunc.data.size(); i++) {
					if(cwFunc.data.get(i).across == true && direction.equalsIgnoreCase("a") && cwFunc.data.get(i).num == num) {
						valid = true;
						for(int j = 0; j < cwFunc.completed.size(); j++) {
							if(cwFunc.completed.get(j).equalsIgnoreCase(cwFunc.data.get(i).answer)) {
								valid = false;
							}
						}
					}
					if(cwFunc.data.get(i).across == false && direction.equalsIgnoreCase("d") && cwFunc.data.get(i).num == num) {
						valid = true;
						for(int j = 0; j < cwFunc.completed.size(); j++) {
							if(cwFunc.completed.get(j).equalsIgnoreCase(cwFunc.data.get(i).answer)) {
								valid = false;
							}
						}
					}
				}
				if(!valid) {
					cwS.cwServerThreads.get(turn-1).sendMessage("That is not a valid option");
				}
			}
		}
			
		if(direction.equalsIgnoreCase("d")) cwS.cwServerThreads.get(turn-1).sendMessage("What is your guess for " + num + " down?");
		else if(direction.equalsIgnoreCase("a")) cwS.cwServerThreads.get(turn-1).sendMessage("What is your guess for " + num + " across?");
		
		//get the answer
		String currAnswer = cwS.cwServerThreads.get(turn-1).readMessage();
		
		//tell users and server what the guess was
		
		if(direction.equalsIgnoreCase("d")) cwS.broadcastFromPlayer("Player " + turn + " guessed '" + currAnswer + "' for " + num +" down.", cwS.cwServerThreads.get(turn-1));
		else if(direction.equalsIgnoreCase("a")) cwS.broadcastFromPlayer("Player " + turn + " guessed '" + currAnswer + "' for " + num +" across.", cwS.cwServerThreads.get(turn-1));
		cwS.broadcastFromPlayer("  ", cwS.cwServerThreads.get(turn-1));
		
		boolean correct = cwFunc.tryUpdateBoard(currAnswer, num, direction);
		
		if(correct) {
			cwS.broadcastFromServer("That is correct!");
			System.out.println("That is correct.");
			String deleteQ = "";
			for(int i = 0; i < cwFunc.data.size(); i++) {
				if(cwFunc.data.get(i).across == true){
					if(cwFunc.data.get(i).num == num && direction.equalsIgnoreCase("a")) {
						deleteQ = cwFunc.data.get(i).question;
						break;
					}
				}
				if(cwFunc.data.get(i).across == false) {
					if(cwFunc.data.get(i).num == num && direction.equalsIgnoreCase("d")) {
						deleteQ = cwFunc.data.get(i).question;
						break;
					}
				}
			}
			for(int i = 0; i < cwFunc.questions.size(); i++) {
				if(cwFunc.questions.get(i).contains(deleteQ)) {
					cwFunc.questions.remove(i);
					break;
				}
			}
			scores[turn-1]++;
		}
		//if the player is wrong it is the next players turn
		else{
			cwS.broadcastFromServer("That is incorrect.");
			System.out.println("That is incorrect.");
			turn++;
		}
	}
	
	public static void gamePlay(CWServer cwS) {
		CrossWordFunc cwFunc = new CrossWordFunc();
		scores = new int[cwS.cwServerThreads.size()];
		for(int i = 0; i < cwS.cwServerThreads.size(); i++) {
			scores[i] = 0;
		}
		cwFunc.GameBoardInit();
		boolean gameOver = false;
		if(cwFunc.noValidFileFormat == true) {
			System.out.println("No valid game files. Ending game.");
			cwS.broadcastFromServer("No valid game files. Ending game.");
			gameOver = true;
		}
		if(cwFunc.noValidFiles == true) {
			System.out.println("No valid gameboard can be made. Ending game.");
			cwS.broadcastFromServer("No valid gameboard can be made. Ending game.");
			gameOver = true;
		}
		turn = 1;
		while(!gameOver) {
			cwS.sendCurrentboard(cwFunc);
			System.out.println(" ");
			System.out.println("Player " + turn + "'s turn.");
			cwS.broadcastFromServer(" ");
			cwS.broadcastFromServer("Player " + turn + "'s Turn.");
			cwS.broadcastFromServer(" ");
			getPlayerGuess(cwS, cwFunc);
			if(cwFunc.completed.size() == cwFunc.data.size()) {
				gameOver = true;
				System.out.println("The game has concluded.");
				System.out.println("Send the scores.");
				cwS.sendCurrentboard(cwFunc);
				cwS.broadcastFromServer(" ");
				cwS.broadcastFromServer("Final Score");
				int winnerScore = -1;
				int winnerIndex = 0;
				for(int i = 1; i <= cwS.cwServerThreads.size(); i++) {
					cwS.broadcastFromServer("Player " + i + " - " + scores[i-1] + " correct answers");
					if(scores[i-1] > winnerScore) {
						winnerIndex = i;
						winnerScore = scores[i-1];
					}
				}
				cwS.broadcastFromServer("Player " + winnerIndex + " is the winner.");
				cwS.broadcastFromServer("^^kill~`");
			}
			if(turn > cwS.cwServerThreads.size()) turn = 1;
		}
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < 1; i++) {
			CWServer cwServer = new CWServer(3456);
			gamePlay(cwServer);
			i--;
		}
	}
}
