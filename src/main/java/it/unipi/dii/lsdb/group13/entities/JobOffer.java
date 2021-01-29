package it.unipi.dii.lsdb.group13.entities;

import org.bson.Document;

import java.time.LocalDate;
import java.util.UUID;

public class JobOffer {
    String id;
    String title;
    String companyName;
    String postDate;
    String description;
    String jobType;
    String salaryFrom;
    String salaryTo;
    String salaryUnit;
    String state;
    String city;

    public JobOffer(String title, String companyName, String description, String jobType, String salaryFrom,
                    String salaryTo, String salaryUnit, String state, String city) {
        // check the id in database
        this.id = UUID.randomUUID().toString().replace("-","");
        this.title = title;
        this.companyName = companyName;
        this.description = description;
        this.jobType = jobType;
        this.postDate = LocalDate.now().toString();
        this.salaryFrom = salaryFrom;
        this.salaryTo = salaryTo;
        // formatting salary time unit to make it compatible with the format in the Database
        this.salaryUnit = ((salaryUnit.equals("year")) ? "/year" : "/hour");
        this.state = state;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public Document createDoc(){
        Document location = new Document("city", this.city).append("state", this.state);

        Document salary = new Document("from", this.salaryFrom).append("to", this.salaryTo)
                .append("time_unit",salaryUnit);
        return new Document("_id", this.id)
                .append("job_title", this.title)
                .append("company_name", this.companyName)
                .append("location", location)
                .append("salary", salary)
                .append("post_date",this.postDate)
                .append("job_description",description);
    }
}
