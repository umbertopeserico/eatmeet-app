package com.example.eatmeet.adapters;

import com.example.eatmeet.R;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.utils.Images;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
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
        final ImageView icon = (ImageView) convertView.findViewById(R.id.categoryIcon);
        Category element = mItems.get(position);

        new Images(){
            @Override public void onPostExecute(Bitmap result){
                icon.setImageBitmap(result);
            }
        }.execute(element.getIcon());

        /*
        ImageView icon = (ImageView) convertView.findViewById(R.id.categoryIcon);
        String uri = "@drawable/"+element.getIcon();
        uri = Environment.getExternalStorageDirectory().toString() + element.getName() + ".jpg";
        int imageResource = getContext().getResources().getIdentifier(uri, null, getContext().getPackageName());
        icon.setImageDrawable(getContext().getResources().getDrawable(imageResource));
        */

        TextView text = (TextView) convertView.findViewById(R.id.textViewListItem);
        text.setText(element.getName());

        TextView count = (TextView) convertView.findViewById(R.id.countViewListItem);
        count.setText(element.getMeta().get("events_count"));

        return convertView;
    }
}
