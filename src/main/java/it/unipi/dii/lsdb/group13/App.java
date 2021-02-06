package it.unipi.dii.lsdb.group13;

import it.unipi.dii.lsdb.group13.database.AdminDao;
import it.unipi.dii.lsdb.group13.database.MongoDBManager;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** aaacosta, h:5}TWc0
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("LoginPage"), 640, 480);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
    @Override
    public void stop() {
        //We need to set mongoDB public. How should we solve this???
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        mongoDB.closeDB();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}