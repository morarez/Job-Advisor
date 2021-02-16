package it.unipi.dii.lsdb.group13.entities;

public class Employer {
    String name = null;
    String password = null;
    String email = null;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Employer(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    //Without password
    public Employer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String toStringWithoutPassword() {
        String s = "COMPANY NAME: " + this.name + "\nEMAIL: " + this.email;
        return s;
    }
}