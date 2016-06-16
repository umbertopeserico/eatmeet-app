package com.example.eatmeet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eatmeet.R;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.entities.Event;

import java.util.List;

/**
 * Created by sofia on 15/06/2016.
 */
public class EventsAdapter extends ArrayAdapter {

    private List<Event> mItems;
    private Context mContext;
    private int mListRowLayout;

    public EventsAdapter(Context context, int resource, List<Event> objects) {
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

        Event element = mItems.get(position);

        TextView text = (TextView) convertView.findViewById(R.id.textViewListItem);
        text.setText(element.getTitle());

        TextView img = (TextView) convertView.findViewById(R.id.imgViewListItem);
        img.setText(Integer.toString(element.getId()));

        return convertView;
    }
}
