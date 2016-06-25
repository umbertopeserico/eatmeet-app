package com.example.eatmeet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.eatmeet.R;

import java.util.List;

/**
 * Created by sofia on 20/06/2016.
 */
public class OrderAdapter extends ArrayAdapter {

    private List<String> mItems;
    private Context mContext;
    private int mListRowLayout;

    public OrderAdapter (Context context, int resource, List<String> objects) {
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

        RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.radioButton) ;
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change order
            }
        });

        TextView orderListItem = (TextView) convertView.findViewById(R.id.orderListItem);
        orderListItem.setText(element);

        return convertView;
    }
}
