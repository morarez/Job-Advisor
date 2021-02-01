package it.unipi.dii.lsdb.group13.entities;

import java.time.LocalDate;
import java.util.UUID;

public class JobOffer {
    String id;
    String title;
    String companyName;
    String postDate;
    String description;
    String jobType;
    Salary salary;
    Location location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation(){ return location; }

    public Salary getSalary(){ return salary;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public JobOffer(String title, String companyName, String description, String jobType, String salaryFrom,
                    String salaryTo, String salaryUnit, String state, String city) {
        this.id = UUID.randomUUID().toString().replace("-","");
        this.title = title;
        this.companyName = companyName;
        this.description = description;
        this.jobType = jobType;
        this.postDate = LocalDate.now().toString();
        this.location.city = city;
        this.location.state = state;
        this.salary.to = salaryTo;
        this.salary.from = salaryFrom;
        this.salary.timeUnit = salaryUnit;
    }
}
