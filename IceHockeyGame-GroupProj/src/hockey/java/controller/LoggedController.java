package hockey.java.controller;

import java.io.IOException;

import hockey.java.Hockey;
import hockey.java.packet.Constants;
import hockey.java.packet.PacketAttempt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LoggedController {
	@FXML
	private Label appTitle;

	@FXML
	private Label pageTitle;

	@FXML
	private Label message;
	
	@FXML
	private Button playLogged;
	
	@FXML
	private Button stats;
	
	@FXML
	private Button signout;
	
	public void playLogged(ActionEvent event) throws IOException{

		
		PacketAttempt p = new PacketAttempt(Constants.PLAYLOGGEDATTEMPT, Hockey.getUser().getId(), Hockey.getUser().getUsername());
		Hockey.getNetwork().getClient().sendTCP(p);
		System.out.println("sent "+Hockey.getUser().getUsername()+"'s playAsUser Attempt");

		
	}
	
	public void goStats(ActionEvent event) throws IOException{

		PacketAttempt p = new PacketAttempt(Constants.GETSTATSATTEMPT, Hockey.getUser().getId());		
		Hockey.getNetwork().getClient().sendTCP(p);
	}
	

	public void signout(ActionEvent event) throws IOException{	
		PacketAttempt p = new PacketAttempt(Constants.SIGNOUTATTEMPT, Hockey.getUser().getId());		
		Hockey.getNetwork().getClient().sendTCP(p);
		System.out.println("sent signout attempt");
	}

	public void setMessage(String string) {
		message.setText(string);
		
	}
	
}
