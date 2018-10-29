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
import edu.gatech.cs2340.youngmoney.model.Model;

public class NewDonationActivity extends Activity {

    private Location location;
    private Model model = Model.get_instance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_donation);

        location = model.get_current();

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
        EditText fulldesc = findViewById(R.id.fulldesc);
        EditText value = findViewById(R.id.value);
        EditText category = findViewById(R.id.category);
        location.addDonation(new Donation(item.getText().toString(), date.getText().toString(), location, user.getText().toString(), fulldesc.getText().toString(), value.getText().toString(), category.getText().toString()));

        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }
}
