package it.unipi.dii.lsdb.group13.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

class MongoDBManager implements DatabaseManager {
     private MongoClient mongoClient;
     private MongoDatabase database;
     private static MongoDBManager dbManager = null;

     MongoDBManager(){
         String uriString= "mongodb://localhost:27017";
         mongoClient = MongoClients.create(uriString);
         database= mongoClient.getDatabase("job_advisor");
         System.out.println("Connection opened");
     }

    //@Override
    static MongoDBManager getInstance() {
    	if(dbManager == null){
    	    dbManager = new MongoDBManager();
        }
    	return dbManager;
    }

    //@Override
    void closeDB() {
        mongoClient.close();
        System.out.println("Connection closed");
    }

    MongoCollection getJobSeekersCollection(){ return database.getCollection("job_seekers"); }

    MongoCollection getCompaniesCollection() { return database.getCollection("companies"); }

    MongoCollection getJobOffersCollection() { return database.getCollection("job_offers"); }

}