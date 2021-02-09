package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.JobOfferDao;
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
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployerTableViewController {

    @FXML
    private TableView tableEmployer;

    private ObservableList<JobOffer> published = null;

    @FXML
    private void initialize() {
        JobOfferDao jobOfferDao = new JobOfferDao();
        published = FXCollections.observableArrayList(jobOfferDao.findPublished(Session.getLoggedUser()));
        tableEmployer.setItems(published);
    }
    
    @FXML
    private void rowsSelected(MouseEvent mouseEvent) throws IOException {
        JobOffer selected = (JobOffer) tableEmployer.getSelectionModel().getSelectedItem();
        if(selected != null) {
            System.out.println("Rows selected! " + selected.getTitle() + " " + selected.getCompanyName());

            VBox vbox = new VBox(10);

            TextFlow flowTitle = new TextFlow();
            Label title = new Label("TITLE: "); title.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowTitle.getChildren().addAll(title, new Label (selected.getTitle()));

            TextFlow flowCompanyName = new TextFlow();
            Label companyName = new Label("COMPANY NAME: "); companyName.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowCompanyName.getChildren().addAll(companyName, new Label(selected.getCompanyName()));

            TextFlow flowLocation = new TextFlow();
            Label location = new Label("LOCATION: "); location.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowLocation.getChildren().addAll(location, new Label(selected.getLocStr()));

            TextFlow flowPostDate = new TextFlow();
            Label postDate = new Label("POST DATE: "); postDate.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowPostDate.getChildren().addAll(postDate, new Label(selected.getPostDate()));

            TextFlow flowJobType = new TextFlow();
            Label jobType = new Label("JOB TYPE: "); jobType.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowJobType.getChildren().addAll(jobType, new Label(selected.getJobType()));

            TextFlow flowSalary = new TextFlow();
            Label salary = new Label("SALARY: "); salary.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowSalary.getChildren().addAll(salary, new Label(selected.getSalaryStr()));

            Label jobDescription = new Label("DESCRIPTION: "); jobDescription.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            Label description = new Label(selected.getDescription());
            description.wrapTextProperty().setValue(true);

            vbox.getChildren().addAll(flowTitle, flowCompanyName, flowLocation, flowPostDate, flowJobType, flowSalary, jobDescription, description);
            vbox.setStyle("-fx-background-color: #ADD8E6 ; -fx-font-family: sans-serif-verdana ; -fx-font-size: 15px ; -fx-padding: 40");
            Stage jobOfferPage = new Stage();
            jobOfferPage.setTitle("---- JobOffer info ----");
            jobOfferPage.setScene(new Scene(vbox, 440, 500));
            jobOfferPage.show();
        }
    }
}
