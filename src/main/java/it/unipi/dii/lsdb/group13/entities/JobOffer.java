package it.unipi.dii.lsdb.group13.entities;

import java.util.Date;
import java.util.UUID;

public class JobOffer {
    String id;
    String title;
    String companyName;
    Date postDate;
    String description;
    String jobType;
    Salary salary;
    Location location;
    String locStr;
    String salaryStr;

    public String getLocStr(){ return location.getState() + ", " + location.getCity(); }

    public String getSalaryStr(){
        if (salary.from == null) return "Not specified.";
        else return salary.from + " - " + salary.to + " (" + salary.timeUnit + ")"; }

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation(){ return location; }

    public Salary getSalary(){ return salary;}

    public String getTitle() {
        return title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Date getPostDate() {
        return postDate;
    }

    public String getDescription() {
        return description;
    }

    public String getJobType() {
        return jobType;
    }

    //Create job offer with salary
    public JobOffer(String title, String companyName, String description, String jobType, String state, String city,
                    String salaryFrom, String salaryTo, String salaryTimeUnit) {
        this.id = UUID.randomUUID().toString().replace("-","");
        System.out.println(this.id);
        this.title = title;
        this.companyName = companyName;
        this.description = description;
        this.jobType = jobType;
        this.postDate = new Date();
        location = new Location(state, city);
        String formattedTimeUnit = ((salaryTimeUnit.equals("Year")) ? "/year" : "/hour");
        salary = new Salary(salaryFrom, salaryTo, formattedTimeUnit);
    }

    //Create job offer without salary
    public JobOffer(String title, String companyName, String description, String jobType, String state, String city) {
        this.id = UUID.randomUUID().toString().replace("-","");
        System.out.println(this.id);
        this.title = title;
        this.companyName = companyName;
        this.description = description;
        this.jobType = jobType;
        this.postDate = new Date();
        location = new Location(state, city);
    }

    //get job offer from DB
    public JobOffer(String id, String title, String companyName, Date postDate, String description, String jobType,
                    String state, String city,String from,String to,String timeUnit) {
        this.id = id;
        this.title = title;
        this.companyName = companyName;
        this.postDate = postDate;
        this.description = description;
        this.jobType = jobType;
        location = new Location(state, city);
        salary = new Salary(from,to,timeUnit);
    }

    //first constructor for job offers from Neo4J
    public JobOffer(String id, String title) {
        this.id = id;
        this.title = title;
    }

    //second constructor for job offers from Neo4J
    public JobOffer(String id, String title, String companyName, Date postDate) {
        this.id = id;
        this.title = title;
        this.companyName = companyName;
        this.postDate = postDate;
    }

    public String toStringWithoutDescription() {

        return " JOB TITLE: " + this.title + "\nCOMPANY NAME: " + this.companyName + "\nPOST DATE: " + this.postDate +
                "\nJOB TYPE: " + this.jobType;
    }
}
