package com.example.eatmeet.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eatmeet.R;
import com.example.eatmeet.activities.EventActivity;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.utils.Images;

import java.util.List;

/**
 * Created by sofia on 15/06/2016.
 */
public class EventsAdapterTest extends ArrayAdapter {

    private List<Event> mItems;
    private Context mContext;
    private int mListRowLayout;

    public EventsAdapterTest(Context context, int resource, List<Event> objects) {
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

        Event event = mItems.get(position);

        TextView eventsTitle = (TextView) convertView.findViewById(R.id.eventsTitle);
        eventsTitle.setText(event.getTitle());

        return convertView;
    }
}
