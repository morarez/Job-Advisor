package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.JobOfferDao;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SearchJobOfferController {

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

    private final TableView tableView = new TableView();;

    @FXML
    private void initialize(){
        final TableColumn<JobOffer, String> column1 = new TableColumn<>("Job Title");
        final TableColumn<JobOffer, String> column2 = new TableColumn<>("Company");
        final TableColumn<JobOffer, String> column3 = new TableColumn<>("Post Date");
        final TableColumn<JobOffer, String> column4 = new TableColumn<>("Location");
        final TableColumn<JobOffer, String> column5 = new TableColumn<>("Job Type");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));
        column2.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        column3.setCellValueFactory(new PropertyValueFactory<>("postDate"));
        column4.setCellValueFactory(new PropertyValueFactory<>("locStr"));
        column5.setCellValueFactory(new PropertyValueFactory<>("jobType"));
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);
    }

    @FXML
    private void searchByCity(){
        if(city.getText().length() < 3 ) {
            error.setText("Please enter city! (at least 3 characters)");
            error.setTextFill(Color.web("#ff0000",0.8));
        } else {
            String cityEntered= city.getText().toLowerCase();
            ObservableList<JobOffer> published = FXCollections.observableArrayList(jobOfferDao.getJobOffersByCity(cityEntered));
            showTableView(published);
        }
    }

    @FXML
    private void searchByJobTitle(){
        if(jobTitle.getText().length() < 3) {
            error.setText("Please enter Job Title! (at least 3 characters)");
            error.setTextFill(Color.web("#ff0000",0.8));
        }
        else {
            String titleText= jobTitle.getText();
            ObservableList<JobOffer> published = FXCollections.observableArrayList(jobOfferDao.getJobOffersByJobTitle(titleText));
            showTableView(published);
        }
    }

    @FXML
    private void searchByJobType(){
        if(jobType.getValue().equals("Job Type")) {
            error.setText("Please select Job Type!");
            error.setTextFill(Color.web("#ff0000",0.8));
        } else {
            String jobTypeText = jobType.getValue().toString();
            ObservableList<JobOffer> published = FXCollections.observableArrayList(jobOfferDao.getJobOffersByJobType(jobTypeText));
            showTableView(published);
        }
    }

    @FXML
    private void searchByCompany(){
        if(company.getText().length() < 3) {
            error.setText("Please enter company name! (at least 3 characters)");
            error.setTextFill(Color.web("#ff0000",0.8));

        } else {
            String companyEntered= company.getText().toLowerCase();
            ObservableList<JobOffer> published = FXCollections.observableArrayList(jobOfferDao.getJobOffersByCompany(companyEntered));
            showTableView(published);
        }

    }

    @FXML
    private void searchBySalary(){
        if((minSalary.getText().isEmpty()) || (timeUnit.getValue().equals("Time Unit"))) {
            error.setText("Please enter both Minimum Salary and Time Unit!");
            error.setTextFill(Color.web("#ff0000",0.8));
        }
        else {
            String minimum= minSalary.getText();
            double min = Double.parseDouble(minimum);
            String timeunit= timeUnit.getValue().toString();
            ObservableList<JobOffer> published = FXCollections.observableArrayList(jobOfferDao.getJobOffersBySalary(timeunit,min));
            TableColumn<JobOffer, Double> column6 = new TableColumn<>("Salary");
            column6.setCellValueFactory(new PropertyValueFactory<>("salaryStr"));
            tableView.getColumns().add(column6);
            showTableView(published);
        }
    }

    private void showTableView(ObservableList<JobOffer> published){
        tableView.setItems(published);
        VBox vbox = new VBox(tableView);
        Scene scene = new Scene(vbox);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
