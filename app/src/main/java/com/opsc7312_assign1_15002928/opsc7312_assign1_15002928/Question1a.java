package com.opsc7312_assign1_15002928.opsc7312_assign1_15002928;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class Question1a extends AppCompatActivity implements LocationListener{

    private Button btnToQuestion1b;
    private TextView txt_latitude;
    private TextView txt_longitude;
    private TextView txt_address;

    private LocationManager locationManager;
    private String provider;
    private double gLatitude = 0.0;
    private double gLongitude = 0.0;
    private Location location;
    private String gAddressLine = "address unknown";

    Handler handlerGeoPrint = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            printData(gLatitude, gLongitude, gAddressLine);
        }
    };

    Runnable runnableSetGeocoder = new Runnable() {
        @Override
        public void run() {
            setUpLocation();
            setGeocoder();

            handlerGeoPrint.sendEmptyMessage(0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1a);

        btnToQuestion1b = (Button) findViewById(R.id.btnToQuestion2);
        txt_latitude = (TextView) findViewById(R.id.txtLatitude);
        txt_longitude = (TextView) findViewById(R.id.txtLongitude);
        txt_address = (TextView) findViewById(R.id.txtAddress);

        setUpLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.question1_menu, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                //Putting extra data for identify which Activity called the HowToUse...
                Intent intent = new Intent(this, HowToUse.class);
                intent.putExtra("optionHowToUse", "Q1");
                startActivity(intent);
                return true;

            case R.id.refresh:
                showToast("Refreshing");
                Thread threadSetGeocoder = new Thread(runnableSetGeocoder);
                threadSetGeocoder.start();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            locationManager.requestLocationUpdates(provider, 400, 1, this);

        }catch (SecurityException exc){
            Log.e("Exc", exc.toString());
        }catch (Exception exc){
            Log.e("Exc", exc.toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        try{
            locationManager.removeUpdates(this);

        }catch (SecurityException exc){
            Log.e("Exc", exc.toString());
        }catch (Exception exc){
            Log.e("Exc", exc.toString());
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        gLatitude = location.getLatitude();
        gLongitude = location.getLongitude();

        //setGeocoder();
        Thread threadSetGeocoder = new Thread(runnableSetGeocoder);
        threadSetGeocoder.start();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void btnClick_btnRefreshAddress(View view){

        //setUpLocation();
        //setGeocoder();
        showToast("Refreshing");
        Thread threadSetGeocoder = new Thread(runnableSetGeocoder);
        threadSetGeocoder.start();
    }

    public void btnClick_btnToQuestion1b(View view){
        Intent intent = new Intent(this, Question1b.class);
        startActivity(intent);
    }

    private void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void setUpLocation(){
        Criteria criteria = new Criteria();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(criteria, false);

        try{
            location = locationManager.getLastKnownLocation(provider);

            if(location != null){
                //A location provider if found...
                System.out.print("Provider " + provider + " has been selected.");
            }else{
                //Location provider is not found, meaning no latitude & longitude
                showToast("Location provider unavailable.");
            }

        }catch(SecurityException exc){
            exc.printStackTrace();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
    }

    private void setGeocoder(){
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

        try{
            //This below will check if GPS is enabled before it uses the network location...
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(isGPSEnabled){
                //if(location == null){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 6 * 1, 10, this);

                //if(locationManager != null){
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if(location != null){
                        gLatitude = location.getLatitude();
                        gLongitude = location.getLongitude();
                    }
                //}
                //}
            }else {

            }

            List<Address> addresses = geocoder.getFromLocation(gLatitude, gLongitude, 1);

            if(addresses != null){
                //Address returnAddress = addresses.get(0);
                String addressLine = null;

                String adrs = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

                addressLine = "Address: " + adrs + "\n" +
                        "City: " + city + "\n" +
                        "State: " + state + "\n" +
                        "Country: " + country + "\n" +
                        "Postal Code: " + postalCode + "\n" +
                        "Known Name: " + knownName;

                gAddressLine = addressLine;

                //printData(gLatitude, gLongitude, addressLine);

            }else{
                txt_address.setText("No address found!");
            }

        }catch (SecurityException exc){
            exc.printStackTrace();
        }catch (Exception exc){
            exc.printStackTrace();
        }
    }

    private void printData(double lat, double lng, String address){

        if(gLatitude == 0 || gLongitude == 0){
            showToast("Something went wrong.\nCheck your Internet and GPS.");
        }

        txt_latitude.setText(String.valueOf(lat));
        txt_longitude.setText(String.valueOf(lng));
        txt_address.setText(address);
    }
}
