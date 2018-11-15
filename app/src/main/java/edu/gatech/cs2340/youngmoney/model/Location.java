package edu.gatech.cs2340.youngmoney.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import android.content.Context;

public class Location implements Serializable {

    private String id;
    private String name;
    private String type;
    private String phone;
    private String address;
    private String website;
    private String cord;
    private ArrayList<Donation> donations;


    public Location (String id, String name, String type, String zip, String phone, String state, String address, String website, String lat, String lng, String city) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.phone = phone;
        this.address = address + ", " + city + ", " + state + ", " + zip;
        this.website = website;
        this.cord = "<" + lat + ", " + lng + ">";
        this.donations = new ArrayList<>();

    }
    public String getId() { return id; }
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
    public void addDonation(Donation donation, Context context) {
        donations.add(donation);
        if (context != null) {
            try {
                FileOutputStream outputStream = context.openFileOutput("donations.csv", Context.MODE_APPEND);
                String serialized = donation.getItem() + "," + donation.getDate() + "," +
                        donation.getLocation() + "," + donation.getUser() + "," +
                        donation.getFulldesc() + "," + donation.getValue() + "," +
                        donation.getCategory() + "\n";
                outputStream.write(serialized.getBytes());
                outputStream.close();
            } catch (IOException exception) {
            }
        }
    }
}

