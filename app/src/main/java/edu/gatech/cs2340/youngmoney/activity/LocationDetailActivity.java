package edu.gatech.cs2340.youngmoney.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import edu.gatech.cs2340.youngmoney.model.Model;
import edu.gatech.cs2340.youngmoney.model.Location;
import edu.gatech.cs2340.youngmoney.R;

public class LocationDetailActivity extends AppCompatActivity {

    private Location loc;
    private Model model = Model.get_instance();
    private RecyclerView recyclerView;
    private SimpleDonationRecyclerViewAdapter adapter;

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

        final Button button = findViewById(R.id.buttonDonation);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newDonation();
            }
        });

        recyclerView = findViewById(R.id.donations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        adapter = new SimpleDonationRecyclerViewAdapter(LocationDetailActivity.this, loc.getDonations());
        recyclerView.setAdapter(adapter);
    }

    private void newDonation() {
        Intent intent = new Intent(this, NewDonationActivity.class);
        startActivity(intent);
    }
}
