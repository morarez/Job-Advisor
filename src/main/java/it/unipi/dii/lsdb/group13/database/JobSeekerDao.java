package it.unipi.dii.lsdb.group13.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import it.unipi.dii.lsdb.group13.entities.JobSeeker;
import it.unipi.dii.lsdb.group13.main.Session;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

public class JobSeekerDao {

    public boolean signUp(String fname, String lname, String username, String birthday, String g, String password, String email, String city, String state, String skill) {
        try {
            char gender= g.charAt(0);
            List<String> skills = new ArrayList<>(Arrays.asList(skill.split(",")));
            MongoDBManager mongoDB = MongoDBManager.getInstance();
            MongoCollection mongodb= mongoDB.getJobSeekersCollection();
            Document doc= new Document("_id",username).append("password",password).append("first_name",fname).append("last_name",lname).append("birthdate",birthday).append("gender",gender).append("email",email).append("location", new Document("city",city).append("state",state)).append("skills",skills);
            mongodb.insertOne(doc);
            return true;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void delJobSeekerAccount()
    {
        Session.getSingleton();
        String username= Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        MongoCollection mongodb= mongoDB.getJobSeekersCollection();
        DeleteResult deleteresult= mongodb.deleteOne(eq("_id",username));
        System.out.println(deleteresult.getDeletedCount()+" document has been deleted.");
    }

    public boolean changePassword(String newpwd, String pwdagain) {
        Session.getSingleton();
         String username= Session.getLoggedUser();
         MongoDBManager mongoDB = MongoDBManager.getInstance();
         MongoCollection col= mongoDB.getJobSeekersCollection();
         try {
                         col.updateOne(eq("_id",username),set("password",newpwd));
                         return true;
                 }
         catch(Exception e) {
             System.out.println(e.getMessage());
             return false;
         }
     }
        

  /*  public void changeFirstName()
    {
        Scanner sc= new Scanner(System.in);
        Session.getSingleton();
        String username= Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        try {
            System.out.println("Enter first name: ");
            String fname= sc.nextLine();
            MongoCollection col= mongoDB.getJobSeekersCollection();
            col.updateOne(eq("_id",username),set("first_name",fname));
            System.out.println("First Name updated!");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void changeLastName()
    {
        Scanner sc= new Scanner(System.in);
        Session.getSingleton();
        String username= Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        try {
            System.out.println("Enter last name: ");
            String lname= sc.nextLine();
            MongoCollection col= mongoDB.getJobSeekersCollection();
            col.updateOne(eq("_id",username),set("last_name",lname));
            System.out.println("Last Name updated!");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void changeBirthday()
    {
        Scanner sc= new Scanner(System.in);
        Session.getSingleton();
        String username= Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        try {
            System.out.println("Enter your birthdate (mm/dd/yy): ");
            String birthday= sc.nextLine();
            MongoCollection col= mongoDB.getJobSeekersCollection();
            col.updateOne(eq("_id",username),set("birthdate",birthday));
            System.out.println("Birthday updated!");
            mongoDB.closeDB();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void changeGender()
    {
        Scanner sc= new Scanner(System.in);
        Session.getSingleton();
        String username= Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        try {
            System.out.println("Enter gender (M/F): ");
            char gender= sc.nextLine().charAt(0);
            MongoCollection col= mongoDB.getJobSeekersCollection();
            col.updateOne(eq("_id",username),set("gender",gender));
            System.out.println("Gender updated!");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void changeEmail()
    {
        Scanner sc= new Scanner(System.in);
        Session.getSingleton();
        String username= Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        try {
            System.out.println("Enter new email: ");
            String email= sc.nextLine();
            MongoCollection col= mongoDB.getJobSeekersCollection();
            col.updateOne(eq("_id",username),set("email",email));
            System.out.println("Email updated!");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void changeLocation()
    {
        Scanner sc= new Scanner(System.in);
        Session.getSingleton();
        String username= Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        try {
            System.out.println("Enter city: ");
            String city= sc.nextLine();
            System.out.println("Enter state: ");
            String state= sc.nextLine();
            Document loc= new Document();
            loc.put("city",city);
            loc.put("state",state);
            MongoCollection col= mongoDB.getJobSeekersCollection();
            col.updateOne(eq("_id",username),set("location",loc));
            System.out.println("Location updated!");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }*/
    public boolean addSkill(String skill)
    {
        Session.getSingleton();
        String username= Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        try {
            MongoCollection col= mongoDB.getJobSeekersCollection();
            col.updateOne(eq("_id",username),push("skills",skill));
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean deleteSkill(String skill)
    {
        Session.getSingleton();
        String username= Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        try {
            MongoCollection col= mongoDB.getJobSeekersCollection();
            col.updateOne(eq("_id",username),pull("skills",skill));
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public String searchUsername(String username) {
        String foundedPw;
        Document doc;
        MongoDBManager mongoDB = MongoDBManager.getInstance();

        doc = (Document) mongoDB.getJobSeekersCollection().find(eq("_id", username)).first();
        System.out.println("arrived here 2");
        if (doc == null) {
            throw new IllegalStateException("Invalid username");
        } else {
            foundedPw = (String) doc.get("password");
        }
        System.out.println("check founded password: "+ foundedPw);
        return foundedPw;
    }

    public JobSeeker findUser(String username){
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        System.out.println(username);
        Document user = (Document) mongoDB.getJobSeekersCollection().find(eq("id", username)).first();
        return new JobSeeker(username, user.get("first_name").toString(), user.get("last_name").toString(),
                user.get("gender").toString(), user.get("birthdate").toString(), user.get("email").toString());
    }

}
