package com.example.eatmeet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.entities.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofia on 28/07/2016.
 */
public class FilterRestaurantsAdapter extends ArrayAdapter {

    protected List<Restaurant> mItems;
    private Context mContext;
    private int mListRowLayout;

    public FilterRestaurantsAdapter(Context context, int resource, List<Restaurant> objects) {
        super(context, resource, objects);
        mItems = objects;
        mContext = context;
        mListRowLayout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(mListRowLayout, null);

        TextView listItem = (TextView) convertView.findViewById(R.id.textViewListItemFilterRestaurant);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBoxListItemFilterRestaurant);
        final Restaurant restaurant = mItems.get(position);

        listItem.setText(restaurant.getName());
        checkBox.setId(restaurant.getId());

        checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ArrayList<Integer> alreadySetRestaurants = EatMeetApp.getFiltersManager().getF_restaurants();
                if(isChecked){
                    alreadySetRestaurants.add(restaurant.getId());
                    System.out.println("aggiungoo il ristorante " + restaurant.getId());
                } else {
                    alreadySetRestaurants.remove((Object) restaurant.getId());
                    alreadySetRestaurants.remove(restaurant.getId());
                    System.out.println("tolgo il ristorante " + restaurant.getId());
                }
                EatMeetApp.getFiltersManager().setF_restaurants(alreadySetRestaurants);
            }
        });

        return convertView;
    }

}
