package edu.gatech.cs2340.youngmoney;

import edu.gatech.cs2340.youngmoney.model.Location;
import edu.gatech.cs2340.youngmoney.model.Donation;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LocationEqualsTest {
    private Location location1;
    private Location location2;
    private Location location3;
    @Test
    public void testLocationEquals() {
        location1 = new Location("1", "Donation Center", "1", "1", "1111111111",
                "GA", "here", "www", "10", "11", "ATL");
        location2 = new Location("1", "Donation Center", "1", "1", "1111111111",
                "GA", "here", "www", "10", "11", "ATL");
        location3 = new Location("2", "Donation center 3", "warehouse", "04934", "12345",
                "MD","404 Penn Ave", "www.whitehouse.gov", "0", "0", "DC");
        Donation newDonation = new Donation("yes", "now", "here", "me", "an item", "money", "goods");

        assertFalse(location1.equals(null));
        assertTrue(location1.equals(location1));
        assertFalse(location1.equals(newDonation));
        assertTrue(location1.equals(location2));
        assertFalse(location1.equals(location3));

    }
}
