module it.unipi.dii.lsdb.group13 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires java.net.http;
    requires jdk.net;

    opens it.unipi.dii.lsdb.group13.guiController to javafx.fxml;
    exports it.unipi.dii.lsdb.group13;
}