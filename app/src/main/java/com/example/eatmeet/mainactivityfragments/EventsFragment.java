package com.example.eatmeet.mainactivityfragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eatmeet.R;
import com.example.eatmeet.adapters.EventsAdapter;
import com.example.eatmeet.dao.EventDAO;
import com.example.eatmeet.dao.EventDAOImpl;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.utils.Notificable;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment  implements Notificable {

    private ArrayAdapter<String> eventAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        EventDAO eventDao = new EventDAOImpl(this);
        List<Event> eventList = eventDao.getEvents();

        eventAdapter = new EventsAdapter(getContext(), R.layout.list_item_event, eventList);

        ListView listView = (ListView) view.findViewById(R.id.listview_events);
        listView.setAdapter(eventAdapter);
        return view;
    }

    @Override
    public void sendNotify() {
        eventAdapter.notifyDataSetChanged();
    }
}
