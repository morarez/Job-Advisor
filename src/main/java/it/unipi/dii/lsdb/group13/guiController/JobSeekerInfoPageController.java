package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.entities.JobSeeker;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

class JobSeekerInfoPageController {

    JobSeeker selected;

    JobSeekerInfoPageController(JobSeeker selected) {
        this.selected = selected;
        showInfoPage();
    }

    private void showInfoPage() {
        if(selected != null) {
            System.out.println("Rows selected! " + selected.getUsername());

            VBox vbox = new VBox(10);

            TextFlow flowUsername = new TextFlow();
            Label username = new Label("USERNAME: "); username.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowUsername.getChildren().addAll(username, new Label (selected.getUsername()));

            TextFlow flowName = new TextFlow();
            Label name = new Label("FULL NAME: "); name.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowName.getChildren().addAll(name, new Label (selected.getFirstName()), new Label(" "), new Label(selected.getLastName()));

            TextFlow flowGender = new TextFlow();
            Label gender = new Label("GENDER: "); gender.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowGender.getChildren().addAll(gender, new Label(selected.getGender()));

            TextFlow flowBirthday = new TextFlow();
            Label birthday = new Label("BIRTHDAY: "); birthday.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowBirthday.getChildren().addAll(birthday, new Label(selected.getBirthdate()));

            TextFlow flowEmail = new TextFlow();
            Label email = new Label("EMAIL: "); email.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowEmail.getChildren().addAll(email, new Label(selected.getEmail()));

            TextFlow flowLocation = new TextFlow();
            Label location = new Label("LOCATION: "); location.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowLocation.getChildren().addAll(location, new Label(selected.getLocation().getCity() + " (" + selected.getLocation().getState() + ")"));

            Label skillsLabel = new Label("SKILLS: ");
            skillsLabel.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");

            Label skills = new Label("No skills");
            if(!selected.getSkills().isEmpty()) {
                skills.setText(selected.getSkillsAsString());
                skills.wrapTextProperty().setValue(true);
            }

            vbox.getChildren().addAll(flowUsername, flowName, flowGender, flowBirthday, flowEmail, flowLocation, skillsLabel, skills);
            vbox.setStyle("-fx-background-color: #ADD8E6 ; -fx-font-family: sans-serif-verdana ; -fx-font-size: 15px ; -fx-padding: 40");
            Stage jobOfferPage = new Stage();
            jobOfferPage.setTitle("---- JobSeeker info ----");
            jobOfferPage.setScene(new Scene(vbox, 440, 500));
            jobOfferPage.show();
        }
    }

}
