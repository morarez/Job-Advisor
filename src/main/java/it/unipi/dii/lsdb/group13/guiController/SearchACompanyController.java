package it.unipi.dii.lsdb.group13.guiController;
import it.unipi.dii.lsdb.group13.App;
import it.unipi.dii.lsdb.group13.database.EmployerDao;
import it.unipi.dii.lsdb.group13.database.JobOfferDao;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import it.unipi.dii.lsdb.group13.entities.Employer;
import it.unipi.dii.lsdb.group13.entities.JobOffer;

import java.io.IOException;

import it.unipi.dii.lsdb.group13.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class SearchACompanyController {
	@FXML
	TextField company;
	@FXML
	Label error;
	@FXML
	Label label1;
	@FXML
	Label label2;
	@FXML
	Label companyFound;
	@FXML
	Label emailFound;
	@FXML
	Label jobOffer;
	@FXML
	Button followButton;
	@FXML
	TableView tableJobOffers;

	@FXML
	    private void initialize() throws IOException {
	        App.setDimStage(1000.0, 600.0);
	    }
	
	@FXML
	private void pressGo() throws IOException{
		if(company.getText().isEmpty()) {
			 error.setText("Please enter company name!");
	            error.setVisible(true);
	            label1.setVisible(false);
				label2.setVisible(false);
	            companyFound.setVisible(false);
				emailFound.setVisible(false);
				followButton.setVisible(false);
				jobOffer.setVisible(false);
				tableJobOffers.setVisible(false);
		}
		else {
			error.setVisible(false);
			EmployerDao employerdao= new EmployerDao();
			Employer employer= employerdao.findUser(company.getText());
			if(employer!=null) {
				JobSeekerDao jobSeekerDao = new JobSeekerDao();
				Session.getSingleton();
				if(jobSeekerDao.isFollowing(Session.getLoggedUser(),employer.getName()))
					followButton.setText("Followed");
				label1.setVisible(true);
				label2.setVisible(true);
				companyFound.setText(employer.getName());
            	companyFound.setVisible(true);
				emailFound.setText(employer.getEmail());
				emailFound.setVisible(true);
				followButton.setVisible(true);
				JobOfferDao joboffer= new JobOfferDao();
				String companyEntered= company.getText().toLowerCase();
            	ObservableList<JobOffer> published = FXCollections.observableArrayList(joboffer.getJobOffersByCompany(companyEntered));
            	jobOffer.setText("Job Offers Posted by the Company are: ");
            	jobOffer.setVisible(true);
            	tableJobOffers.setItems(published);
            	tableJobOffers.setVisible(true);
			}
			else
			{
				error.setText("Company Not Found!");
				error.setVisible(true);
				label1.setVisible(false);
				label2.setVisible(false);
	            companyFound.setVisible(false);
				emailFound.setVisible(false);
				followButton.setVisible(false);
				jobOffer.setVisible(false);
				tableJobOffers.setVisible(false);
				}
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
            flowPostDate.getChildren().addAll(postDate, new Label(selected.getPostDate().toString()));

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
	@FXML
	private void followCompany() {
		JobSeekerDao jobSeekerDao = new JobSeekerDao();
		Session.getSingleton();
		String state = followButton.getText();
		if(state.equals("Follow")) {
			if (jobSeekerDao.followCompany(Session.getLoggedUser(), companyFound.getText()))
				followButton.setText("Followed");
			else
				System.out.println("Failed to follow!");
		}else{
			if (jobSeekerDao.unfollowCompany(Session.getLoggedUser(), companyFound.getText()))
				followButton.setText("Follow");
			else
				System.out.println("Failed to unfollow!");
		}
	}
}
