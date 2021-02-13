package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.App;
import it.unipi.dii.lsdb.group13.database.JobOfferDao;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import it.unipi.dii.lsdb.group13.Session;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

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
        JobOfferDao jobOfferDao = new JobOfferDao();
        Session.getSingleton();
        String companyName = Session.getLoggedUser();
        // When user fill all the required fields for salary
        if(titleField.getText().isEmpty() || stateField.getText().isEmpty() || cityField.getText().isEmpty() ||
                jobTypeField.getValue() == null || descriptionField.getText().isEmpty() ){
            errorMsg.setText("Please enter all the required Fields!");
            errorMsg.setTextFill(Paint.valueOf("red"));
        } else if (salaryUnit.getValue() != null && !salaryFromField.getText().isEmpty() &&
                !salaryToField.getText().isEmpty()) {
            try{
                Float.parseFloat(salaryFromField.getText().replaceAll(",", ""));
                Float.parseFloat(salaryToField.getText().replaceAll(",", ""));
                JobOffer jobOffer = new JobOffer(titleField.getText(),companyName,descriptionField.getText()
                        ,jobTypeField.getValue().toString(),stateField.getText(),cityField.getText(),
                        salaryFromField.getText(),salaryToField.getText(), salaryUnit.getValue().toString());
                if (jobOfferDao.createNewJobOffer(jobOffer)){
                    loadConfirmationPage();
                    App.setRoot("EmployerMenu");
                }
                else errorMsg.setText("Something went wrong! please try again!");
            }catch (NumberFormatException e){
                errorMsg.setText("You should enter numbers for salary fields!");
                errorMsg.setTextFill(Paint.valueOf("red"));
            }
        }  else{
            // When user DOES NOT fill the required fields for salary
            JobOffer jobOffer = new JobOffer(titleField.getText(),companyName,descriptionField.getText()
                    ,jobTypeField.getValue().toString(),stateField.getText(),cityField.getText());
            if (jobOfferDao.createNewJobOffer(jobOffer)) {
                jobOfferDao.addJobOfferToNeo4j(jobOffer);
                loadConfirmationPage();
                App.setRoot("EmployerMenu");
            }
            else {
                errorMsg.setText("Something went wrong! please try again!");
                errorMsg.setTextFill(Paint.valueOf("red"));
            }
        }
    }

    private void loadConfirmationPage() {
        Label confirmMsg = new Label("JOB OFFER HAS BEEN CREATED SUCCESSFULLY!");
        confirmMsg.setStyle("-fx-font-weight: bold ; -fx-font-size: 18");
        confirmMsg.setPadding(new Insets(15));
        Stage confirmation = new Stage();
        confirmation.setTitle("---- Confirmation window ----");
        confirmation.setScene(new Scene(confirmMsg, 440, 100));
        confirmation.show();
    }


}
