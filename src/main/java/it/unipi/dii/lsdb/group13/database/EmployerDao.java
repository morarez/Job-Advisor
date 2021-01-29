package it.unipi.dii.lsdb.group13.database;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import it.unipi.dii.lsdb.group13.main.Session;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.push;
import static com.mongodb.client.model.Updates.set;

public class EmployerDao {

    public void signUp()
    {
        try {
            System.out.println("Enter the name of company: ");
            Scanner sc= new Scanner(System.in);
            String companyName=sc.nextLine();
            System.out.println("Enter email of the company: ");
            String email= sc.nextLine();
            System.out.println("Enter password: ");
            String password= sc.nextLine();
            System.out.println("How many locations do you want to enter: ");
            int n= sc.nextInt();
            List<DBObject> locations= new ArrayList<>();
            for(int i=1;i<=n;i++)
            {
                System.out.println("Enter the city: ");
                Scanner s= new Scanner(System.in);
                String city= s.nextLine();
                System.out.println("Enter the state: ");
                String state= s.nextLine();
                DBObject document= new BasicDBObject();
                document.put("city",city);
                document.put("state",state);
                locations.add(document);
            }
            MongoDBManager mongoDB = MongoDBManager.getInstance();
            MongoCollection companies = mongoDB.getCompaniesCollection();
            Document doc= new Document("_id",companyName).append("email",email).append("password",password).append("locations",locations);
            companies.insertOne(doc);
            System.out.println("Signed up Successfully!");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAccount()
    {
        Session.getSingleton();
        String username = Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        MongoCollection mongodb= mongoDB.getCompaniesCollection();
        DeleteResult deleteresult= mongodb.deleteOne(eq("_id",username));
        System.out.println(deleteresult.getDeletedCount()+" document has been deleted.");
    }

    public void changePassword() {
        Scanner sc = new Scanner(System.in);
        Session.getSingleton();
        String username= Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        String current= null, foundpwd = null, newpwd= null, pwdagain= null;
        Document doc= null;
        try {
            do
            {
                System.out.println("Enter current password: ");
                current=sc.nextLine();
                MongoCollection col= mongoDB.getCompaniesCollection();
                try (MongoCursor<Document> cursor= col.find(eq("_id", username)).iterator())
                {
                    doc= cursor.next();
                    foundpwd= (String) doc.get("password");
                }
                while(foundpwd.equals(current)) {
                    System.out.println("Enter new password: ");
                    newpwd= sc.nextLine();
                    System.out.println("Confirm new password: ");
                    pwdagain= sc.nextLine();
                    if(newpwd.equals(pwdagain))
                    {
                        col.updateOne(eq("_id",username),set("password",newpwd));
                        System.out.println("Password updated!");
                        return;
                    }
                    else
                    {
                        System.out.println("Passwords don't match! \n Try Again! \n");
                        continue;
                    }
                }
                System.out.println("Please enter correct current password again!\n");
            }while(!foundpwd.equals(current));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
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
    public void createNewJobOffer(String title, String description, String city, String state,
                                  String salaryTimeUnit,String minSalary, String maxSalary){
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        Session.getSingleton();
        String companyName = Session.getLoggedUser();
        JobOffer jobOffer = new JobOffer(title,companyName,description,"",minSalary,maxSalary,salaryTimeUnit,state,city);
        try {
            MongoCollection jobOffers= mongoDB.getJobOffersCollection();
            jobOffers.insertOne(jobOffer.createDoc());
            MongoCollection companies = mongoDB.getCompaniesCollection();
            // Add the job offer to the list of company's job offers
            companies.updateOne(eq("_id", companyName), Updates.addToSet("job_offers", jobOffer.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //depending on the role, the appropriate collection in MongoDB is opened and the username is searched into it
    // if it is founded, returns the corresponding password, otherwise returns null
    public String searchUsername(String username) {
        String foundedPw;
        Document doc;
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        doc = (Document) mongoDB.getCompaniesCollection().find(eq("_id", username)).first();
        if (doc == null) {
            throw new IllegalStateException("Invalid username");
        } else {
            foundedPw = (String) doc.get("password");
        }
        System.out.println("check founded password: "+ foundedPw);
        return foundedPw;
    }
}
