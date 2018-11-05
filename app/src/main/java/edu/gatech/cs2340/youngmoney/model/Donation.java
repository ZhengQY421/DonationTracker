package edu.gatech.cs2340.youngmoney.model;

public class Donation {
    private String item;
    private String date;
    private String location;
    private String user;
    private String fulldesc;
    private String value;
    private String category;

    public Donation(String item, String date, String location, String user, String fulldesc, String value, String category) {
        this.item = item;
        this.date = date;
        this.location = location;
        this.user = user;
        this.fulldesc = fulldesc;
        this.value = value;
        this.category = category;
    }

    public String getItem() {
        return item;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getUser() {
        return user;
    }
    public String getFulldesc() {
        return fulldesc;
    }
    public String getValue() {
        return value;
    }
    public String getCategory() {
        return category;
    }
}
