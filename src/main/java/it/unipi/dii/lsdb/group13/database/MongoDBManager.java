package it.unipi.dii.lsdb.group13.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

class MongoDBManager {
     private MongoClient mongoClient;
     private MongoDatabase database;
     private static MongoDBManager dbManager = null;

     private MongoDBManager(){
         ConnectionString uriString = new ConnectionString("mongodb://localhost:27017"); // to connect to single local instance
         //String uriString= "mongodb://localhost:27018,localhost:27019,localhost:27020"; // to connect to local cluster
         MongoClientSettings mcs = MongoClientSettings.builder().applyConnectionString(uriString).build(); // for now its the same then we can change the settings for the cluster
         mongoClient = MongoClients.create(mcs);
         database = mongoClient.getDatabase("job_advisor");
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