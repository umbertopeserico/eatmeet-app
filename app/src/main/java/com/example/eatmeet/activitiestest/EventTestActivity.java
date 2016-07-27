package com.example.eatmeet.activitiestest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.entities.Menu;
import com.example.eatmeet.entities.Restaurant;
import com.example.eatmeet.utils.Images;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PropertyChangeListener listener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                switch (event.getPropertyName()) {
                    case "title":
                        break;
                    case "schedule":
                        break;
                    case "maxPeople":
                        break;
                    case "minPeople":
                        break;
                    case "maxPrice":
                        break;
                    case "minPrice":
                        break;
                    case "pricesArray":
                        break;
                    case "menu":
                        break;
                    case "restaurant":
                        break;
                    case "participants":
                        break;
                    case "actualPrice":
                        break;
                    case "photos":
                        break;
                    default:
                }
                Logger.getLogger("FIRE!!").log(Level.WARNING, "Fired: "+event.getPropertyName());
            }
        };

        EventDAO eventDAO = EatMeetApp.getDaoFactory().getEventDAO();
        BackendStatusManager backendStatusManager = new BackendStatusManager();
        backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(String response, Integer code) {
                Logger.getLogger(EventTestActivity.this.getClass().getName()).log(Level.INFO,  ""+code+": "+response);
            }

            @Override
            public void onFailure(String response, Integer code) {
                Logger.getLogger(EventTestActivity.this.getClass().getName()).log(Level.INFO,  ""+code+": "+response);
            }
        });
        eventDAO.getEvent(listener, 1, backendStatusManager);

    }

}
