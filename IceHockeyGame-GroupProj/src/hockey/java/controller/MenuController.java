package hockey.java.controller;

import java.io.IOException;

import hockey.java.Hockey;
import hockey.java.packet.Constants;
import hockey.java.packet.PacketAttempt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuController {
	@FXML
	private Label appTitle;

	@FXML
	private Label pageTitle;
	
	@FXML
	private Button login;
	
	@FXML
	private Button signup;
	
	@FXML
	private Button playAsGuest;

	
	public void playAsGuest(ActionEvent event) throws IOException{
		PacketAttempt p = new PacketAttempt(Constants.PLAYGUESTATTEMPT);
		Hockey.getNetwork().getClient().sendTCP(p);
		System.out.println("sent playAsGuest Attempt");
	}
	
	public void goLogin(ActionEvent event) throws IOException{
		System.out.println("Switching scene to Login");
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		primaryStage.setScene(Hockey.getLoginScene());
		
	}
	
	public void goSignup(ActionEvent event) throws IOException{
		System.out.println("Switching scene to Signup");
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		primaryStage.setScene(Hockey.getSignupScene());
		
			
	}



	
}
