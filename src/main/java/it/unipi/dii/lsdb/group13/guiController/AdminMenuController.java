package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.App;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static it.unipi.dii.lsdb.group13.App.loadFXML;

public class AdminMenuController {

    @FXML
    private BorderPane menuAdmin;

    @FXML
    private void initialize() {
        pressHomePage();
    }

    @FXML
    private void pressExit() throws IOException {
        App.setRoot("LoginPage");
    }

    @FXML
    private void pressHomePage() {
        menuAdmin.setCenter(new Label("Home page for admin"));
        menuAdmin.setMargin(menuAdmin.getCenter(), new Insets(30, 40, 30, 30));
    }
    @FXML
    private void pressCitiesAnalytic() throws IOException {
        VBox vbox = (VBox) loadFXML("RankCitiesAnalytic");
        menuAdmin.setCenter(vbox);
    }
}