package edu.gatech.cs2340.youngmoney.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.app.Notification;
import android.app.NotificationManager;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.gatech.cs2340.youngmoney.R;
import edu.gatech.cs2340.youngmoney.model.Donation;
import edu.gatech.cs2340.youngmoney.model.Location;
import edu.gatech.cs2340.youngmoney.model.Model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOError;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;

import javax.net.ssl.HttpsURLConnection;

public class NewDonationActivity extends Activity {

    private Location location;
    private Model model = Model.get_instance();
    private final String USER_AGENT = "Mozilla/5.0";

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

        /*
        String tittle="Young Money";
        String subject="New Donation at " + location.getName();
        String body= "Click to check";

        NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder
                (getApplicationContext()).setContentTitle(tittle).setContentText(body).
                setContentTitle(subject).setSmallIcon(R.drawable.abc).build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify); */

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc)
                        .setContentTitle("Young Money App")
                        .setContentText("New Donation at " + location.getName());

        Intent notificationIntent = new Intent(this, NewDonationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    private void cancel() {
        this.finish();
    }

    private void newDonation() {
        EditText item = findViewById(R.id.item);
        if (TextUtils.isEmpty(item.getText().toString())) {
            item.setError(getString(R.string.error_field_required));
        } else {
            EditText user = findViewById(R.id.user);
            EditText date = findViewById(R.id.date);
            EditText fulldesc = findViewById(R.id.fulldesc);
            EditText value = findViewById(R.id.value);
            EditText category = findViewById(R.id.category);
            location.addDonation(new Donation(item.getText().toString(),
                    date.getText().toString(),
                    location.getName(),
                    user.getText().toString(),
                    fulldesc.getText().toString(),
                    value.getText().toString(),
                    category.getText().toString()), this);



            new SendGarbageTask().execute(item.getText().toString(), date.getText().toString(), user.getText().toString(), fulldesc.getText().toString(), value.getText().toString(), category.getText().toString());

            Intent intent = new Intent(this, LocationActivity.class);
            startActivity(intent);
        }
    }

    class SendGarbageTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... params) {
            try {
                String url = "https://ridgefieldttt.com/2340api.php";
                String urlParameters = "dest=donations&locid="+location.getId()+"&item="+params[0]
                        +"&date="+params[1]
                        +"&location="+location.getName()
                        +"&user="+params[2]
                        +"&fulldesc="+params[3]
                        +"&value="+params[4]
                        +"&category="+params[5];
                URL obj = new URL(url);
                HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

                //add reuqest header
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                // Send post request
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());

                return response.toString();
            } catch (Exception e) {
                this.exception = e;
                System.out.println("I hate this life " + e.toString());
                return null;
            }
        }

        protected void onPostExecute(String swag) {
            // TODO: check this.exception
            // TODO: do something with the feed

            System.out.println(" I love swag. "+swag);
        }
    }
}
