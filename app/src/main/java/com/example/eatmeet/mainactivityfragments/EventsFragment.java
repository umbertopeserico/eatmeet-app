package com.example.eatmeet.mainactivityfragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.activities.EventActivity;
import com.example.eatmeet.activities.FilterActivity;
import com.example.eatmeet.activities.FiltersActivity;
import com.example.eatmeet.activities.MainActivity;
import com.example.eatmeet.activitiestest.EventsTestActivity;
import com.example.eatmeet.adapters.EventsAdapter;
import com.example.eatmeet.adapters.EventsAdapterTest;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.dao.implementations.oldrest.EventDAOImpl;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.observablearraylist.ObservableArrayList;
import com.example.eatmeet.observablearraylist.OnAddListener;
import com.example.eatmeet.utils.FiltersManager;
import com.example.eatmeet.utils.Notificable;
import com.example.eatmeet.utils.Visibility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment  implements Notificable {
    final Visibility visibility = new Visibility();
    private String order_by_temp = "order_by_date";
    private ArrayAdapter eventAdapter;
    private View view;
    ObservableArrayList<Event> eventList;
    BackendStatusManager backendStatusManager;
    JSONObject parameters;
    EventDAO eventDao;
    @Override
    public void onResume(){
        super.onResume();
        //refresh();
    }

    public View refresh(){
        eventList.clear();

        LinearLayout button_filter = (LinearLayout) view.findViewById(R.id.button_filter);
        android.widget.LinearLayout.LayoutParams button_filter_layout_params = (android.widget.LinearLayout.LayoutParams) button_filter.getLayoutParams();
        button_filter_layout_params.bottomMargin = 12;
        button_filter.setLayoutParams(button_filter_layout_params);
        CardView noEvents = (CardView) view.findViewById(R.id.card_view_events_summary);
        visibility.makeInvisible(noEvents);

        try {
            parameters = EatMeetApp.getFiltersManager().constructParameters();
        } catch (Exception e){
            e.printStackTrace();
        }
        eventDao.getEvents(eventList, backendStatusManager, parameters);
        return view;
    }

    public View retrieve(){

        eventList = new ObservableArrayList<>();
        backendStatusManager = new BackendStatusManager();
        parameters = new JSONObject();

        eventDao = EatMeetApp.getDaoFactory().getEventDAO();
        ListView listView = (ListView) view.findViewById(R.id.listview_events);

        backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                Logger.getLogger(EventsFragment.this.getClass().getName()).log(Level.INFO, "Connection succeded");
                if (((List<Event>) response).size() == 0){
                    CardView noEvents = (CardView) view.findViewById(R.id.card_view_events_summary);
                    visibility.makeVisible(noEvents);
                    LinearLayout button_filter = (LinearLayout) view.findViewById(R.id.button_filter);
                    android.widget.LinearLayout.LayoutParams button_filter_layout_params = (android.widget.LinearLayout.LayoutParams) button_filter.getLayoutParams();
                    button_filter_layout_params.bottomMargin = 18;
                    button_filter.setLayoutParams(button_filter_layout_params);
                }
            }

            @Override
            public void onFailure(Object response, Integer code) {
                Logger.getLogger(EventsFragment.this.getClass().getName()).log(Level.SEVERE, "Connection NOT succeded");
            }
        });

        eventAdapter = new EventsAdapter(this.getContext(), R.layout.list_item_event, eventList);
        listView.setAdapter(eventAdapter);

        eventList.setOnAddListener(new OnAddListener() {
            @Override
            public void onAddEvent(Object item) {
                Log.d("EVENTS :", "add element "+item.toString());
                eventAdapter.notifyDataSetChanged();
            }
        });
        //eventList = new ObservableArrayList<>();
        //eventAdapter = new EventsAdapter(getContext(), R.layout.list_item_event, eventList);
        //ListView listView = (ListView) view.findViewById(R.id.listview_events);
        //listView.setAdapter(eventAdapter);
        refresh();
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

        retrieve();

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
