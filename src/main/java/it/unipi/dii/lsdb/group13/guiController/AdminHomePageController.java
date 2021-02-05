package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.EmployerDao;
import it.unipi.dii.lsdb.group13.database.JobOfferDao;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import it.unipi.dii.lsdb.group13.entities.Employer;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import it.unipi.dii.lsdb.group13.entities.JobSeeker;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class AdminHomePageController {

    @FXML
    public Button deleteButton;

    @FXML
    private Label errorMessage;

    @FXML
    private TextField id;

    @FXML
    private ChoiceBox toSearchChoice;

    @FXML
    private TextArea textArea;

    private JobSeeker foundedS = null;
    private Employer foundedE = null;
    private JobOffer foundedJ = null;

    @FXML
    private void searchById() {
        if(id.getText().isEmpty()) {
            errorMessage.setText("Insert the id");
            errorMessage.setVisible(true);
        } else {
            errorMessage.setVisible(false);
            switch (toSearchChoice.getValue().toString()) {
                case "Job seeker":
                    try {
                        JobSeekerDao seeker = new JobSeekerDao();
                        foundedS = seeker.findUser(id.getText());
                        textArea.setText(foundedS.toStringWithoutPassword());
                    } catch(NullPointerException e) {
                        textArea.setText("No job seeeker founded with this id");
                    }
                    break;
                case "Employer":
                    try {
                        EmployerDao employer = new EmployerDao();
                        foundedE = employer.findUser(id.getText());
                        textArea.setText(foundedE.toStringWithoutPassword());
                    } catch(NullPointerException e) {
                        textArea.setText("No employer founded with this id");
                    }
                    break;
                case "Job offer":
                    try {
                        JobOfferDao jobOffer = new JobOfferDao();
                        foundedJ = jobOffer.getById(id.getText());
                        textArea.setText(foundedJ.toStringWithoutDescription());
                    } catch(NullPointerException e) {
                        textArea.setText("No job offer founded with this id");
                    }
                    break;
            }
        }
    }

    @FXML
    private void deleteFounded() {
        switch (toSearchChoice.getValue().toString()) {
            case "Job seeker":
                if ( foundedS != null ) {
                    JobSeekerDao seekerDao = new JobSeekerDao();
                    if(seekerDao.deleteJobSeeker(foundedS.getUsername()) == 0) {
                        errorMessage.setText("There is no document with this id to delete");
                        errorMessage.setVisible(true);
                    } else {
                        errorMessage.setText("Document with id = " + foundedS.getUsername() + " successfully deleted");
                        errorMessage.setVisible(true);
                    }
                    foundedS = null;
                } else {
                    errorMessage.setText("Insert the id of the job seeker");
                }
                break;
            case "Employer":
                if ( foundedE != null ) {
                    EmployerDao employerDao = new EmployerDao();
                    if(employerDao.deleteEmployer(foundedE.getName()) == 0) {
                        errorMessage.setText("There is no document with this id to delete");
                        errorMessage.setVisible(true);
                    } else {
                        errorMessage.setText("Document with id = " + foundedE.getName() + " successfully deleted");
                        errorMessage.setVisible(true);
                    }
                    foundedE = null;
                } else {
                    errorMessage.setText("Insert the id of the employer");
                }
                break;
            case "Job offer":
                if ( foundedJ != null ) {
                    JobOfferDao jobOfferDao = new JobOfferDao();
                    if(!jobOfferDao.deleteJobOffer(foundedJ.getId())) {
                        errorMessage.setText("There is no document with this id to delete");
                        errorMessage.setVisible(true);
                    } else {
                        errorMessage.setText("Document with id = " + foundedJ.getId() + " successfully deleted");
                        errorMessage.setVisible(true);
                    }
                    foundedJ = null;
                } else {
                    errorMessage.setText("Insert the id of the job offer");
                }
                break;
            default:
                errorMessage.setText("Select something to delete");
                errorMessage.setVisible(true);
        }

        textArea.clear();
        id.clear();
    }

    @FXML
    private void emptyChoiceBox() {
        toSearchChoice.getSelectionModel().select("Search by Id");
    }
}
