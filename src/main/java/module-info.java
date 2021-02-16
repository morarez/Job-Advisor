module it.unipi.dii.lsdb.group13 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires javafx.base;
	requires javafx.graphics;
	requires java.xml;
    requires java.net.http;
    requires jdk.net;
    requires org.neo4j.driver;
    requires log4j; //required for logging

    opens it.unipi.dii.lsdb.group13.guiController to javafx.fxml;
    opens it.unipi.dii.lsdb.group13.entities to javafx.base;
    exports it.unipi.dii.lsdb.group13;
}