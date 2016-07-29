package com.example.eatmeet.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.appyvet.rangebar.RangeBar;
import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.adapters.FilterCategoriesAdapter;
import com.example.eatmeet.adapters.FilterRestaurantsAdapter;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.CategoryDAO;
import com.example.eatmeet.dao.interfaces.RestaurantDAO;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.entities.Restaurant;
import com.example.eatmeet.observablearraylist.ObservableArrayList;
import com.example.eatmeet.observablearraylist.OnAddListener;
import com.example.eatmeet.utils.FiltersManager;
import com.example.eatmeet.utils.Visibility;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeConfigurationException;

public class FiltersActivity extends AppCompatActivity {
    private View view;
    ListView listView;
    ArrayAdapter arrayAdapter;

    public void retrieveCategories(){
        ObservableArrayList<Category> observableArrayListCategory = new ObservableArrayList<>();
        BackendStatusManager backendStatusManager = new BackendStatusManager();
        int list_item = -1;
        int listview = -1;
        CategoryDAO categoryDAO = EatMeetApp.getDaoFactory().getCategoryDAO();
        list_item = R.layout.list_item_filter_category;
        listview = R.id.listview_categories;

        listView = (ListView) findViewById(listview);
        arrayAdapter = new FilterCategoriesAdapter(this, list_item, observableArrayListCategory);

        listView.setAdapter(arrayAdapter);
        backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                Logger.getLogger(FiltersActivity.this.getClass().getName()).log(Level.INFO, "Connection succeded");
            }

            @Override
            public void onFailure(Object response, Integer code) {
                Logger.getLogger(FiltersActivity.this.getClass().getName()).log(Level.SEVERE, "Connection NOT succeded");
            }
        });

        observableArrayListCategory.setOnAddListener(new OnAddListener() {
            @Override
            public void onAddEvent(Object item) {
                arrayAdapter.notifyDataSetChanged();
            }
        });

        categoryDAO.getCategories(observableArrayListCategory, backendStatusManager);
    }

    public void retrieveRestaurants(){
        ObservableArrayList<Restaurant> observableArrayListRestaurant = new ObservableArrayList<>();
        BackendStatusManager backendStatusManager = new BackendStatusManager();
        int list_item = -1;
        int listview = -1;
        RestaurantDAO restaurantDAO = EatMeetApp.getDaoFactory().getRestaurantDAO();
        list_item = R.layout.list_item_filter_restaurant;
        listview = R.id.listview_restaurants;

        listView = (ListView) findViewById(listview);

        arrayAdapter = new FilterRestaurantsAdapter(this, list_item, observableArrayListRestaurant);
        listView.setAdapter(arrayAdapter);
        backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                Logger.getLogger(FiltersActivity.this.getClass().getName()).log(Level.INFO, "Connection succeded");
            }

            @Override
            public void onFailure(Object response, Integer code) {
                Logger.getLogger(FiltersActivity.this.getClass().getName()).log(Level.SEVERE, "Connection NOT succeded");
            }
        });

        observableArrayListRestaurant.setOnAddListener(new OnAddListener() {
            @Override
            public void onAddEvent(Object item) {
                arrayAdapter.notifyDataSetChanged();
            }
        });

        restaurantDAO.getRestaurants(observableArrayListRestaurant, backendStatusManager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        /*add back button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        retrieveCategories();
        retrieveRestaurants();

        /*add actions to buttons*/
        Button confirm_filter = (Button) findViewById(R.id.confirm_filter);
        confirm_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { changeFilter(); }
        });
        Button undo_filter = (Button) findViewById(R.id.undo_filter);
        undo_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { undoFilter(); }
        });
        findViewById(R.id.card_view_categories_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCard("open","categories");
            }
        });
        findViewById(R.id.card_view_categories_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCard("close","categories");
            }
        });
        findViewById(R.id.card_view_restaurants_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCard("open","restaurants");
            }
        });
        findViewById(R.id.card_view_restaurants_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCard("close","restaurants");
            }
        });
        findViewById(R.id.grey_background).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCard("close","restaurants");
                toggleCard("close","categories");
            }
        });

        RangeBar rangebar_people = (RangeBar) findViewById(R.id.rangebar_people);
        RangeBar rangebar_price = (RangeBar) findViewById(R.id.rangebar_price);
        RangeBar rangebar_actual_sale = (RangeBar) findViewById(R.id.rangebar_actual_sale);
        DatePicker datepicker_max_date = (DatePicker) findViewById(R.id.max_date);
        DatePicker datepicker_min_date = (DatePicker) findViewById(R.id.min_date);

        /*set initial value as the actual filter value*/
        FiltersManager fm = EatMeetApp.getFiltersManager();
        rangebar_people.setRangePinsByValue(fm.getF_min_people(),fm.getF_max_people());
        rangebar_price.setRangePinsByValue((float) fm.getF_min_price(),(float) fm.getF_max_price());
        rangebar_actual_sale.setRangePinsByValue((float) fm.getF_min_actual_sale(),(float) fm.getF_max_actual_sale());

        Date actual_min_date = fm.getF_min_date();
        Calendar cal = Calendar.getInstance();
        if(actual_min_date != null) {
            cal.setTime(actual_min_date);
        }
        datepicker_min_date.init(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(year,monthOfYear,dayOfMonth);
                EatMeetApp.getFiltersManager().setF_min_date(cal.getTime());
            }
        });

        Date actual_max_date = fm.getF_max_date();
        cal = Calendar.getInstance();
        if(actual_max_date != null) {
            cal.setTime(actual_max_date);
        }
        datepicker_max_date.init(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(year,monthOfYear,dayOfMonth);
                EatMeetApp.getFiltersManager().setF_max_date(cal.getTime());
            }
        });

        /*set change value listeners*/
        rangebar_price.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                EatMeetApp.getFiltersManager().setF_max_price(Double.parseDouble(rightPinValue));
                EatMeetApp.getFiltersManager().setF_min_price(Double.parseDouble(leftPinValue));
            }
        });
        rangebar_people.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                EatMeetApp.getFiltersManager().setF_max_people(Integer.parseInt(rightPinValue));
                EatMeetApp.getFiltersManager().setF_min_people(Integer.parseInt(leftPinValue));
            }
        });
        rangebar_actual_sale.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                EatMeetApp.getFiltersManager().setF_max_actual_sale(Double.parseDouble(rightPinValue));
                EatMeetApp.getFiltersManager().setF_min_actual_sale(Double.parseDouble(leftPinValue));
            }
        });
    }

    /*open and close card containing restaurant or category choise*/
    private void toggleCard(String action, String target){
        CardView target_card_view = null;
        RelativeLayout grey_background = (RelativeLayout) findViewById(R.id.grey_background);
        Visibility visibility = new Visibility();
        switch (target) {
            case "categories":
                target_card_view = (CardView) findViewById(R.id.card_view_categories);
                break;
            case "restaurants":
                target_card_view = (CardView) findViewById(R.id.card_view_restaurants);
        }
        switch (action) {
            case "open":
                visibility.makeVisible(target_card_view);
                visibility.makeVisible(grey_background);
                break;
            case "close":
                visibility.makeInvisible(target_card_view);
                visibility.makeInvisible(grey_background);
        }
    }

    /*add action for back button*/
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void closeKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void undoFilter(){
        EatMeetApp.getFiltersManager().resetOldFilters();
        onSupportNavigateUp();
    }

    public void changeFilter(){
        try {
            EatMeetApp.getFiltersManager().constructParameters();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*go to main activity that will refresh events and send you to events*/
        Intent intent = new Intent(FiltersActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("from", "FiltersActivity");
        startActivity(intent);
    }
}
