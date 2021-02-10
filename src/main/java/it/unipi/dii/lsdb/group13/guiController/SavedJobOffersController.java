package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.AdminDao;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import it.unipi.dii.lsdb.group13.main.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SavedJobOffersController {

    @FXML
    private Label label;

    @FXML
    private void initialize(){

        JobSeekerDao jobSeekerDao = new JobSeekerDao();
        Session.getSingleton();
        ObservableList<String> details = FXCollections.observableArrayList(jobSeekerDao.savedOffers(Session.getLoggedUser()));

        TableView<String> tableView = new TableView<>();
        TableColumn<String, String> col1 = new TableColumn<>();
        tableView.getColumns().addAll(col1);

        col1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        tableView.setItems(details);

        StackPane sp = new StackPane(tableView);
        Scene scene = new Scene(sp);
        Stage savedPage = new Stage();
        savedPage.setScene(scene);
        savedPage.show();
    }
}
