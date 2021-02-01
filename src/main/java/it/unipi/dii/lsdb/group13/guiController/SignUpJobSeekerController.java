package it.unipi.dii.lsdb.group13.guiController;
import java.io.IOException;
import it.unipi.dii.lsdb.group13.App;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class SignUpJobSeekerController{

	public SignUpJobSeekerController() {}
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
	private void presssubmit() throws IOException {
		if((fname.getText().isEmpty()) || (lname.getText().isEmpty())  || (birthday.getValue() == null) || (gender.getValue()== null) || (jcity.getText().isEmpty()) || (jstate.getText().isEmpty()) || (jemail.getText().isEmpty()) || (jpassword.getText().isEmpty()) || (username.getText().isEmpty()))
		{
			error.setText("Please enter all required fields properly!");
			error.setTextFill(Color.web("#ff0000",0.8));
			return;
		}
		else
		{
			JobSeekerDao seeker= new JobSeekerDao();
			String isValid = seeker.signUp(fname.getText(),lname.getText(),username.getText(),birthday.getValue(),gender.getValue().toString(),jpassword.getText(),jemail.getText(),jcity.getText(),jstate.getText());
			if(isValid.equals("true"))
				App.setRoot("SignUpConfirmation");
			else
				error.setText("Sign Up Failed because of this exception: \n"+isValid);
			error.setTextFill(Color.web("#ff0000",0.8));
		}
	}

	@FXML
	private void goBack() throws IOException{
		App.setRoot("LoginPage");
	}
}