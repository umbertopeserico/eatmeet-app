package com.example.eatmeet.activitiestest;

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
import com.example.eatmeet.adapters.EventsAdapterTest;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.dao.interfaces.UserDAO;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.observablearraylist.ObservableArrayList;
import com.example.eatmeet.observablearraylist.OnAddListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EventsTestActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BackendStatusManager backendStatusManager = new BackendStatusManager();

        EventDAO eventDAO = EatMeetApp.getDaoFactory().getEventDAO();
        ObservableArrayList<Event> eventsList = new ObservableArrayList<>();
        listView = (ListView) findViewById(R.id.listView);

        backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                Logger.getLogger(EventsTestActivity.this.getClass().getName()).log(Level.INFO, "Connection succeded");
            }

            @Override
            public void onFailure(Object response, Integer code) {
                Logger.getLogger(EventsTestActivity.this.getClass().getName()).log(Level.SEVERE, "Connection NOT succeded");
            }
        });

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
    }

}
