package edu.gatech.cs2340.youngmoney.model;


import java.util.ArrayList;

public class ModelLocations {

    private static final ModelLocations _instance = new ModelLocations();

    private ArrayList<Location> _current;

    public ModelLocations(){

            _current = null;
    }

    public static ModelLocations get_instance (){

        return _instance;
    }

    public ArrayList<Location> get_current() {
        return _current;
    }

    public void set_current (ArrayList<Location> loc) {
        _current = loc;
    }

}
