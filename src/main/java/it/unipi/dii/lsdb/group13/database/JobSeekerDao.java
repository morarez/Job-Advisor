package it.unipi.dii.lsdb.group13.database;

import com.mongodb.client.result.DeleteResult;
import it.unipi.dii.lsdb.group13.entities.JobSeeker;
import it.unipi.dii.lsdb.group13.main.Session;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

public class JobSeekerDao {

    public boolean signUp(String fname, String lname, String username, LocalDate birthdate, String g, String password,
                          String email, String city, String state, String skill) {
        try {
            char gender= g.charAt(0);
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(birthdate.toString());
            //in the database we have
            String formattedDate = new SimpleDateFormat("M/d/yy").format(date);
            List<String> skills = new ArrayList<>(Arrays.asList(skill.split(",")));
            MongoDBManager mongoDB = MongoDBManager.getInstance();
            Document doc = new Document("_id",username).append("password",password).append("first_name",fname)
                    .append("last_name",lname).append("birthdate",formattedDate).append("gender",gender).append("email",email)
                    .append("location", new Document("city",city).append("state",state)).append("skills",skills);
            mongoDB.getJobSeekersCollection().insertOne(doc);
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
        String username = Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        DeleteResult deleteResult= mongoDB.getJobSeekersCollection().deleteOne(eq("_id",username));
        System.out.println(deleteResult.getDeletedCount()+" document has been deleted.");
    }

    public boolean changePassword(String newpwd, String pwdagain) {
         Session.getSingleton();
         String username= Session.getLoggedUser();
         MongoDBManager mongoDB = MongoDBManager.getInstance();
         try {
             mongoDB.getJobSeekersCollection().updateOne(eq("_id",username),set("password",newpwd));
             return true;
         }
         catch(Exception e) {
             System.out.println(e.getMessage());
             return false;
         }
     }

  /*
    public void changeFirstName()
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
            mongoDB.getJobSeekersCollection().updateOne(eq("_id",username),push("skills",skill));
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @SuppressWarnings("if the skill is not in the database, it still says skill deleted")
    public boolean deleteSkill(String skill)
    {
        Session.getSingleton();
        String username= Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        try {
            mongoDB.getJobSeekersCollection().updateOne(eq("_id",username),pull("skills",skill));
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
        Document user = (Document) mongoDB.getJobSeekersCollection().find(eq("_id", username)).first();
        // handle nullPointerException
        if (user != null) {
            Document loc = (Document) user.get("location");
            if (user.get("skills") != null) {
                return new JobSeeker(username, user.getString("first_name"), user.getString("last_name"),
                        user.getString("gender"), user.getString("birthdate"), user.getString("email"),
                        loc.getString("state"), loc.getString("city"),
                        (List<String>)user.get("skills", List.class));
            } else {
                return new JobSeeker(username, user.getString("first_name"), user.getString("last_name"),
                        user.getString("gender"), user.getString("birthdate"), user.getString("email"),
                        loc.getString("state"), loc.getString("city"));
            }
        } else {
            return new JobSeeker();
        }
    }
}
