package it.unipi.dii.lsdb.group13.guiController;
import org.apache.log4j.Logger;
import it.unipi.dii.lsdb.group13.Session;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import it.unipi.dii.lsdb.group13.entities.Employer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

class CompanyInfoPageController {
	Logger logger;
	 public CompanyInfoPageController() {
	    	logger = Logger.getLogger(CompanyInfoPageController.class.getName());
	    }
    private Employer selected;

    CompanyInfoPageController(Employer selected) {
        this.selected = selected;
        showInfoPage(false);
    }

    CompanyInfoPageController(Employer selected, boolean withFollowButton) {
        this.selected = selected;
        showInfoPage(withFollowButton);
    }

    private void showInfoPage(boolean withFollowButton) {
        JobSeekerDao jobSeekerDao = new JobSeekerDao();
        if(selected != null) {


            VBox vbox = new VBox(10);

            TextFlow flowCompanyName = new TextFlow();
            Label title = new Label("COMPANY NAME: "); title.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowCompanyName.getChildren().addAll(title, new Label (selected.getName()));

            TextFlow flowEmail = new TextFlow();
            Label location = new Label("EMAIL: "); location.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowEmail.getChildren().addAll(location, new Label(selected.getEmail()));

            if(withFollowButton) {
                Label errorMsg = new Label("error message");
                errorMsg.setStyle("-fx-text-fill: RED ; -fx-font-size: 12 ; -fx-font-weight: bold");
                errorMsg.wrapTextProperty().setValue(true);
                errorMsg.setVisible(false);

                Button followBu = new Button();
                if (jobSeekerDao.isFollowing(Session.getLoggedUser(), selected.getName()))
                    followBu.setText("UNFOLLOW");
                else
                    followBu.setText("FOLLOW");

                followBu.setStyle("-fx-border-color: darkgray; -fx-border-radius: 8px; -fx-border-width: 2px; -fx-padding: 5px 22px; -fx-background-radius: 8px;");

                followBu.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        if (followBu.getText().equals("FOLLOW")) {
                            if (jobSeekerDao.followCompany(Session.getLoggedUser(), selected.getName())) {
                                followBu.setText("UNFOLLOW");
                                errorMsg.setText("You now follow the company!");
                                errorMsg.setVisible(true);
                            }
                            else {
                                errorMsg.setText("Failed to follow the company!");
                                errorMsg.setVisible(true);
                            }
                        } else {
                            if (jobSeekerDao.unfollowCompany(Session.getLoggedUser(), selected.getName())) {
                                followBu.setText("FOLLOW");
                                errorMsg.setText("You don't follow\nanymore the company");
                                errorMsg.setVisible(true);
                            }
                            else {
                                errorMsg.setText("Failed to unfollow the company!");
                                errorMsg.setVisible(true);
                            }
                        }
                    }
                });

                HBox hbox = new HBox(40);
                hbox.getChildren().addAll(followBu, errorMsg);
                vbox.getChildren().addAll(flowCompanyName, flowEmail, hbox);
            } else {
                vbox.getChildren().addAll(flowCompanyName, flowEmail);
            }

            vbox.setStyle("-fx-background-color: #ADD8E6 ; -fx-font-family: sans-serif-verdana ; -fx-font-size: 15px ; -fx-padding: 40");
            Stage jobOfferPage = new Stage();
            jobOfferPage.setTitle("---- Company info ----");
            jobOfferPage.setScene(new Scene(vbox, 440, 300));
            jobOfferPage.show();
        }else {
            logger.info("Row Selected = NULL");
        }
    }

}
