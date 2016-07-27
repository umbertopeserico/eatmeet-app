package com.example.eatmeet.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.adapters.CategoriesAdapter;
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

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Elementi comuni a tutti i test
        BackendStatusManager backendStatusManager = new BackendStatusManager();
        listView = (ListView) findViewById(R.id.listView);

        backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(String response, Integer code) {
                Logger.getLogger(TestActivity.this.getClass().getName()).log(Level.INFO, "Connection succeded");
            }

            @Override
            public void onFailure(String response, Integer code) {
                Logger.getLogger(TestActivity.this.getClass().getName()).log(Level.SEVERE, "Connection NOT succeded");
            }
        });

        // Events Test
        EventDAO eventDAO = EatMeetApp.getDaoFactory().getEventDAO();
        ObservableArrayList<Event> eventsList = new ObservableArrayList<>();
        arrayAdapter = new EventsAdapterTest(this, R.layout.list_item_event, eventsList);
        listView.setAdapter(arrayAdapter);

        eventsList.setOnAddListener(new OnAddListener() {
            @Override
            public void onAddEvent(Object item) {
                arrayAdapter.notifyDataSetChanged();
            }
        });

        JSONObject params = new JSONObject();
        JSONObject filters = new JSONObject();
        JSONArray categories = new JSONArray();
        categories.put(1);
        try {
            filters.put("categories", categories);
            params.put("filters", filters);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        eventDAO.getEvents(eventsList, backendStatusManager, params);
        // Categories Test
        /*CategoryDAO categoryDAO = EatMeetApp.getDaoFactory().getCategoryDAO();
        ObservableArrayList<Category> categoryList = new ObservableArrayList<>();
        BackendStatusManager backendStatusManager = new BackendStatusManager();
        categoryListView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new CategoriesAdapterTest(this, R.layout.list_item_category, (List<Category>) categoryList);
        categoryListView.setAdapter(arrayAdapter);

        categoryList.setOnAddListener(new OnAddListener() {
            @Override
            public void onAddEvent(Object item) {
                arrayAdapter.notifyDataSetChanged();
            }
        });

        categoryDAO.getCategories(categoryList, backendStatusManager);*/
    }

}
