package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.JobOfferDao;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import it.unipi.dii.lsdb.group13.Session;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import java.util.Calendar;

public class EmployerTableViewController {

    @FXML
    private TableColumn dateCol;

    @FXML
    private Label errorMsg;

    @FXML
    private TableView tableEmployer;

    private ObservableList<JobOffer> published = null;

    @FXML
    private void initialize() {
        JobOfferDao jobOfferDao = new JobOfferDao();
        published = FXCollections.observableArrayList(jobOfferDao.findPublished(Session.getLoggedUser()));


        dateCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JobOffer, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<JobOffer, String> p) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(p.getValue().getPostDate());
                int month = cal.get(Calendar.MONTH) + 1;
                String date = cal.get(Calendar.YEAR) + "-" + month + "-" + cal.get(Calendar.DAY_OF_MONTH);
                return new ReadOnlyObjectWrapper(date);
            }
        });

        ObservableList<JobOffer> offers = FXCollections.observableArrayList(jobOfferDao.postsByFollowedCompanies(Session.getLoggedUser()));

        tableEmployer.setItems(published);
    }
    
    @FXML
    private void pressView() {
        JobOffer selected = (JobOffer) tableEmployer.getSelectionModel().getSelectedItem();
        JobOfferInfoPageController jobOfferInfoPageController = new JobOfferInfoPageController(selected, false);
    }

    @FXML
    private void pressDeleteButton() {
        JobOffer offer = (JobOffer) tableEmployer.getSelectionModel().getSelectedItem();
        if (offer == null) {
            errorMsg.setText("Please select \nsomething");
            errorMsg.setVisible(true);
        } else {
            errorMsg.setVisible(false);
            JobOfferDao jobOfferDao = new JobOfferDao();
            boolean ret = jobOfferDao.deleteJobOffer(offer.getId());
            if ( ret == false ) {
                errorMsg.setText("Something went \nwrong. Please\ntry again");
            } else {
                initialize();
                errorMsg.setText("Job offer\nsuccessfully deleted");
            }
            errorMsg.setVisible(true);
        }
    }
}
