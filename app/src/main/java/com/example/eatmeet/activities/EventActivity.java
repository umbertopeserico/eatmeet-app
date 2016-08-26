package com.example.eatmeet.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appyvet.rangebar.RangeBar;
import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.utils.Visibility;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventActivity extends AppCompatActivity {

    private TextView title;//textViewEvent
    private TextView scheduleView;
    private TextView address;
    private TextView restaurant;
    private TextView participants;
    private TextView actual_price;
    private TextView menu;
    private Button bookButton;
    private Button unbookButton;
    private Toolbar toolbar;
    private ImageView eventImage;//final ImageView image;

    private int newEventId;
    private int eventId;

    private ProgressBar loadingBar;
    private LinearLayout loadingBarContainer;
    private TextView messagesLabel;

    RelativeLayout contentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        eventId = 1;
        if(extras!=null) {
            eventId = extras.getInt("id");
            eventId = extras.getInt("id");
        }
        newEventId = eventId;

        setContentView(R.layout.activity_event);

        initViewElements();
        setActions();
        loadData();

    }

    private void setActions() {
        setSupportActionBar(toolbar);

        /*add back button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventActivity.this, ConfirmActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id", newEventId);
                startActivity(intent);
            }
        });

        unbookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventActivity.this, ConfirmActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id", newEventId);
                startActivity(intent);
            }
        });
    }

    private void loadData(){
        //EventDAO eventDAO = new EventDAOImpl(null);
        //final Event myNewEvent = eventDAO.getEventById(eventId);
        final EventDAO eventDAO = EatMeetApp.getDaoFactory().getEventDAO();
        final BackendStatusManager backendStatusManager = new BackendStatusManager();
        backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                Logger.getLogger(EventActivity.this.getClass().getName()).log(Level.INFO, "Connection succeded");
                Event event = (Event) response;

                System.out.println("PARTECIPANTI:" + event.getParticipants());
                if(EatMeetApp.getCurrentUser() == null) {
                    Visibility.makeInvisible(unbookButton);
                    Visibility.makeVisible(bookButton);
                } else if(event.getParticipants().contains(EatMeetApp.getCurrentUser())) {
                    Visibility.makeVisible(unbookButton);
                    Visibility.makeInvisible(bookButton);
                } else {
                    Visibility.makeVisible(bookButton);
                    Visibility.makeInvisible(unbookButton);
                }

                if(event.getSchedule().before(Calendar.getInstance().getTime())) {
                    Visibility.makeInvisible(unbookButton);
                    Visibility.makeInvisible(bookButton);
                }

                if (title != null) {
                    title.setText(event.getTitle());
                    Visibility.makeVisible(title);
                }

                System.out.println("SCHEDULE LISTENER WORKING");
                Date scheduleDate = event.getSchedule();
                String scheduleString="il ";
                Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
                calendar.setTime(scheduleDate);   // assigns calendar to given date
                scheduleString+=(calendar.get(Calendar.MONTH) + 1) + "/";
                scheduleString+=calendar.get(Calendar.MONTH) + "/";
                scheduleString+=calendar.get(Calendar.YEAR) + " alle ";
                int hrs = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
                int mnts = calendar.get(Calendar.MINUTE); // gets hour in 24h format
                String hrsMnts = String.format("%02d:%02d", hrs, mnts);
                scheduleString += hrsMnts;
                if (scheduleView != null) {
                    scheduleView.setText(scheduleString);
                    Visibility.makeVisible(scheduleView);
                }

                System.out.println("restaurant LISTENER WORKING");
                if (address != null) {
                    String newValue = event.getRestaurant().getStreet().toString();
                    newValue += " - "+ event.getRestaurant().getZipCode().toString();
                    newValue += " "+ event.getRestaurant().getCity().toString();
                    newValue += " ("+ event.getRestaurant().getProvince().toString()+")";
                    address.setText(newValue);
                    Visibility.makeVisible(address);
                }
                if (restaurant != null) {
                    String newValue = event.getRestaurant().getName().toString();
                    restaurant.setText(newValue);
                    Visibility.makeVisible(restaurant);
                }

                System.out.println("participants LISTENER WORKING");
                if (participants != null) {
                    participants.setText("Partecipanti: " + event.getParticipantsCount().toString());
                    Visibility.makeVisible(participants);
                }

                if (actual_price != null) {
                    actual_price.setText("Prezzo: " + event.getActualPrice().toString() + "€");
                    Visibility.makeVisible(actual_price);

                    final RangeBar pricesBar = (RangeBar) findViewById(R.id.pricesBar);
                    pricesBar.setEnabled(false);
                    pricesBar.setClickable(false);
                    float maxPrice = event.getMaxPrice().floatValue();
                    float minPrice = event.getMinPrice().floatValue();
                    final TextView minPeoplePrice = (TextView) findViewById(R.id.minPeoplePrice);
                    minPeoplePrice.setText("MIN: " + Float.toString(minPrice) + "€ " + event.getMinPeople() + " persone");
                    final TextView maxPeoplePrice = (TextView) findViewById(R.id.maxPeoplePrice);
                    maxPeoplePrice.setText("MAX: " + Float.toString(maxPrice) + "€ " + event.getMaxPeople() + " persone");
                    float actualPrice = event.getActualPrice().floatValue();
                    final int actualPeople = event.getParticipantsCount();
                    final TextView priceRepresentationSummary = (TextView) findViewById(R.id.priceRepresentationSummary);
                    final TextView priceRepresentationChangingSummary = (TextView) findViewById(R.id.priceRepresentationChangingSummary);
                    priceRepresentationSummary.setText("Attualmente: " + actualPrice + "€ per " + actualPeople + " persone" );
                    priceRepresentationChangingSummary.setText("Prezzo per " + actualPeople + " persone: circa " + actualPrice + "€" );
                    Visibility.makeInvisible(priceRepresentationChangingSummary);
                    pricesBar.setTickInterval((maxPrice-minPrice)/6);
                    pricesBar.setTickStart(minPrice);
                    pricesBar.setTickEnd(maxPrice);
                    pricesBar.setRangePinsByValue(minPrice, actualPrice);//funziona!
                    pricesBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
                        @Override
                        public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                            long newActualPeople = Math.round(Math.random()*100);//TODO: ask server for new people count instead of random
                            String estimate = "Prezzo per " + newActualPeople + " persone: circa" + rightPinValue + "€ a testa";
                            if(newActualPeople > actualPeople) {
                                estimate = "Se prenoti per " + (newActualPeople-actualPeople) + " costerà circa " + rightPinValue + "€ a testa";
                                Toast.makeText(EventActivity.this, estimate, Toast.LENGTH_SHORT).show();
                            }
                            priceRepresentationChangingSummary.setText(estimate);
                        }
                    });
                }

                if (menu != null) {
                    menu.setText(event.getMenu().getTextMenu().toString());
                    Visibility.makeVisible(menu);
                }

                if(eventImage != null){
                    String tmpFileName = "event-activity_" + event.getPhotos().get(0).getImage().substring(event.getPhotos().get(0).getImage().lastIndexOf("/")+1);

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
                        eventDAO.getImage(event.getPhotos().get(0).getImage(), tmpFileName, imageStatusManager, getCacheDir());
                    }
                    else
                    {
                        System.out.println("Cache esiste");
                        if(!file.isDirectory()) {
                            eventImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                        }
                    }
                    Visibility.makeVisible(eventImage);
                }
                Visibility.makeInvisible(loadingBar);
                Visibility.makeInvisible(loadingBarContainer);
                Visibility.makeVisible(contentEvent);
            }
            @Override
            public void onFailure(Object response, Integer code) {
                Visibility.makeInvisible(loadingBar);
                Visibility.makeVisible(loadingBarContainer);
                Visibility.makeVisible(messagesLabel);
                messagesLabel.setText(getString(R.string.network_error));
                Toast.makeText(EventActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                Logger.getLogger(EventActivity.this.getClass().getName()).log(Level.SEVERE, "Connection NOT succeded");
            }
        });
        eventDAO.getEvent(eventId,backendStatusManager);
    }

    private void initViewElements() {
        bookButton = (Button) findViewById(R.id.book);
        unbookButton = (Button) findViewById(R.id.unbook);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        eventImage = (ImageView) findViewById(R.id.eventImage);
        title = (TextView) findViewById(R.id.titleEvent);//textViewEvent
        scheduleView = (TextView) findViewById(R.id.scheduleEvent);
        address = (TextView) findViewById(R.id.address);
        restaurant = (TextView) findViewById(R.id.restaurant);
        participants = (TextView) findViewById(R.id.participants_count);
        actual_price = (TextView) findViewById(R.id.price);
        menu = (TextView) findViewById(R.id.menu);

        loadingBar = (ProgressBar) findViewById(R.id.loadingBar);
        loadingBarContainer = (LinearLayout) findViewById(R.id.loadingBarContainer);
        messagesLabel = (TextView) findViewById(R.id.messagesLabel);

        contentEvent = (RelativeLayout) findViewById(R.id.content_event);

        Visibility.makeInvisible(eventImage);
        Visibility.makeInvisible(title);
        Visibility.makeInvisible(scheduleView);
        Visibility.makeInvisible(address);
        Visibility.makeInvisible(restaurant);
        Visibility.makeInvisible(participants);
        Visibility.makeInvisible(actual_price);
        Visibility.makeInvisible(menu);

        Visibility.makeInvisible(contentEvent);

        Visibility.makeVisible(loadingBar);
        Visibility.makeVisible(loadingBarContainer);
        Visibility.makeInvisible(messagesLabel);

    }

    /*add action for back button*/
    @Override
    public boolean onSupportNavigateUp(){
        /*
        finish();
        */
        Intent intent = new Intent(EventActivity.this, MainActivity.class);
        intent.putExtra("destination", "1");
        startActivity(intent);

        return true;
    }
}
