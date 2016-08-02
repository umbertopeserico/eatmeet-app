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
import android.widget.TextView;

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
import com.example.eatmeet.dao.interfaces.CategoryDAO;
import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.dao.implementations.oldrest.EventDAOImpl;
import com.example.eatmeet.dao.interfaces.RestaurantDAO;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.entities.Restaurant;
import com.example.eatmeet.observablearraylist.ObservableArrayList;
import com.example.eatmeet.observablearraylist.OnAddListener;
import com.example.eatmeet.utils.FiltersManager;
import com.example.eatmeet.utils.Notificable;
import com.example.eatmeet.utils.Visibility;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

        /*remove feedback of no events if present*/
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

        /*show active filters*/
        TextView active_filters_none = (TextView) view.findViewById(R.id.active_filters_none);
        final TextView active_filters_restaurant = (TextView) view.findViewById(R.id.active_filters_restaurant);
        final TextView active_filters_categories = (TextView) view.findViewById(R.id.active_filters_categories);
        TextView active_filters_min_date = (TextView) view.findViewById(R.id.active_filters_min_date);
        TextView active_filters_max_date = (TextView) view.findViewById(R.id.active_filters_max_date);
        TextView active_filters_min_people = (TextView) view.findViewById(R.id.active_filters_min_people);
        TextView active_filters_max_people = (TextView) view.findViewById(R.id.active_filters_max_people);
        TextView active_filters_min_actual_sale = (TextView) view.findViewById(R.id.active_filters_min_actual_sale);
        TextView active_filters_max_actual_sale = (TextView) view.findViewById(R.id.active_filters_max_actual_sale);
        TextView active_filters_min_price = (TextView) view.findViewById(R.id.active_filters_min_price);
        TextView active_filters_max_price = (TextView) view.findViewById(R.id.active_filters_max_price);

        visibility.makeVisible(active_filters_none);
        visibility.makeInvisible(active_filters_restaurant);
        visibility.makeInvisible(active_filters_categories);
        visibility.makeInvisible(active_filters_min_date);
        visibility.makeInvisible(active_filters_max_date);
        visibility.makeInvisible(active_filters_min_people);
        visibility.makeInvisible(active_filters_max_people);
        visibility.makeInvisible(active_filters_min_actual_sale);
        visibility.makeInvisible(active_filters_max_actual_sale);
        visibility.makeInvisible(active_filters_min_price);
        visibility.makeInvisible(active_filters_max_price);

        if(EatMeetApp.getFiltersManager().isActiveF_restaurants()){
            visibility.makeInvisible(active_filters_none);
            visibility.makeVisible(active_filters_restaurant);
            final List<Integer> restaurantIdList = EatMeetApp.getFiltersManager().getF_restaurants();
            RestaurantDAO restaurantDAO = EatMeetApp.getDaoFactory().getRestaurantDAO();
            final ObservableArrayList<Restaurant> restaurantObservableList = new ObservableArrayList<>();
            BackendStatusManager backendStatusManager = new BackendStatusManager();
            final ArrayList<String> restaurantNames = new ArrayList<>();
            backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
                @Override
                public void onSuccess(Object response, Integer code) {
                    Logger.getLogger(EventsFragment.this.getClass().getName()).log(Level.INFO, "Connection succeded");
                    String restaurantString = "Ristoranti: ";
                    for (int i = 0; i < restaurantNames.size(); i++) {
                        if (i != 0) {
                            restaurantString += ", ";
                        }
                        restaurantString += restaurantNames.get(i);
                    }
                    active_filters_restaurant.setText(restaurantString);
                }
                @Override
                public void onFailure(Object response, Integer code) {
                    Logger.getLogger(EventsFragment.this.getClass().getName()).log(Level.SEVERE, "Connection NOT succeded");
                }
            });
            restaurantObservableList.setOnAddListener(new OnAddListener() {
                @Override
                public void onAddEvent(Object item){
                    Restaurant restaurant = (Restaurant) item;
                    for (int i = 0; i < restaurantIdList.size(); i++) {
                        Integer restaurantId = restaurantIdList.get(i);
                        if (restaurant.getId() == restaurantId) {
                            restaurantNames.add(restaurant.getName());
                        }
                    }
                }
            });
            restaurantDAO.getRestaurants(restaurantObservableList, backendStatusManager);
        }

        if(EatMeetApp.getFiltersManager().isActiveF_categories()){
            visibility.makeInvisible(active_filters_none);
            visibility.makeVisible(active_filters_categories);
            final List<Integer> categoriesIdList = EatMeetApp.getFiltersManager().getF_categories();
            CategoryDAO categoryDAO = EatMeetApp.getDaoFactory().getCategoryDAO();
            final ObservableArrayList<Category> categoryObservableList = new ObservableArrayList<>();
            BackendStatusManager backendStatusManager = new BackendStatusManager();
            final ArrayList<String> categoryNames = new ArrayList<>();
            backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
                @Override
                public void onSuccess(Object response, Integer code) {
                    Logger.getLogger(EventsFragment.this.getClass().getName()).log(Level.INFO, "Connection succeded");
                    String categoryString = "Categorie: ";
                    for (int i = 0; i < categoryNames.size(); i++) {
                        if (i != 0) {
                            categoryString += ", ";
                        }
                        categoryString += categoryNames.get(i);
                    }
                    active_filters_categories.setText(categoryString);
                }
                @Override
                public void onFailure(Object response, Integer code) {
                    Logger.getLogger(EventsFragment.this.getClass().getName()).log(Level.SEVERE, "Connection NOT succeded");
                }
            });
            categoryObservableList.setOnAddListener(new OnAddListener() {
                @Override
                public void onAddEvent(Object item){
                    Category category = (Category) item;
                    for (int i = 0; i < categoriesIdList.size(); i++) {
                        Integer categoryId = categoriesIdList.get(i);
                        if (category.getId() == categoryId) {
                            categoryNames.add(category.getName());
                        }
                    }
                }
            });
            categoryDAO.getCategories(categoryObservableList, backendStatusManager);
        }


        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        if(EatMeetApp.getFiltersManager().isActiveF_min_date()){
            visibility.makeInvisible(active_filters_none);
            visibility.makeVisible(active_filters_min_date);
            String scheduleString = "Data da: ";
            calendar.setTime(EatMeetApp.getFiltersManager().getF_min_date());   // assigns calendar to given date
            scheduleString+=calendar.get(Calendar.DAY_OF_MONTH) + "/";
            scheduleString+=calendar.get(Calendar.MONTH) + 1;
            active_filters_min_date.setText(scheduleString + "\n");
        }
        if(EatMeetApp.getFiltersManager().isActiveF_max_date()){
            visibility.makeInvisible(active_filters_none);
            visibility.makeVisible(active_filters_max_date);
            String scheduleString = "Data fino a: ";
            calendar.setTime(EatMeetApp.getFiltersManager().getF_max_date());   // assigns calendar to given date
            scheduleString+=calendar.get(Calendar.DAY_OF_MONTH) + "/";
            scheduleString+=calendar.get(Calendar.MONTH) + 1;
            active_filters_max_date.setText(scheduleString + "\n");
        }

        if(EatMeetApp.getFiltersManager().isActiveF_min_people()){
            visibility.makeInvisible(active_filters_none);
            visibility.makeVisible(active_filters_min_people);
            active_filters_min_people.setText("Persone da: " + EatMeetApp.getFiltersManager().getF_min_people());
        }
        if(EatMeetApp.getFiltersManager().isActiveF_max_people()){
            visibility.makeInvisible(active_filters_none);
            visibility.makeVisible(active_filters_max_people);
            active_filters_max_people.setText("Persone fino a: " + EatMeetApp.getFiltersManager().getF_max_people());
        }

        if(EatMeetApp.getFiltersManager().isActiveF_min_actual_sale()){
            visibility.makeInvisible(active_filters_none);
            visibility.makeVisible(active_filters_min_actual_sale);
            active_filters_min_actual_sale.setText("Sconto da: " + EatMeetApp.getFiltersManager().getF_min_actual_sale() + "%");
        }
        if(EatMeetApp.getFiltersManager().isActiveF_max_actual_sale()){
            visibility.makeInvisible(active_filters_none);
            visibility.makeVisible(active_filters_max_actual_sale);
            active_filters_max_actual_sale.setText("Sconto fino a: " + EatMeetApp.getFiltersManager().getF_max_actual_sale() + "%");
        }


        if(EatMeetApp.getFiltersManager().isActiveF_min_price()){
            visibility.makeInvisible(active_filters_none);
            visibility.makeVisible(active_filters_min_price);
            active_filters_min_price.setText("Prezzo da: " + EatMeetApp.getFiltersManager().getF_min_price() + "€");
        }
        if(EatMeetApp.getFiltersManager().isActiveF_max_price()){
            visibility.makeInvisible(active_filters_none);
            visibility.makeVisible(active_filters_max_price);
            active_filters_max_price.setText("Prezzo fino a: " + EatMeetApp.getFiltersManager().getF_max_price() + "€");
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
                    /*add feedback of no events*/
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
