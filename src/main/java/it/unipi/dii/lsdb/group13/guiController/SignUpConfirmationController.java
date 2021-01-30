package it.unipi.dii.lsdb.group13.guiController;

import java.io.IOException;
import it.unipi.dii.lsdb.group13.App;
import javafx.fxml.FXML;

public class SignUpConfirmationController {
	
	public SignUpConfirmationController() {};
	
	@FXML
	private void showLogin() throws IOException{
		App.setRoot("LoginPage");
	}
	

}
