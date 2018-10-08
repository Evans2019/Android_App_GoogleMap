package com.opsc7312_assign1_15002928.opsc7312_assign1_15002928;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class Question2 extends FragmentActivity implements OnMapReadyCallback, LocationListener{

    private GoogleMap mMap;
    private Spinner spinner_MapType;
    private Location location;
    private Boolean showNormalMap = false;
    private LatLng latLng;
    private dbHandler db;
    private final int[] map_types = {GoogleMap.MAP_TYPE_NORMAL,
                                    GoogleMap.MAP_TYPE_SATELLITE,
                                    GoogleMap.MAP_TYPE_HYBRID,
                                    GoogleMap.MAP_TYPE_TERRAIN,
                                    GoogleMap.MAP_TYPE_NONE};

    private double mLatitude = 0;
    private double mLongitude = 0;

    AlertDialog.Builder builderMyPlaceDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        builderMyPlaceDesc = new AlertDialog.Builder(this);

        try{
            mMap = mapFragment.getMap();
            mMap.setMyLocationEnabled(true);

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            location = locationManager.getLastKnownLocation(provider);

            //mLatitude = location.getLatitude();
            //mLongitude = location.getLongitude();

            if(location != null){
                onLocationChanged(location);
            }

            locationManager.requestLocationUpdates(provider, 20000, 0, this);

        }catch (SecurityException exc){
            exc.printStackTrace();
        }catch (Exception exc){
            exc.printStackTrace();
        }

        spinner_MapType = (Spinner) findViewById(R.id.spnr_mapType);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.map_type));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.map_type, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner_MapType.setAdapter(adapter);

        spinner_MapType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(), parent.getItemIdAtPosition(position) + " is selected", Toast.LENGTH_LONG).show();

                if(position == 0){
                    mMap.setMapType(map_types[0]);
                    if(showNormalMap == true){
                        showToast("Normal View selected");
                    }
                    showNormalMap = true;
                }
                else if(position == 1){
                    mMap.setMapType(map_types[1]);
                    showToast("Satellite View selected");
                }
                else if(position == 2){
                    mMap.setMapType(map_types[2]);
                    showToast("Hybrid View selected");
                }
                else if(position == 3){
                    mMap.setMapType(map_types[3]);
                    showToast("Terrain View selected");
                }
                else if(position == 4){
                    mMap.setMapType(map_types[4]);
                    showToast("None View selected");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        latLng = new LatLng(mLatitude, mLongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.5f));

        //This method sets all saved places markers on to map.
        setMarkers(googleMap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.question2_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                //Putting extra data for identify which Activity called the HowToUse...
                final Intent intent = new Intent(this, HowToUse.class);
                intent.putExtra("optionHowToUse", "Q2");
                startActivity(intent);
                return true;

            case R.id.saveLocation:
                //This will call saveLocation which sends location data to MyPlace Activity...
                saveLocation();
                return true;

            case R.id.myPlaces:
                //

                startActivity(new Intent(this, MyPlaceList.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void btnClick_saveLocation(View view){
        //This will call saveLocation which sends location data to MyPlace Activity...
        saveLocation();
    }

    private void saveLocation(){
        final Intent intentSave = new Intent(this, MyPlace.class);
        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builderMyPlaceDesc.setView(input);

        builderMyPlaceDesc.setTitle("Enter Location Title");
        builderMyPlaceDesc.setPositiveButton("Save Location", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(input.getText().toString().equals("") || input.getText().toString().equals(null) || input.getText().toString().trim().isEmpty()){
                    showToast("Please type Place Title to continue.");
                }else{
                    if(mLatitude != 0 || mLongitude != 0){
                        //This will open a my_place activity and sends latitude & longitude to that activity...
                        intentSave.putExtra("option_SaveOrView", "save");
                        intentSave.putExtra("locationPoint_lat", String.valueOf(mLatitude));
                        intentSave.putExtra("locationPoint_long", String.valueOf(mLongitude));
                        //intentSave.putExtra("locationDesc", WordUtils.capitalize(input.getText().toString().toLowerCase()));
                        intentSave.putExtra("locationDesc", input.getText().toString());
                        startActivity(intentSave);
                    }else {
                        showToast("Could not get your location, check your Internet");
                    }
                }
            }
        });

        builderMyPlaceDesc.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builderMyPlaceDesc.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();

        latLng = new LatLng(mLatitude, mLongitude);

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
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

    private void setMarkers(GoogleMap mGmaps){
        try {

            this.mMap = mGmaps;

            final List<LocationData> locationDatas = db.getAllLocationData();
            for (LocationData locationData : locationDatas) {

                LatLng sydney = new LatLng(Double.parseDouble(locationData.get_latitude()), Double.parseDouble(locationData.get_longitude()));
                mMap.addMarker(new MarkerOptions().position(sydney).title(locationData.get_title()));

            }
        }catch (Exception exc){
            exc.printStackTrace();
        }
    }

    private void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
