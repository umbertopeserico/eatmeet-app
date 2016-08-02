package com.example.eatmeet.activitiestest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.dao.interfaces.UserDAO;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.entities.User;

import org.w3c.dom.Text;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EventParticipationTestActivity extends AppCompatActivity {

    private Button bookButton;
    private Button unbookButton;
    private TextView eventTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_participation_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        UserDAO userDAO = EatMeetApp.getDaoFactory().getUserDAO();
        User user = new User();
        user.setEmail("umberto2@peserico.me");
        user.setPassword("ciao1234");
        userDAO.signIn(user, new BackendStatusManager());

        bookButton = (Button) findViewById(R.id.bookButton);
        unbookButton = (Button) findViewById(R.id.unbookButton);
        eventTitle = (TextView) findViewById(R.id.textView);

        EventDAO eventDAO = EatMeetApp.getDaoFactory().getEventDAO();
        final BackendStatusManager eventBSM = new BackendStatusManager();
        eventBSM.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                final Event currentEvent = (Event) response;
                eventTitle.setText(currentEvent.getTitle());

                bookButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EventDAO eventDAO = EatMeetApp.getDaoFactory().getEventDAO();
                        BackendStatusManager eventParticipationBSM = new BackendStatusManager();
                        eventBSM.setBackendStatusListener(new BackendStatusListener() {
                            @Override
                            public void onSuccess(Object response, Integer code) {
                                CharSequence message = "Prenotazione effettuata correttamente";
                                Toast.makeText(EventParticipationTestActivity.this, message, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Object response, Integer code) {
                                CharSequence message = "Errore nella prenotazione. Si prega di riprovare";
                                Toast.makeText(EventParticipationTestActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });

                        eventDAO.addParticipation(currentEvent, 18, eventParticipationBSM);
                    }
                });

                unbookButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("ON BUTT SUCC","Cliked me");
                        EventDAO eventDAO = EatMeetApp.getDaoFactory().getEventDAO();
                        BackendStatusManager unbookBSM = new BackendStatusManager();
                        Event event = new Event();
                        event.setId(1);
                        eventDAO.removeParticipation(event, unbookBSM);
                    }
                });
            }

            @Override
            public void onFailure(Object response, Integer code) {
                CharSequence message = "Impossibile recuperare l'evento";
                Toast.makeText(EventParticipationTestActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        eventDAO.getEvent(1, eventBSM);

    }

}
