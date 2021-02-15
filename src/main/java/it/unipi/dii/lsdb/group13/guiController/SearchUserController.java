package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import it.unipi.dii.lsdb.group13.entities.JobSeeker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class SearchUserController {
	public SearchUserController() {
    	Logger logger = Logger.getLogger(SearchUserController.class.getName());
    }
    @FXML
    private Label errorMsg;

    @FXML
    private TableView tableFoundedUsers;

    @FXML
    private TextField cityField;

    @FXML
    private TextField stateField;

    @FXML
    private TextField skillField;

    private ObservableList<JobSeeker> oList = null;

    @FXML
    private void pressSearch() {

        if ( (!cityField.getText().isEmpty() && !skillField.getText().isEmpty()) || (!stateField.getText().isEmpty() && !skillField.getText().isEmpty())) {
            errorMsg.setText("Search only by skill or by location, no both");
            errorMsg.setVisible(true);
        }

        if ( !cityField.getText().isEmpty() && !stateField.getText().isEmpty()) {

            JobSeekerDao jobSeekerDao = new JobSeekerDao();
            List<JobSeeker> seekers = new ArrayList<>(jobSeekerDao.searchLocation(cityField.getText(), stateField.getText()));
            oList = FXCollections.observableArrayList(seekers);
            tableFoundedUsers.setItems(oList);

        } else if (!cityField.getText().isEmpty()) {

            JobSeekerDao jobSeekerDao = new JobSeekerDao();
            List<JobSeeker> seekers = new ArrayList<>(jobSeekerDao.searchByCity(cityField.getText()));
            oList = FXCollections.observableArrayList(seekers);
            tableFoundedUsers.setItems(oList);

        } else if (!stateField.getText().isEmpty()) {
            JobSeekerDao jobSeekerDao = new JobSeekerDao();
            List<JobSeeker> seekers = new ArrayList<>(jobSeekerDao.searchByState(stateField.getText()));
            oList = FXCollections.observableArrayList(seekers);
            tableFoundedUsers.setItems(oList);

        } else if ( !skillField.getText().isEmpty() ) {
            JobSeekerDao jobSeekerDao = new JobSeekerDao();
            List<JobSeeker> seekers = new ArrayList<>(jobSeekerDao.searchSkill(skillField.getText()));
            oList = FXCollections.observableArrayList(seekers);
            tableFoundedUsers.setItems(oList);

        } else {
            errorMsg.setText("Insert a skill or a location");
            errorMsg.setVisible(true);
        }
    }

    @FXML
    private void rowSelected() {
        JobSeeker selected = (JobSeeker) tableFoundedUsers.getSelectionModel().getSelectedItem();
        JobSeekerInfoPageController jobSeekerInfoPageController = new JobSeekerInfoPageController(selected);
    }

    @FXML
    private void emptyFields() {
        errorMsg.setVisible(false);
        oList = null;
        tableFoundedUsers.setItems(null);
    }
}
