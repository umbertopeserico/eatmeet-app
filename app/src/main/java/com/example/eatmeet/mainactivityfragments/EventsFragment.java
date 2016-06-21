package com.example.eatmeet.mainactivityfragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.eatmeet.R;
import com.example.eatmeet.activities.FilterActivity;
import com.example.eatmeet.activities.MainActivity;
import com.example.eatmeet.adapters.EventsAdapter;
import com.example.eatmeet.adapters.FiltersAdapter;
import com.example.eatmeet.adapters.OrderAdapter;
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
    private String order_by = "order_by_date";
    private String order_by_temp = "order_by_date";
    private ArrayAdapter<String> eventAdapter;
    private View view;
    List<Event> eventList = null;
    JSONObject parameters = new JSONObject();
    EventDAO eventDao;
    private JSONObject constructParameters() throws JSONException {
        JSONObject parameters= new JSONObject();
        Context context = getContext();
        MainActivity mainActivity = (MainActivity) context;
        JSONObject all_filters = new JSONObject();

        /*set filter parameters*/
        ArrayList<Integer> f_categories = new ArrayList<>();
        if(mainActivity.on_categories) {
            f_categories = mainActivity.getF_categories();
        }
        all_filters.put("categories", new JSONArray(f_categories));

        if(mainActivity.on_people) {
            int f_min_people = mainActivity.getF_min_people();
            JSONObject participants_range = new JSONObject();
            participants_range.put("start", f_min_people);
            int f_max_people = mainActivity.getF_max_people();
            if(f_max_people==0) f_max_people = 1000;
            participants_range.put("end", f_max_people);
            all_filters.put("participants_range", participants_range);
        }

        parameters.put("filters",all_filters);
        /*set order parameters*/
        if(order_by=="order_by_date") {
            JSONObject schedule = new JSONObject();
            schedule.put("schedule","asc");
            parameters.put("order", schedule);
        } else if (order_by=="order_by_price") {
            JSONObject price = new JSONObject();
            price.put("actual_price","desc");
            parameters.put("order", price);
        }
        //System.out.println("PARAMETERS: " + parameters);
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
        /*
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
        */
        Button filter = (Button) view.findViewById(R.id.filter);
        assert filter != null;
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterFilter();
                /*
                Visibility visibility = new Visibility();
                visibility.toggleView(view.findViewById(R.id.listview_filters));
                if(visibility.isVisible(view.findViewById(R.id.listview_events))==visibility.isVisible(view.findViewById(R.id.listview_filters)))
                {
                    visibility.toggleView(view.findViewById(R.id.listview_events));
                }
                visibility.makeInvisible(view.findViewById(R.id.radioGroup));
                */
            }
        });
        /**
         * ordina per
         */
        /*
        List<String> orderList = new ArrayList<>();
        orderList.add("Prezzo discendente");
        orderList.add("Vicinanza");
        orderList.add("Data");
        ArrayAdapter<String> orderAdapter = new OrderAdapter(getContext(), R.layout.list_item_order, orderList);
        final ListView listViewOrder = (ListView) view.findViewById(R.id.listview_orders);
        listViewOrder.setAdapter(orderAdapter);
        */
        Button order = (Button) view.findViewById(R.id.order);
        assert order != null;
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterOrder();
                /*
                Visibility visibility = new Visibility();
                visibility.toggleView(view.findViewById(R.id.radioGroup));
                if(visibility.isVisible(view.findViewById(R.id.listview_events))==visibility.isVisible(view.findViewById(R.id.radioGroup)))
                {
                    visibility.toggleView(view.findViewById(R.id.listview_events));
                }
                visibility.makeInvisible(view.findViewById(R.id.listview_filters));
                */
            }
        });
        RadioButton order_by_price = (RadioButton) view.findViewById(R.id.order_by_price);
        //RadioButton order_by_proximity = (RadioButton) view.findViewById(R.id.order_by_proximity);
        RadioButton order_by_date = (RadioButton) view.findViewById(R.id.order_by_date);
        order_by_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onRadioButtonClicked(v); }
        });
        /*order_by_proximity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onRadioButtonClicked(v); }
        });*/
        order_by_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onRadioButtonClicked(v); }
        });
        Button confirm_order = (Button) view.findViewById(R.id.confirm_order);
        confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { changeOrder(); }
        });
        Button undo_order = (Button) view.findViewById(R.id.undo_order);
        undo_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { undoOrder(); }
        });
        Button undo_filter = (Button) view.findViewById(R.id.undo_filter);
        undo_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { undoFilter(); }
        });
        Button confirm_filter = (Button) view.findViewById(R.id.confirm_filter);
        confirm_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { changeFilter(); }
        });

        setRadio();

        return view;
    }

    /*
    public void sendMessage(View view) {
        Intent intent = new Intent(getContext(), FilterActivity.class);

        //intent.putExtra(EXTRA_MESSAGE, "2");
        startActivity(intent);
    }
    */

    public void setRadio(){
        if(order_by=="order_by_date"){
            ((RadioButton) view.findViewById(R.id.order_by_date)).setChecked(true);
        } else if (order_by=="order_by_price"){
            ((RadioButton) view.findViewById(R.id.order_by_price)).setChecked(true);
        /*} else if (order_by=="order_by_proximity"){
            ((RadioButton) view.findViewById(R.id.order_by_proximity)).setChecked(true);*/
        }
    }
    public void exitFilter() {
        Visibility visibility = new Visibility();
        visibility.makeInvisible(view.findViewById(R.id.radioGroup));
        visibility.makeVisible(view.findViewById(R.id.listview_events));
        visibility.makeInvisible(view.findViewById(R.id.listview_filters));
        visibility.makeVisible(view.findViewById(R.id.order));
        visibility.makeVisible(view.findViewById(R.id.filter));
        visibility.makeInvisible(view.findViewById(R.id.filter_buttons));
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    public void enterFilter(){
        Visibility visibility = new Visibility();
        visibility.makeInvisible(view.findViewById(R.id.radioGroup));
        visibility.makeInvisible(view.findViewById(R.id.listview_events));
        visibility.makeVisible(view.findViewById(R.id.listview_filters));
        visibility.makeInvisible(view.findViewById(R.id.order));
        visibility.makeInvisible(view.findViewById(R.id.filter));
        visibility.makeVisible(view.findViewById(R.id.filter_buttons));
    }
    public void exitOrder(){
        setRadio();
        Visibility visibility = new Visibility();
        visibility.makeInvisible(view.findViewById(R.id.radioGroup));
        visibility.makeVisible(view.findViewById(R.id.listview_events));
        visibility.makeInvisible(view.findViewById(R.id.listview_filters));
        visibility.makeVisible(view.findViewById(R.id.order));
        visibility.makeVisible(view.findViewById(R.id.filter));
        visibility.makeInvisible(view.findViewById(R.id.filter_buttons));
    }
    public void enterOrder(){
        Visibility visibility = new Visibility();
        visibility.makeVisible(view.findViewById(R.id.radioGroup));
        visibility.makeInvisible(view.findViewById(R.id.listview_events));
        visibility.makeInvisible(view.findViewById(R.id.listview_filters));
        visibility.makeInvisible(view.findViewById(R.id.order));
        visibility.makeInvisible(view.findViewById(R.id.filter));
        visibility.makeInvisible(view.findViewById(R.id.filter_buttons));
    }
    public void undoFilter(){
        exitFilter();
    }
    public void changeFilter(){
        Context context = getContext();
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.removeF_categories();
        mainActivity.removeF_min_people();
        mainActivity.removeF_max_people();
        EditText filter_min_people = (EditText) view.findViewById(R.id.filter_min_people);
        if(filter_min_people.getText().toString() == "ernesto") {
            mainActivity.setF_min_people(Integer.parseInt(filter_min_people.getText().toString()));
        }
        EditText filter_max_people = (EditText) view.findViewById(R.id.filter_max_people);
        if(filter_max_people.getText().toString() == "ernesto") {
            mainActivity.setF_max_people(Integer.parseInt(filter_max_people.getText().toString()));
        }
        exitFilter();
        refresh();
    }
    public void undoOrder(){
        exitOrder();
    }
    public void changeOrder(){
        order_by = order_by_temp;
        exitOrder();
        refresh();
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.order_by_date:
                if (checked)
                    order_by_temp = "order_by_date";
                    break;
            case R.id.order_by_price:
                if (checked)
                    order_by_temp = "order_by_price";
                    break;
            /*case R.id.order_by_proximity:
                if (checked)
                    order_by_temp = "order_by_proximity";
                    break;*/
        }
    }

    @Override
    public void sendNotify() {
        eventAdapter.notifyDataSetChanged();
    }
}
