package it.unipi.dii.lsdb.group13.database;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.eq;
import java.util.Arrays;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Projections.computed;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Filters.gte;

public class JobOfferDao {

    public boolean createNewJobOffer(JobOffer jobOffer){
        boolean ret = true;
        Document job;
        try {
            MongoDBManager mongoDB = MongoDBManager.getInstance();
            Document locationDoc = new Document("city", jobOffer.getLocation().getCity())
                    .append("state", jobOffer.getLocation().getState());
            if(jobOffer.getSalary() != null){
                Document salaryDoc = new Document("from", jobOffer.getSalary().getFrom())
                        .append("to", jobOffer.getSalary().getTo()).append("time_unit",jobOffer.getSalary().getTimeUnit());
                job = new Document("_id", jobOffer.getId()).append("job_title", jobOffer.getTitle())
                        .append("company_name", jobOffer.getCompanyName()).append("location", locationDoc)
                        .append("salary", salaryDoc).append("post_date",jobOffer.getPostDate())
                        .append("job_type",jobOffer.getJobType()).append("job_description",jobOffer.getDescription());
            }else {
                job = new Document("_id", jobOffer.getId()).append("job_title", jobOffer.getTitle())
                        .append("company_name", jobOffer.getCompanyName()).append("location", locationDoc)
                        .append("post_date",jobOffer.getPostDate()).append("job_type",jobOffer.getJobType())
                        .append("job_description",jobOffer.getDescription());
            }
            mongoDB.getJobOffersCollection().insertOne(job);
            // Add the job offer to the list of company's job offers
            mongoDB.getCompaniesCollection().updateOne(eq("_id", jobOffer.getCompanyName()),
                    Updates.addToSet("job_offers", jobOffer.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            ret = false;
        }
        return ret;
    }

    public boolean deleteJobOffer(String id){
        boolean ret = true;
        try {
            MongoDBManager mongoDB = MongoDBManager.getInstance();
            Document d = (Document) mongoDB.getJobOffersCollection().find(eq("_id", id)).first();
            String companyName = d.getString("company_name");
            DeleteResult deleteResult= mongoDB.getJobOffersCollection().deleteOne(eq("_id",id));
            if (deleteResult.getDeletedCount() == 1) {
                Bson filter = eq("_id", companyName);
                Bson delete = Updates.pull("job_offers", id);
                UpdateResult updateResult = mongoDB.getCompaniesCollection().updateOne(filter, delete);
                if (updateResult.getModifiedCount() != 1 ) { return false; }
            }
            System.out.println(deleteResult.getDeletedCount() + " job has been deleted.");
        }catch (Exception e){
            e.printStackTrace();
            ret = false;
        }
        return ret;
    }

    public List<JobOffer> getJobOffersByCity(String city){
        List<JobOffer> jobOffers = new ArrayList<>();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        MongoCollection<Document> collection= mongoDB.getJobOffersCollection();
        AggregateIterable<Document> founded = collection.aggregate(Arrays.asList(project(fields(include("job_title",
                "job_type", "job_description", "company_name", "location", "post_date"), computed("lower",
                eq("$toLower", "$location.city")))), match(eq("lower", city))));
        for(Document doc: founded) {
            //Calling Constructor to display city Name in Table View
            jobOffers.add(parseJobOffer(doc));
        }
        return jobOffers;
    }

    public List<JobOffer> getJobOffersByCompany(String company){
        List<JobOffer> jobOffers = new ArrayList<>();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        MongoCollection<Document> collection= mongoDB.getJobOffersCollection();
        AggregateIterable<Document> founded = collection.aggregate(Arrays.asList(project(fields(
                include("job_title", "job_type", "job_description", "company_name", "location", "post_date"),
                computed("lower", eq("$toLower", "$company_name")))), match(eq("lower", company))));
        for(Document doc: founded) {
            jobOffers.add(parseJobOffer(doc));
        }
        return jobOffers;
    }

    public List<JobOffer> getJobOffersByJobType(String jobType){
        List<JobOffer> jobOffers = new ArrayList<>();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        FindIterable<Document> founded = mongoDB.getJobOffersCollection().find(eq("job_type", jobType));
        for (Document doc: founded) {
            jobOffers.add(parseJobOffer(doc));
        }
        return jobOffers;
    }

    public List<JobOffer> getJobOffersByJobTitle(String jobTitle){
        List<JobOffer> jobOffers = new ArrayList<>();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        Document regQuery = new Document();
        regQuery.append("$regex", ".*(?).*" + Pattern.quote(jobTitle));
        regQuery.append("$options", "i");
        FindIterable<Document> founded = mongoDB.getJobOffersCollection().find(eq("job_title", regQuery));
        for(Document doc: founded) {
            jobOffers.add(parseJobOffer(doc));
        }
        return jobOffers;
    }

    public List<JobOffer> getJobOffersBySalary(String timeunit, Double minimum){
        List<JobOffer> jobOffers = new ArrayList<>();
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        MongoCollection collection = mongoDB.getJobOffersCollection();
        Bson m = match(eq("salary.time_unit", timeunit));
        Bson p = project(fields(include("job_title", "company_name", "job_type","location", "salary", "post_date"),
                computed("time", "$salary.time_unit"),
                computed("min",
                        eq("$convert",
                                new BsonDocument("input", new BsonDocument("$reduce",
                                        new BsonDocument("input", new BsonDocument("$split",new BsonArray(Arrays.asList(
                                                new BsonString("$salary.from"),
                                                new BsonString(",")))))
                                                .append("initialValue", new BsonString(""))
                                                .append("in", new BsonDocument("$concat", new BsonArray(Arrays.asList(
                                                        new BsonString("$$value"),
                                                        new BsonString("$$this")))))))
                                        .append("to",new BsonString("double"))
                                        .append("onError",new BsonString("0"))))));
        Bson n = match(gte("min", minimum));
        AggregateIterable<Document> founded = collection.aggregate(Arrays.asList(m, p , n));
        for(Document doc: founded) {
            jobOffers.add(parseJobOffer(doc));

        }
        return jobOffers;
    }

    public JobOffer getById(String Id) {
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        Document doc = (Document) mongoDB.getJobOffersCollection().find(eq("_id", Id)).first();
        if (doc != null) {
            return parseJobOffer(doc);
        } else {
            return null;
        }
    }

    public List<JobOffer> findPublished(String username) {
        List<JobOffer> jobOffers = new ArrayList<>();
        MongoDBManager mongoDB = MongoDBManager.getInstance();

        FindIterable<Document> founded = mongoDB.getJobOffersCollection().find(eq("company_name", username))
                .sort(new Document("post_date", -1));
        for(Document doc: founded) {
            jobOffers.add(parseJobOffer(doc));
        }
        return jobOffers;
    }

    private JobOffer parseJobOffer(Document doc){
        return new JobOffer(doc.getString("_id"), doc.getString("job_title"), doc.getString("company_name"),
                doc.getString("post_date"),  doc.getString("job_description"), doc.getString("job_type"),
                doc.getEmbedded(List.of("location", "state"), String.class), doc.getEmbedded(List.of("location", "city"), String.class),
                doc.getEmbedded(List.of("salary", "from"), String.class),doc.getEmbedded(List.of("salary", "to"), String.class),
                doc.getEmbedded(List.of("salary", "time_unit"), String.class));
    }

}
