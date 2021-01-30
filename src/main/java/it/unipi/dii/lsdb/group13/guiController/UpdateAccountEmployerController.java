package it.unipi.dii.lsdb.group13.guiController;

import java.io.IOException;

import it.unipi.dii.lsdb.group13.App;
import it.unipi.dii.lsdb.group13.database.EmployerDao;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class UpdateAccountEmployerController {
	public void UpdateAccountEmployerController() {}
	

	@FXML
	private PasswordField npwd;
	@FXML
	private PasswordField cpwd;
	@FXML
	private Label error;
	

	@FXML
	private void pressUpdate() throws IOException {
		if(npwd.getText().isEmpty() || cpwd.getText().isEmpty())
		{
			error.setText("Please enter both fields!");
			error.setTextFill(Color.web("#ff0000",0.8));
			return;
		}
		else
		{
			if(npwd.getText().equals(cpwd.getText()))
			{
	        	EmployerDao employer= new EmployerDao();
	        	boolean isValid= employer.changePassword(npwd.getText(),cpwd.getText());
	        	if(isValid==true)
	        	{
	        		error.setText("Password Updated!");
	        		//App.setRoot("EmployerHomePage");
	        	}
	        	else {
	        		error.setText("Password couldn't update!");
					error.setTextFill(Color.web("#ff0000",0.8));
	        	}
			}
			else
			{
				error.setText("Passwords don't match!");
				error.setTextFill(Color.web("#ff0000",0.8));
			}
		}
	}
}