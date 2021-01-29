package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class JobSeekerHomePageController {

    @FXML
    private void pressExit() throws IOException {
        App.setRoot("LoginPage");
    }
    @FXML
    private void pressViewAccount() throws IOException {
        App.setRoot("ViewJobSeekerAccount");
    }
}