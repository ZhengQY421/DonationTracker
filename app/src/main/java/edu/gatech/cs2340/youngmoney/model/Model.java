package edu.gatech.cs2340.youngmoney.model;

import android.util.Log;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.gatech.cs2340.youngmoney.model.Location;



public class Model {

    private static final Model _instance = new Model();

    private Location _current;
    private ArrayList<Location> locations;

    public Model (){

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
