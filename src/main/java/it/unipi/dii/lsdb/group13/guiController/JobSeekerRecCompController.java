package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.EmployerDao;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import it.unipi.dii.lsdb.group13.entities.Employer;
import it.unipi.dii.lsdb.group13.Session;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import java.util.ArrayList;

public class JobSeekerRecCompController {

    @FXML
    private TableColumn companyCol;

    @FXML
    private TableView tableRecComp;

    @FXML
    private void initialize() {
        String username = Session.getLoggedUser();
        JobSeekerDao seekerDao = new JobSeekerDao();
        ArrayList<String> recommended = new ArrayList(seekerDao.findRecommendedCompanies(username));
        ObservableList<String> recCompanies = FXCollections.observableArrayList(recommended);

        companyCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                return new ReadOnlyObjectWrapper(p.getValue());
            }
        });

        tableRecComp.setItems(recCompanies);
    }

    @FXML
    private void rowsSelected() {
        EmployerDao employerDao = new EmployerDao();
        System.out.println("row selected: " + tableRecComp.getSelectionModel().getSelectedItem());
        Employer selected =  employerDao.findUser((String) tableRecComp.getSelectionModel().getSelectedItem());
        CompanyInfoPageController companyInfoPageController = new CompanyInfoPageController(selected, true);
    }
}
