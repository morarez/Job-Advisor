package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.App;
import it.unipi.dii.lsdb.group13.database.EmployerDao;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import it.unipi.dii.lsdb.group13.main.Session;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CreateJobOfferController {

    @FXML
    private Label errorMsg;

    @FXML
    private TextField titleField;

    @FXML
    private TextField stateField;

    @FXML
    private TextField cityField;

    @FXML
    private ChoiceBox salaryUnit;

    @FXML
    private TextField salaryFromField;

    @FXML
    private TextField salaryToField;

    @FXML
    private ChoiceBox jobTypeField;

    @FXML
    private TextArea descriptionField;

    @FXML
    public void pressSubmit() throws IOException{
        EmployerDao employerDao = new EmployerDao();
        Session.getSingleton();
        String companyName =Session.getLoggedUser();
        if(titleField.getText().isEmpty() || stateField.getText().isEmpty() || cityField.getText().isEmpty() ||
                jobTypeField.getValue() == null || descriptionField.getText().isEmpty() ){
            errorMsg.setText("Please enter all the required Fields!");
        } else if (salaryUnit.getValue() != null && !salaryFromField.getText().isEmpty() &&
                !salaryToField.getText().isEmpty()) {
            // When user fill all the required fields for salary
            JobOffer jobOffer = new JobOffer(titleField.getText(),companyName,descriptionField.getText()
                    ,jobTypeField.getValue().toString(),stateField.getText(),cityField.getText(),
                    salaryFromField.getText(),salaryToField.getText(), salaryUnit.getValue().toString());
            employerDao.createNewJobOffer(jobOffer);
        }  else{
            // When user DOES NOT fill the required fields for salary
            JobOffer jobOffer = new JobOffer(titleField.getText(),companyName,descriptionField.getText()
                    ,jobTypeField.getValue().toString(),stateField.getText(),cityField.getText());
            employerDao.createNewJobOffer(jobOffer);
        }
    }
}
