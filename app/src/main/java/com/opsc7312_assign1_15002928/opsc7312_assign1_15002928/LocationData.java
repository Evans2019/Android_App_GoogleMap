package com.opsc7312_assign1_15002928.opsc7312_assign1_15002928;

/**
 * Created by adminstration on 8/29/2016.
 */
public class LocationData {

    private int _id;
    private String _latitude;
    private String _longitude;
    private String _address;
    private String _date;
    private String _title;

    public LocationData() {

    }

    public LocationData(int id, String latitude, String longitude, String address, String date, String title) {
        this._id = id;
        this._latitude = latitude;
        this._longitude = longitude;
        this._address = address;
        this._date = date;
        this._title = title;
    }

    //Setters to set the data...
    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_latitude(String _latitude) {
        this._latitude = _latitude;
    }

    public void set_longitude(String _longitude) {
        this._longitude = _longitude;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    //Getter for getting the data...
    public int get_id() {
        return _id;
    }

    public String get_latitude() {
        return _latitude;
    }

    public String get_longitude() {
        return _longitude;
    }

    public String get_address() {
        return _address;
    }

    public String get_date() {
        return _date;
    }

    public String get_title() {
        return _title;
    }
}
