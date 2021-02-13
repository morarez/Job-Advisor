package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.App;
import it.unipi.dii.lsdb.group13.database.EmployerDao;
import it.unipi.dii.lsdb.group13.entities.Employer;
import it.unipi.dii.lsdb.group13.Session;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;

public class EmployerInfoController {

    private EmployerDao employerDao = new EmployerDao();

    @FXML
    private Text companyName;

    @FXML
    private Text companyEmail;

    @FXML
    private void initialize(){
        Session.getSingleton();
        Employer user = employerDao.findUser(Session.getLoggedUser());
        companyName.setText("Company: " + user.getName());
        companyEmail.setText("Email: " + user.getEmail());
        // A tableview for showing company's job offers
    }

    @FXML
    private void deleteAccount() throws IOException {
        Session.getSingleton();
        if(employerDao.deleteAccount(Session.getLoggedUser()
        ))
            App.setRoot("LoginPage");
    }

}
