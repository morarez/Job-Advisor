package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.EmployerDao;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import it.unipi.dii.lsdb.group13.main.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployerTableViewController {

    @FXML
    private TableView tableEmployer;

    private ObservableList<JobOffer> published = null;

    @FXML
    private void initialize() {
        //tableEmployer.setRowFactory();
        EmployerDao employer = new EmployerDao();
        published = FXCollections.observableArrayList(employer.findPublished(Session.getLoggedUser()));
        tableEmployer.setItems(published);
    }

    @FXML
    private void rowsSelected(MouseEvent mouseEvent) throws IOException {
        JobOffer selected = (JobOffer) tableEmployer.getSelectionModel().getSelectedItem();
        if(selected != null) {
            System.out.println("Rows selected! " + selected.getTitle() + " " + selected.getCompanyName());

            VBox vbox = new VBox(10);

            Label jobTitle = new Label("TITLE: " + selected.getTitle());
            Label company = new Label("COMPANY: " + selected.getCompanyName());
            Label postDate = new Label("POST DATE: " + selected.getPostDate());
            Label jobType = new Label("JOB TYPE: " + selected.getPostDate());
            Label jobDescription = new Label("DESCRIPTION: " +  selected.getDescription());
            jobDescription.wrapTextProperty().setValue(true);

            vbox.getChildren().addAll(jobTitle, company, postDate, jobType, jobDescription);
            vbox.setStyle("-fx-background-color: #ADD8E6 ; -fx-font-family: sans-serif-verdana ; -fx-font-size: 15px ; -fx-padding: 40");
            Stage jobOfferPage = new Stage();
            jobOfferPage.setTitle("---- JobOffer info ----");
            jobOfferPage.setScene(new Scene(vbox, 440, 500));
            jobOfferPage.show();
            //tableEmployer.getSelectionModel().clearSelection();
        }
    }
}
