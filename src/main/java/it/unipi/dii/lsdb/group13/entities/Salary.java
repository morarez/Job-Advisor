package it.unipi.dii.lsdb.group13.entities;

public class Salary {
    String from;
    String to;
    String timeUnit;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    Salary(String from, String to, String timeUnit){
        this.from = from;
        this.to = to;
        this.timeUnit = timeUnit;
    }

}
