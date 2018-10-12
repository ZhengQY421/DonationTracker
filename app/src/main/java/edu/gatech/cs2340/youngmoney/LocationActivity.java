package edu.gatech.cs2340.youngmoney;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LocationActivity extends Activity {

    private class Location {
        private String name;
        private String type;
        private double lat;
        private double lng;
        private String address;
        private String phone;

        public Location(String name, String type, double lat, double lng, String address, String phone) {
            this.name = name;
            this.type = type;
            this.lat = lat;
            this.lng = lng;
            this.address = address;
            this.phone = phone;
        }

        public String getName() {
            return name;
        }
        public String getType() {
            return type;
        }
        public double getLat() {
            return lat;
        }
        public double getLng() {
            return lng;
        }
        public String getAddress() {
            return address;
        }
        public String getPhone() {
            return phone;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        loadCSV();
    }

    private void loadCSV() {
        try {
            ArrayList<Location> locations = new ArrayList<>();
            InputStreamReader is = new InputStreamReader(getAssets().open("LocationData.csv"));
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            while((line = reader.readLine()) != null) {
                String[] s = line.split(",");
                locations.add(new Location(s[1], s[8], Double.parseDouble(s[2]), Double.parseDouble(s[3]), s[4] + ", " + s[5] + ", " + s[6] + ", " + s[7], s[9]));
            }
            String output = "";
            for (Location l : locations) {
                output += l.getName() + "\n";
            }
            TextView text = (TextView)findViewById(R.id.locations_list);
            text.setText(output);
        } catch (IOException e) { }
    }

}
