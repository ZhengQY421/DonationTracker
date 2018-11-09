package edu.gatech.cs2340.youngmoney.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import edu.gatech.cs2340.youngmoney.model.Donation;
import edu.gatech.cs2340.youngmoney.model.ModelLocations;
import edu.gatech.cs2340.youngmoney.model.Location;
import edu.gatech.cs2340.youngmoney.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import java.net.HttpURLConnection;
import java.net.URL;

public class LocationActivity extends AppCompatActivity {

    private ArrayList<Location> locations;
    private ArrayList<Donation> donations;
    private RecyclerView recyclerView;
    private RecyclerView donationRecyclerView;
    private SimpleLocationRecyclerViewAdapter adapter;
    private SimpleDonationRecyclerViewAdapter donationAdapter;
    private ModelLocations modelLocations = ModelLocations.get_instance();
    private TextView searchMessage;

    private SearchView searchView;
    private boolean categorySearch = false;
    private String searchText = "";
    private List<Donation> searchResults = new ArrayList<Donation>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locations = modelLocations.get_current();
        donations = new ArrayList<>();

        recyclerView = findViewById(R.id.location_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if (locations == null) {

            locations = new ArrayList<>();

            Thread loadData = new Thread(new Runnable() {
                @Override
                public void run() {
                    loadCSV();
                    loadDonations();
                }
            });

            loadData.start();

            try{
                loadData.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            modelLocations.set_current(locations);
        }

        searchMessage = findViewById(R.id.searchMessage);
        searchView = findViewById(R.id.itemSearchAllLocations);


        adapter = new SimpleLocationRecyclerViewAdapter(LocationActivity.this, modelLocations.get_current());
        recyclerView.setAdapter(adapter);


        donationRecyclerView = findViewById(R.id.donations);
        donationRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        updateSearchResults();
        donationAdapter = new SimpleDonationRecyclerViewAdapter(this, searchResults);
        donationRecyclerView.setAdapter(donationAdapter);

        Spinner spinner = (Spinner) findViewById(R.id.searchSpinner);

        List<String> searchOptions = new ArrayList<String>();
        searchOptions.add("Search by Name");
        searchOptions.add("Search by Category");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, searchOptions);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int position, long id) {
                if (position == 0) {
                    categorySearch = false;
                } else {
                    categorySearch = true;
                }
                updateSearchResults();
            }
            @Override
            public void onNothingSelected(AdapterView view) {}
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchText = query.toLowerCase(Locale.US);
                updateSearchResults();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText.toLowerCase(Locale.US);
                updateSearchResults();
                return false;
            }
        });

    }



    private void updateSearchResults() {
        searchResults.clear();

        if (searchText.equals("")) {
            searchMessage.setText("");
        } else {
            for (Location loc : locations) {
                for (Donation donation : loc.getDonations()) {
                    String toMatch = categorySearch ? donation.getCategory() : donation.getItem();
                    if (toMatch.toLowerCase().indexOf(searchText) != -1) {
                        searchResults.add(donation);
                    }
                }
            }

            if (searchResults.size() == 0) {
                searchMessage.setText("No items found.");
            } else {
                searchMessage.setText("");
            }
        }
        if (donationAdapter != null) {
            donationAdapter.notifyDataSetChanged();
        }
    }



    private void loadCSV() {
        HttpURLConnection con;
        String url = "https://www.ridgefieldttt.com/2340api.php?src=locations";

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setRequestMethod("GET");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

                String line;

                while ((line = in.readLine()) != null) {
                    String[] s = line.split(",", -1);
                    Location loc = new Location(s[0],s[1],s[2], s[3], s[4], s[5], s[6], s[7], s[8], s[9]);
                    locations.add(loc);
                }
            }

            con.disconnect();

        } catch (IOException e) {
            System.out.println("error");
        }
    }

    private void loadDonations() {
        HttpURLConnection con;
        String url = "https://www.ridgefieldttt.com/2340api.php?src=donations";

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setRequestMethod("GET");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

                String line;

                while ((line = in.readLine()) != null) {
                    String[] s = line.split(",", -1);
                    Donation don = new Donation(s[0], s[1], s[2], s[3], s[4], s[5], s[6]);
                    for (Location location : locations) {
                        if (location.getName().equals(don.getLocation())) {
                            location.addDonation(don, null);
                        }
                    }
                }
            }

            con.disconnect();

        } catch (IOException e) {
            System.out.println("error");
        }
    }
}

