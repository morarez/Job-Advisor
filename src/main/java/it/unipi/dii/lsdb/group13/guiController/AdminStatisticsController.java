package it.unipi.dii.lsdb.group13.guiController;

import it.unipi.dii.lsdb.group13.database.AdminDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class AdminStatisticsController {
    @FXML
    private Label seekersCount;

    @FXML
    private Label companiesCount;

    @FXML
    private Label offersCount;

    @FXML
    private void initialize(){
        AdminDao adminDao = new AdminDao();
        ArrayList<Long> stats = (ArrayList<Long>) adminDao.statistics();
        seekersCount.setText("Number of job-seekers: " + stats.get(0).toString());
        companiesCount.setText("Number of companies: " + stats.get(1).toString());
        offersCount.setText("Number of job offers: " + stats.get(2).toString());
    }

}
