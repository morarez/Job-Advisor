package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.JobOfferDao;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

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
    private void rowSelected(){
        JobOffer selected = (JobOffer) tableJobOffers.getSelectionModel().getSelectedItem();
        JobOfferInfoPageController jobOfferInfoPageController = new JobOfferInfoPageController(selected, true);
    }
}
