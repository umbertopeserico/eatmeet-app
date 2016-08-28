package com.example.eatmeet.activities.mainactivityfragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.activities.FiltersActivity;
import com.example.eatmeet.activities.OrderActivity;
import com.example.eatmeet.adapters.EventsAdapter;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.observablearraylist.ObservableArrayList;
import com.example.eatmeet.observablearraylist.OnAddListener;
import com.example.eatmeet.utils.Refreshable;
import com.example.eatmeet.utils.Visibility;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment implements Refreshable {

    private View view;
    private EventDAO eventDAO;
    private JSONObject parameters;
    private final ObservableArrayList<Event> eventsList;
    private ListView eventsListView;
    private EventsAdapter eventsAdapter;
    private ProgressBar loadingBar;
    private LinearLayout loadingBarContainer;
    private TextView messagesLabel;
    private LinearLayout filterStatusLayout;
    private CardView filterStatusCardView;
    private TextView filtersEnabledText;

    private Button filterButton;
    private Button orderButton;

    public EventsFragment() {
        eventDAO = EatMeetApp.getDaoFactory().getEventDAO();
        eventsList = new ObservableArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events, container, false);
        initViewElements();
        setActions();
        loadData();
        return view;
    }

    @Override
    public void refresh() {
        if(isAdded()) {
            loadData();
        }
    }

    // Funzioni di logica interna
    private void loadData() {
        if(EatMeetApp.getFiltersManager().isEnabledFilters()) {
            parameters = EatMeetApp.getFiltersManager().buildJson();
        } else {
            parameters = new JSONObject();
        }
        eventsList.clear();
        BackendStatusManager eventsBSM = new BackendStatusManager();
        eventsBSM.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                Visibility.makeInvisible(loadingBar);
                Visibility.makeInvisible(loadingBarContainer);

                if(eventsList.size()==0) {
                    Log.i("EVENTS LOAD", "NO EVENTS FOUND");
                    Visibility.makeVisible(loadingBarContainer);
                    Visibility.makeVisible(messagesLabel);
                    messagesLabel.setText(R.string.events_no_event);
                }

                if((EatMeetApp.getFiltersManager().isEnabledFilters() || (EatMeetApp.getFiltersManager().isEnabledOrder()))) {
                    Visibility.makeVisible(filterStatusLayout);
                    filtersEnabledText.setText(EatMeetApp.getFiltersManager().toString());
                }

                EatMeetApp.getFiltersManager().setEnabledFilters(false);
                EatMeetApp.getFiltersManager().setEnabledOrder(true);
            }

            @Override
            public void onFailure(Object response, Integer code) {
                if (getActivity() != null && isAdded()) {
                    Visibility.makeInvisible(loadingBar);
                    Visibility.makeVisible(loadingBarContainer);
                    Visibility.makeVisible(messagesLabel);
                    if(response==null) {
                        messagesLabel.setText(getString(R.string.network_error));
                        Toast.makeText(EventsFragment.this.getActivity(), getString(R.string.network_error), Toast.LENGTH_LONG).show();
                    }
                }
                EatMeetApp.getFiltersManager().setEnabledFilters(false);
                EatMeetApp.getFiltersManager().setEnabledOrder(true);
            }
        });
        eventsList.setOnAddListener(new OnAddListener() {
            @Override
            public void onAddEvent(Object item) {
                eventsAdapter.notifyDataSetChanged();
            }
        });

        Visibility.makeVisible(loadingBar);
        Visibility.makeVisible(loadingBarContainer);
        Visibility.makeInvisible(messagesLabel);
        Visibility.makeVisible(filterStatusLayout);
        eventDAO.getEvents(eventsList, eventsBSM, parameters);
    }

    private void initViewElements() {
        eventsListView = (ListView) view.findViewById(R.id.eventsListView);
        eventsAdapter = new EventsAdapter(this.getActivity(), R.layout.list_item_event, eventsList);
        eventsListView.setAdapter(eventsAdapter);
        loadingBar = (ProgressBar) view.findViewById(R.id.loadingBar);
        loadingBarContainer = (LinearLayout) view.findViewById(R.id.loadingBarContainer);
        messagesLabel = (TextView) view.findViewById(R.id.messagesLabel);
        filterStatusLayout = (LinearLayout) view.findViewById(R.id.filterStatusLayout);
        filterStatusCardView = (CardView) view.findViewById(R.id.filterStatusCardView);
        filterButton = (Button) view.findViewById(R.id.filterButton);
        orderButton = (Button) view.findViewById(R.id.orderButton);
        filtersEnabledText = (TextView) view.findViewById(R.id.filtersEnabledText);
    }

    private void setActions() {
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventsFragment.this.getActivity(), FiltersActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventsFragment.this.getActivity(), OrderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
