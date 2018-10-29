package edu.gatech.cs2340.youngmoney.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import edu.gatech.cs2340.youngmoney.model.ModelLocations;
import edu.gatech.cs2340.youngmoney.model.Location;
import edu.gatech.cs2340.youngmoney.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class LocationActivity extends AppCompatActivity {

    private ArrayList<Location> locations;
    private RecyclerView recyclerView;
    private SimpleLocationRecyclerViewAdapter adapter;
    private ModelLocations modelLocations = ModelLocations.get_instance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locations = new ArrayList<>();

        recyclerView = findViewById(R.id.location_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if (modelLocations.get_current() == null) {
            loadCSV();
        }

        adapter = new SimpleLocationRecyclerViewAdapter(LocationActivity.this, modelLocations.get_current());
        recyclerView.setAdapter(adapter);

        //TODO: figure out how to fetch and display from database
        //loadDatabase();
    }

    private void loadCSV() {
        try {
            InputStreamReader is = new InputStreamReader(getAssets().open("LocationData.csv"));
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            while((line = reader.readLine()) != null) {
                String[] s = line.split(",");
                Location loc = new Location(s[1],s[8],s[7], s[9], s[6], s[4], s[10], s[2], s[3], s[5]);
                locations.add(loc);
            }
            modelLocations.set_current(locations);
        } catch (IOException e) { }
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

