package it.unipi.dii.lsdb.group13.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import it.unipi.dii.lsdb.group13.entities.Employer;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import it.unipi.dii.lsdb.group13.main.Session;
import org.bson.Document;

import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.push;
import static com.mongodb.client.model.Updates.set;

public class EmployerDao {

	 public String signUp(String companyName,String email,String password)
	    {
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

    public boolean deleteAccount() {
	     boolean ret = true;
    	try {
        Session.getSingleton();
        String username = Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        MongoCollection<Document> companies= mongoDB.getCompaniesCollection();
        MongoCollection<Document> joboffers= mongoDB.getJobOffersCollection();
        DeleteResult offers= joboffers.deleteMany(eq("company_name",username));
        System.out.println(offers.getDeletedCount()+" Job Offers have been deleted.");
        DeleteResult deleteresult= companies.deleteOne(eq("_id",username));
        System.out.println(deleteresult.getDeletedCount()+" company has been deleted.");
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		ret = false;
    	}
    	return ret;
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

    public void addLocation() {
        Scanner sc= new Scanner(System.in);
        Session.getSingleton();
        String username= Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        try {
            List<Document> location= new ArrayList<>();
            System.out.println("Enter city: ");
            String city= sc.nextLine();
            System.out.println("Enter state: ");
            String state= sc.nextLine();
            Document loc= new Document();
            loc.put("city",city);
            loc.put("state",state);
            System.out.println(loc);
            MongoCollection col= mongoDB.getCompaniesCollection();
            col.updateOne(eq("_id",username),push("locations",loc));
            System.out.println("New location added!");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void changeEmail() {
        Scanner sc= new Scanner(System.in);
        Session.getSingleton();
        String username= Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        try {
            System.out.println("Enter new email: ");
            String email= sc.nextLine();
            MongoCollection col= mongoDB.getCompaniesCollection();
            col.updateOne(eq("_id",username),set("email",email));
            System.out.println("Email updated!");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
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

    public int deleteEmployer(String username) {
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        DeleteResult deleted = mongoDB.getCompaniesCollection().deleteOne(eq("_id", username));
        return (int) deleted.getDeletedCount();
    }
}
