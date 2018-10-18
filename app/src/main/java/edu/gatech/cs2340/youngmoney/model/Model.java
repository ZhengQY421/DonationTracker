package edu.gatech.cs2340.youngmoney.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.gatech.cs2340.youngmoney.model.Location;



public class Model {

    private static final Model _instance = new Model();

    private Location _current;

    private Model (){

            _current = null;
    }

    public static Model get_instance (){

        return _instance;
    }

    public Location get_current() {
        return _current;
    }

    public void set_current (Location loc) {
        _current = loc;
    }

}
