package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.Session;
import it.unipi.dii.lsdb.group13.database.JobOfferDao;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.Calendar;

public class JobSeekerHomePageController {

    @FXML
    private TableColumn dateCol;

    @FXML
    private TableView tablePublished;

    @FXML
    private void initialize() {
        JobOfferDao jobOfferDao = new JobOfferDao();

        dateCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JobOffer, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<JobOffer, String> p) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(p.getValue().getPostDate());
                int month = cal.get(Calendar.MONTH)+1;
                String date = cal.get(Calendar.YEAR) + "-" + month + "-" + cal.get(Calendar.DAY_OF_MONTH);
                return new ReadOnlyObjectWrapper(date);
            }
        });

        ObservableList<JobOffer> offers = FXCollections.observableArrayList(jobOfferDao.postsByFollowedCompanies(Session.getLoggedUser()));
        tablePublished.setItems(offers);
    }

    public void rowsSelected() {
        JobOfferDao jobOfferDao = new JobOfferDao();
        JobSeekerDao jobSeekerDao = new JobSeekerDao();
        JobOffer item = (JobOffer) tablePublished.getSelectionModel().getSelectedItem();
        JobOfferInfoPageController infoPageController = new JobOfferInfoPageController(jobOfferDao.getById(item.getId()), true);
    }
}
