package edu.gatech.cs2340.youngmoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button button = findViewById(R.id.log_out_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout() {
        Intent intent = new Intent(this, SelectActivity.class);
        startActivity(intent);
    }

    /*
    private void readCSVFile() {
        SimpleModel model = SimpleModel.INSTANCE;

        try {
            InputStream is = getResources().openRawResource(R.raw.sample);

            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                Log.d(HomeActivity.TAG, line);
                String[] tokens = line.split(",");
                int id = Integer.parseInt(tokens[1]);
                model.addItem
            }
        }
    }*/
}
