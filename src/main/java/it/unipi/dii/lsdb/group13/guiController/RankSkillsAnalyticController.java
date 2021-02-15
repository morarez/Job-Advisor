package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RankSkillsAnalyticController {

    @FXML
    private Label label;

    @FXML
    private void initialize(){
        JobSeekerDao jobSeekerDao = new JobSeekerDao();
        label.setText(jobSeekerDao.rankSkills().toString());
        label.wrapTextProperty().setValue(true);
    }
}
