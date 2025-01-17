package com.example.eatmeet.activities.usereventsactivityfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.adapters.EventsAdapter;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.UserDAO;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.observablearraylist.ObservableArrayList;
import com.example.eatmeet.observablearraylist.OnAddListener;
import com.example.eatmeet.utils.Visibility;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PastEventsFragment extends Fragment {

    private ListView eventsListView;
    private ArrayAdapter arrayAdapter;
    private ProgressBar loadingBar;
    private TextView messagesLabel;
    private ObservableArrayList<Event> eventsList;

    public PastEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_events, container, false);

        eventsListView = (ListView) view.findViewById(R.id.eventsListView);
        eventsList = new ObservableArrayList<>();
        arrayAdapter = new EventsAdapter(getActivity(),R.layout.list_item_event, eventsList);
        eventsListView.setAdapter(arrayAdapter);
        loadingBar = (ProgressBar) view.findViewById(R.id.loadingBar);
        messagesLabel = (TextView) view.findViewById(R.id.messagesLabel);

        UserDAO userDAO = EatMeetApp.getDaoFactory().getUserDAO();
        BackendStatusManager eventsBSM = new BackendStatusManager();
        eventsBSM.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                Visibility.makeInvisible(loadingBar);
                if(eventsList.size() == 0) {
                    messagesLabel.setText(R.string.my_events_no_past_events);
                    Visibility.makeVisible(messagesLabel);
                } else {
                    Visibility.makeInvisible(messagesLabel);
                }
                Log.i("USER PAST EVENTS: ", ""+ ((List<Event>) response).size());
            }

            @Override
            public void onFailure(Object response, Integer code) {
                Visibility.makeInvisible(loadingBar);
                Log.e("USER PAST EVENTS: ", "Errore");
            }
        });
        eventsList.setOnAddListener(new OnAddListener() {
            @Override
            public void onAddEvent(Object item) {
                arrayAdapter.notifyDataSetChanged();
            }
        });

        Visibility.makeVisible(loadingBar);
        userDAO.getPastEvents(eventsList,eventsBSM);

        return view;
    }

}
