package it.unipi.dii.lsdb.group13.guiController;
import java.io.IOException;
import java.text.ParseException;

import it.unipi.dii.lsdb.group13.entities.JobSeeker;
import org.apache.log4j.Logger;

import it.unipi.dii.lsdb.group13.App;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class SignUpJobSeekerController{

	public SignUpJobSeekerController() {
	    	Logger logger = Logger.getLogger(SignUpJobSeekerController.class.getName());    
	}
	@FXML
	private TextField fname;
	@FXML
	private TextField lname;
	@FXML
	private TextField jemail;
	@FXML
	private TextField jcity;
	@FXML
	private TextField jstate;
	@FXML
	private ChoiceBox gender;
	@FXML
	private PasswordField jpassword;
	@FXML
	private DatePicker birthday;
	@FXML
	private TextField username;
	@FXML
	private Label error;
	
	 @FXML
	 private void initialize() throws IOException { App.setDimStage(750.0, 650.0); }

	@FXML
	private void presssubmit() throws IOException {
		if((fname.getText().isEmpty()) || (lname.getText().isEmpty())  || (birthday.getValue() == null) || (gender.getValue()== null) || (jcity.getText().isEmpty()) || (jstate.getText().isEmpty()) || (jemail.getText().isEmpty()) || (jpassword.getText().isEmpty()) || (username.getText().isEmpty()))
		{
			error.setText("Please enter all required fields properly!");
			error.setTextFill(Color.web("#ff0000",0.8));
			return;
		}
		else
		{
			JobSeekerDao jobSeekerDao = new JobSeekerDao();
			JobSeeker jobSeeker = null;
			try {
				jobSeeker = new JobSeeker(username.getText(),jpassword.getText(),fname.getText(),lname.getText(),
						gender.getValue().toString(),birthday.getValue(),jemail.getText(),jstate.getText(),jcity.getText());
			}catch (ParseException ep){
				error.setText("Enter valid Birthdate!");
			}
			String isValid = jobSeekerDao.signUp(jobSeeker);
			if(isValid.equals("true")) {
				jobSeekerDao.addJobSeekerToNeo4j(username.getText());
				App.setRoot("SignUpConfirmation");
			}
			else {
				error.setText("Sign Up Failed because of this exception: \n" + isValid);
				error.setTextFill(Color.web("#ff0000", 0.8));
			}
		}
	}

	@FXML
	private void goBack() throws IOException{
		App.setRoot("LoginPage");
	}
}