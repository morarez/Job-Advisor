package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.Session;
import it.unipi.dii.lsdb.group13.database.EmployerDao;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class EmployerViewFollowersController {

    @FXML
    private TableView tableFollowers;

    @FXML
    private TableColumn nameCol;

    @FXML
    private void initialize() {
        EmployerDao employerDao = new EmployerDao();
        ObservableList<String> followers = FXCollections.observableArrayList(employerDao.findFollowers(Session.getLoggedUser()));

        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<String>, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList<String>, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue());
            }
        });

        tableFollowers.setItems(followers);
    }
    @FXML
    private void rowsSelected() {
        String selected = (String) tableFollowers.getSelectionModel().getSelectedItem();
        JobSeekerDao jobSeekerDao = new JobSeekerDao();
        JobSeekerInfoPageController jobSeekerInfoPageController = new JobSeekerInfoPageController(jobSeekerDao.findUser(selected));
    }
}
