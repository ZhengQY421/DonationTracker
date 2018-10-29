package edu.gatech.cs2340.youngmoney.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Location implements Serializable {

    private String name;
    private String type;
    private String phone;
    private String address;
    private String website;
    private String cord;
    private ArrayList<Donation> donations;


    public Location (String name, String type, String zip, String phone, String state, String address, String website, String lat, String lng, String city) {

        this.name = name;
        this.type = type;
        this.phone = phone;
        this.address = address + ", " + city + ", " + state + ", " + zip;
        this.website = website;
        this.cord = "<" + lat + ", " + lng + ">";
        this.donations = new ArrayList<>();

    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address;
    }
    public  String getWebsite () {
        return website;
    }
    public String getCord () {
        return  cord;
    }
    public ArrayList<Donation> getDonations() {
        return donations;
    }
    public void addDonation(Donation donation) {
        donations.add(donation);
    }
}

