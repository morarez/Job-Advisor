package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import it.unipi.dii.lsdb.group13.entities.JobSeeker;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class SearchUserController {

    @FXML
    private Label errorMsg;

    @FXML
    private TableView tableFoundedUsers;

    @FXML
    private TextField cityField;

    @FXML
    private TextField stateField;

    @FXML
    private TextField skillField;

    private ObservableList<JobSeeker> oList = null;

    @FXML
    private void pressSearch() {

        if ( (!cityField.getText().isEmpty() && !skillField.getText().isEmpty()) || (!stateField.getText().isEmpty() && !skillField.getText().isEmpty())) {
            errorMsg.setText("Search only by skill or by location, no both");
            errorMsg.setVisible(true);
        }

        if ( !cityField.getText().isEmpty() && !stateField.getText().isEmpty()) {
            System.out.println("Search by location");

            JobSeekerDao jobSeekerDao = new JobSeekerDao();
            List<JobSeeker> seekers = new ArrayList<>(jobSeekerDao.searchLocation(cityField.getText(), stateField.getText()));
            oList = FXCollections.observableArrayList(seekers);
            tableFoundedUsers.setItems(oList);

        } else if (!cityField.getText().isEmpty()) {

            System.out.println("Search by city");

            JobSeekerDao jobSeekerDao = new JobSeekerDao();
            List<JobSeeker> seekers = new ArrayList<>(jobSeekerDao.searchByCity(cityField.getText()));
            oList = FXCollections.observableArrayList(seekers);
            tableFoundedUsers.setItems(oList);

        } else if (!stateField.getText().isEmpty()) {

            System.out.println("Search by state");

            JobSeekerDao jobSeekerDao = new JobSeekerDao();
            List<JobSeeker> seekers = new ArrayList<>(jobSeekerDao.searchByState(stateField.getText()));
            oList = FXCollections.observableArrayList(seekers);
            tableFoundedUsers.setItems(oList);

        } else if ( !skillField.getText().isEmpty() ) {

            System.out.println("Search by skill");

            JobSeekerDao jobSeekerDao = new JobSeekerDao();
            List<JobSeeker> seekers = new ArrayList<>(jobSeekerDao.searchSkill(skillField.getText()));
            oList = FXCollections.observableArrayList(seekers);
            tableFoundedUsers.setItems(oList);

        } else {
            errorMsg.setText("Insert a skill or a location");
            errorMsg.setVisible(true);
        }
    }

    @FXML
    private void rowSelected() {
        JobSeeker selected = (JobSeeker) tableFoundedUsers.getSelectionModel().getSelectedItem();
        if(selected != null) {
            System.out.println("Rows selected! " + selected.getUsername());

            VBox vbox = new VBox(10);

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
            Label skills = null;
            if (selected.getSkills() != null) {
                skills = new Label(selected.getSkillsAsString());
                skills.wrapTextProperty().setValue(true);
            } else {
                skills = new Label("No skills");
                skills.wrapTextProperty().setValue(true);
            }

            vbox.getChildren().addAll(flowName, flowGender, flowBirthday, flowEmail, flowLocation, skillsLabel, skills);
            vbox.setStyle("-fx-background-color: #ADD8E6 ; -fx-font-family: sans-serif-verdana ; -fx-font-size: 15px ; -fx-padding: 40");
            Stage jobOfferPage = new Stage();
            jobOfferPage.setTitle("---- JobSeeker info ----");
            jobOfferPage.setScene(new Scene(vbox, 440, 500));
            jobOfferPage.show();
        }
    }

    @FXML
    private void emptyFields() {
        errorMsg.setVisible(false);
        oList = null;
        tableFoundedUsers.setItems(null);
        System.out.println("arrived here");
    }
}
