package hockey.java.controller;

import java.io.IOException;

import hockey.java.Hockey;
import hockey.java.packet.Constants;
import hockey.java.packet.PacketAttempt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController{
	//private SQLModel model = new SQLModel();
	
	@FXML
	private Label appTitle;

	@FXML
	private Label pageTitle;
	
	@FXML
	private Label message;
	
	@FXML
 	private TextField username;
	
	@FXML
	private TextField password;
	
	@FXML
	private Button login;
	
	@FXML
	private Button back;
	
	public void login(ActionEvent event) throws IOException{
		System.out.println(username.getText() + " " + password.getText());
		
		//sending login packet
		PacketAttempt p = new PacketAttempt(Constants.LOGINATTEMPT,username.getText(),password.getText());		
		Hockey.getNetwork().getClient().sendTCP(p);
	
	}
	
	
	@FXML
	public void goBack(ActionEvent event) throws IOException {
		System.out.println("going back to main menu");
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		primaryStage.setScene(Hockey.getMenuScene());
		//primaryStage.show();
	
		
	}

	public void setMessage(String string) {
		message.setText(string);
		
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


}
