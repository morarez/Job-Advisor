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
    String city_name;

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
    //getter to display City Name in Table View of Search Job Offer By City
    public String getCity_name() {
    	return city_name;
    }
    //setter to display City Name in Table View of Search Job Offer By City
    public void setCity_name(String city_name) {
    	this.city_name = city_name;
    }

    //Job offer with salary
    public JobOffer(String title, String companyName, String description, String jobType, String state, String city,
                    String salaryFrom, String salaryTo, String salaryTimeUnit) {
        this.id = UUID.randomUUID().toString().replace("-","");
        System.out.println(this.id);
        this.title = title;
        this.companyName = companyName;
        this.description = description;
        this.jobType = jobType;
        this.postDate = LocalDate.now().toString();
        location = new Location(state, city);
        String formattedTimeUnit = ((salaryTimeUnit.equals("Year")) ? "/year" : "/hour");
        salary = new Salary(salaryFrom, salaryTo, formattedTimeUnit);
    }

    //Job offer without salary
    public JobOffer(String title, String companyName, String description, String jobType, String state, String city) {
        this.id = UUID.randomUUID().toString().replace("-","");
        System.out.println(this.id);
        this.title = title;
        this.companyName = companyName;
        this.description = description;
        this.jobType = jobType;
        this.postDate = LocalDate.now().toString();
        location = new Location(state, city);
    }

    public JobOffer(String id, String title, String companyName, String postDate, String description, String jobType, String state, String city) {
        this.id = id;
        this.title = title;
        this.companyName = companyName;
        this.postDate = postDate;
        this.description = description;
        this.jobType = jobType;
        location = new Location(state, city);
    }

    public String toStringWithoutDescription() {

        String s = " JOB TITLE: " + this.title + "\nCOMPANY NAME: " + this.companyName + "\nPOST DATE: " + this.postDate +
                "\nJOB TYPE: " + this.jobType;

        return s;
    }
    
    //Constructor to display city name in the table view when searching Job Offer By City
    public JobOffer(String id, String title, String companyName, String postDate, String state, String city, String city_name)
    {
        this.id = id;
        this.title = title;
        this.companyName = companyName;
        this.postDate = postDate;
        location = new Location(state, city);
        this.city_name = city_name;
    }
}
