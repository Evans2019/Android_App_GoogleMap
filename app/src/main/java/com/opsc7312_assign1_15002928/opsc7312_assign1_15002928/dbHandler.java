package com.opsc7312_assign1_15002928.opsc7312_assign1_15002928;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adminstration on 8/27/2016.
 */
public class dbHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "location_db.db";
    public static final String TABLE_LOCATIONS = "tbl_locations";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TITLE = "tittle";

    /*public dbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }*/

    public dbHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_LOCATIONS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LATITUDE + " TEXT, " +
                COLUMN_LONGITUDE + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TITLE  + " TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        onCreate(db);
    }

    //This method will be saving to database...
    public void saveLovcation(LocationData locData){
        ContentValues values = new ContentValues();
        values.put(COLUMN_LATITUDE, locData.get_longitude());
        values.put(COLUMN_LONGITUDE, locData.get_longitude());
        values.put(COLUMN_ADDRESS, locData.get_address());
        values.put(COLUMN_DATE, locData.get_date());
        values.put(COLUMN_TITLE, locData.get_title());

        this.getWritableDatabase().insertOrThrow(TABLE_LOCATIONS, "", values);
    }

    //this method will be deleting from the database...
    public void deleteLocation(int id){
        SQLiteDatabase db = getWritableDatabase();
        //db.execSQL("DELETE FROM " + TABLE_LOCATIONS + " WHERE " + COLUMN_ID + " = \"" + id + "\";");
        db.delete(TABLE_LOCATIONS, COLUMN_ID + " = ?",
                new String[] {String.valueOf(id)});
        db.close();
    }

    //This method will getting a single data from the database...
    public LocationData getLocationData(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LOCATIONS,
                new String[] {COLUMN_ID, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_ADDRESS, COLUMN_DATE, COLUMN_TITLE}, COLUMN_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        LocationData locationData = new LocationData(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        return locationData;
    }

    //This method will be getting all data from the database...
    public List<LocationData> getAllLocationData(){
        List<LocationData> locationList = new ArrayList<LocationData>();
        String query = "SELECT * FROM " + TABLE_LOCATIONS + ";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                LocationData locationData = new LocationData();
                locationData.set_id(Integer.parseInt(cursor.getString(0)));
                locationData.set_latitude(cursor.getString(1));
                locationData.set_longitude(cursor.getString(2));
                locationData.set_address(cursor.getString(3));
                locationData.set_date(cursor.getString(4));
                locationData.set_title(cursor.getString(5));

                //Adding to locationList array
                locationList.add(locationData);
            }while (cursor.moveToNext());
        }

        return locationList;
    }

    //Well this method will just counting the number of data in the database table...
    public int getLocationDataCount(){
        String query = "SELECT * FROM " + TABLE_LOCATIONS + ";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        return cursor.getCount();
    }

}
