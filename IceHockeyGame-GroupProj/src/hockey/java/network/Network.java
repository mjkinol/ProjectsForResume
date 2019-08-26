package hockey.java.network;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import hockey.java.Hockey;
import hockey.java.Master;
import hockey.java.controller.GameController;
import hockey.java.front.Goal;
import hockey.java.front.Striker;
import hockey.java.front.User;
import hockey.java.packet.Constants;
import hockey.java.packet.PacketPU;
import hockey.java.packet.PacketPuck;
import hockey.java.packet.PacketReturn;
import hockey.java.packet.PacketStats;
import hockey.java.packet.PacketStriker;
import javafx.application.Platform;

public class Network extends Listener{

	//private static Hockey hockey;
	private static Client client = new Client();;
	private static String ip = NetworkHelper.client_ngrok_url;
	private static int port = NetworkHelper.client_tcpPort;
	
	public Network(/*Hockey hockey*/) {
	
		System.out.println("Starting Network constructor");

		// register packet
		NetworkHelper.registerClasses(client.getKryo());

		client.start();


		try {
			System.out.println("Client trying to connect to " + ip + ":" + port);
			client.connect(5000, ip, port); //blocks for 5 seconds
		} catch (IOException e) {
			System.out.println("Cannot connect to server. Exiting Client.");
			System.exit(2);
		} 

		// add listener for connected/received/disconnected methods
		client.addListener(this);
		
		System.out.println("Client waiting for a packet...");		
			
	}
	
	// runs upon packet received
	public void received(Connection c, Object o) {
		
		if (o instanceof PacketReturn){
			System.out.println("Client received PacketReturn of type " + ((PacketReturn) o).status);

			int playerID = ((PacketReturn) o).playerNum;
			int id = ((PacketReturn) o).dbid;
			String username = ((PacketReturn) o).username;
			String message = ((PacketReturn) o).message;

			switch(((PacketReturn) o).status) {

			case Constants.SIGNUPSUCCESS:
			case Constants.LOGINSUCCESS: 

				System.out.println("user dbid = " + id + " username = " + username);
				System.out.println("setting scene to logged");
				Platform.runLater(() -> {
					Hockey.setUser(new User(id,username));
					Hockey.getPrimaryStage().setScene(Hockey.getLoggedScene());
					Hockey.getSignupController().getUsername().clear();
					Hockey.getSignupController().getPassword().clear();
					Hockey.getSignupController().getPasswordc().clear();
					Hockey.getLoginController().getUsername().clear();
					Hockey.getLoginController().getPassword().clear();
                });
				System.out.println("set scene complete");
				break;
			case Constants.SIGNUPFAILURE: 
				Platform.runLater(() -> {
					Hockey.getSignupController().setMessage(message);
					if(message.equals("username is already taken")) {
						Hockey.getSignupController().getUsername().clear();
						Hockey.getSignupController().getPassword().clear();
						Hockey.getSignupController().getPasswordc().clear();
						
					} else if (message.equals("confirm password is different")) {
						Hockey.getSignupController().getPasswordc().clear();
					}
                });

				break;
			case Constants.LOGINFAILURE: 

				Platform.runLater(() -> {
				
					Hockey.getLoginController().setMessage(message);
					if(message.equals("user does not exist")) {
						Hockey.getLoginController().getUsername().clear();
					} else if (message.equals("password is incorrect")) {
						Hockey.getLoginController().getPassword().clear();
					}
                });
				break;
			case Constants.SIGNOUTSUCCESS: 
				Platform.runLater(() -> {
					Hockey.getPrimaryStage().setScene(Hockey.getMenuScene());;
                });
				break;
			case Constants.SIGNOUTFAILURE: 
				Platform.runLater(() -> {
					Hockey.getLoggedController().setMessage(message);
                });
				break;
			case Constants.PLAYSUCCESS:
//				System.out.println("player one "+ Master.getPlayerlist().get(0));
//				System.out.println("player two "+ Master.getPlayerlist().get(1));
				System.out.println("You are No. "+ ((PacketReturn) o).playerNum + " player in player list");
				System.out.println("Going to game scene");
				Platform.runLater(() -> {
					Hockey.getPrimaryStage().setScene(Hockey.getGameScene());
					Hockey.getGameController().init(playerID);
					Hockey.getGameController().gameLoop();
                });
				break;
			case Constants.PLAYFAILUREFEW: 
				Platform.runLater(() -> {
					Hockey.getWaitController().setMessage(username);
					Hockey.getPrimaryStage().setScene(Hockey.getWaitScene());	
                });
				break;
			case Constants.PLAYFAILUREMANY: 
				Platform.runLater(() -> {
					Hockey.getWaitController().setMessage(username);
					Hockey.getPrimaryStage().setScene(Hockey.getWaitScene());
                });
				break;
			case Constants.GOAL:
				int goalplayer = ((PacketReturn) o).dbid;
				Platform.runLater(() -> {
					Hockey.getGameController().setScore(goalplayer);

					Hockey.getGameController().showGoalMessage();
					GameController.getPuck().resetSize();
					GameController.getMidline().reset();
					GameController.getSelfGoal().reset();
					GameController.getOtherGoal().reset();
					
					
				});
				//Show goal message on screen and stop for 3 seconds
							
				break;
			case Constants.GAMEOVER:
				String endmessage = ((PacketReturn) o).message;
				System.out.println("GAME OVER HEREEEEEEEEEEEEEEEEEEEEEE");
				Platform.runLater(() -> {
					Hockey.getGameController().stopGame();
					Hockey.getGameOverController().setMessage(endmessage, ((PacketReturn) o).isGuest);				
					Hockey.getPrimaryStage().setScene(Hockey.getGameOverScene());
				});
				
				break;
			}
		} else if (o instanceof PacketStriker){
			//System.out.println("Client received PacketStriker of id = " + ((PacketStriker) o).id);
			Platform.runLater(() -> {
				if(GameController.getOtherStriker().getPlayer().getPlayerID() == ((PacketStriker)o).id) {
					// update other striker
					GameController.getOtherStriker().setPosition(((PacketStriker) o).locX, ((PacketStriker) o).locY);
					GameController.getOtherStriker().setVelocity(((PacketStriker) o).velX, ((PacketStriker) o).velY);
					GameController.getOtherStriker().display();
		           	 
				} else {
					// update self striker
					GameController.getSelfStriker().setPosition(((PacketStriker) o).locX, ((PacketStriker) o).locY);
					GameController.getSelfStriker().setVelocity(((PacketStriker) o).velX, ((PacketStriker) o).velY);
					GameController.getSelfStriker().display();
				}
			});
				
			
			
		} else if (o instanceof PacketPuck){
			//System.out.println("Client received PacketPuck!");
			Platform.runLater(() -> {
					
				GameController.getPuck().setPosition(((PacketPuck) o).locX, ((PacketPuck) o).locY);
				GameController.getPuck().setVelocity(((PacketPuck) o).velX, ((PacketPuck) o).velY);
				GameController.getPuck().display();
			});
			
		} else if (o instanceof PacketStats){
			System.out.println("Client received PacketStats!");
			Platform.runLater(() -> {
				Hockey.getStatsController().setStats(((PacketStats) o).matchesWon, ((PacketStats) o).matchesLost, ((PacketStats) o).goalsFor, ((PacketStats) o).goalsAgainst);
			
				Hockey.getPrimaryStage().setScene(Hockey.getStatsScene());
            });
		} else if (o instanceof PacketPU){
			System.out.println("Client recieved PacketPU");

			double x = ((PacketPU) o).x;
			double y = ((PacketPU) o).y;
			int id = ((PacketPU) o).playerID;
			switch(((PacketPU) o).puid) {
/*** Midline ***/
			case Constants.PUMIDLINESHOW: 
				Platform.runLater(() -> {
//					System.out.println("midline : " + ((Color)GameController.getPuMidline().getCircle().getFill()).getRed() + "," + ((Color)GameController.getPuMidline().getCircle().getFill()).getGreen() + "," + ((Color)GameController.getPuMidline().getCircle().getFill()).getBlue() + ",");
					GameController.getPuMidline().show(x, y);
	            });
				
				
				break;
			case Constants.PUMIDLINEACT: 
				
				Platform.runLater(() -> {
					Striker s;
					if(GameController.getSelfStriker().getPlayer().getPlayerID() == id) {
						s = GameController.getSelfStriker();
					} else {
						s = GameController.getOtherStriker();
					}
					GameController.getPuMidline().activate(GameController.getMidline(), s);
					GameController.getPuMidline().hide();
	            });
				break;
/*** Puck Size ***/
			case Constants.PUPUCKSIZESHOW:
				Platform.runLater(() -> {
					
//					System.out.println("pucksize: " + ((Color)GameController.getPuPuck().getCircle().getFill()).getRed() + "," + ((Color)GameController.getPuPuck().getCircle().getFill()).getGreen() + "," + ((Color)GameController.getPuPuck().getCircle().getFill()).getBlue() + ",");
					GameController.getPuPuck().show(x, y);
	            });
				break;

			case Constants.PUPUCKSIZEACT:
				Platform.runLater(() -> {
					GameController.getPuPuck().activate(GameController.getPuck());
					GameController.getPuPuck().hide();
	            });
				break;
/*** Goal Size ***/
			case Constants.PUGOALSIZESHOW:
				Platform.runLater(() -> {
					System.out.println("Received PUGOALSIZE  SHOW");
					GameController.getPuGoal().show(x, y);
	            });
				break;
			
			case Constants.PUGOALSIZEACT:
				Platform.runLater(() -> {
					System.out.println("Received PUGOALSIZE  ACT id = " + id);
					Goal g;
					if(id == 2) {
						g = GameController.getSelfGoal();
					} else {
						g = GameController.getOtherGoal();
					}
					
					GameController.getPuGoal().activate(g);
					GameController.getPuGoal().hide();
	            });
				break;
			
			}
			
		}

	}
	
	
	public void disconnected(Connection c) {
		System.out.println("Disconnected from server. ");
	}
	

	public Client getClient() { return client; }
	
}
