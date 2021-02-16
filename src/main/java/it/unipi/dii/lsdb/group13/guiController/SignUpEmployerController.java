package it.unipi.dii.lsdb.group13.guiController;
import java.io.IOException;

import it.unipi.dii.lsdb.group13.entities.Employer;
import org.apache.log4j.Logger;

import it.unipi.dii.lsdb.group13.App;
import it.unipi.dii.lsdb.group13.database.EmployerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class SignUpEmployerController {
	public SignUpEmployerController() {
    	Logger logger = Logger.getLogger(SignUpEmployerController.class.getName());    
}
	@FXML
	private TextField companyName;
	@FXML
	private PasswordField cpassword;
	@FXML
	private TextField cemail;
	@FXML
	private Label error;
	
	
	@FXML
	private void initialize(){
	        App.setDimStage(800.0, 600.0);
	    }

	@FXML
	private void pressSubmit(ActionEvent event) throws IOException {
        if((companyName.getText().isEmpty()) || (cpassword.getText().isEmpty()) || (cemail.getText().isEmpty()))
        {
        	error.setText("Please enter all required fields!");
        	error.setTextFill(Color.web("#ff0000",0.8));
        }
        else
        {
        	EmployerDao employerDao= new EmployerDao();
			Employer newEmp = new Employer(companyName.getText(),cemail.getText(),cpassword.getText());
			String isValid= employerDao.signUp(newEmp);
        	if(isValid.equals("true")){
				employerDao.addEmployerToNeo4j(companyName.getText());
				App.setRoot("SignUpConfirmation");

			} else
        		error.setText("Sign Up Failed because: \n"+isValid);
        	error.setTextFill(Color.web("#ff0000",0.8));
        }
	}
	@FXML
	private void goBack() throws IOException{
		App.setRoot("LoginPage");
	}
}