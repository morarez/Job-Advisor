package it.unipi.dii.lsdb.group13.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import it.unipi.dii.lsdb.group13.entities.Employer;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import it.unipi.dii.lsdb.group13.main.Session;
import org.bson.Document;
import org.neo4j.driver.TransactionWork;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import static org.neo4j.driver.Values.parameters;

public class EmployerDao {

	 public String signUp(String companyName,String email,String password) {
	    	try {
	            MongoDBManager mongoDB = MongoDBManager.getInstance();
	            MongoCollection companies = mongoDB.getCompaniesCollection();
	            Document doc= new Document("_id",companyName).append("email",email).append("password",password);
	            companies.insertOne(doc);
	            return "true";
	        }
	        catch(Exception e) {
	            return e.getMessage();
	        }
	 }

    public void addEmployerToNeo4j(String name){
        Neo4jManager neo4j = Neo4jManager.getInstance();
        try (org.neo4j.driver.Session session = neo4j.getDriver().session()) {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run("MERGE (c:Company {name: $name})",
                        parameters("name", name));
                return null;
            });
            System.out.println("User added to neo4j");
        }
    }

    public boolean deleteAccount(String username) {
	     boolean ret = true;
    	try {
            MongoDBManager mongoDB = MongoDBManager.getInstance();
            MongoCollection<Document> companies= mongoDB.getCompaniesCollection();
            MongoCollection<Document> joboffers= mongoDB.getJobOffersCollection();
            DeleteResult offers= joboffers.deleteMany(eq("company_name",username));
            System.out.println(offers.getDeletedCount()+" Job Offers have been deleted.");
            DeleteResult deleteresult= companies.deleteOne(eq("_id",username));
            System.out.println(deleteresult.getDeletedCount()+" company has been deleted.");
            deleteFromNeo4j(username);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		ret = false;
    	}
    	return ret;
    }

    private void deleteFromNeo4j(String name){
        Neo4jManager neo4j = Neo4jManager.getInstance();
        try (org.neo4j.driver.Session session = neo4j.getDriver().session()) {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run("Match(c:Company {name: $name }) OPTIONAL MATCH (c)-[:PUBLISHED]->(m) DETACH DELETE c,m",
                        parameters("name", name));
                return null;
            });
            System.out.println("User deleted from neo4j");
        }
    }

    public boolean changePassword(String newpwd, String pwdagain) {
        Session.getSingleton();
        String username= Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        MongoCollection col= mongoDB.getCompaniesCollection();
        try {
            col.updateOne(eq("_id",username),set("password",newpwd));
            return true;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //depending on the role, the appropriate collection in MongoDB is opened and the username is searched into it
    // if it is founded, returns the corresponding password, otherwise returns null
    public String searchUsername(String username)  throws Exception{
        String foundedPw;
        Document doc;
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        doc = (Document) mongoDB.getCompaniesCollection().find(eq("_id", username)).first();
        if (doc == null) {
            throw new Exception("Invalid username");
        } else {
            foundedPw = (String) doc.get("password");
        }
        System.out.println("check founded password: "+ foundedPw);
        return foundedPw;
    }

    public Employer findUser(String username) {
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        Document user = (Document) mongoDB.getCompaniesCollection().find(eq("_id", username)).first();
        // handle nullPointerException
        if (user != null) {
            return new Employer(username, user.getString("email"));
        } else {
            return null;
        }
    }
}
