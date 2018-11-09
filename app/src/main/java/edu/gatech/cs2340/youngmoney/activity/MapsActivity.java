package edu.gatech.cs2340.youngmoney.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import edu.gatech.cs2340.youngmoney.R;
import edu.gatech.cs2340.youngmoney.model.Location;
import edu.gatech.cs2340.youngmoney.model.ModelLocations;


/**
 * Parses latitude and longitude from CSV and pins it on Google Maps.
 * Displays location name and telephone number on the map pin.
 *
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Location> locations;
    private ModelLocations modelLocations = ModelLocations.get_instance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locations = modelLocations.get_current();

        if (locations == null){

            locations = new ArrayList<>();

            Thread loadData = new Thread(new Runnable() {
                @Override
                public void run() {
                    loadCSV();
                }
            });

            loadData.start();

            try{
                loadData.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            modelLocations.set_current(locations);
        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        CameraUpdate atl = CameraUpdateFactory.newLatLngZoom(new LatLng(33.7890, -84.3880), 11.0f);
        mMap.moveCamera(atl);

        for (Location loc: locations){

            String cord = loc.getCord();
            double lat = Double.parseDouble(cord.substring(1,cord.indexOf(',')));
            double lng = Double.parseDouble(cord.substring(cord.indexOf(',')+1,cord.length()-1));
            LatLng latlng = new LatLng(lat, lng);

            mMap.addMarker(new MarkerOptions().position(latlng).title(loc.getName()).snippet(loc.getPhone()));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));

        }
    }

    private void loadCSV() {

        HttpURLConnection con;
        String url = "https://www.ridgefieldttt.com/2340api.php?src=locations";

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setRequestMethod("GET");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

                String line;

                while ((line = in.readLine()) != null) {
                    String[] s = line.split(",", -1);
                    Location loc = new Location(s[0],s[1],s[2], s[3], s[4], s[5], s[6], s[7], s[8], s[9]);
                    locations.add(loc);
                }
            }

            con.disconnect();

        } catch (IOException e) {
            System.out.println("error");
        }
    }
}
