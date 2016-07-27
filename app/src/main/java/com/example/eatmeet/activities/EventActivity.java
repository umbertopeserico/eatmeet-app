package com.example.eatmeet.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eatmeet.R;
import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.dao.implementations.oldrest.EventDAOImpl;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.entities.Menu;
import com.example.eatmeet.entities.Restaurant;
import com.example.eatmeet.utils.Images;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        int eventId = 1;
        if(extras!=null) {
            eventId = extras.getInt("id");
        }
        final int newEventId = eventId;

        EventDAO eventDAO = new EventDAOImpl(null);
        final Event myNewEvent = eventDAO.getEventById(eventId);

        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button bookButton = (Button) findViewById(R.id.book);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventActivity.this, ConfirmActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id", newEventId);
                startActivity(intent);
            }
        });

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
                        TextView scheduleView = (TextView) findViewById(R.id.scheduleEvent);
                        Date scheduleDate = (Date) event.getNewValue();
                        String scheduleString="il ";
                        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
                        calendar.setTime(scheduleDate);   // assigns calendar to given date
                        scheduleString+=calendar.get(Calendar.DAY_OF_MONTH) + "/";
                        scheduleString+=calendar.get(Calendar.MONTH) + "/";
                        scheduleString+=calendar.get(Calendar.YEAR) + " alle ";
                        int hrs = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
                        int mnts = calendar.get(Calendar.MINUTE); // gets hour in 24h format
                        String hrsMnts = String.format("%02d:%02d", hrs, mnts);
                        scheduleString += hrsMnts;
                        if (scheduleView != null) {
                            scheduleView.setText(scheduleString);
                        }
                        break;
                    case "restaurant":
                        TextView address = (TextView) findViewById(R.id.address);
                        if (address != null) {
                            String newValue = ((Restaurant) event.getNewValue()).getStreet().toString();
                            newValue += " - "+((Restaurant) event.getNewValue()).getZipCode().toString();
                            newValue += " "+((Restaurant) event.getNewValue()).getCity().toString();
                            newValue += " ("+((Restaurant) event.getNewValue()).getProvince().toString()+")";
                            address.setText(newValue);
                        }
                        TextView restaurant = (TextView) findViewById(R.id.restaurant);
                        if (restaurant != null) {
                            String newValue = ((Restaurant) event.getNewValue()).getName().toString();
                            restaurant.setText(newValue);
                        }
                        break;
                    case "participants":
                        TextView participants = (TextView) findViewById(R.id.participants);
                        if (participants != null) {
                            participants.setText("Partecipanti: " + event.getNewValue().toString());
                        }
                        break;
                    case "actual_price":
                        TextView actual_price = (TextView) findViewById(R.id.price);
                        if (actual_price != null) {
                            actual_price.setText("Prezzo: " + event.getNewValue().toString() + "€");
                        }
                        break;
                    case "menu":
                        TextView menu = (TextView) findViewById(R.id.menu);
                        if (menu != null) {
                            menu.setText(((Menu) event.getNewValue()).getTextMenu().toString());
                        }
                        break;
                    case "urlImage":
                        final ImageView image = (ImageView) findViewById(R.id.eventImage);
                        new Images(){
                            @Override public void onPostExecute(Bitmap result){
                                image.setImageBitmap(result);
                            }
                        }.execute((String) event.getNewValue());
                    default:

                }

            }
        });
    }

}
