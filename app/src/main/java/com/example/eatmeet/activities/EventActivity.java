package com.example.eatmeet.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.entities.Menu;
import com.example.eatmeet.entities.Restaurant;
import com.example.eatmeet.observablearraylist.ObservableArrayList;
import com.example.eatmeet.utils.Images;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        //EventDAO eventDAO = new EventDAOImpl(null);
        //final Event myNewEvent = eventDAO.getEventById(eventId);
        final EventDAO eventDAO = EatMeetApp.getDaoFactory().getEventDAO();
        BackendStatusManager backendStatusManager = new BackendStatusManager();
        backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                Logger.getLogger(EventActivity.this.getClass().getName()).log(Level.INFO, "Connection succeded");
                Event event = (Event) response;

                TextView title = (TextView) findViewById(R.id.titleEvent);//textViewEvent
                if (title != null) {
                    title.setText(event.getTitle());
                }

                System.out.println("SCHEDULE LISTENER WORKING");
                TextView scheduleView = (TextView) findViewById(R.id.scheduleEvent);
                Date scheduleDate = event.getSchedule();
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

                System.out.println("restaurant LISTENER WORKING");
                TextView address = (TextView) findViewById(R.id.address);
                if (address != null) {
                    String newValue = event.getRestaurant().getStreet().toString();
                    newValue += " - "+ event.getRestaurant().getZipCode().toString();
                    newValue += " "+ event.getRestaurant().getCity().toString();
                    newValue += " ("+ event.getRestaurant().getProvince().toString()+")";
                    address.setText(newValue);
                }
                TextView restaurant = (TextView) findViewById(R.id.restaurant);
                if (restaurant != null) {
                    String newValue = event.getRestaurant().getName().toString();
                    restaurant.setText(newValue);
                }

                System.out.println("participants LISTENER WORKING");
                TextView participants = (TextView) findViewById(R.id.participants_count);
                if (participants != null) {
                    participants.setText("Partecipanti: " + event.getParticipantsCount().toString());
                }

                TextView actual_price = (TextView) findViewById(R.id.price);
                if (actual_price != null) {
                    actual_price.setText("Prezzo: " + event.getActualPrice().toString() + "€");
                }

                TextView menu = (TextView) findViewById(R.id.menu);
                if (menu != null) {
                    menu.setText(event.getMenu().getTextMenu().toString());
                }

                final ImageView eventImage = (ImageView) findViewById(R.id.eventImage);
                String tmpFileName = event.getPhotos().get(0).getImage().substring(event.getPhotos().get(0).getImage().lastIndexOf("/")+1);

                final File file = new File(getCacheDir(), tmpFileName);
                if(!file.exists()) {
                    System.out.println("Cache non esiste");
                    BackendStatusManager imageStatusManager = new BackendStatusManager();
                    imageStatusManager.setBackendStatusListener(new BackendStatusListener() {
                        @Override
                        public void onSuccess(Object response, Integer code) {
                            System.out.println("Immagine scaricata");
                            eventImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                        }

                        @Override
                        public void onFailure(Object response, Integer code) {
                            System.out.println("Immagine NON scaricata");
                        }
                    });
                    eventDAO.getImage(event.getPhotos().get(0).getImage(), imageStatusManager, getCacheDir());
                }
                else
                {
                    System.out.println("Cache esiste");
                    if(!file.isDirectory()) {
                        eventImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    }
                }
                /*
                new Images(){
                    @Override public void onPostExecute(Bitmap result){
                        eventImage.setImageBitmap(result);
                    }
                }.execute((String) event.getUrlImage());
                */
            }
            @Override
            public void onFailure(Object response, Integer code) {
                Logger.getLogger(EventActivity.this.getClass().getName()).log(Level.SEVERE, "Connection NOT succeded");
            }
        });
        eventDAO.getEvent(eventId,backendStatusManager);

        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*add back button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        /*
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
                        System.out.println("SCHEDULE LISTENER WORKING");
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
                        System.out.println("restaurant LISTENER WORKING");
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
                    case "participants_count":
                        System.out.println("participants LISTENER WORKING");
                        TextView participants = (TextView) findViewById(R.id.participants_count);
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
        */
    }


    /*add action for back button*/
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
