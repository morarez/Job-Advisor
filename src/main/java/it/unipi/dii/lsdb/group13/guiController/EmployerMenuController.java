package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.App;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class EmployerMenuController {

    @FXML
    private BorderPane menuEmployer;

    @FXML
    private void initialize() throws IOException {
        pressHomePage();
    }

    @FXML
    private void pressExit() throws IOException {
        App.setRoot("LoginPage");
    }
    @FXML
    private void pressViewAccount() throws IOException {
        VBox vbox = (VBox) App.loadFXML("EmployerInfo");
        menuEmployer.getChildren().removeAll();
        menuEmployer.setCenter(vbox);
    }
    @FXML
    private void pressUpdateAccount() throws IOException {
        GridPane pane = (GridPane) App.loadFXML("UpdateAccountEmployer");
        menuEmployer.getChildren().removeAll();
        menuEmployer.setCenter(pane);
    }

    @FXML
    private void pressHomePage() throws IOException {
        VBox vbox = (VBox) App.loadFXML("EmployerHomePage");
        GridPane tableJobOffers = (GridPane) App.loadFXML("EmployerTableView");
        menuEmployer.setCenter(tableJobOffers);
        menuEmployer.setMargin(menuEmployer.getCenter(), new Insets(30, 40, 30, 30));
    }

    @FXML
    private void pressPublishNewJob() throws IOException {
        VBox vbox = (VBox) App.loadFXML("CreateJobOffer");
        menuEmployer.getChildren().removeAll();
        menuEmployer.setCenter(vbox);
    }
}