package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.App;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.IOException;

import org.apache.log4j.Logger;

public class JobSeekerMenuController {
	 public JobSeekerMenuController() {
	    	Logger logger = Logger.getLogger(JobSeekerMenuController.class.getName());
	    }
    @FXML
    private BorderPane menuJobSeeker;

    @FXML
    private void initialize() throws IOException {
        App.setDimStage(900.0, 600.0);
        pressHomePage();
    }

    @FXML
    private void pressExit() throws IOException {
        App.setRoot("LoginPage");
        App.setDimStage(640.0, 550.0);
    }

    @FXML
    private void pressFollowedCompanies() throws IOException {
        GridPane pane = (GridPane) App.loadFXML("FollowedCompanies");
        menuJobSeeker.getChildren().removeAll(menuJobSeeker.getCenter(), menuJobSeeker.getLeft(), menuJobSeeker.getRight());
        menuJobSeeker.setCenter(pane);
    }

    @FXML
    private void pressSavedJobOffers() throws IOException {
        GridPane pane = (GridPane) App.loadFXML("SavedJobOffers");
        menuJobSeeker.getChildren().removeAll(menuJobSeeker.getCenter(), menuJobSeeker.getLeft(), menuJobSeeker.getRight());
        menuJobSeeker.setCenter(pane);
    }

    @FXML
    private void pressViewAccount() throws IOException {
        VBox vbox = (VBox) App.loadFXML("JobSeekerInfo");
        menuJobSeeker.getChildren().removeAll(menuJobSeeker.getCenter(), menuJobSeeker.getLeft(), menuJobSeeker.getRight());
        menuJobSeeker.setCenter(vbox);
    }

    @FXML
    private void pressUpdateAccount() throws IOException {
        GridPane pane = (GridPane) App.loadFXML("UpdateAccountJobSeeker");
        menuJobSeeker.getChildren().removeAll(menuJobSeeker.getCenter(), menuJobSeeker.getLeft(), menuJobSeeker.getRight());
        menuJobSeeker.setCenter(pane);
    }

    @FXML
    public void pressHomePage() throws IOException {
        GridPane pane = (GridPane) App.loadFXML("JobSeekerHomePage");
        menuJobSeeker.getChildren().removeAll(menuJobSeeker.getCenter(), menuJobSeeker.getLeft(), menuJobSeeker.getRight());
        menuJobSeeker.setCenter(pane);
    }

    @FXML
    private void pressSearchJobOffer() throws IOException{
    	GridPane pane = (GridPane) App.loadFXML("SearchJobOffer");
        menuJobSeeker.getChildren().removeAll(menuJobSeeker.getCenter(), menuJobSeeker.getLeft(), menuJobSeeker.getRight());
    	menuJobSeeker.setCenter(pane);
    }

    @FXML
    private void pressSearchCompany() throws IOException{
    	GridPane pane = (GridPane) App.loadFXML("SearchACompany");
        menuJobSeeker.getChildren().removeAll(menuJobSeeker.getCenter(), menuJobSeeker.getLeft(), menuJobSeeker.getRight());
    	menuJobSeeker.setCenter(pane);
    }

    @FXML
    private void pressViewRecommendations() throws IOException {
        GridPane paneComp = (GridPane) App.loadFXML("JobSeekerRecComp");
        GridPane paneOff = (GridPane) App.loadFXML("JobSeekerRecOffers");
        menuJobSeeker.getChildren().removeAll(menuJobSeeker.getCenter(), menuJobSeeker.getLeft(), menuJobSeeker.getRight());
        menuJobSeeker.setLeft(paneComp);
        menuJobSeeker.setRight(paneOff);
    }
}