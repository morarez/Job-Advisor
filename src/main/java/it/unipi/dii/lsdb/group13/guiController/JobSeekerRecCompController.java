package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.EmployerDao;
import it.unipi.dii.lsdb.group13.database.JobOfferDao;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import it.unipi.dii.lsdb.group13.entities.Employer;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import it.unipi.dii.lsdb.group13.main.Session;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
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

        Employer selected =  employerDao.findUser((String) tableRecComp.getSelectionModel().getSelectedItem());

        if(selected != null) {
            System.out.println("Rows selected! " + selected.getName());


            VBox vbox = new VBox(10);

            TextFlow flowCompanyName = new TextFlow();
            Label title = new Label("COMPANY NAME: "); title.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowCompanyName.getChildren().addAll(title, new Label (selected.getName()));

            TextFlow flowEmail = new TextFlow();
            Label location = new Label("EMAIL: "); location.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowEmail.getChildren().addAll(location, new Label(selected.getEmail()));

            vbox.getChildren().addAll(flowCompanyName, flowEmail);
            vbox.setStyle("-fx-background-color: #ADD8E6 ; -fx-font-family: sans-serif-verdana ; -fx-font-size: 15px ; -fx-padding: 40");
            Stage jobOfferPage = new Stage();
            jobOfferPage.setTitle("---- Company info ----");
            jobOfferPage.setScene(new Scene(vbox, 440, 300));
            jobOfferPage.show();
        }
    }
}
