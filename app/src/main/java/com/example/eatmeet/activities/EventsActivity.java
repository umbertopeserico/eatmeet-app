package com.example.eatmeet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eatmeet.R;
import com.example.eatmeet.dao.EventDAO;
import com.example.eatmeet.dao.EventDAOImpl;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.adapters.EventsAdapter;
import com.example.eatmeet.utils.Notificable;

import java.util.List;

public class EventsActivity extends AppCompatActivity implements Notificable {

    private ArrayAdapter<String> eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        Intent intent = getIntent();

        EventDAO eventDao = new EventDAOImpl(this);
        List<Event> eventList = eventDao.getEvents();

        eventAdapter = new EventsAdapter(this, R.layout.list_item_event, eventList);

        ListView listView = (ListView) findViewById(R.id.listview_events);
        listView.setAdapter(eventAdapter);
    }

    @Override
    public void sendNotify() {
        eventAdapter.notifyDataSetChanged();
    }
}
