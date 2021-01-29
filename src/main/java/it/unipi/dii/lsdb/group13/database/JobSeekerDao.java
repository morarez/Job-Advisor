package it.unipi.dii.lsdb.group13.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import it.unipi.dii.lsdb.group13.entities.JobSeeker;
import it.unipi.dii.lsdb.group13.main.Session;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

public class JobSeekerDao {

    public void signUp() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your first name: ");
            String fname= sc.nextLine();
            System.out.println("Enter your last name: ");
            String lname= sc.nextLine();
            System.out.println("Enter your username: ");
            String username= sc.nextLine();
            System.out.println("Enter your birthday (mm/dd/yy): ");
            String birthday= sc.nextLine();
            System.out.println("Enter your gender (M/F): ");
            char gender= sc.nextLine().charAt(0);
            System.out.println("Enter your password: ");
            String password= sc.nextLine();
            System.out.println("Enter your email: ");
            String email= sc.nextLine();
            System.out.println("Enter your city: ");
            String city= sc.nextLine();
            System.out.println("Enter your state: ");
            String state= sc.nextLine();
            System.out.println("How many skills do you want to enter: ");
            int sk= sc.nextInt();
            List<String> skills = new ArrayList<String>();
            for (int i=0;i<sk;i++) {
                System.out.println("Enter skill# "+(i+1));
                Scanner s= new Scanner(System.in);
                skills.add(s.nextLine());
            }
            MongoDBManager mongoDB = MongoDBManager.getInstance();
            MongoCollection mongodb= mongoDB.getJobSeekersCollection();
            Document doc= new Document("_id",username).append("password",password).append("first_name",fname).append("last_name",lname).append("birthdate",birthday).append("gender",gender).append("email",email).append("location", new Document("city",city).append("state",state)).append("skills",skills);
            mongodb.insertOne(doc);
            System.out.println("Signed up Successfully!");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
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

    public void changePassword()
    {
        Scanner sc= new Scanner(System.in);
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
                MongoCollection col= mongoDB.getJobSeekersCollection();
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
    }
    public void addSkill()
    {
        Scanner sc= new Scanner(System.in);
        Session.getSingleton();
        String username= Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        try {
            System.out.println("Enter new skill: ");
            String skill= sc.nextLine();
            MongoCollection col= mongoDB.getJobSeekersCollection();
            col.updateOne(eq("_id",username),push("skills",skill));
            System.out.println("New sill added!");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void deleteSkill()
    {
        Scanner sc= new Scanner(System.in);
        Session.getSingleton();
        String username= Session.getLoggedUser();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        try {
            System.out.println("Which skill do you want to remove: ");
            String skill= sc.nextLine();
            MongoCollection col= mongoDB.getJobSeekersCollection();
            col.updateOne(eq("_id",username),pull("skills",skill));
            System.out.println("Skill removed!");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
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
