package com.example.eatmeet.mainactivityfragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment  implements Notificable {
    //public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    private ArrayAdapter<String> eventAdapter;
    private View view;
    int i = 9;
    List<Event> eventList = null;
    JSONObject parameters = new JSONObject();
    EventDAO eventDao;
    private JSONObject constructParameters() throws JSONException {
        JSONObject parameters= new JSONObject();
        Context context = getContext();
        MainActivity mainActivity = (MainActivity) context;
        ArrayList<Integer> f_categories = mainActivity.getF_categories();
        ArrayList<Integer> f_restaurants = mainActivity.getF_restaurants();
        JSONObject category_parameter = new JSONObject();
        category_parameter.put("categories",new JSONArray(f_categories));
        category_parameter.put("restaurants",new JSONArray(f_restaurants));

        parameters.put("filters",category_parameter);
        /*
        for (int i = 0; i < f_categories.size() ; i++){
            System.out.println();
            try {
                category_parameter.put(Integer.toString(i),Integer.toString(f_categories.get(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(f_categories.get(i)==f_categories.size()-1){
                try {
                    parameters.put("categories",category_parameter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        */
        return parameters;
    }
    @Override
    public void onResume(){ super.onResume();}

    public View refresh(){
        try {
            parameters = constructParameters();
        } catch (Exception e){
            e.printStackTrace();
        }
        eventList = null;
        eventList = eventDao.getEvents(parameters);
        eventAdapter = new EventsAdapter(getContext(), R.layout.list_item_event, eventList);
        ListView listView = (ListView) view.findViewById(R.id.listview_events);
        listView.setAdapter(eventAdapter);
        return view;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_events,this)
                .commit();*/
        view = inflater.inflate(R.layout.fragment_events, container, false);

        eventDao = new EventDAOImpl(this);
        eventList = new ArrayList<>();
        try {
            parameters = constructParameters();
        } catch (Exception e){
            e.printStackTrace();
        }
        refresh();
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
