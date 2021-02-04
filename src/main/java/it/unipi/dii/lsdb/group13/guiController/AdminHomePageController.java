package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.EmployerDao;
import it.unipi.dii.lsdb.group13.database.JobOfferDao;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import it.unipi.dii.lsdb.group13.entities.Employer;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import it.unipi.dii.lsdb.group13.entities.JobSeeker;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AdminHomePageController {

    @FXML
    private Label errorMessage;

    @FXML
    private TextField id;

    @FXML
    private ChoiceBox toSearchChoice;

    @FXML
    private TextArea textArea;

    @FXML
    private void searchById() {
        if(id.getText().isEmpty()) {
            errorMessage.setText("Insert the id");
            errorMessage.setVisible(true);
        } else {
            errorMessage.setVisible(false);
            switch (toSearchChoice.getValue().toString()) {
                case "Job seeker":
                    JobSeekerDao seeker = new JobSeekerDao();
                    JobSeeker foundedS = seeker.findUser(id.getText());
                    textArea.setText(foundedS.toStringWithoutPassword());
                    break;
                case "Employer":
                    EmployerDao employer = new EmployerDao();
                    Employer foundedE = employer.findUser(id.getText());
                    textArea.setText(foundedE.toStringWithoutPassword());
                    break;
                case "Job offer":
                    JobOfferDao jobOffer = new JobOfferDao();
                    JobOffer foundedJ = jobOffer.getById(id.getText());
                    textArea.setText(foundedJ.toStringWithoutDescription());
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    @FXML
    private void clickOnTextArea() {
        System.out.println("clicked");
    }

    @FXML
    private void emptyChoiceBox() {
        toSearchChoice.getSelectionModel().select("Search by Id");
    }
}
