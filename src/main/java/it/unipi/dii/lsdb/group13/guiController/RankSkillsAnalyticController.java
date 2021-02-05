package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.AdminDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RankSkillsAnalyticController {

    @FXML
    private Label label;

    @FXML
    private void initialize(){
        AdminDao adminDao = new AdminDao();
        label.setText(adminDao.rankSkills().toString());
    }
}
