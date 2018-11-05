package edu.gatech.cs2340.youngmoney.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

        locations = new ArrayList<>();
        donations = new ArrayList<>();

        recyclerView = findViewById(R.id.location_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if (modelLocations.get_current() == null) {
            loadCSV();
            loadDonations();
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


        //TODO: figure out how to fetch and display from database
        //loadDatabase();
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
        try {
            InputStreamReader is = new InputStreamReader(getAssets().open("LocationData.csv"));
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            while((line = reader.readLine()) != null) {
                String[] s = line.split(",", -1);
                Location loc = new Location(s[1],s[8],s[7], s[9], s[6], s[4], s[10], s[2], s[3], s[5]);
                locations.add(loc);
            }
            modelLocations.set_current(locations);
        } catch (IOException e) { }
    }

    private void loadDonations() {
        try {
            FileInputStream fis = openFileInput("donations.csv");
            DataInputStream reader = new DataInputStream(fis);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] s = line.split(",", -1);
                Donation don = new Donation(s[0], s[1], s[2], s[3], s[4], s[5], s[6]);
                for (Location location : locations) {
                    if (location.getName().equals(don.getLocation())) {
                        location.addDonation(don, null);

                    }
                }
            }
        } catch (IOException e) {}
    }

    /*
    private void loadDatabase (){

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Donation Centers");

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String name = dataSnapshot.getKey();

                HashMap<String, String> info = (HashMap<String, String>)dataSnapshot.getValue();

                String zip = String.valueOf(info.get("Zip"));
                String type = info.get("Type");
                String phone = info.get("Phone");
                String state = info.get("State");
                String address = info.get("Street Address");
                String website = info.get("Website");
                String lat = String.valueOf(info.get("Latitude"));
                String city = info.get("City");
                String lng = String.valueOf(info.get("Long"));

                Location loc = new Location(name, type, zip, phone, state, address, website, lat, lng, city);
                locations.add(loc);

                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*
        mRef.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Database","Database initialized");

                locations = new ArrayList<>();

                for (DataSnapshot snapshotNode: dataSnapshot.getChildren()) {

                    String name = snapshotNode.getKey();

                    HashMap<String, String> info = (HashMap<String, String>)snapshotNode.getValue();

                    String zip = String.valueOf(info.get("Zip"));
                    String type = info.get("Type");
                    String phone = info.get("Phone");
                    String state = info.get("State");
                    String address = info.get("Street Address");
                    String website = info.get("Website");
                    String lat = String.valueOf(info.get("Latitude"));
                    String city = info.get("City");
                    String lng = String.valueOf(info.get("Long"));

                    Location loc = new Location(name, type, zip, phone, state, address, website, lat, lng, city);
                    locations.add(loc);
                    SimpleLocationRecyclerViewAdapter adapter = new SimpleLocationRecyclerViewAdapter(LocationActivity.this, locations);
                    recyclerView.setAdapter(adapter);
                    //adapter.notifyDataSetChanged();
                    Log.d("Database",loc.getName()+" added");
                }

                Log.d("Database","Database initialization done");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        */
    }

