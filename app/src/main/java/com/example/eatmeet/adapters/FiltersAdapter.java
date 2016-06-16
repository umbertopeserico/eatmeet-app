package com.example.eatmeet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eatmeet.R;
import com.example.eatmeet.entities.Event;

import java.util.List;

/**
 * Created by sofia on 15/06/2016.
 */
public class FiltersAdapter extends ArrayAdapter {

    private List<String> mItems;
    private Context mContext;
    private int mListRowLayout;

    public FiltersAdapter (Context context, int resource, List<String> objects) {
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

        String element = mItems.get(position);

        TextView text = (TextView) convertView.findViewById(R.id.textViewListItem);
        text.setText(element);

        TextView img = (TextView) convertView.findViewById(R.id.imgViewListItem);
        img.setText(element);

        return convertView;
    }
}
