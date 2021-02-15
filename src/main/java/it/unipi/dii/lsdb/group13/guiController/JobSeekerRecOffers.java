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
import java.util.ArrayList;
import java.util.Calendar;

public class JobSeekerRecOffers {

    @FXML
    private TableColumn dateCol;

    @FXML
    private TableView tableRecOffers;

    @FXML
    private void initialize() {
        String username = Session.getLoggedUser();
        JobSeekerDao seekerDao = new JobSeekerDao();
        ArrayList<JobOffer> recommended = new ArrayList(seekerDao.findRecommendedJobOffers(username));
        ObservableList<JobOffer> recOffers = FXCollections.observableArrayList(recommended);

        dateCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JobOffer, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<JobOffer, String> p) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(p.getValue().getPostDate());
                int month = cal.get(Calendar.MONTH)+1;
                String date = cal.get(Calendar.YEAR) + "-" + month + "-" + cal.get(Calendar.DAY_OF_MONTH);
                return new ReadOnlyObjectWrapper(date);
            }
        });

        tableRecOffers.setItems(recOffers);
    }

    @FXML
    private void rowsSelected() {
        JobOfferDao jobOfferDao = new JobOfferDao();
        JobOffer selected = (JobOffer) tableRecOffers.getSelectionModel().getSelectedItem();
        JobOfferInfoPageController jobOfferInfoPageController= new JobOfferInfoPageController(jobOfferDao.getById(selected.getId()), true);
    }
}
