package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.App;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class JobSeekerMenuController {

    @FXML
    private BorderPane menuJobSeeker;

    @FXML
    private void pressExit() throws IOException {
        App.setRoot("LoginPage");
    }
    @FXML
    private void pressViewAccount() throws IOException {
        VBox vbox = (VBox) App.loadFXML("JobSeekerInfo");
        menuJobSeeker.setCenter(vbox);
    }
    @FXML
    private void pressUpdateAccount() throws IOException {
        GridPane pane = (GridPane) App.loadFXML("UpdateAccountJobSeeker");
        menuJobSeeker.setCenter(pane);
    }

    @FXML
    public void pressHomePage() throws IOException {
        VBox vbox = (VBox) App.loadFXML("JobSeekerHomePage");
        menuJobSeeker.setCenter(vbox);
    }
    @FXML
    private void pressSearchJobOffer() throws IOException{
    	GridPane pane = (GridPane) App.loadFXML("SearchJobOffer");
    	menuJobSeeker.getChildren().removeAll();
    	menuJobSeeker.setCenter(pane);
    	pane.setPrefWidth(1000);
    	pane.setPrefHeight(500);
    }
}