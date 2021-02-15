package it.unipi.dii.lsdb.group13;

import it.unipi.dii.lsdb.group13.database.MongoDBManager;
import it.unipi.dii.lsdb.group13.database.Neo4jManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/** aaacosta, h:5}TWc0
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;
    private static Logger logger = null;

    Neo4jManager neo4jManager;
    MongoDBManager mongoDBManager;
    

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        scene = new Scene(loadFXML("LoginPage"), 640, 480);
        stage.setScene(scene);
        //stage.setFullScreen(true);
        stage.show();
        loadlog4j();
        neo4jManager = Neo4jManager.getInstance();
        mongoDBManager = MongoDBManager.getInstance();
        
    }
    @Override
    public void stop() {
        neo4jManager.close();
        mongoDBManager.close();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void setDimStage(Double width, Double height) {
        stage.setWidth(width);
        stage.setHeight(height);
    }
    public static void loadlog4j() {
    	String path = System.getProperty("user.dir") + "/log.properties";
    	PropertyConfigurator.configure(path);
    }

    public static void main(String[] args) {
        launch();
    }

}