package hockey.java.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import hockey.java.Hockey;
import hockey.java.database.SQLModel;
import hockey.java.packet.Constants;
import hockey.java.packet.PacketAttempt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignupController{
	
	//private SQLModel model = new SQLModel();
	
	@FXML
	private Label appTitle;

	@FXML
	private Label pageTitle;
	
	@FXML
	private /*static*/ Label message;
	
	@FXML
	private TextField username;

	@FXML
	private TextField password;
	
	@FXML
	private TextField passwordc;
	
	@FXML
	private Button signup;
	
	@FXML
	private Button back;
	
	
	@FXML
	public void signup(ActionEvent event) throws IOException{
		
		
		System.out.println(username.getText() + " " + password.getText() + " " + passwordc.getText());
		
		//sending signup packet
		PacketAttempt p = new PacketAttempt(Constants.SIGNUPATTEMPT,username.getText(),password.getText(),passwordc.getText());
		Hockey.getNetwork().getClient().sendTCP(p);

		System.out.println("Sent PacketAttempt of type 1: SignupAttempt");
		
	}
	

	@FXML
	public void goBack(ActionEvent event) throws IOException {
		
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		primaryStage.setScene(Hockey.getMenuScene());
		//primaryStage.show();
	
		
	}

	public /*static*/ void setMessage(String string) {
		if(message == null) System.out.println("message is null");
		else {
			System.out.println("setting message"); 
			message.setText(string);
		}
		
	}


	public TextField getUsername() {
		return username;
	}


	public void setUsername(TextField username) {
		this.username = username;
	}


	public TextField getPassword() {
		return password;
	}


	public void setPassword(TextField password) {
		this.password = password;
	}


	public TextField getPasswordc() {
		return passwordc;
	}


	public void setPasswordc(TextField passwordc) {
		this.passwordc = passwordc;
	}

	


}
