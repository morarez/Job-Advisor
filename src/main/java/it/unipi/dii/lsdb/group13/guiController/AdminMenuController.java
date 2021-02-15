package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.App;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static it.unipi.dii.lsdb.group13.App.loadFXML;

public class AdminMenuController {

    @FXML
    private BorderPane menuAdmin;

    @FXML
    private void initialize() throws IOException {
        pressHomePage();
        App.setDimStage(640.0, 550.0);
    }

    @FXML
    private void pressExit() throws IOException {
        App.setRoot("LoginPage");
    }

    @FXML
    private void pressHomePage() throws IOException {
        GridPane searchPage = (GridPane) App.loadFXML("AdminHomePage");
        menuAdmin.setCenter(searchPage);
        menuAdmin.setMargin(menuAdmin.getCenter(), new Insets(10, 20, 10, 20));
    }
    @FXML
    private void pressCitiesAnalytic() throws IOException {
        VBox vbox = (VBox) loadFXML("RankCitiesAnalytic");
        menuAdmin.setCenter(vbox);
    }

    @FXML
    private void pressSkillsAnalytic() throws IOException {
        VBox vbox = (VBox) loadFXML("RankSkillsAnalytic");
        menuAdmin.setCenter(vbox);
    }
    @FXML
    private void pressCompaniesAnalytic() throws IOException {
    	GridPane pane = (GridPane) loadFXML("TopFollowedCompaniesAnalytic");
        menuAdmin.setCenter(pane);
    }
    @FXML
    private void pressStatistics() throws IOException{
        VBox vbox = (VBox) loadFXML("AdminStatistics");
        menuAdmin.setCenter(vbox);
    }
}