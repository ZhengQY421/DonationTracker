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
    /**
     * getter for id
     * @return id
     */
    public String getId() { return id; }

    /**
     * getter for name
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * getter for type
     * @return type
     */
    public String getType() {
        return type;
    }
    /**
     * getter for phone
     * @return phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * getter for address
     * @return address
     */
    public String getAddress() {
        return address;
    }
    /**
     * getter for website
     * @return website
     */
    public  String getWebsite () {
        return website;
    }
    /**
     * getter for coordinates
     * @return coordinates
     */
    public String getCord () {
        return  cord;
    }
    /**
     * getter for donations
     * @return donations
     */
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
    /**
     * equals method to test if two locations are equal
     * @param o object to be tested
     * @return boolean of if locations are found to be equal
     */
    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(o == this){
            return true;
        }

        if(!(o instanceof Location)) {
            return false;
        }
        Location loc = (Location) o;
        // didnt want to individually check all attributes
        // if the id reprs are the same, all is the same
        if (this.id.equals(((Location) o).getId())) {
            return true;
        } else {
            return false;
        }

    }
}

