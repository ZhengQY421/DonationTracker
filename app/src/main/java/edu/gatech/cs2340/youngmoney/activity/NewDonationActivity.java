package edu.gatech.cs2340.youngmoney.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.gatech.cs2340.youngmoney.R;
import edu.gatech.cs2340.youngmoney.model.Donation;
import edu.gatech.cs2340.youngmoney.model.Location;

public class NewDonationActivity extends Activity {

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_donation);

        Intent intent = getIntent();
        location = (Location) intent.getSerializableExtra("location");

        TextView locationText = findViewById(R.id.location);
        locationText.setText(location.getName());

        final Button button = findViewById(R.id.cancel);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancel();
            }
        });

        final Button button2 = findViewById(R.id.create);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newDonation();
            }
        });
    }

    private void cancel() {
        this.finish();
    }

    private void newDonation() {
        EditText item = findViewById(R.id.item);
        EditText user = findViewById(R.id.user);
        EditText date = findViewById(R.id.date);
        location.addDonation(new Donation(item.getText().toString(), date.getText().toString(), location, user.getText().toString()));
        cancel();
    }
}
