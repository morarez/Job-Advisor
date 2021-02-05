package it.unipi.dii.lsdb.group13.guiController;

import java.io.IOException;
import it.unipi.dii.lsdb.group13.database.JobOfferDao;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import it.unipi.dii.lsdb.group13.entities.Location;
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



	public void SearchJobOfferController() {}
	@FXML
	private void searchByCity() throws IOException{
		if(city.getText().isEmpty()) {
			error.setText("Please enter city!");
			error.setTextFill(Color.web("#ff0000",0.8));
            return;
			
		}
		else {
			String cityy= city.getText();
       String cityEntered= cityy.toLowerCase();
		System.out.println(cityEntered);
		JobOfferDao offer= new JobOfferDao();
		ObservableList<JobOffer> published = FXCollections.observableArrayList(offer.getJobOffersByCity(cityEntered));
		System.out.println(published);
        TableView tableView= new TableView();
        TableColumn<JobOffer, String> column1 = new TableColumn<>("Job Title");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<JobOffer, String> column2 = new TableColumn<>("Company");
        column2.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        TableColumn<JobOffer, String> column3 = new TableColumn<>("City");
        column3.setCellValueFactory(new PropertyValueFactory<>("city_name"));
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
	private void searchByJobTitle() throws IOException{
		if(jobTitle.getText().isEmpty()) {
			error.setText("Please enter Job Title!");
			error.setTextFill(Color.web("#ff0000",0.8));
            return;
			
		}
		else {
       String jobtitle= jobTitle.getText();
		System.out.println(jobtitle);
		JobOfferDao offer= new JobOfferDao();
		ObservableList<JobOffer> published = FXCollections.observableArrayList(offer.getJobOffersByJobTitle(jobtitle));
		System.out.println(published);
        TableView tableView= new TableView();
        TableColumn<JobOffer, String> column1 = new TableColumn<>("Job Title");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<JobOffer, String> column2 = new TableColumn<>("Company");
        column2.setCellValueFactory(new PropertyValueFactory<>("companyName"));
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
	private void searchByJobType() throws IOException{
		if(jobType.getValue()== null) {
			error.setText("Please select Job Type!");
			error.setTextFill(Color.web("#ff0000",0.8));
            return;
			
		}
		else {
       String jobtype= jobType.getValue().toString();
		System.out.println(jobtype);
		JobOfferDao offer= new JobOfferDao();
		ObservableList<JobOffer> published = FXCollections.observableArrayList(offer.getJobOffersByJobType(jobtype));
		System.out.println(published);
        TableView tableView= new TableView();
        TableColumn<JobOffer, String> column1 = new TableColumn<>("Job Title");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<JobOffer, String> column2 = new TableColumn<>("Company");
        column2.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        TableColumn<JobOffer, String> column3 = new TableColumn<>("Job Type");
        column3.setCellValueFactory(new PropertyValueFactory<>("jobType"));
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
	private void searchByCompany() throws IOException{
		if(company.getText().isEmpty()) {
			error.setText("Please enter company name!");
			error.setTextFill(Color.web("#ff0000",0.8));
            return;
			
		}
		else {
       String companyEntered= company.getText().toLowerCase();
		System.out.println(companyEntered);
		JobOfferDao offer= new JobOfferDao();
		ObservableList<JobOffer> published = FXCollections.observableArrayList(offer.getJobOffersByCompany(companyEntered));
		System.out.println(published);
        TableView tableView= new TableView();
        TableColumn<JobOffer, String> column1 = new TableColumn<>("Job Title");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<JobOffer, String> column2 = new TableColumn<>("Company");
        column2.setCellValueFactory(new PropertyValueFactory<>("companyName"));
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
	private void searchBySalary() throws IOException{
		if((minSalary.getText().isEmpty()) || (timeUnit.getValue()== null)) {
			error.setText("Please enter both Minimum Salary and Time Unit!");
			error.setTextFill(Color.web("#ff0000",0.8));
            return;
			
		}
		else {
       String minimum= minSalary.getText();
       double min = Double.parseDouble(minimum);
		String timeunit= timeUnit.getValue().toString();
		JobOfferDao offer= new JobOfferDao();
		ObservableList<JobOffer> published = FXCollections.observableArrayList(offer.getJobOffersBySalary(timeunit,min));
		System.out.println(published);
        TableView tableView= new TableView();
        TableColumn<JobOffer, String> column1 = new TableColumn<>("Job Title");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<JobOffer, String> column2 = new TableColumn<>("Company");
        column2.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        TableColumn<JobOffer, Double> column3 = new TableColumn<>("Minimum Salary");
        column3.setCellValueFactory(new PropertyValueFactory<>("minSalary"));
        TableColumn<JobOffer, String> column4 = new TableColumn<>("Time Unit");
        column4.setCellValueFactory(new PropertyValueFactory<>("timeUnit"));
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
