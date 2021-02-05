package it.unipi.dii.lsdb.group13.database;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
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

public class JobOfferDao {

    private static final String String = null;

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
            DeleteResult deleteResult= mongoDB.getJobOffersCollection().deleteOne(eq("_id",id));
            System.out.println(deleteResult.getDeletedCount()+" job has been deleted.");
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
		AggregateIterable<Document> founded = collection.aggregate(Arrays.asList(project(fields(include("job_title", "job_type", "job_description", "company_name", "location", "post_date"), computed("lower", eq("$toLower", "$location.city")))), match(eq("lower", city))));
		for(Document doc: founded) {
			System.out.println(doc.toJson());
            jobOffers.add(new JobOffer(doc.getString("_id"), doc.getString("job_title"), doc.getString("company_name"), doc.getString("post_date"),  doc.getString("job_description"),
                                        doc.getString("job_type"), doc.getString("location.state"), doc.getString("location.city")));
		}
        return jobOffers;
		
		
	}
    public List<JobOffer> getJobOffersByCompany(String company){
    	List<JobOffer> jobOffers = new ArrayList<>();
		MongoDBManager mongoDB = MongoDBManager.getInstance();
		MongoCollection<Document> collection= mongoDB.getJobOffersCollection();
		AggregateIterable<Document> founded = collection.aggregate(Arrays.asList(project(fields(include("job_title", "job_type", "job_description", "company_name", "location", "post_date"), computed("lower", eq("$toLower", "$company_name")))), match(eq("lower", company))));
        for(Document doc: founded) {
            jobOffers.add(new JobOffer(doc.getString("_id"), doc.getString("job_title"), doc.getString("company_name"), doc.getString("post_date"),  doc.getString("job_description"),
                                        doc.getString("job_type"), doc.getString("location.state"), doc.getString("location.city")));
        }
        return jobOffers;
    }

    public List<JobOffer> getJobOffersByJobType(String jobtype){
    	List<JobOffer> jobOffers = new ArrayList<>();
		MongoDBManager mongoDB = MongoDBManager.getInstance();
		FindIterable<Document> founded = mongoDB.getJobOffersCollection().find(eq("job_type", jobtype));
		System.out.println(founded);
        for(Document doc: founded) {
            jobOffers.add(new JobOffer(doc.getString("_id"), doc.getString("job_title"), doc.getString("company_name"), doc.getString("post_date"),  doc.getString("job_description"),
                                        doc.getString("job_type"), doc.getString("location.state"), doc.getString("location.city")));
        }
        System.out.println(jobOffers);
        return jobOffers;
    }
    
    public List<JobOffer> getJobOffersByJobTitle(String jobtitle){
    	List<JobOffer> jobOffers = new ArrayList<>();
		MongoDBManager mongoDB = MongoDBManager.getInstance();
	    Document regQuery = new Document();
	    regQuery.append("$regex", ".*(?).*" + Pattern.quote(jobtitle));
	    regQuery.append("$options", "i");
		FindIterable<Document> founded = mongoDB.getJobOffersCollection().find(eq("job_title", regQuery));
		System.out.println(founded);
        for(Document doc: founded) {
            jobOffers.add(new JobOffer(doc.getString("_id"), doc.getString("job_title"), doc.getString("company_name"), doc.getString("post_date"),  doc.getString("job_description"),
                                        doc.getString("job_type"), doc.getString("location.state"), doc.getString("location.city")));
        }
        System.out.println(jobOffers);
        return jobOffers;
    }
    
  /*  public List<JobOffer> getJobOffersBySalary(String timeunit, String min){
    	List<JobOffer> jobOffers = new ArrayList<>();
		MongoDBManager mongoDB = MongoDBManager.getInstance();
		Bson mymatch = Aggregates.match(Filters.eq("salary.time_unit",timeunit));
		Bson mymatch1= Aggregates.match(Filters.gte("salary.from",min));
		AggregateIterable<Document> founded = mongoDB.getJobOffersCollection().aggregate(Arrays.asList(mymatch,mymatch1));
		System.out.println(founded);
		for(Document doc: founded) {
			 jobOffers.add(new JobOffer(doc.getString("_id"), doc.getString("job_title"), doc.getString("company_name"), doc.getString("post_date"),  doc.getString("job_description"),
                     doc.getString("job_type"), doc.getString("location.state"), doc.getString("location.city")));
}
		System.out.println(jobOffers);
		return jobOffers;
    	
    } */

}
