package com.example.eatmeet.mainactivityfragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.eatmeet.R;
import com.example.eatmeet.activities.FilterActivity;
import com.example.eatmeet.activities.MainActivity;
import com.example.eatmeet.adapters.EventsAdapter;
import com.example.eatmeet.adapters.FiltersAdapter;
import com.example.eatmeet.dao.EventDAO;
import com.example.eatmeet.dao.EventDAOImpl;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.utils.Notificable;
import com.example.eatmeet.utils.Visibility;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment  implements Notificable {
    //public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    private ArrayAdapter<String> eventAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_events, container, false);

        EventDAO eventDao = new EventDAOImpl(this);
        List<Event> eventList = eventDao.getEvents();

        eventAdapter = new EventsAdapter(getContext(), R.layout.list_item_event, eventList);

        ListView listView = (ListView) view.findViewById(R.id.listview_events);
        listView.setAdapter(eventAdapter);

        /*
        Button buttonCalls2 = (Button) view.findViewById(R.id.mioBottone);
        buttonCalls2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FilterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        */


        /**
         * filtra per
         */
        List<String> filterList = new ArrayList<>();
        filterList.add("Prezzo");
        filterList.add("Categoria");
        filterList.add("Date");
        filterList.add("Vicinanza");
        filterList.add("Sconto");
        filterList.add("Partecipanti");
        filterList.add("Ristorante");
        ArrayAdapter<String> filterAdapter = new FiltersAdapter(getContext(), R.layout.list_item_filter, filterList);
        final ListView listViewFilter = (ListView) view.findViewById(R.id.listview_filters);
        listViewFilter.setAdapter(filterAdapter);
        Button filter = (Button) view.findViewById(R.id.filter);
        assert filter != null;
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Visibility visibility = new Visibility();
                visibility.toggleView(view.findViewById(R.id.listview_filters));
                if(visibility.isVisible(view.findViewById(R.id.listview_events))==visibility.isVisible(view.findViewById(R.id.listview_filters)))
                {
                    visibility.toggleView(view.findViewById(R.id.listview_events));
                }
                visibility.makeInvisible(view.findViewById(R.id.listview_orders));
            }
        });
        /**
         * ordina per
         */
        List<String> orderList = new ArrayList<>();
        orderList.add("Prezzo discendente");
        orderList.add("Vicinanza");
        orderList.add("Data");
        ArrayAdapter<String> orderAdapter = new FiltersAdapter(getContext(), R.layout.list_item_filter, orderList);
        final ListView listViewOrder = (ListView) view.findViewById(R.id.listview_orders);
        listViewOrder.setAdapter(orderAdapter);
        Button order = (Button) view.findViewById(R.id.order);
        assert order != null;
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Visibility visibility = new Visibility();
                visibility.toggleView(view.findViewById(R.id.listview_orders));
                if(visibility.isVisible(view.findViewById(R.id.listview_events))==visibility.isVisible(view.findViewById(R.id.listview_orders)))
                {
                    visibility.toggleView(view.findViewById(R.id.listview_events));
                }
                visibility.makeInvisible(view.findViewById(R.id.listview_filters));
            }
        });

        return view;
    }

    /*
    public void sendMessage(View view) {
        Intent intent = new Intent(getContext(), FilterActivity.class);

        //intent.putExtra(EXTRA_MESSAGE, "2");
        startActivity(intent);
    }
    */

    @Override
    public void sendNotify() {
        eventAdapter.notifyDataSetChanged();
    }
}
