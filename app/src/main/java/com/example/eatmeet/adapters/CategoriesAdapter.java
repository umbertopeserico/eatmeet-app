package com.example.eatmeet.adapters;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.activities.MainActivity;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.CategoryDAO;
import com.example.eatmeet.entities.Category;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by sofia on 15/06/2016.
 */
public class CategoriesAdapter extends ArrayAdapter {

    protected List<Category> mItems;
    private Context mContext;
    private int mListRowLayout;

    public CategoriesAdapter(Context context, int resource, List<Category> objects) {
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

        final Category category = mItems.get(position);
        final TextView listItem = (TextView) convertView.findViewById(R.id.textViewListItem);
        final ImageView categoryImage = (ImageView) convertView.findViewById(R.id.categoryIcon);
        final MainActivity mainActivity = (MainActivity) mContext;
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EatMeetApp.getFiltersManager().resetFilters();
                EatMeetApp.getFiltersManager().addCategory(category);
                EatMeetApp.getFiltersManager().setEnabledFilters(true);
                mainActivity.setCurrentFragment(1);
            }
        });

        CategoryDAO categoryDAO = EatMeetApp.getDaoFactory().getCategoryDAO();

        String tmpFileName = "categories-list_"+ category.getImage().substring(category.getImage().lastIndexOf("/")+1);

        final File file = new File(this.getContext().getCacheDir(), tmpFileName);
        if(!file.exists()) {
            System.out.println("Cache non esiste");
            BackendStatusManager imageStatusManager = new BackendStatusManager();
            imageStatusManager.setBackendStatusListener(new BackendStatusListener() {
                @Override
                public void onSuccess(Object response, Integer code) {
                    System.out.println("Immagine scaricata");
                    categoryImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                }

                @Override
                public void onFailure(Object response, Integer code) {
                    System.out.println("Immagine NON scaricata");
                }
            });
            categoryDAO.getImage(category.getImage(), tmpFileName, imageStatusManager, this.getContext().getCacheDir());
        }
        else
        {
            System.out.println("Cache esiste");
            if(!file.isDirectory()) {
                categoryImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            }
        }

        listItem.setText(category.getName());
        TextView count = (TextView) convertView.findViewById(R.id.countViewListItem);
        count.setText(category.getEventsCount().toString());

        return convertView;
    }
}
