package edu.gatech.cs2340.youngmoney;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button button1 = findViewById(R.id.log_out_button);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logout();
            }
        });

        final Button button2 = findViewById(R.id.locations_button);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                locations();
            }
        });
    }

    private void logout() {
        Intent intent = new Intent(this, SelectActivity.class);
        startActivity(intent);
    }

    private void locations() {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

}
