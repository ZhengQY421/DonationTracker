package edu.gatech.cs2340.youngmoney.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.gatech.cs2340.youngmoney.model.Model;
import edu.gatech.cs2340.youngmoney.model.Location;
import edu.gatech.cs2340.youngmoney.R;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        final Button button = findViewById(R.id.username_sign_in_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login();
            }
        });

        final Button button2 = findViewById(R.id.username_register_button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                register();
            }
        });
    }

    public static int failedLoginAttempts = 0;
    private void login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void register() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}
