package it.unipi.dii.lsdb.group13.guiController;
import it.unipi.dii.lsdb.group13.App;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import it.unipi.dii.lsdb.group13.main.Session;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class LoginController {
    @FXML
    private Text errorMessage;

    @FXML
    private ChoiceBox initialChoice;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    public LoginController() {}

    @FXML
    private void pressSignUpEmployerButton() throws IOException {
    	App.setRoot("SignUpEmployer");
    }

    @FXML
    private void pressSignUpJobSeeekerButton() throws IOException {
    	App.setRoot("SignUpJobSeeker");
    }

    @FXML
    private void pressLoginButton() throws IOException{
        String username = usernameField.getText();
        String password = passwordField.getText();
        String choice = initialChoice.getValue().toString();


        if (username.isEmpty() || password.isEmpty()) {
            errorMessage.setText("Please insert both \nusername and password");
            errorMessage.setVisible(true);
            return;
        }

        if (choice.equals("Who are you?")) {
            if (username.equals("admin") && password.equals("admin")) {
                errorMessage.setText("you are the admin"); /* only for testing */
                errorMessage.setVisible(true);
                /* show admin page*/
                return;
            } else if (username.equals("admin") && !password.equals("admin")) {
                errorMessage.setText("Wrong password \nfor the admin");
                errorMessage.setVisible(true);
                return;
            } else {
                errorMessage.setText("You must choice between \njob seeker and employer");
                errorMessage.setVisible(true);
                return;
            }
        } else {
            JobSeekerDao seeker = new JobSeekerDao();
            System.out.println("inserted pw: " + password);
            if (!password.equals(seeker.searchUsername(username))) {
                errorMessage.setText("Wrong password");
                errorMessage.setVisible(true);
                return;
            } else {
            	//created session to test update account code
            	Session.getSingleton();
    			Session.setLoggedUser(username);
                App.setRoot("JobSeekerHomePage");
            }
        }

    }
}
