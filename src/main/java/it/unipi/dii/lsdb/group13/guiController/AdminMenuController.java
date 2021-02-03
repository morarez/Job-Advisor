package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.App;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

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
}