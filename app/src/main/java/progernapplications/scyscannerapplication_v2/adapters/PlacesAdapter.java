package progernapplications.scyscannerapplication_v2.adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import progernapplications.scyscannerapplication_v2.R;
import progernapplications.scyscannerapplication_v2.models.Place;

public class PlacesAdapter extends ArrayAdapter<Place> {

    private final Context context;
    private final List<Place> mPlaces;
    private final List<Place> mPlaces_ALL;
    private final List<Place> mPlaces_Suggestion;
    private final int mLayoutResourseID;


    public PlacesAdapter(Context context, int resourse, List<Place> places) {
        super(context, resourse, places);
        this.context = context;
        this.mLayoutResourseID = resourse;
        this.mPlaces = new ArrayList<>(places);
        this.mPlaces_ALL = new ArrayList<>(places);
        this.mPlaces_Suggestion = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mPlaces.size();
    }

    public Place getItem(int position) {
        return mPlaces.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {

            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                convertView = inflater.inflate(mLayoutResourseID, parent, false);
            }

            Place place = getItem(position);
            TextView placeID = (TextView) convertView.findViewById(R.id.place_ID);
            TextView placeName = (TextView) convertView.findViewById(R.id.place_name);
            TextView countryName = (TextView) convertView.findViewById(R.id.country_name);

            placeID.setText(place.getId());
            placeName.setText(place.getName());
            countryName.setText(place.getCountryName());
        } catch (Exception ex) {
            //
        }

        return convertView;
    }


}
