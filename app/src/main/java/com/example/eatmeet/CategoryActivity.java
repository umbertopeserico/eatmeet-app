package com.example.eatmeet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eatmeet.dao.CategoryDAOImpl;
import com.example.eatmeet.entities.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sofia on 13/06/2016.
 */
public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        ArrayAdapter<String> categoryAdapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            ArrayList<Category> categories;
            CategoryDAOImpl categoryDAOImpl = new CategoryDAOImpl();
            categories = (ArrayList<Category>) categoryDAOImpl.getCategories();

            ArrayList<String> categoriesName = new ArrayList<>();
            for (int i = 0; i < categories.size(); i++){
                categoriesName.add(categories.get(i).getName());
            }

            categoryAdapter =
                    new ArrayAdapter<String>(
                            getActivity(), // The current context (this activity)
                            R.layout.list_item_category, // The name of the layout ID.
                            R.id.list_item_category_textview, // The ID of the textview to populate.
                            categoriesName);

            View rootView = inflater.inflate(R.layout.categories_fragment_main, container, false);

            // Get a reference to the ListView, and attach this adapter to it.
            ListView listView = (ListView) rootView.findViewById(R.id.listview_categories);
            listView.setAdapter(categoryAdapter);

            //Connection c = new Connection();
            new Connection(){
                @Override public void onPostExecute(String result)
                {
                    Log.d("My tag 2",result);
                    try {
                        JSONObject obj = new JSONObject(result);
                        JSONArray arr = obj.getJSONArray("categories");
                        for(int i = 0; i < arr.length(); i++) {
                            String name = arr.getJSONObject(i).getString("name");
                            int id = arr.getJSONObject(i).getInt("id");
                            System.out.println(name + " " + id);
                            Category newCategory = new Category();
                            newCategory.setId(id);
                            newCategory.setName(name);
                            //categoryAdapter.clear();
                            categoryAdapter.add(newCategory.getName());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute("http://eatmeet.herokuapp.com/api/categories");
            //c.execute("http://eatmeet.herokuapp.com/api/categories");

            return rootView;
        }
    }
}
