package com.example.eatmeet.adapters;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.activities.MainActivity;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.mainactivityfragments.CategoriesFragment;
import com.example.eatmeet.mainactivityfragments.EventsFragment;
import com.example.eatmeet.utils.Configs;
import com.example.eatmeet.utils.Images;
import com.example.eatmeet.utils.Post;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.TintContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        Category element = mItems.get(position);

        final TextView listItem = (TextView) convertView.findViewById(R.id.textViewListItem);
        final ImageView icon = (ImageView) convertView.findViewById(R.id.categoryIcon);
        final MainActivity mainActivity = (MainActivity) mContext;
        final ArrayList<Integer> categories = new ArrayList<>();
        categories.add(element.getId());
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logger.getLogger("Logging action from category to events").log(Level.WARNING, "Entro nel cambio di fragment");
                //set filters
                EatMeetApp.getFiltersManager().removeAllFilters();
                EatMeetApp.getFiltersManager().setF_categories(categories);
                //go to event fragment v1
                //FragmentTransaction trans = mainActivity.getSupportFragmentManager().beginTransaction();
				/*
				 * We use the "root frame" defined in
				 * "root_fragment.xml" as the reference to replace fragment
				 */
                //Fragment eventsFragment = new EventsFragment();
                //FrameLayout layout = (FrameLayout) mainActivity.findViewById(R.id.fragment_categories);
                //layout.removeAllViewsInLayout();
                //trans.replace(R.id.fragment_categories, eventsFragment);
				/*
				 * The following lines allow us to add the fragment
				 * to the stack and return to it later, by pressing back
				 */
                //trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                //trans.addToBackStack(null);

                //trans.commit();
                //go to event fragment v2
                mainActivity.setCurrentFragment(1);
            }
        });

        new Images(){
            @Override public void onPostExecute(Bitmap result){
                icon.setImageBitmap(result);
            }
        }.execute(element.getImage());

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
        count.setText(element.getEventsCount());

        return convertView;
    }
}
