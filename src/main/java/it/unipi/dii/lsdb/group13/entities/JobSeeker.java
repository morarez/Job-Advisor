package it.unipi.dii.lsdb.group13.entities;

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

    public String getLocationState(){ return location.state; }

    public String getLocationCity(){ return location.city; }

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

    public JobSeeker(String username, String firstName, String lastName, String gender, String birthdate, String email
            ,String state, String city) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
        this.location = new Location(state, city);
    }
}
