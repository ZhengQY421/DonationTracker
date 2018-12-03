package edu.gatech.cs2340.youngmoney.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.gatech.cs2340.youngmoney.model.Donation;
import edu.gatech.cs2340.youngmoney.model.Model;
import edu.gatech.cs2340.youngmoney.model.Location;
import edu.gatech.cs2340.youngmoney.R;
import edu.gatech.cs2340.youngmoney.model.ModelDonation;

public class LocationDetailActivity extends AppCompatActivity {

    private Location loc;
    private Model model = Model.get_instance();

    private RecyclerView recyclerView;
    private SimpleDonationRecyclerViewAdapter adapter;
    private SearchView searchView;
    private Spinner spinner;
    private boolean categorySearch = false;
    private String searchText = "";
    private TextView searchMessage;
    private ArrayList<Donation> donations;
    private RecyclerView donationList;
    private SimpleDonationRecyclerViewAdapter donationAdapter;

    private FusedLocationProviderClient mFusedLocationClient;
    private Button direction;

    private List<Donation> searchResults = new ArrayList<Donation>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loc = model.get_current();
        setContentView(R.layout.activity_location_detail);

        TextView name = findViewById(R.id.loc_detail_name);
        name.setText(loc.getName());

        TextView addr = findViewById(R.id.loc_detail_addr);
        addr.setText(loc.getAddress());

        TextView coord = findViewById(R.id.loc_detail_coord);
        coord.setText(loc.getCord());

        TextView phone = findViewById(R.id.loc_detail_phone);
        phone.setText(loc.getPhone());

        TextView type = findViewById(R.id.loc_detail_type);
        type.setText(loc.getType());

        TextView web = findViewById(R.id.loc_detail_web);
        web.setText(loc.getWebsite());

        searchMessage = findViewById(R.id.searchMessage);

        searchView = findViewById(R.id.itemSearchLocation);

        final Button button = findViewById(R.id.buttonDonation);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newDonation();
            }
        });

        recyclerView = findViewById(R.id.donations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //updateSearchResults();
        adapter = new SimpleDonationRecyclerViewAdapter(LocationDetailActivity.this, searchResults,false);
        recyclerView.setAdapter(adapter);

        donations = loc.getDonations();

        donationList = findViewById(R.id.existing_donations);
        donationList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        donationAdapter = new SimpleDonationRecyclerViewAdapter(LocationDetailActivity.this, donations, true);
        donationList.setAdapter(donationAdapter);

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

        direction = findViewById(R.id.direction);
        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission( LocationDetailActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

                    requestPermission();
                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(LocationDetailActivity.this);
                builder.setMessage("Open Google Maps?").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


            }
        });




    }


    private void updateSearchResults() {
        searchResults.clear();

        if (searchText.equals("")) {
            Log.i("foopie", "fooping");
            searchResults.clear();
            return;
        }
        for (Donation donation : loc.getDonations()) {
            String toMatch = categorySearch ? donation.getCategory() : donation.getItem();
            Log.i("foopie", "trying to match");
            if (toMatch.toLowerCase().indexOf(searchText) != -1) {
                searchResults.add(donation);
            }
        }
        if (searchResults.size() == 0) {
            searchMessage.setText("No items found.");
        } else {
            searchMessage.setText("");
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }


    private void newDonation() {
        Intent intent = new Intent(this, NewDonationActivity.class);
        startActivity(intent);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(
                LocationDetailActivity.this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},1);
    }
}
