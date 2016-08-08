package com.example.eatmeet.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.eatmeet.utils.OldFiltersManager;
import com.example.eatmeet.utils.Visibility;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FiltersActivity extends AppCompatActivity {

    private FiltersManager filtersManager;

    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private Button applyFiltersButton;
    private Button resetFiltersButton;
    private CardView openCategoriesCardView;
    private Button categoriesCardViewCloseButton;
    private CardView openRestaurantCardView;
    private Button restaurantsCardViewCloseButton;
    private RelativeLayout overlay;
    private RangeBar peopleRangeBar;
    private RangeBar priceRangeBar;
    private RangeBar actualSaleRangeBar;
    private DatePicker maxDate = (DatePicker) findViewById(R.id.maxDate);
    private DatePicker minDate = (DatePicker) findViewById(R.id.minDate);

    private void initViewElements() {
        applyFiltersButton = (Button) findViewById(R.id.applyFilters);
        resetFiltersButton = (Button) findViewById(R.id.resetFilters);
        openCategoriesCardView = (CardView) findViewById(R.id.openCategoriesCardView);
        categoriesCardViewCloseButton = (Button) findViewById(R.id.categoriesCardViewCloseButton);
        openRestaurantCardView = (CardView) findViewById(R.id.openRestaurantCardView);
        restaurantsCardViewCloseButton = (Button) findViewById(R.id.restaurantsCardViewCloseButton);
        overlay = (RelativeLayout) findViewById(R.id.overlay);
        peopleRangeBar = (RangeBar) findViewById(R.id.peopleCardView);
        priceRangeBar = (RangeBar) findViewById(R.id.priceRangeBar);
        actualSaleRangeBar = (RangeBar) findViewById(R.id.actualSaleRangeBar);
        maxDate = (DatePicker) findViewById(R.id.maxDate);
        minDate = (DatePicker) findViewById(R.id.minDate);
        filtersManager = new FiltersManager();
    }

    private void setActions() {
        applyFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFilter();
            }
        });
        resetFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFilter();
            }
        });
        openCategoriesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCard("open","categories");
            }
        });
        categoriesCardViewCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCard("close","categories");
            }
        });
        openRestaurantCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCard("open","restaurants");
            }
        });
        restaurantsCardViewCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCard("close","restaurants");
            }
        });
        overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCard("close","restaurants");
                toggleCard("close","categories");
            }
        });
    }

    private void setValues() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        assert getSupportActionBar()!=null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViewElements();
        setActions();

    }

    @Override
    protected void onResume(){
        super.onResume();
        retrieveCategories();
        retrieveRestaurants();



        RangeBar rangebar_people = (RangeBar) findViewById(R.id.peopleRangeBar);
        RangeBar rangebar_price = (RangeBar) findViewById(R.id.priceRangeBar);
        RangeBar rangebar_actual_sale = (RangeBar) findViewById(R.id.actualSaleRangeBar);
        DatePicker datepicker_max_date = (DatePicker) findViewById(R.id.maxDate);
        DatePicker datepicker_min_date = (DatePicker) findViewById(R.id.minDate);

        if (peopleRangeBar != null) {
            peopleRangeBar.findViewById(Resources.getSystem().getIdentifier("year", "id", "android")).setVisibility(View.GONE);//always actual year
        }
        if (minDate != null) {
            minDate.findViewById(Resources.getSystem().getIdentifier("year", "id", "android")).setVisibility(View.GONE);//always actual year
        }

        /*set initial value as the actual filter value*/
        OldFiltersManager fm = EatMeetApp.getFiltersManager();
        peopleRangeBar.setRangePinsByValue(fm.getF_min_people(),fm.getF_max_people());
        priceRangeBar.setRangePinsByValue((float) fm.getF_min_price(),(float) fm.getF_max_price());
        actualSaleRangeBar.setRangePinsByValue((float) fm.getF_min_actual_sale(),(float) fm.getF_max_actual_sale());

        Date actual_min_date = fm.getF_min_date();
        Calendar cal = Calendar.getInstance();
        if(actual_min_date != null) {
            cal.setTime(actual_min_date);
        }
        datepicker_min_date.init(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                int yearNow = cal.get(Calendar.YEAR);//always actual year
                cal.set(yearNow,monthOfYear,dayOfMonth);
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
                int yearNow = cal.get(Calendar.YEAR);//always actual year
                cal.set(yearNow,monthOfYear,dayOfMonth);
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
        RelativeLayout grey_background = (RelativeLayout) findViewById(R.id.overlay);
        Visibility visibility = new Visibility();
        switch (target) {
            case "categories":
                target_card_view = (CardView) findViewById(R.id.categoriesCardView);
                break;
            case "restaurants":
                target_card_view = (CardView) findViewById(R.id.restaurantsCardView);
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

    private void resetFilter(){
        EatMeetApp.getFiltersManager().removeAllFilters();
        changeFilter();
    }

    private void changeFilter(){
        Intent intent = new Intent(FiltersActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("from", "FiltersActivity");
        startActivity(intent);
    }

    private void retrieveCategories(){
        ObservableArrayList<Category> observableArrayListCategory = new ObservableArrayList<>();
        BackendStatusManager backendStatusManager = new BackendStatusManager();
        int list_item = -1;
        int listview = -1;
        CategoryDAO categoryDAO = EatMeetApp.getDaoFactory().getCategoryDAO();
        list_item = R.layout.list_item_filter_category;
        listview = R.id.categoriesListView;

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

    private void retrieveRestaurants(){
        ObservableArrayList<Restaurant> observableArrayListRestaurant = new ObservableArrayList<>();
        BackendStatusManager backendStatusManager = new BackendStatusManager();
        int list_item = -1;
        int listview = -1;
        RestaurantDAO restaurantDAO = EatMeetApp.getDaoFactory().getRestaurantDAO();
        list_item = R.layout.list_item_filter_restaurant;
        listview = R.id.restaurantsListView;

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
}
