package edu.gatech.cs2340.youngmoney.model;

public class Donation {
    private String item;
    private String date;
    private Location location;
    private String user;

    public Donation(String item, String date, Location location, String user) {
        this.item = item;
        this.date = date;
        this.location = location;
        this.user = user;
    }

    public String getItem() {
        return item;
    }

    public String getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }

    public String getUser() {
        return user;
    }
}
