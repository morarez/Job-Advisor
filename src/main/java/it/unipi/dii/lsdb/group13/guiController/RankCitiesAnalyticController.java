package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.JobOfferDao;
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
        JobOfferDao jobOfferDao = new JobOfferDao();
        label.setText(jobOfferDao.rankCities(jobType.getValue().toString()).toString());
    }
}
