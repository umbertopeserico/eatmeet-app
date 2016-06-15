package com.example.eatmeet.utils;

import com.example.eatmeet.R;
import com.example.eatmeet.entities.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofia on 15/06/2016.
 */
public class MyListViewAdapter extends ArrayAdapter {

    private List<Category> mItems;
    private Context mContext;
    private int mListRowLayout;

    public MyListViewAdapter(Context context, int resource, List<Category> objects) {
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

        Category element = mItems.get(position);

        TextView text = (TextView) convertView.findViewById(R.id.textViewListItem);
        text.setText(element.getName());

        TextView img = (TextView) convertView.findViewById(R.id.imgViewListItem);
        img.setText(Integer.toString(element.getId()));

        return convertView;
    }
}
