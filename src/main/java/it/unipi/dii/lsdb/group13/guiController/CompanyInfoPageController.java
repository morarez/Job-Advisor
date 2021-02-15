package it.unipi.dii.lsdb.group13.guiController;

import org.apache.log4j.Logger;

import it.unipi.dii.lsdb.group13.entities.Employer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

class CompanyInfoPageController {
	 public CompanyInfoPageController() {
	    	Logger logger = Logger.getLogger(CompanyInfoPageController.class.getName());
	    }
    private Employer selected;

    CompanyInfoPageController(Employer selected) {
        this.selected = selected;
        showInfoPage();
    }

    private void showInfoPage() {
        if(selected != null) {
            System.out.println("Rows selected! " + selected.getName());


            VBox vbox = new VBox(10);

            TextFlow flowCompanyName = new TextFlow();
            Label title = new Label("COMPANY NAME: "); title.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowCompanyName.getChildren().addAll(title, new Label (selected.getName()));

            TextFlow flowEmail = new TextFlow();
            Label location = new Label("EMAIL: "); location.setStyle("-fx-font-size: 18 ; -fx-font-weight: bold ; -fx-text-fill: cadetblue");
            flowEmail.getChildren().addAll(location, new Label(selected.getEmail()));

            vbox.getChildren().addAll(flowCompanyName, flowEmail);
            vbox.setStyle("-fx-background-color: #ADD8E6 ; -fx-font-family: sans-serif-verdana ; -fx-font-size: 15px ; -fx-padding: 40");
            Stage jobOfferPage = new Stage();
            jobOfferPage.setTitle("---- Company info ----");
            jobOfferPage.setScene(new Scene(vbox, 440, 300));
            jobOfferPage.show();
        }
    }

}
