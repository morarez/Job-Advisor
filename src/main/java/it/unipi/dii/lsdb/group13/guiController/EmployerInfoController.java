package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.App;
import it.unipi.dii.lsdb.group13.database.EmployerDao;
import it.unipi.dii.lsdb.group13.entities.Employer;
import it.unipi.dii.lsdb.group13.main.Session;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;

public class EmployerInfoController {

    @FXML
    private Text companyName;

    @FXML
    private Text companyEmail;

    @FXML
    public void initialize(){
        EmployerDao employerDao = new EmployerDao();
        Session.getSingleton();
        Employer user = employerDao.findUser(Session.getLoggedUser());
        companyName.setText("Company: " + user.getName());
        companyEmail.setText("Email: " + user.getEmail());
        // A tableview for showing company's job offers
    }

    @FXML
    private void goHome() throws IOException {
        App.setRoot("JobSeekerHomePage");
    }


}