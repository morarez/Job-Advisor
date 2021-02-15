package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.JobOfferDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class AdminStatisticsController {
	 public AdminStatisticsController() {
	    	Logger logger = Logger.getLogger(AdminStatisticsController.class.getName());
	    }
    @FXML
    private Label seekersCount;

    @FXML
    private Label companiesCount;

    @FXML
    private Label offersCount;

    @FXML
    private void initialize(){
        JobOfferDao jobOfferDao = new JobOfferDao();
        ArrayList<Long> stats = (ArrayList<Long>) jobOfferDao.statistics();
        seekersCount.setText("Number of job-seekers: " + stats.get(0).toString());
        companiesCount.setText("Number of companies: " + stats.get(1).toString());
        offersCount.setText("Number of job offers: " + stats.get(2).toString());
    }

}
