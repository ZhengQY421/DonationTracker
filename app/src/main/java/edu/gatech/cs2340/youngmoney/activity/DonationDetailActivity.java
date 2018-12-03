package edu.gatech.cs2340.youngmoney.activity;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import edu.gatech.cs2340.youngmoney.R;
import edu.gatech.cs2340.youngmoney.model.Donation;
import edu.gatech.cs2340.youngmoney.model.ModelDonation;

public class DonationDetailActivity extends Activity {

    private ModelDonation model = ModelDonation.get_instance();
    private Donation donation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_detail);

        donation = model.get_current();

        TextView item = findViewById(R.id.donation_title);
        item.setText(donation.getItem());

        TextView user = findViewById(R.id.user);
        user.setText(donation.getUser());

        TextView location = findViewById(R.id.location);
        location.setText(donation.getLocation());

        TextView date = findViewById(R.id.date);
        date.setText(donation.getDate());

        TextView fulldesc = findViewById(R.id.fulldesc);
        fulldesc.setText(donation.getFulldesc());

        TextView value = findViewById(R.id.value);
        value.setText(donation.getValue());

        TextView category = findViewById(R.id.category);
        category.setText(donation.getCategory());
    }

}
