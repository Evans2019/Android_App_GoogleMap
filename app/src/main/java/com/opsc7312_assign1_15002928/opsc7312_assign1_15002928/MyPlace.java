package com.opsc7312_assign1_15002928.opsc7312_assign1_15002928;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyPlace extends AppCompatActivity {

    private TextView txtLat;
    private TextView txtlong;
    private TextView txtAddress;
    private TextView txtTitle;
    private TextView txtDate;

    private String latitude = null;
    private String longitude = null;
    private String address = null;
    private String title = null;
    private String date = null;
    private int id = 0;

    private dbHandler db;
    private LocationData locationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_place);

        LinearLayout layoutForView = (LinearLayout) findViewById(R.id.layoutForView);
        LinearLayout layoutForSave = (LinearLayout) findViewById(R.id.layoutForSave);

        locationData = new LocationData();
        txtLat = (TextView) findViewById(R.id.txtV_lat);
        txtlong = (TextView) findViewById(R.id.txtV_long);
        txtAddress = (TextView) findViewById(R.id.txtV_address);
        txtTitle = (TextView) findViewById(R.id.txtV_title);
        txtDate = (TextView) findViewById(R.id.txtV_date);

        db = new dbHandler(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }

        String bundleData = bundle.getString("option_SaveOrView");
        try{
            if(bundleData.equals("save")){
                txtDate.setVisibility(View.GONE);

                latitude = bundle.getString("locationPoint_lat");
                longitude = bundle.getString("locationPoint_long");
                title = bundle.getString("locationDesc");

                setAddress(Double.parseDouble(latitude), Double.parseDouble(longitude));
                txtLat.setText(latitude);
                txtlong.setText(longitude);
                txtTitle.setText(title);

            }else if(bundleData.equals("view")){
                layoutForSave.setVisibility(View.GONE);
                layoutForView.setVisibility(View.VISIBLE);


                //if(bundleLocationData != null){
                    id = Integer.parseInt(bundle.getString("pId"));
                    txtLat.setText(bundle.getString("pLat"));
                    txtlong.setText(bundle.getString("pLng"));
                    txtTitle.setText(bundle.getString("pTtl"));
                    txtAddress.setText(bundle.getString("pAddr"));
                    txtDate.setText(bundle.getString("pDate"));
                //}else {
                 //   showToast("Error! Phone sent empty field's.");
                 //   finish();
                //}
            }
        }catch (NullPointerException exc){
            exc.printStackTrace();
        }catch (Exception exc){
            exc.printStackTrace();
        }
    }

    private void setAddress(double lati, double longi){
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

        try{
            List<Address> addressList = geocoder.getFromLocation(lati, longi, 1);

            if(addressList != null){

                String adrs = addressList.get(0).getAddressLine(0);
                String city = addressList.get(0).getLocality();
                String state = addressList.get(0).getAdminArea();
                String country = addressList.get(0).getCountryName();
                String postalCode = addressList.get(0).getPostalCode();
                String knownName = addressList.get(0).getFeatureName();

                address = "Address: " + adrs + "\n" +
                        "City: " + city + "\n" +
                        "State: " + state + "\n" +
                        "Country: " + country + "\n" +
                        "Postal Code: " + postalCode + "\n" +
                        "Known Name: " + knownName;

                txtAddress.setText(address);
            }else{
                showToast("No address found!");
            }
        }catch (Exception exc){
            exc.printStackTrace();
        }
    }

    //This method makes my life easy by showing a Toast without writing a-lot of code...
    private void showToast(String text){
        Toast.makeText(this,text, Toast.LENGTH_LONG).show();
    }

    public void btnClick_sCancel(View view){
        //finish method closes current Activity...
        finish();
    }

    public void btnClick_save(View view){
        if(address != null){
            //Getting current date when user clicks Save button...
            date = DateFormat.getDateInstance().format(new Date());

            //Saving data to database...
            db.saveLovcation(new LocationData(0, latitude, longitude, address, date, title));
            showToast("Location was saved successfully");
            finish();
        }else{
            showToast("Address is empty, please refresh to get Address");
        }
    }

    public void btnClick_back(View view){
        finish();
    }

    public void btnClick_delete(View view){
        showToast(txtTitle.getText() + " was deleted.");
        db.deleteLocation(id);
        finish();
    }
}
