package it.unipi.dii.lsdb.group13.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class JobSeeker{
    String firstName;
    String lastName;
    String gender;
    String birthdate;
    String username;
    String password;
    String email;
    Location location;
    List<String> skills = new ArrayList<>();

    public List<String> getSkills() {
        return skills;
    }

    public Location getLocation(){ return location; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {return username;}

    //Sign up job seeker
    public JobSeeker(String username, String password, String firstName, String lastName, String gender, LocalDate birthdate,
                     String email, String state, String city) throws ParseException {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = String.valueOf(gender.charAt(0));
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(birthdate.toString());
        String formattedDate = new SimpleDateFormat("M/d/yy").format(date);
        this.birthdate = formattedDate;
        this.email = email;
        this.location = new Location(state, city);
    }

    //User without skills
    public JobSeeker(String username, String firstName, String lastName, String gender, String birthdate,
                     String email, String state, String city){
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
        this.location = new Location(state, city);
    }

    //User with skills
    public JobSeeker(String username, String firstName, String lastName, String gender, String birthdate,
                     String email, String state, String city, List skills) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
        this.location = new Location(state, city);
        this.skills = skills;
    }

    public String toStringWithoutPassword() {

        String s = "FIRST NAME: " + this.firstName + "\nLAST NAME: " + this.lastName + "\nBIRTHDAY: " + this.birthdate +
                "\nGENDER: " + this.gender + "\nEMAIL: " + this.email + "\nLOCATION: " + this.location.city + " (" + this.location.getState() + ")";

        if(!this.skills.isEmpty()) {
            s = s.concat("\nSKILLS: " + this.getSkillsAsString());
        } else {
            s = s.concat("\nNo skills");
        }

        return s;
    }

    public String getSkillsAsString() {
        String s = "";
        for(String skill : this.skills) {
            s = skill + "    " + s;
        }

        return s;
    }
}
