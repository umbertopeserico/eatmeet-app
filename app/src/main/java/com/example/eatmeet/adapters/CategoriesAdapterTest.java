package com.example.eatmeet.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.activitiestest.CategoriesTestActivity;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.CategoryDAO;
import com.example.eatmeet.entities.Category;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

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
        final ImageView categoryImage = (ImageView) convertView.findViewById(R.id.categoryIcon);
        Category category = mItems.get(position);



        CategoryDAO categoryDAO = EatMeetApp.getDaoFactory().getCategoryDAO();

        String tmpFileName = category.getImage().substring(category.getImage().lastIndexOf("/")+1);

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
            categoryDAO.getImage(category.getImage(), imageStatusManager, this.getContext().getCacheDir());
        }
        else
        {
            System.out.println("Cache esiste");
            if(!file.isDirectory()) {
                categoryImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            }
        }

        listItem.setText(category.getName());

        return convertView;
    }

}