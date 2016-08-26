package com.example.eatmeet.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.activitiestest.EventParticipationTestActivity;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.utils.Visibility;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfirmActivity extends AppCompatActivity {

    private Event currentEvent;
    private int eventId = 1;
    private int bookedPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*add back button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            eventId = extras.getInt("id");
            if(extras.getString("bookedPeople")!=null){
                bookedPeople = extras.getInt("bookedPeople");
            }
        }

        Button homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);
                intent.putExtra("destination", "1");
                startActivity(intent);
            }
        });

        final EventDAO eventDAO = EatMeetApp.getDaoFactory().getEventDAO();
        Visibility.makeVisible(findViewById(R.id.loadingBar));
        Visibility.makeVisible(findViewById(R.id.loadingBarContainer));
        Visibility.makeInvisible(findViewById(R.id.content_confirm));
        final BackendStatusManager backendStatusManager = new BackendStatusManager();
        backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                currentEvent = (Event) response;
                Visibility.makeInvisible(findViewById(R.id.loadingBar));
                Visibility.makeInvisible(findViewById(R.id.loadingBarContainer));
                Visibility.makeVisible(findViewById(R.id.content_confirm));
                final Spinner spinnerPeople = (Spinner) findViewById(R.id.spinnerPeople);// Array of choices
                List<Integer> numberPeopleList = new ArrayList<>();
                for(int i = 1; i <= (currentEvent.getMaxPeople() - currentEvent.getParticipantsCount()); i++){
                    numberPeopleList.add(i);
                }
                ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, numberPeopleList);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPeople.setAdapter(spinnerArrayAdapter);
                spinnerPeople.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        changePeopleNumber(position+1);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
            @Override
            public void onFailure(Object response, Integer code) {
                Visibility.makeInvisible(findViewById(R.id.loadingBar));
                Visibility.makeVisible(findViewById(R.id.loadingBarContainer));
                Visibility.makeVisible(findViewById(R.id.messagesLabel));
                Visibility.makeInvisible(findViewById(R.id.content_confirm));
                if(response==null) {
                    ((TextView) findViewById(R.id.messagesLabel)).setText(getString(R.string.network_error));
                    Toast.makeText(ConfirmActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
        eventDAO.getEvent(eventId,backendStatusManager);

    final Button bookButton = (Button) findViewById(R.id.bookButton);
        if (EatMeetApp.getCurrentUser() != null){
            assert bookButton!=null;
            bookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Spinner spinnerPeople = (Spinner) findViewById(R.id.spinnerPeople);
                    findViewById(R.id.spinnerPeople).setEnabled(false);
                    bookButton.setBackgroundColor(Color.parseColor("#cccccc"));
                    bookButton.setEnabled(false);

                    EventDAO eventDAO = EatMeetApp.getDaoFactory().getEventDAO();
                    BackendStatusManager eventParticipationBSM = new BackendStatusManager();
                    eventParticipationBSM.setBackendStatusListener(new BackendStatusListener() {
                        @Override
                        public void onSuccess(Object response, Integer code) {

                            Context context = getApplicationContext();
                            Integer bookedPeople = (Integer) spinnerPeople.getSelectedItem();
                            CharSequence text = "Hai prenotato per " + bookedPeople + " persone";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            TextView bookedFeedback = (TextView) findViewById(R.id.bookedFeedback);
                            bookedFeedback.setText(text);
                            Visibility.makeVisible(bookedFeedback);
                            Visibility.makeVisible(findViewById(R.id.homeButton));
                            toast.show();

                            CharSequence message = "Prenotazione effettuata correttamente";
                            Toast.makeText(ConfirmActivity.this, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Object response, Integer code) {
                            findViewById(R.id.spinnerPeople).setEnabled(true);
                            bookButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            bookButton.setEnabled(true);
                            CharSequence message = "Errore nella prenotazione. Si prega di riprovare";
                            Toast.makeText(ConfirmActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                    eventDAO.addParticipation(currentEvent, bookedPeople, eventParticipationBSM);
                }
            });
        } else {
            bookButton.setText("ACCEDI PER PRENOTARE");
            bookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Spinner spinnerPeople = (Spinner) findViewById(R.id.spinnerPeople);
                    Integer bookedPeople = (Integer) spinnerPeople.getSelectedItem();
                    Intent intent = new Intent(ConfirmActivity.this, SignInActivity.class);
                    intent.putExtra("from", "ConfirmActivity");
                    intent.putExtra("eventId", eventId);
                    intent.putExtra("bookedPeople", bookedPeople);
                    startActivity(intent);
                }
            });
        }
    }

    private void changePeopleNumber(int peopleNumber){
        Integer newPosition = (currentEvent.getParticipantsCount()-1)+peopleNumber;
        Double newPrice = currentEvent.getPricesArray().get(newPosition);
        String estimate = newPrice + "€ a persona se prenoti per " + peopleNumber + " persone.";
        TextView dynamicPrice = (TextView) findViewById(R.id.dynamicPrice);
        dynamicPrice.setText(estimate);
        Toast.makeText(ConfirmActivity.this, estimate, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp(){
        /*
        finish();
        */
        Intent intent = new Intent(ConfirmActivity.this, EventActivity.class);
        intent.putExtra("id", eventId);
        startActivity(intent);
        return true;
    }
}
