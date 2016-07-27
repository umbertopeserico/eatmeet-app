package com.example.eatmeet.mainactivityfragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.activities.EventActivity;
import com.example.eatmeet.activities.FilterActivity;
import com.example.eatmeet.activities.FiltersActivity;
import com.example.eatmeet.activities.MainActivity;
import com.example.eatmeet.adapters.EventsAdapter;
import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.dao.implementations.rest.EventDAOImpl;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.utils.FiltersManager;
import com.example.eatmeet.utils.Notificable;
import com.example.eatmeet.utils.Visibility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment  implements Notificable {
    private String order_by_temp = "order_by_date";
    private ArrayAdapter<String> eventAdapter;
    private View view;
    List<Event> eventList = null;
    JSONObject parameters = new JSONObject();
    EventDAO eventDao;
    @Override
    public void onResume(){ super.onResume();}

    public View refresh(){
        try {
            parameters = EatMeetApp.getFiltersManager().constructParameters();
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
            parameters = EatMeetApp.getFiltersManager().constructParameters();
        } catch (Exception e){
            e.printStackTrace();
        }
        refresh();

        Button filter = (Button) view.findViewById(R.id.filter);
        assert filter != null;
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FiltersActivity.class);
                //intent.putExtra(EXTRA_MESSAGE, "2");
                startActivity(intent);
            }
        });

        /**
         * ordina per
         */

        Button order = (Button) view.findViewById(R.id.order);
        assert order != null;
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterOrder();
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

        setRadio();

        return view;
    }


    public void setRadio(){
        if(EatMeetApp.getFiltersManager().getF_order_by()=="order_by_date"){
            ((RadioButton) view.findViewById(R.id.order_by_date)).setChecked(true);
        } else if (EatMeetApp.getFiltersManager().getF_order_by()=="order_by_price"){
            ((RadioButton) view.findViewById(R.id.order_by_price)).setChecked(true);
        /*} else if (EatMeetApp.getFiltersManager().getF_order_by()=="order_by_proximity"){
            ((RadioButton) view.findViewById(R.id.order_by_proximity)).setChecked(true);*/
        }
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
    public void undoOrder(){
        exitOrder();
    }
    public void changeOrder(){
        EatMeetApp.getFiltersManager().setF_order_by(order_by_temp);
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
