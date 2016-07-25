package com.example.eatmeet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eatmeet.R;
import com.example.eatmeet.entities.Category;

import java.util.List;

/**
 * Created by umberto on 25/07/16.
 */
public class CategoriesAdapterTest extends ArrayAdapter {

    protected List<Category> mItems;
    private Context mContext;
    private int mListRowLayout;

    public CategoriesAdapterTest(Context context, int resource, List<Category> objects) {
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

        TextView listItem = (TextView) convertView.findViewById(R.id.textViewListItem);
        Category element = mItems.get(position);

        listItem.setText(element.getName());

        return convertView;
    }

}
