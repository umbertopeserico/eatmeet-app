package com.example.eatmeet.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.CategoryDAO;
import com.example.eatmeet.entities.Category;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofia on 28/07/2016.
 */
public class FilterCategoriesAdapter extends ArrayAdapter {

    protected List<Category> mItems;
    protected List<Category> mSelectedCategories;
    private Context mContext;
    private int mListRowLayout;

    public FilterCategoriesAdapter(Context context, int resource, List<Category> objects, List<Category> selectedCategories) {
        super(context, resource, objects);
        mItems = objects;
        mContext = context;
        mListRowLayout = resource;
        mSelectedCategories = selectedCategories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(mListRowLayout, null);

        //TextView listItem = (TextView) convertView.findViewById(R.id.textViewListItemFilterCategory);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBoxListItemFilterCategory);
        final Category category = mItems.get(position);

        //listItem.setText(category.getName());
        checkBox.setId(category.getId());
        checkBox.setText(category.getName());
        if(EatMeetApp.getFiltersManager().getSelectedCategories().contains(category)){
            checkBox.setChecked(true);
            if(!mSelectedCategories.contains(category)) {
                mSelectedCategories.add(category);
            }
        }

        checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!mSelectedCategories.contains(category)) {
                        mSelectedCategories.add(category);
                    }
                } else {
                    mSelectedCategories.remove(category);
                }
            }
        });

        return convertView;
    }

}
