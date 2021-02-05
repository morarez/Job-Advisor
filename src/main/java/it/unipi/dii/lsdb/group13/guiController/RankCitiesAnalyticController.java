package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.AdminDao;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class RankCitiesAnalyticController {
    @FXML
    private Label label;

    @FXML
    private ChoiceBox jobType;

    @FXML
    private void pressSubmit(){
        AdminDao adminDao = new AdminDao();
        adminDao.rankSkills();
        label.setText(adminDao.rankCities(jobType.getValue().toString()).toString());
    }
}
