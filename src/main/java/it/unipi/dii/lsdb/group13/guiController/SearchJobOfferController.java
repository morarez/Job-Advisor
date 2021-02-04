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
       String cityEntered= city.getText();
		System.out.println(cityEntered);
		JobOfferDao offer= new JobOfferDao();
		ObservableList<JobOffer> published = FXCollections.observableArrayList(offer.getJobOffersByCity(cityEntered));
		System.out.println(published);
        TableView tableView= new TableView();
        TableColumn<JobOffer, String> column1 = new TableColumn<>("Job Title");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<JobOffer, String> column2 = new TableColumn<>("Company");
        column2.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        TableColumn<JobOffer, Location> column3 = new TableColumn<>("Job Description");
        column3.setCellValueFactory(new PropertyValueFactory<>("description"));
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
       String companyEntered= company.getText();
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
		
	}

}
