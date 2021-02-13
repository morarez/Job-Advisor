package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.EmployerDao;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
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

public class FollowedCompaniesController {

    @FXML
    private TableView tableFollowed;

    @FXML
    private Label errorMsg;

    @FXML
    private TableColumn nameCol;

    @FXML
    private void initialize(){
        JobSeekerDao jobSeekerDao = new JobSeekerDao();
        Session.getSingleton();
        ObservableList<String> details = FXCollections.observableArrayList(jobSeekerDao.followedCompanies(Session.getLoggedUser()));

        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList<String>, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue());
            }
        });

        tableFollowed.setItems(details);
    }

    @FXML
    private void rowsSelected() {
        EmployerDao employerDao = new EmployerDao();
        CompanyInfoPageController companyInfoPageController = new CompanyInfoPageController(employerDao.findUser(tableFollowed.getSelectionModel().getSelectedItem().toString()));
    }

    @FXML
    private void pressUnfollow() {
        String selected = (String) tableFollowed.getSelectionModel().getSelectedItem();
        if (selected == null) {
            errorMsg.setText("Please select\na company");
            errorMsg.setVisible(true); ;
        } else {
            errorMsg.setVisible(false);
            JobSeekerDao jobSeekerDao = new JobSeekerDao();
            boolean result = jobSeekerDao.unfollowCompany(Session.getLoggedUser(), selected);
            if(!result) {
                errorMsg.setText("Something went wrong.\nTry again");
            } else {
                initialize();
                errorMsg.setText("You no longer follow\nthe \"" + selected + "\"\ncompany");
            }
            errorMsg.setVisible(true);
        }
    }
}
