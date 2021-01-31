package it.unipi.dii.lsdb.group13.entities;

import java.util.*;

// for now we do not have locations, workExperience and Skills, we will add them if needed
public class JobSeeker{
    String firstName;
    String lastName;
    String gender;
    String birthdate;
    String username;
    String password;
    String email = null;
    Location location;

    public List<String> getSkills() {
        return skills;
    }

    List<String> skills;

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

    public JobSeeker(){
        System.out.println("found null user");
    }

    //User without skills
    public JobSeeker(String username, String firstName, String lastName, String gender, String birthdate,
                     String email, String state, String city) {
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
}
