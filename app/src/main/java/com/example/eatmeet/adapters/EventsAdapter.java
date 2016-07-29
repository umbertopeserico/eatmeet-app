package com.example.eatmeet.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
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

        final Event event = mItems.get(position);

        Log.d("EVENTS: ", "Add element to listview "+event.toString());

        /*
        TextView text = (TextView) convertView.findViewById(R.id.textViewListItem);
        text.setText(event.getTitle());
        */
        final ImageView eventsImage = (ImageView) convertView.findViewById(R.id.eventsImage);
        /*new Images(){
            @Override public void onPostExecute(Bitmap result){
                eventsImage.setImageBitmap(result);
            }
        }.execute((String) event.getUrlImageMedium());*/
        TextView eventsTitle = (TextView) convertView.findViewById(R.id.eventsTitle);
        eventsTitle.setText(event.getTitle());

        final LinearLayout eventLayout = (LinearLayout) convertView.findViewById(R.id.list_item_events);
        eventLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                Intent intent = new Intent(context, EventActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id", event.getId());
                context.startActivity(intent);
            }
        });

        /*
        TextView img = (TextView) convertView.findViewById(R.id.countViewListItem);
        img.setText(Integer.toString(eventLayout.getId()));
        */
        return convertView;
    }
}
