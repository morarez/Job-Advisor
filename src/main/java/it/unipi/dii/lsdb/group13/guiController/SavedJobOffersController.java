package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.JobOfferDao;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;

import org.apache.log4j.Logger;

import it.unipi.dii.lsdb.group13.Session;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class SavedJobOffersController {
	public SavedJobOffersController() {
    	Logger logger = Logger.getLogger(SavedJobOffersController.class.getName());
    }
    @FXML
    private Label errorMsg;

    @FXML
    private TableView tableSaved;

    @FXML
    private void initialize(){
        JobSeekerDao jobSeekerDao = new JobSeekerDao();
        Session.getSingleton();
        ObservableList<JobOffer> details = FXCollections.observableArrayList(jobSeekerDao.savedOffers(Session.getLoggedUser()));
        tableSaved.setItems(details);

    }

    @FXML
    private void pressView() {
        JobOfferDao jobOfferDao = new JobOfferDao();
        JobOffer selected = (JobOffer) tableSaved.getSelectionModel().getSelectedItem();
        JobOfferInfoPageController jobOfferInfoPageController = new JobOfferInfoPageController(jobOfferDao.getById(selected.getId()), false);
    }

    @FXML
    private void pressUnsave() {
        JobOffer selected = (JobOffer) tableSaved.getSelectionModel().getSelectedItem();
        if (selected == null) {
            errorMsg.setText("Please select\na job offer");
            errorMsg.setVisible(true); ;
        } else {
            errorMsg.setVisible(false);
            JobSeekerDao jobSeekerDao = new JobSeekerDao();
            boolean result = jobSeekerDao.unSaveJobOffer(Session.getLoggedUser(), selected.getId());
            if(!result) {
                errorMsg.setText("Something went wrong.\nTry again");
            } else {
                initialize();
                errorMsg.setText("Job offer " + selected.getTitle() + " removed");
                errorMsg.wrapTextProperty().setValue(true);
            }
            errorMsg.setVisible(true);
        }

    }
}
