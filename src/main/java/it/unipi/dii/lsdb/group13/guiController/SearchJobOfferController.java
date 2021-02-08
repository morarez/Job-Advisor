package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.App;
import it.unipi.dii.lsdb.group13.database.JobOfferDao;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchJobOfferController {

    @FXML
    private TableView tableJobOffers;

    @FXML
    GridPane pane;

    @FXML
    TextField city;

    @FXML
    TextField company;

    @FXML
    ChoiceBox jobType;

    @FXML
    TextField jobTitle;

    @FXML
    TextField minSalary;

    @FXML
    ChoiceBox timeUnit;

    @FXML
    Label error;

    private final JobOfferDao jobOfferDao = new JobOfferDao();

    @FXML
    private void initialize() {
        App.setDimStage(900.0, 600.0);
        error.setVisible(false);
    }

    @FXML
    private void searchByCity(){
        if(city.getText().length() < 3 ) {
            error.setText("Please enter city! \n(at least 3 characters)");
            error.setVisible(true);
        } else {
            error.setVisible(false);
            String cityEntered= city.getText().toLowerCase();
            ObservableList<JobOffer> published = FXCollections.observableArrayList(jobOfferDao.getJobOffersByCity(cityEntered));
            tableJobOffers.setItems(published);
        }
    }

    @FXML
    private void searchByJobTitle(){
        if(jobTitle.getText().length() < 3) {
            error.setText("Please enter Job Title! \n(at least 3 characters)");
            error.setVisible(true);
        }
        else {
            error.setVisible(false);
            String titleText= jobTitle.getText();
            ObservableList<JobOffer> published = FXCollections.observableArrayList(jobOfferDao.getJobOffersByJobTitle(titleText));
            tableJobOffers.setItems(published);
        }
    }

    @FXML
    private void searchByJobType(){
        if(jobType.getValue().equals("Job Type")) {
            error.setText("Please select Job Type!");
            error.setVisible(true);
        } else {
            error.setVisible(false);
            String jobTypeText = jobType.getValue().toString();
            ObservableList<JobOffer> published = FXCollections.observableArrayList(jobOfferDao.getJobOffersByJobType(jobTypeText));
            tableJobOffers.setItems(published);
        }
    }

    @FXML
    private void searchByCompany(){
        if(company.getText().length() < 3) {
            error.setText("Please enter company \nname! \n(at least 3 characters)");
            error.setVisible(true);

        } else {
            error.setVisible(false);
            String companyEntered= company.getText().toLowerCase();
            ObservableList<JobOffer> published = FXCollections.observableArrayList(jobOfferDao.getJobOffersByCompany(companyEntered));
            tableJobOffers.setItems(published);
        }

    }

    @FXML
    private void searchBySalary(){
        if((minSalary.getText().isEmpty()) || (timeUnit.getValue().equals("Time Unit"))) {
            error.setText("Please enter both \nMinimum Salary and\nTime Unit!");
            error.setVisible(true);
        }
        else {
            error.setVisible(false);
            String minimum= minSalary.getText();
            double min = Double.parseDouble(minimum);
            String timeunit= timeUnit.getValue().toString();
            ObservableList<JobOffer> published = FXCollections.observableArrayList(jobOfferDao.getJobOffersBySalary(timeunit,min));
            tableJobOffers.setItems(published);
        }
    }

    @FXML
    private void rowSelected() throws IOException {
        JobOffer selected = (JobOffer) tableJobOffers.getSelectionModel().getSelectedItem();
        if(selected != null) {
            System.out.println("Rows selected! " + selected.getTitle() + " " + selected.getCompanyName());

            VBox vbox = new VBox(10);

            Button saveBu = new Button("SAVE");
            saveBu.setPadding(new Insets(2, 20, 2, 20));
            saveBu.setStyle("-fx-border-color: darkgray; -fx-border-radius: 8px; -fx-border-width: 2px; -fx-padding: 5px 22px; -fx-text-align: center; -fx-background-radius: 8px;");

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


            vbox.getChildren().addAll(saveBu, flowTitle, flowCompanyName, flowLocation, flowPostDate, flowJobType, flowSalary, jobDescription, description);
            vbox.setStyle("-fx-background-color: #ADD8E6 ; -fx-font-family: sans-serif-verdana ; -fx-font-size: 15px ; -fx-padding: 40");
            Stage jobOfferPage = new Stage();
            jobOfferPage.setTitle("---- JobOffer info ----");
            jobOfferPage.setScene(new Scene(vbox, 440, 500));
            jobOfferPage.show();
        }
    }
}
