package it.unipi.dii.lsdb.group13.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import it.unipi.dii.lsdb.group13.entities.Employer;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
		FindIterable<Document> founded = mongoDB.getJobOffersCollection().find(eq("location.city", city));
		System.out.println(founded);
        for(Document doc: founded) {
            jobOffers.add(new JobOffer(doc.getString("_id"), doc.getString("job_title"), doc.getString("company_name"), doc.getString("post_date"),  doc.getString("job_description"),
                                        doc.getString("job_type"), doc.getString("location.state"), doc.getString("location.city")));
        }
        System.out.println(jobOffers);
        return jobOffers;
		
		
	}
    public List<JobOffer> getJobOffersByCompany(String company){
    	List<JobOffer> jobOffers = new ArrayList<>();
		MongoDBManager mongoDB = MongoDBManager.getInstance();
		FindIterable<Document> founded = mongoDB.getJobOffersCollection().find(eq("company_name", company));
		System.out.println(founded);
        for(Document doc: founded) {
            jobOffers.add(new JobOffer(doc.getString("_id"), doc.getString("job_title"), doc.getString("company_name"), doc.getString("post_date"),  doc.getString("job_description"),
                                        doc.getString("job_type"), doc.getString("location.state"), doc.getString("location.city")));
        }
        System.out.println(jobOffers);
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

    public JobOffer getById(String Id) {
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        Document doc = (Document) mongoDB.getJobOffersCollection().find(eq("_id", Id)).first();
        if (doc != null) {
            return new JobOffer(doc.getString("_id"), doc.getString("job_title"), doc.getString("company_name"), doc.getString("post_date"),  doc.getString("job_description"),
                    doc.getString("job_type"), doc.getString("location.state"), doc.getString("location.city"));
        } else {
            return null;
        }
    }
}
