package it.unipi.dii.lsdb.group13.guiController;

import java.io.IOException;

import org.apache.log4j.Logger;

import it.unipi.dii.lsdb.group13.App;
import javafx.fxml.FXML;

public class SignUpConfirmationController {
	
	public SignUpConfirmationController() {
	    	Logger logger = Logger.getLogger(SignUpConfirmationController.class.getName());    
	}
	
	@FXML
	private void showLogin() throws IOException{
		App.setRoot("LoginPage");
	}
	

}
