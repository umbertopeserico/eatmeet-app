package com.example.eatmeet.activitiestest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.adapters.CategoriesAdapterTest;
import com.example.eatmeet.adapters.EventsAdapterTest;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.CategoryDAO;
import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.observablearraylist.ObservableArrayList;
import com.example.eatmeet.observablearraylist.OnAddListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoriesTestActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CategoryDAO categoryDAO = EatMeetApp.getDaoFactory().getCategoryDAO();
        ObservableArrayList<Category> categoryList = new ObservableArrayList<>();
        BackendStatusManager backendStatusManager = new BackendStatusManager();
        listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new CategoriesAdapterTest(this, R.layout.list_item_category, categoryList);
        listView.setAdapter(arrayAdapter);

        backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                Logger.getLogger(CategoriesTestActivity.this.getClass().getName()).log(Level.INFO, "Connection succeded");
            }

            @Override
            public void onFailure(Object response, Integer code) {
                Logger.getLogger(CategoriesTestActivity.this.getClass().getName()).log(Level.SEVERE, "Connection NOT succeded");
            }
        });

        categoryList.setOnAddListener(new OnAddListener() {
            @Override
            public void onAddEvent(Object item) {
                arrayAdapter.notifyDataSetChanged();
            }
        });

        categoryDAO.getCategories(categoryList, backendStatusManager);
    }

}
