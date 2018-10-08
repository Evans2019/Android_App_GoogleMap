package com.opsc7312_assign1_15002928.opsc7312_assign1_15002928;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MyPlaceList extends AppCompatActivity {

    private dbHandler db;
    private ListView lstV_locations;
    private LocationData gLocationData;
    //Intent intentSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_place_list);

        loader();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loader();
    }

    private void longClickDialog(){
        final CharSequence selections[] = new CharSequence[]{"View in Detail", "Delete"};

        //This will show a Dialog to let the user Delete, Show Location on Map or View Location in details...
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(gLocationData.get_title());
        builder.setItems(selections, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        //showToast(selections[1].toString() + " selected");
                        intentDataProcess();
                        break;
                    case 1:
                        //showToast(selections[2].toString() + " selected");
                        db.deleteLocation(gLocationData.get_id());
                        loader();
                        break;

                }
            }
        });
        builder.show();
    }

    private void loader(){
        //Intent intentSave = new Intent(this, MyPlace.class);
        gLocationData = new LocationData();
        db = new dbHandler(this);
        lstV_locations = (ListView) findViewById(R.id.lstV_locations);

        final String[] titleArray = new String[db.getLocationDataCount()];
        final String[] latLngArray = new String[db.getLocationDataCount()];
        final String[] latArray = new String[db.getLocationDataCount()];
        final String[] lngArray = new String[db.getLocationDataCount()];
        final String[] dateArray = new String[db.getLocationDataCount()];
        final int[] idArray = new int[db.getLocationDataCount()];
        final String[] addressArray = new String[db.getLocationDataCount()];

        try{
            /*ArrayList<String> titleArray = new ArrayList<String>();
            ArrayList<String> latLngArray = new ArrayList<String>();
            ArrayList<String> dateArray = new ArrayList<String>();*/

            int counter = 0;

            final List<LocationData> locationDatas = db.getAllLocationData();
            for(LocationData locationData: locationDatas){
            /*String tester = "ID: " + locationData.get_id() +
                    "Title: " + locationData.get_title() +
                    "\nLatitude: " + locationData.get_latitude() +
                    "\nLongitude: " + locationData.get_longitude() +
                    "\nAddress: " + locationData.get_address() +
                    "\nDate: " + locationData.get_date();*/
                /*titleArray.add(locationData.get_title());
                latLngArray.add(locationData.get_latitude() + "*S, " + locationData.get_longitude() + "*E");
                dateArray.add(locationData.get_date());*/

                titleArray[counter] = locationData.get_title();
                latLngArray[counter] = locationData.get_latitude() + "*S, " + locationData.get_longitude() + "*E";
                latArray[counter] = locationData.get_latitude();
                lngArray[counter] = locationData.get_longitude();
                dateArray[counter] = locationData.get_date();
                idArray[counter] = locationData.get_id();
                addressArray[counter] = locationData.get_address();

                counter++;
            }

            ListAdapter locationAdapter = new Location_ListVew_Adapter(this, titleArray, latLngArray, dateArray);
            lstV_locations.setAdapter(locationAdapter);

        }catch (Exception exc){
            exc.printStackTrace();
        }

        lstV_locations.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        gLocationData.set_id(idArray[position]);
                        gLocationData.set_date(dateArray[position]);
                        gLocationData.set_title(titleArray[position]);
                        gLocationData.set_address(addressArray[position]);
                        gLocationData.set_latitude(latArray[position]);
                        gLocationData.set_longitude(lngArray[position]);

                        intentDataProcess();
                    }
                }
        );

        lstV_locations.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        gLocationData.set_id(idArray[position]);
                        gLocationData.set_title(titleArray[position]);
                        gLocationData.set_latitude(latArray[position]);
                        gLocationData.set_longitude(lngArray[position]);
                        gLocationData.set_address(addressArray[position]);
                        gLocationData.set_date(dateArray[position]);

                        longClickDialog();
                        return true;
                    }
                }
        );
    }

    private void intentDataProcess(){
        Intent intentSave = new Intent(this, MyPlace.class);

        intentSave.putExtra("option_SaveOrView", "view");
        intentSave.putExtra("pLat", gLocationData.get_latitude());
        intentSave.putExtra("pLng", gLocationData.get_longitude());
        intentSave.putExtra("pAddr", gLocationData.get_address());
        intentSave.putExtra("pTtl", gLocationData.get_title());
        intentSave.putExtra("pDate", gLocationData.get_date());
        intentSave.putExtra("pId", String.valueOf(gLocationData.get_id()));

        startActivity(intentSave);
    }

    private void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
