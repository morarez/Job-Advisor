package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.App;
import javafx.event.Event;
import javafx.fxml.FXML;

import java.io.IOException;

public class AdminHomePageController {

    @FXML
    private void pressExit() throws IOException {
        App.setRoot("LoginPage");
    }
}