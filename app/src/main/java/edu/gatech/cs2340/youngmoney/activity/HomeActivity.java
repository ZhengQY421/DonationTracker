package edu.gatech.cs2340.youngmoney.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.gatech.cs2340.youngmoney.R;

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

        final Button button3 = findViewById(R.id.map_button);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                map();
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

    private void map(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}
