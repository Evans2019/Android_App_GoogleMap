package com.opsc7312_assign1_15002928.opsc7312_assign1_15002928;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by adminstration on 8/31/2016.

public class Location_ListVew_Adapter extends ArrayAdapter<String>{

    public Location_ListVew_Adapter(Context context, String[] locations) {
        super(context, R.layout.location_listview,locations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.location_listview, parent, false);

        //getting position of an item within the array...
        String itemTitle = getItem(position);
        String itemLatNlong = getItem(position);
        String itemDate = getItem(position);
        TextView title = (TextView) view.findViewById(R.id.txtV_titleC);
        TextView latNlong = (TextView) view.findViewById(R.id.txtV_latNlongC);
        TextView date = (TextView) view.findViewById(R.id.txtV_dateC);

        //Setting the passed texts to its own view...
        title.setText(itemTitle);
        latNlong.setText(itemLatNlong);
        date.setText(itemDate);

        return view;
    }
}*/

public class Location_ListVew_Adapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] aItemTitle;
    private final String[] aLatLng;
    private final String[] aDate;

    public Location_ListVew_Adapter(Context context, String[] iTitle, String[] iLatLng, String[] iDate) {
        super(context, R.layout.location_listview,iTitle);

        this.context = context;
        this.aItemTitle = iTitle;
        this.aLatLng = iLatLng;
        this.aDate = iDate;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.location_listview, parent, false);

        TextView title = (TextView) view.findViewById(R.id.txtV_titleC);
        TextView latNlong = (TextView) view.findViewById(R.id.txtV_latNlongC);
        TextView date = (TextView) view.findViewById(R.id.txtV_dateC);

        //Setting the passed texts to its own view...
        title.setText(aItemTitle[position]);
        latNlong.setText(aLatLng[position]);
        date.setText("Date: " + aDate[position]);

        return view;
    }
}