package com.example.eatmeet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.eatmeet.R;
import com.example.eatmeet.dao.CategoryDAO;
import com.example.eatmeet.dao.CategoryDAOImpl;
import com.example.eatmeet.dao.EventDAO;
import com.example.eatmeet.dao.EventDAOImpl;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.utils.Configs;
import com.example.eatmeet.utils.Connection;
import com.example.eatmeet.utils.Notificable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        int eventId = 1;
        /*if(extras!=null) {
            eventId = extras.getString("id");
            TextView eventName = (TextView) findViewById(R.id.textViewEvent);
            eventName.setText(eventId);
        }*/

        EventDAO eventDAO = new EventDAOImpl(null);
        final Event myNewEvent = eventDAO.getEventById(eventId);

        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myNewEvent.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {

                switch (event.getPropertyName()) {
                    case "title":
                        TextView title = (TextView) findViewById(R.id.titleEvent);//textViewEvent
                        if (title != null) {
                            title.setText(event.getNewValue().toString());
                        }
                        break;
                    case "schedule":
                        TextView schedule = (TextView) findViewById(R.id.scheduleEvent);
                        if (schedule != null) {
                            schedule.setText(event.getNewValue().toString());
                        }
                        break;
                    default:

                }

            }
        });
    }

}
