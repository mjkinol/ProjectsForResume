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

public class WaitController {
	@FXML
	private Label appTitle;

	@FXML
	private Label pageTitle;
	
	@FXML
	private Label message;

	//https://stackoverflow.com/questions/34785417/javafx-fxml-controller-constructor-vs-initialize-method
	@FXML
    protected void initialize() {
        // populate listView https://stackoverflow.com/questions/47509117/javafx-populate-a-simple-listview?rq=1
		
    }

	public void setMessage(String string) {
		message.setText(string);
		
	}
	

}
