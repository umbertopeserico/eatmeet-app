package com.example.eatmeet.activities.mainactivityfragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import com.example.eatmeet.adapters.EventsAdapter;
import com.example.eatmeet.adapters.EventsAdapterTest;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.observablearraylist.ObservableArrayList;
import com.example.eatmeet.observablearraylist.OnAddListener;

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
    private EventsAdapterTest eventsAdapter;
    private ProgressBar loadingBar;
    private LinearLayout loadingBarContainer;
    private TextView messagesLabel;
    private LinearLayout filterStatusLayout;
    private CardView filterStatusCardView;

    private Button filterButton;
    private Button orderButton;

    public EventsFragment() {
        eventDAO = EatMeetApp.getDaoFactory().getEventDAO();
        eventsList = new ObservableArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events, container, false);
        initViewElements();
        setActions();
        /*
         * Inizializzazione parametri dei filti:
         * Per ora inizializzati ad un elemento JSON vuoto.
         */
        parameters = new JSONObject();
        loadData();
        return view;
    }

    // Funzioni di logica interna
    private void loadData() {
        eventsList.clear();
        BackendStatusManager eventsBSM = new BackendStatusManager();
        eventsBSM.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                loadingBar.setVisibility(View.GONE);
                loadingBarContainer.setVisibility(View.GONE);
                filterStatusLayout.setVisibility(View.GONE);
                // Gestire il caso in cui siano settati filtri: mostrare la cardview se si
            }

            @Override
            public void onFailure(Object response, Integer code) {
                if (getActivity() != null && isAdded()) {
                    loadingBar.setVisibility(View.GONE);
                    loadingBarContainer.setVisibility(View.VISIBLE);
                    messagesLabel.setVisibility(View.VISIBLE);
                    filterStatusCardView.setVisibility(View.GONE);
                    if(response==null) {
                        messagesLabel.setText(getString(R.string.network_error));
                        Toast.makeText(EventsFragment.this.getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        eventsList.setOnAddListener(new OnAddListener() {
            @Override
            public void onAddEvent(Object item) {
                eventsAdapter.notifyDataSetChanged();
            }
        });

        loadingBar.setVisibility(View.VISIBLE);
        loadingBarContainer.setVisibility(View.VISIBLE);
        messagesLabel.setVisibility(View.GONE);
        filterStatusCardView.setVisibility(View.GONE);
        eventDAO.getEvents(eventsList, eventsBSM, parameters);
    }

    private void initViewElements() {
        eventsListView = (ListView) view.findViewById(R.id.eventsListView);
        eventsAdapter = new EventsAdapterTest(this.getActivity(), R.layout.list_item_event, eventsList);
        eventsListView.setAdapter(eventsAdapter);
        loadingBar = (ProgressBar) view.findViewById(R.id.loadingBar);
        loadingBarContainer = (LinearLayout) view.findViewById(R.id.loadingBarContainer);
        messagesLabel = (TextView) view.findViewById(R.id.messagesLabel);
        filterStatusLayout = (LinearLayout) view.findViewById(R.id.filterStatusLayout);
        filterStatusCardView = (CardView) view.findViewById(R.id.filterStatusCardView);
        filterButton = (Button) view.findViewById(R.id.filterButton);
        orderButton = (Button) view.findViewById(R.id.orderButton);
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

            }
        });
    }

    @Override
    public void refresh() {

    }
}
