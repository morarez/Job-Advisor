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

    private final TableView tableView = new TableView();
    private final TableColumn<JobOffer, String> column1 = new TableColumn<>("Job Title");
    private final TableColumn<JobOffer, String> column2 = new TableColumn<>("Company");

    @FXML
    private void searchByCity(){
        if(city.getText().isEmpty()) {
            error.setText("Please enter city!");
            error.setTextFill(Color.web("#ff0000",0.8));
        } else {
            String cityy= city.getText();
            String cityEntered= cityy.toLowerCase();
            ObservableList<JobOffer> published = FXCollections.observableArrayList(jobOfferDao.getJobOffersByCity(cityEntered));
            column1.setCellValueFactory(new PropertyValueFactory<>("title"));
            column2.setCellValueFactory(new PropertyValueFactory<>("companyName"));
            TableColumn<JobOffer, String> column3 = new TableColumn<>("City");
            column3.setCellValueFactory(new PropertyValueFactory<>("city_name"));
            tableView.getColumns().clear();
            tableView.getColumns().add(column1);
            tableView.getColumns().add(column2);
            tableView.getColumns().add(column3);
            tableView.setItems(published);
            VBox vbox = new VBox(tableView);
            Scene scene = new Scene(vbox);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void searchByJobTitle(){
        if(jobTitle.getText().isEmpty()) {
            error.setText("Please enter Job Title!");
            error.setTextFill(Color.web("#ff0000",0.8));
        }
        else {
            String titleText= jobTitle.getText();
            ObservableList<JobOffer> published = FXCollections.observableArrayList(jobOfferDao.getJobOffersByJobTitle(titleText));
            column1.setCellValueFactory(new PropertyValueFactory<>("title"));
            column2.setCellValueFactory(new PropertyValueFactory<>("companyName"));
            tableView.getColumns().clear();
            tableView.getColumns().add(column1);
            tableView.getColumns().add(column2);
            tableView.setItems(published);
            VBox vbox = new VBox(tableView);
            Scene scene = new Scene(vbox);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    private void searchByJobType(){
        if(jobType.getValue()== null) {
            error.setText("Please select Job Type!");
            error.setTextFill(Color.web("#ff0000",0.8));
        } else {
            String jobTypeText = jobType.getValue().toString();
            ObservableList<JobOffer> published = FXCollections.observableArrayList(jobOfferDao.getJobOffersByJobType(jobTypeText));
            column1.setCellValueFactory(new PropertyValueFactory<>("title"));
            column2.setCellValueFactory(new PropertyValueFactory<>("companyName"));
            TableColumn<JobOffer, String> column3 = new TableColumn<>("Job Type");
            column3.setCellValueFactory(new PropertyValueFactory<>("jobType"));
            tableView.getColumns().clear();
            tableView.getColumns().add(column1);
            tableView.getColumns().add(column2);
            tableView.getColumns().add(column3);
            tableView.setItems(published);
            VBox vbox = new VBox(tableView);
            Scene scene = new Scene(vbox);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void searchByCompany(){
        if(company.getText().isEmpty()) {
            error.setText("Please enter company name!");
            error.setTextFill(Color.web("#ff0000",0.8));

        } else {
            String companyEntered= company.getText().toLowerCase();
            ObservableList<JobOffer> published = FXCollections.observableArrayList(jobOfferDao.getJobOffersByCompany(companyEntered));
            column1.setCellValueFactory(new PropertyValueFactory<>("title"));
            column2.setCellValueFactory(new PropertyValueFactory<>("companyName"));
            tableView.getColumns().clear();
            tableView.getColumns().add(column1);
            tableView.getColumns().add(column2);
            tableView.setItems(published);
            VBox vbox = new VBox(tableView);
            Scene scene = new Scene(vbox);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
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
            column1.setCellValueFactory(new PropertyValueFactory<>("title"));
            column2.setCellValueFactory(new PropertyValueFactory<>("companyName"));
            TableColumn<JobOffer, Double> column3 = new TableColumn<>("Minimum Salary");
            column3.setCellValueFactory(new PropertyValueFactory<>("minSalary"));
            TableColumn<JobOffer, String> column4 = new TableColumn<>("Time Unit");
            column4.setCellValueFactory(new PropertyValueFactory<>("timeUnit"));
            tableView.getColumns().clear();
            tableView.getColumns().add(column1);
            tableView.getColumns().add(column2);
            tableView.getColumns().add(column3);
            tableView.getColumns().add(column4);
            tableView.setItems(published);
            VBox vbox = new VBox(tableView);
            Scene scene = new Scene(vbox);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }
}
