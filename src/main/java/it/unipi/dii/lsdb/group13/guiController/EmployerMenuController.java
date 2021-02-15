package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.App;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.IOException;

import org.apache.log4j.Logger;

public class EmployerMenuController {
	 public EmployerMenuController() {
	    	Logger logger = Logger.getLogger(EmployerMenuController.class.getName());
	    }

    @FXML
    private BorderPane menuEmployer;

    @FXML
    private void initialize() throws IOException {
        pressHomePage();
        App.setDimStage(700.0, 700.0);
    }

    @FXML
    private void pressExit() throws IOException {
        App.setRoot("LoginPage");
        App.setDimStage(640.0, 550.0);
    }

    @FXML
    private void pressViewAccount() throws IOException {
        VBox vbox = (VBox) App.loadFXML("EmployerInfo");
        menuEmployer.setCenter(vbox);
    }

    @FXML
    private void pressUpdateAccount() throws IOException {
        GridPane pane = (GridPane) App.loadFXML("UpdateAccountEmployer");
        menuEmployer.setCenter(pane);
    }

    @FXML
    private void pressHomePage() throws IOException {
        GridPane tableJobOffers = (GridPane) App.loadFXML("EmployerTableView");
        menuEmployer.setCenter(tableJobOffers);
        menuEmployer.setMargin(menuEmployer.getCenter(), new Insets(30, 40, 30, 30));
    }

    @FXML
    private void pressPublishNewJob() throws IOException {
        VBox vbox = (VBox) App.loadFXML("CreateJobOffer");
        menuEmployer.setCenter(vbox);
    }

    @FXML
    private void pressSearchUser() throws IOException {
        VBox vbox = (VBox) App.loadFXML("SearchUser");
        menuEmployer.setCenter(vbox);
    }

    @FXML
    private void pressViewFollowers() throws IOException {
        GridPane pane = (GridPane) App.loadFXML("EmployerViewFollowers");
        menuEmployer.setCenter(pane);
    }
}