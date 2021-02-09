package it.unipi.dii.lsdb.group13.database;

public class DatabaseManager {
    public static void closeDatabases(){
        MongoDBManager mongoDBManager = MongoDBManager.getInstance();
        Neo4jManager neo4jManager = Neo4jManager.getInstance();
        mongoDBManager.closeDB();
        neo4jManager.close();
    }
}
