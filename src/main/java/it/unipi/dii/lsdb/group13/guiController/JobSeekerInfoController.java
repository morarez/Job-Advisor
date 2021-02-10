package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.App;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import it.unipi.dii.lsdb.group13.entities.JobSeeker;
import it.unipi.dii.lsdb.group13.main.Session;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;

public class JobSeekerInfoController {

    private JobSeekerDao jobSeekerDao = new JobSeekerDao();

    @FXML
    private Text firstName;

    @FXML
    private Text lastName;

    @FXML
    private Text gender;

    @FXML
    private Text email;

    @FXML
    private Text birthdate;

    @FXML
    private Text loc;

    @FXML
    private Text skills;


    @FXML
    private void initialize(){
        Session.getSingleton();
        JobSeeker user = jobSeekerDao.findUser(Session.getLoggedUser());
        firstName.setText("First Name: " + user.getFirstName());
        lastName.setText("Last Name: " + user.getLastName());
        gender.setText("Gender: " + user.getGender());
        email.setText("Email: " + user.getEmail());
        birthdate.setText("BirthDate: " + user.getBirthdate());
        loc.setText("Location:   State: " + user.getLocation().getState() +
                " - City: " + user.getLocation().getCity());
        if (user.getSkills() != null)
            skills.setText("Skills: " +  user.getSkills().toString());
    }
    @FXML
    private void deleteAccount() throws IOException {
        Session.getSingleton();
        if(jobSeekerDao.deleteAccount(Session.getLoggedUser()))
            App.setRoot("LoginPage");
    }
}
