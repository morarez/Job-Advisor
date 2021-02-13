package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.Session;
import it.unipi.dii.lsdb.group13.database.EmployerDao;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class TopFollowedCompaniesAnalyticController {
	@FXML
	private TableView tableTopCompanies;
	@FXML
	private TableColumn company;
	
	@FXML
    private void initialize() {
        EmployerDao employerDao = new EmployerDao();
        ObservableList<String> followers = FXCollections.observableArrayList(employerDao.findTopCompanies());

        company.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList<String>, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue());
            }
        });

        tableTopCompanies.setItems(followers);
    }

}
