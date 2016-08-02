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
    private Context mContext;
    private int mListRowLayout;

    public FilterCategoriesAdapter(Context context, int resource, List<Category> objects) {
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

        TextView listItem = (TextView) convertView.findViewById(R.id.textViewListItemFilterCategory);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBoxListItemFilterCategory);
        final Category category = mItems.get(position);

        listItem.setText(category.getName());
        checkBox.setId(category.getId());
        if(EatMeetApp.getFiltersManager().getF_categories().contains(category.getId())){
            System.out.println("STO AGGIUNGENDO LA CATEGORIA " + category.getName() + " ai filtri");
            checkBox.setChecked(true);
        }

        checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ArrayList<Integer> alreadySetCategories = EatMeetApp.getFiltersManager().getF_categories();
                if(isChecked){
                    alreadySetCategories.add(category.getId());
                } else {
                    alreadySetCategories.remove((Object) category.getId());
                }
                EatMeetApp.getFiltersManager().setF_categories(alreadySetCategories);
            }
        });

        return convertView;
    }

}
