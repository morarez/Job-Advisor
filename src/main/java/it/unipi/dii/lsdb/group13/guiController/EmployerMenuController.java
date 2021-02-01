package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.App;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class EmployerMenuController {


    public BorderPane menuEmployer;

    @FXML
    private void pressExit() throws IOException {
        App.setRoot("LoginPage");
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
        VBox vbox = (VBox) App.loadFXML("EmployerHomePage");
        menuEmployer.setCenter(vbox);
    }
    @FXML
    private void pressPublishNewJob() throws IOException {
        VBox vbox = (VBox) App.loadFXML("CreateJobOffer");
        menuEmployer.setCenter(vbox);
    }
}