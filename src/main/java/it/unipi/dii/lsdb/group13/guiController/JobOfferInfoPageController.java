package it.unipi.dii.lsdb.group13.guiController;

import org.apache.log4j.Logger;

import it.unipi.dii.lsdb.group13.Session;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

class JobOfferInfoPageController {
	 public JobOfferInfoPageController() {
	    	Logger logger = Logger.getLogger(JobOfferInfoPageController.class.getName());
	    }
    private JobOffer selected;

    JobOfferInfoPageController(JobOffer selected, boolean withButton) {
        this.selected = selected;
        showInfoPage(withButton);
    }

    private void showInfoPage(boolean withButton) {
        JobSeekerDao jobSeekerDao = new JobSeekerDao();
        if(selected != null) {
            System.out.println("Rows selected! " + selected.getTitle() + " " + selected.getCompanyName());

            VBox vbox = new VBox(10);

            HBox hbox = new HBox(40);
            if (withButton) {
                Label errorMsg = new Label("error message");
                errorMsg.setStyle("-fx-text-fill: RED ; -fx-font-size: 12 ; -fx-font-weight: bold");
                errorMsg.setVisible(false);

                Button saveBu = new Button("SAVE");
                saveBu.setStyle("-fx-border-color: darkgray; -fx-border-radius: 8px; -fx-border-width: 2px; -fx-padding: 5px 22px; -fx-text-align: center; -fx-background-radius: 8px;");

                if (jobSeekerDao.isSaved(Session.getLoggedUser(), selected.getId()))
                    saveBu.setText("SAVED");
                else
                    saveBu.setText("SAVE");

                saveBu.setPadding(new Insets(2, 20, 2, 20));

                saveBu.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        String state = saveBu.getText();
                        if (state.equals("SAVE")) {
                            if (jobSeekerDao.saveJobOffer(Session.getLoggedUser(), selected.getId())) {
                                saveBu.setText("SAVED");
                                errorMsg.setText("Job offer saved!");
                                errorMsg.setVisible(true);
                            }
                            else {
                                errorMsg.setText("Failed to save!");
                                errorMsg.setVisible(true);
                            }
                        } else {
                            if (jobSeekerDao.unSaveJobOffer(Session.getLoggedUser(), selected.getId())) {
                                saveBu.setText("SAVE");
                                errorMsg.setText("Job offer correctly unsaved");
                                errorMsg.setVisible(true);
                            }
                            else {
                                errorMsg.setText("Failed to unsave!");
                                errorMsg.setVisible(true);
                            }
                        }
                    }
                });
                hbox.getChildren().addAll(saveBu, errorMsg);
            }

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

            if(withButton)
                vbox.getChildren().addAll(hbox, flowTitle, flowCompanyName, flowLocation, flowPostDate, flowJobType, flowSalary, jobDescription, description);
            else
                vbox.getChildren().addAll(flowTitle, flowCompanyName, flowLocation, flowPostDate, flowJobType, flowSalary, jobDescription, description);
            vbox.setStyle("-fx-background-color: #ADD8E6 ; -fx-font-family: sans-serif-verdana ; -fx-font-size: 15px ; -fx-padding: 40");
            Stage jobOfferPage = new Stage();
            jobOfferPage.setTitle("---- JobOffer info ----");
            jobOfferPage.setScene(new Scene(vbox, 440, 500));
            jobOfferPage.show();
        }
    }
}
