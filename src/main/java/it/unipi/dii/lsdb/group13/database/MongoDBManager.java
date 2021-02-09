package it.unipi.dii.lsdb.group13.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

class MongoDBManager {
     private MongoClient mongoClient;
     private MongoDatabase database;
     private static MongoDBManager dbManager = null;

     private MongoDBManager(){
         String uriString= "mongodb://localhost:27017";
         mongoClient = MongoClients.create(uriString);
         database= mongoClient.getDatabase("job_advisor");
         System.out.println("Mongo Connection opened");
     }

    static MongoDBManager getInstance() {
    	if(dbManager == null){
    	    dbManager = new MongoDBManager();
        }
    	return dbManager;
    }

    void closeDB() {
        mongoClient.close();
        System.out.println("Mongo Connection closed");
    }

    MongoCollection getJobSeekersCollection(){ return database.getCollection("job_seekers"); }

    MongoCollection getCompaniesCollection() { return database.getCollection("companies"); }

    MongoCollection getJobOffersCollection() { return database.getCollection("job_offers"); }

}