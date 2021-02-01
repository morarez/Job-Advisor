package it.unipi.dii.lsdb.group13.entities;

// for now we don't have jobOffers and locations, we will add them if needed
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

    public Employer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Employer() {
        System.out.println("user not found");
    }
}