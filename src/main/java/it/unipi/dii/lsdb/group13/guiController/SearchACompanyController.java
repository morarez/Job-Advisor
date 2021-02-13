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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
        JobOfferInfoPageController jobOfferInfoPageController = new JobOfferInfoPageController(selected, true);
    }

	@FXML
	private void followCompany() {
		JobSeekerDao jobSeekerDao = new JobSeekerDao();
		String state = followButton.getText();
		if(state.equals("Follow")) {
			if (jobSeekerDao.followCompany(Session.getLoggedUser(), companyFound.getText()))
				followButton.setText("Followed");
			else {
				error.setText("Failed to follow!");
				error.setVisible(true);
			}
		}else{
			if (jobSeekerDao.unfollowCompany(Session.getLoggedUser(), companyFound.getText()))
				followButton.setText("Follow");
			else {
				error.setText("Failed to unfollow!");
				error.setVisible(true);
			}
		}
	}
}
