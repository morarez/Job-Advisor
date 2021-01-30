package it.unipi.dii.lsdb.group13.guiController;
import java.io.IOException;
import it.unipi.dii.lsdb.group13.App;
import it.unipi.dii.lsdb.group13.database.EmployerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class SignUpEmployerController {
	
	public void SignUpEmployerController() {}
	
	@FXML
	private TextField companyName;
	@FXML
	private PasswordField cpassword;
	@FXML
	private TextField cemail;
	@FXML
	private TextField ccity;
	@FXML
	private TextField cstate;
	@FXML
	private Button submitButton;
	@FXML
	private Label error;
	
	
	@FXML
	private void presssubmit(ActionEvent event) throws IOException {
        if((companyName.getText().isEmpty()) || (cpassword.getText().isEmpty()) || (cemail.getText().isEmpty()) || (ccity.getText().isEmpty()) || (cstate.getText().isEmpty()))
        {
        	error.setText("Please enter all required fields!");
        	error.setTextFill(Color.web("#ff0000",0.8));
            return;
        }
        else
        {
        	EmployerDao employer= new EmployerDao();
        	boolean isValid= employer.signUp(companyName.getText(),cemail.getText(),cpassword.getText(),ccity.getText(),cstate.getText());
        	if(isValid==true)
        	App.setRoot("SignUpConfirmation");
        	else
        		error.setText("Sign Up Failed!");
        	error.setTextFill(Color.web("#ff0000",0.8));
        }
	}
}