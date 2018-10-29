package edu.gatech.cs2340.youngmoney.model;


public class ModelDonation {

    private static final ModelDonation _instance = new ModelDonation();

    private Donation _current;

    public ModelDonation(){

            _current = null;
    }

    public static ModelDonation get_instance (){

        return _instance;
    }

    public Donation get_current() {
        return _current;
    }

    public void set_current (Donation loc) {
        _current = loc;
    }

}
