package it.unipi.dii.lsdb.group13.database;

import org.apache.log4j.Logger;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBManager {
     private MongoClient mongoClient;
     private MongoDatabase database;
     private static MongoDBManager dbManager = null;

     private MongoDBManager(){
    	 Logger logger = Logger.getLogger(Neo4jManager.class.getName());
         //to connect to local instance
         ConnectionString uriString = new ConnectionString("mongodb://localhost:27017"); // to connect to single local instance
         MongoClientSettings mcs = MongoClientSettings.builder().applyConnectionString(uriString).build();

         //to connect to local cluster
         //ConnectionString uriString= new ConnectionString("mongodb://localhost:27018,localhost:27019,localhost:27020");

         //to connect to the cluster of VMs
         /*
         ConnectionString uriString= new ConnectionString("mongodb://172.16.3.151:27020,172.16.3.123:27020, 172.16.3.124:27020");
         MongoClientSettings mcs = MongoClientSettings.builder().applyConnectionString(uriString)
                 .readPreference(ReadPreference.nearest())
                 .retryWrites(true)
                 .writeConcern(WriteConcern.W1)
                 .build(); */



         mongoClient = MongoClients.create(mcs);
         database = mongoClient.getDatabase("job_advisor");
         System.out.println("Mongo Connection opened");


     }

    public static MongoDBManager getInstance() {
    	if(dbManager == null){
    	    dbManager = new MongoDBManager();
        }
    	return dbManager;
    }

    public void close() {
        mongoClient.close();
        System.out.println("Mongo Connection closed");
    }

    MongoCollection getJobSeekersCollection(){ return database.getCollection("job_seekers"); }

    MongoCollection getCompaniesCollection() { return database.getCollection("companies"); }

    MongoCollection getJobOffersCollection() { return database.getCollection("job_offers"); }

}