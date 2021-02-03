package it.unipi.dii.lsdb.group13.database;

import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import it.unipi.dii.lsdb.group13.entities.JobOffer;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

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

}
