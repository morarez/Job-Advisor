package it.unipi.dii.lsdb.group13.guiController;

import org.apache.log4j.Logger;

import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RankSkillsAnalyticController {
	public RankSkillsAnalyticController() {
    	Logger logger = Logger.getLogger(RankSkillsAnalyticController.class.getName());
    }
    @FXML
    private Label label;

    @FXML
    private void initialize(){
        JobSeekerDao jobSeekerDao = new JobSeekerDao();
        label.setText(jobSeekerDao.rankSkills().toString());
        label.wrapTextProperty().setValue(true);
    }
}
