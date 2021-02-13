package it.unipi.dii.lsdb.group13.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import it.unipi.dii.lsdb.group13.entities.JobSeeker;
import it.unipi.dii.lsdb.group13.Session;
import org.bson.Document;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static org.neo4j.driver.Values.parameters;

public class JobSeekerDao {

    public String signUp(String fname, String lname, String username, LocalDate birthdate, String g, String password,
                          String email, String city, String state) {
        try {
            char gender= g.charAt(0);
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(birthdate.toString());
            //in the database we have
            String formattedDate = new SimpleDateFormat("M/d/yy").format(date);
           // List<String> skills = new ArrayList<>(Arrays.asList(skill.split(",")));
            MongoDBManager mongoDB = MongoDBManager.getInstance();
            Document doc = new Document("_id",username).append("password",password).append("first_name",fname)
                    .append("last_name",lname).append("birthdate",formattedDate).append("gender",gender).append("email",email)
                    .append("location", new Document("city",city).append("state",state));
            mongoDB.getJobSeekersCollection().insertOne(doc);
            return "true";
        }
        catch(Exception e) {
            return e.getMessage();
        }
    }
    public void addJobSeekerToNeo4j(String username){
        Neo4jManager neo4j = Neo4jManager.getInstance();
        try (org.neo4j.driver.Session session = neo4j.getDriver().session()) {
                session.writeTransaction((TransactionWork<Void>) tx -> {
                    tx.run("MERGE (js:JobSeeker {username: $username})",
                            parameters("username", username));
                    return null;
                });
                System.out.println("User added to neo4j");
        }
    }

    public boolean deleteAccount(String username) {
        boolean ret = true;
        try {
            MongoDBManager mongoDB = MongoDBManager.getInstance();
            mongoDB.getJobSeekersCollection().deleteOne(eq("_id",username));
            deleteFromNeo4j(username);
        }catch (Exception e){
            e.printStackTrace();
            ret = false;
        }
        return ret;
    }

    private void deleteFromNeo4j(String username){
        Neo4jManager neo4j = Neo4jManager.getInstance();
        try (org.neo4j.driver.Session session = neo4j.getDriver().session()) {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run("Match(j:JobSeeker) WHERE j.username = $username DETACH DELETE j",
                        parameters("username", username));
                return null;
            });
            System.out.println("User deleted from neo4j");
        }
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

    public String searchUsername(String username) throws Exception{
        String foundedPw;
        Document doc;
        MongoDBManager mongoDB = MongoDBManager.getInstance();

        doc = (Document) mongoDB.getJobSeekersCollection().find(eq("_id", username)).first();
        System.out.println("arrived here 2");
        if (doc == null) {
            throw new Exception("Invalid username");
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
            if (user.containsKey("skills")) {
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
            return null;
        }
    }

    public List<JobSeeker> searchSkill(String skill) {
        List<JobSeeker> seekers = new ArrayList<>();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        FindIterable findIterable = mongoDB.getJobSeekersCollection().find(eq("skills", skill));
        MongoCursor<Document> cursor = findIterable.iterator();

        while ( cursor.hasNext() ) {
            Document doc = cursor.next();
            Document loc = doc.get("location", Document.class);
            seekers.add( new JobSeeker(doc.getString("_id"), doc.getString("first_name"), doc.getString("last_name"),
                    doc.getString("gender"), doc.getString("birthdate"), doc.getString("email"),
                    loc.getString("state"), loc.getString("city"), doc.getList("skills", String.class)) );
        }
        cursor.close();

        return seekers;
    }

    public List<JobSeeker> searchLocation(String city, String state) {

        List<JobSeeker> seekers = new ArrayList<>();

        MongoDBManager mongoDB = MongoDBManager.getInstance();
        MongoCursor<Document> cursor = mongoDB.getJobSeekersCollection().find(and(eq("location.city", city), eq("location.state", state))).iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            Document loc = doc.get("location", Document.class);
            seekers.add(new JobSeeker(doc.getString("_id"), doc.getString("first_name"), doc.getString("last_name"),
                    doc.getString("gender"), doc.getString("birthdate"), doc.getString("email"),
                    loc.getString("state"), loc.getString("city"), doc.getList("skills", String.class)) );
        }
        cursor.close();

        return seekers;
    }

    public List<JobSeeker> searchByCity(String city) {
        List<JobSeeker> seekers = new ArrayList<>();

        MongoDBManager mongoDB = MongoDBManager.getInstance();
        MongoCursor<Document> cursor = mongoDB.getJobSeekersCollection().find(eq("location.city", city)).iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            Document loc = doc.get("location", Document.class);
            seekers.add(new JobSeeker(doc.getString("_id"), doc.getString("first_name"), doc.getString("last_name"),
                    doc.getString("gender"), doc.getString("birthdate"), doc.getString("email"),
                    loc.getString("state"), loc.getString("city"), doc.getList("skills", String.class)) );
        }
        cursor.close();

        return seekers;
    }

    public List<JobSeeker> searchByState(String state) {
        List<JobSeeker> seekers = new ArrayList<>();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        MongoCursor<Document> cursor = mongoDB.getJobSeekersCollection().find(eq("location.state", state)).iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            Document loc = doc.get("location", Document.class);
            seekers.add(new JobSeeker(doc.getString("_id"), doc.getString("first_name"), doc.getString("last_name"),
                    doc.getString("gender"), doc.getString("birthdate"), doc.getString("email"),
                    loc.getString("state"), loc.getString("city"), doc.getList("skills", String.class)));
        }
        cursor.close();

        return seekers;
    }

    public boolean followCompany(String jobSeekerUsername, String companyName){
        boolean ret = true;
        Neo4jManager neo4j = Neo4jManager.getInstance();
        try (org.neo4j.driver.Session session = neo4j.getDriver().session() ) {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH (js:JobSeeker),(c:Company) WHERE js.username = $username AND c.name = $name" +
                                " Merge (js)-[r:FOLLOWS]->(c)",
                        parameters( "username", jobSeekerUsername, "name", companyName) );
                return null;
            });
        }catch (Exception e){
            ret = false;
        }
        return ret;
    }

    public boolean unfollowCompany(String jobSeekerUsername, String companyName){
        boolean ret = true;
        Neo4jManager neo4j = Neo4jManager.getInstance();
        try (org.neo4j.driver.Session session = neo4j.getDriver().session() ) {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH (:JobSeeker {username: $username})-[r:FOLLOWS]-(:Company {name: $name}) DELETE r",
                        parameters( "username", jobSeekerUsername, "name", companyName) );
                return null;
            });
        }catch (Exception e){
            ret = false;
        }
        return ret;
    }

    public boolean isFollowing(String jobSeekerUsername, String companyName){
        Neo4jManager neo4j = Neo4jManager.getInstance();
        boolean following = false;
        try (org.neo4j.driver.Session session = neo4j.getDriver().session() ) {
            following = session.readTransaction((TransactionWork<Boolean>) tx -> {
                Result result = tx.run( "MATCH (js:JobSeeker) , (c:Company) " +
                                "WHERE js.username = $username AND c.name=$name " +
                                "RETURN EXISTS( (js)-[:FOLLOWS]-(c) )",
                        parameters( "username", jobSeekerUsername, "name", companyName) );
                return result.single().get(0).asBoolean();
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return following;
    }

    public boolean saveJobOffer(String jobSeekerUsername, String JobOfferId){
        boolean ret = true;
        Neo4jManager neo4j = Neo4jManager.getInstance();
        try (org.neo4j.driver.Session session = neo4j.getDriver().session() ) {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH (js:JobSeeker),(jo:JobOffer) WHERE js.username = $username AND jo.id = $id" +
                                " Merge (js)-[r:SAVED]->(jo)",
                        parameters( "username", jobSeekerUsername, "id", JobOfferId) );
                return null;
            });
        }catch (Exception e){
            ret = false;
        }
        return ret;
    }

    public boolean unSaveJobOffer(String jobSeekerUsername, String JobOfferId){
        boolean ret = true;
        Neo4jManager neo4j = Neo4jManager.getInstance();
        try (org.neo4j.driver.Session session = neo4j.getDriver().session() ) {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH (:JobSeeker {username: $username})-[r:SAVED]-(:JobOffer {id: $id}) DELETE r",
                        parameters( "username", jobSeekerUsername, "id", JobOfferId) );
                return null;
            });
        }catch (Exception e){
            ret = false;
        }
        return ret;
    }

    public boolean isSaved(String jobSeekerUsername, String JobOfferId){
        Neo4jManager neo4j = Neo4jManager.getInstance();
        boolean saved = false;
        try (org.neo4j.driver.Session session = neo4j.getDriver().session() ) {
            saved = session.readTransaction((TransactionWork<Boolean>) tx -> {
                Result result = tx.run( "MATCH (js:JobSeeker) , (jo:JobOffer) " +
                                "WHERE js.username = $username AND jo.id=$id " +
                                "RETURN EXISTS( (js)-[:SAVED]-(jo) )",
                        parameters( "username", jobSeekerUsername, "id", JobOfferId) );
                return result.single().get(0).asBoolean();
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return saved;
    }

    /*
    public List<String> savedOffers(String username) {
        Neo4jManager neo4j = Neo4jManager.getInstance();
        List<String> jobTitles;
        try (org.neo4j.driver.Session session = neo4j.getDriver().session() ) {
            jobTitles = session.readTransaction((TransactionWork<List<String>>) tx -> {
                Result result = tx.run( "MATCH (js:JobSeeker)-[:SAVED]->(jo) WHERE js.username = $username" +
                                " RETURN jo.title as Title",
                        parameters( "username", username) );
                ArrayList<String> titles = new ArrayList<>();
                while(result.hasNext())
                {
                    Record r = result.next();
                    titles.add(r.get("Title").asString());
                }
                return titles;
            });
        }
        return jobTitles;
    } */

    public List<JobOffer> savedOffers(String username) {
        Neo4jManager neo4j = Neo4jManager.getInstance();
        List<JobOffer> jobTitles;
        try (org.neo4j.driver.Session session = neo4j.getDriver().session() ) {
            jobTitles = session.readTransaction((TransactionWork<List<JobOffer>>) tx -> {
                Result result = tx.run( "MATCH (js:JobSeeker)-[:SAVED]->(jo) WHERE js.username = $username" +
                                " RETURN jo.title as title, jo.id AS id",
                        parameters( "username", username) );
                ArrayList<JobOffer> offers = new ArrayList<>();
                while(result.hasNext())
                {
                    Record r = result.next();
                    offers.add(new JobOffer(r.get("id").asString(), r.get("title").asString()));
                }
                return offers;
            });
        }
        return jobTitles;
    }


    public List<String> followedCompanies(String username) {
        Neo4jManager neo4j = Neo4jManager.getInstance();
        List<String> companies;
        try (org.neo4j.driver.Session session = neo4j.getDriver().session() ) {
            companies = session.readTransaction((TransactionWork<List<String>>) tx -> {
                Result result = tx.run( "MATCH (js:JobSeeker)-[:FOLLOWS]->(c) WHERE js.username = $username" +
                                " RETURN c.name as Name",
                        parameters( "username", username) );
                ArrayList<String> comps = new ArrayList<>();
                while(result.hasNext())
                {
                    Record r = result.next();
                    comps.add(r.get("Name").asString());
                }
                return comps;
            });
        }
        return companies;
    }

    public List<String> findRecommendedCompanies(String username) {
        Neo4jManager neo4j = Neo4jManager.getInstance();
        List<String> companies;
        try(org.neo4j.driver.Session session = neo4j.getDriver().session()) {
            companies = session.readTransaction( (TransactionWork<List<String>>) tx -> {

                Result result = tx.run("MATCH (u1:JobSeeker)-[:FOLLOWS]->(c:Company)<-[:FOLLOWS]-(u2:JobSeeker)" +
                                " WHERE u1.username = $username AND u1.username <> u2.username" +
                                " WITH u2 AS foundedUser, count(DISTINCT c) AS strength" +
                                " MATCH (foundedUser)-[:FOLLOWS]->(c1:Company)" +
                                " WHERE NOT EXISTS { (j:JobSeeker {username : $username})-[:FOLLOWS]-(c1) } " +
                                " RETURN c1.name AS companyName, strength ORDER BY strength DESC LIMIT 15", parameters("username", username));

                ArrayList<String> comps = new ArrayList<>();
                while(result.hasNext()) {
                    Record r = result.next();
                    comps.add(r.get("companyName").asString());
                }
                return comps;
            });
        }
        return companies;
    }
}
