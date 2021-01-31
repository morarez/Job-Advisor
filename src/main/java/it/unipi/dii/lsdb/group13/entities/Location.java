package it.unipi.dii.lsdb.group13.entities;

public class Location {
    String city;
    String state;

    Location(String state, String city){
        this.state = state;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }
}
