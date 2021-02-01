package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.App;
import javafx.fxml.FXML;

import java.io.IOException;

public class EmployerHomePageController {

    @FXML
    private void pressExit() throws IOException {
        App.setRoot("LoginPage");
    }
    @FXML
    private void pressViewAccount() throws IOException {
        /*App.setRoot("...");*/
    }
    @FXML
    private void pressUpdateAccount() throws IOException {
    	App.setRoot("UpdateAccountEmployer");
    }
}