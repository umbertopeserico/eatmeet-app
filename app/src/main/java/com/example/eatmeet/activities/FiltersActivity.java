package com.example.eatmeet.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.example.eatmeet.utils.DatePickerUtils;
import com.example.eatmeet.utils.FiltersManager;
import com.example.eatmeet.utils.Visibility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FiltersActivity extends AppCompatActivity {

    private FiltersManager filtersManager;

    private ObservableArrayList<Category> categoriesList;
    private ArrayList<Category> selectedCategoriesList;
    private ArrayList<Restaurant> selectedRestaurantsList;
    private ArrayAdapter categoriesArrayAdapter;
    private ObservableArrayList<Restaurant> restaurantsList;
    private ArrayAdapter restaurantsArrayAdapter;

    private ListView categoriesListView;
    private ListView restaurantsListView;
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
    private DatePicker maxDate;
    private DatePicker minDate;

    private Button openDateFrom;
    private Button openDateTo;
    private CheckBox activateDateFilter;
    private CardView dateFromCardView;
    private Button dateFromCardViewCloseButton;
    private CardView dateToCardView;
    private Button dateToCardViewCloseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        assert getSupportActionBar()!=null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        filtersManager = new FiltersManager();

        categoriesList = new ObservableArrayList<>();
        selectedCategoriesList = new ArrayList<>(filtersManager.getSelectedCategories());
        categoriesArrayAdapter = new FilterCategoriesAdapter(this, R.layout.list_item_filter_category, categoriesList, selectedCategoriesList);

        restaurantsList = new ObservableArrayList<>();
        selectedRestaurantsList = new ArrayList<>(filtersManager.getSelectedRestaurants());
        restaurantsArrayAdapter = new FilterRestaurantsAdapter(this, R.layout.list_item_filter_restaurant, restaurantsList, selectedRestaurantsList);

        initViewElements();
        setActions();
        retrieveCategories();
        retrieveRestaurants();
        setCurrentValues();
        //setFiltersListener();
    }

    private void initViewElements() {
        categoriesListView = (ListView) findViewById(R.id.categoriesListView);
        restaurantsListView = (ListView) findViewById(R.id.restaurantsListView);
        applyFiltersButton = (Button) findViewById(R.id.applyFilters);
        resetFiltersButton = (Button) findViewById(R.id.resetFilters);
        openCategoriesCardView = (CardView) findViewById(R.id.openCategoriesCardView);
        categoriesCardViewCloseButton = (Button) findViewById(R.id.categoriesCardViewCloseButton);
        openRestaurantCardView = (CardView) findViewById(R.id.openRestaurantCardView);
        restaurantsCardViewCloseButton = (Button) findViewById(R.id.restaurantsCardViewCloseButton);
        overlay = (RelativeLayout) findViewById(R.id.overlay);
        peopleRangeBar = (RangeBar) findViewById(R.id.peopleRangeBar);
        priceRangeBar = (RangeBar) findViewById(R.id.priceRangeBar);
        actualSaleRangeBar = (RangeBar) findViewById(R.id.actualSaleRangeBar);
        maxDate = (DatePicker) findViewById(R.id.maxDate);
        minDate = (DatePicker) findViewById(R.id.minDate);
        filtersManager = EatMeetApp.getFiltersManager();
        openDateFrom = (Button) findViewById(R.id.open_date_from);
        openDateTo = (Button) findViewById(R.id.open_date_to);
        activateDateFilter = (CheckBox) findViewById(R.id.activate_date_filter);
        dateFromCardView = (CardView) findViewById(R.id.dateFromCardView);
        dateFromCardViewCloseButton = (Button) findViewById(R.id.dateFromCardViewCloseButton);
        dateToCardView = (CardView) findViewById(R.id.dateToCardView);
        dateToCardViewCloseButton = (Button) findViewById(R.id.dateToCardViewCloseButton);
    }

    private void setActions() {
        openDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCard("open","date_from");
            }
        });
        openDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCard("open","date_to");
            }
        });
        applyFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyFilters();
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
        dateFromCardViewCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCard("close","date_from");
            }
        });
        dateToCardViewCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCard("close","date_to");
            }
        });
        overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCard("close","restaurants");
                toggleCard("close","categories");
                toggleCard("close","date_from");
                toggleCard("close","date_to");
            }
        });
    }

    private void setCurrentValues() {
        if (filtersManager.isMinPeopleEnabled()) {
            peopleRangeBar.setRangePinsByValue(filtersManager.getMinPeople(), Integer.parseInt(peopleRangeBar.getRightPinValue()));
        } else {
            peopleRangeBar.setRangePinsByIndices(0, peopleRangeBar.getRightIndex());
        }

        if (filtersManager.isMaxPeopleEnabled()) {
            peopleRangeBar.setRangePinsByValue(Integer.parseInt(peopleRangeBar.getLeftPinValue()), Integer.parseInt(filtersManager.getMaxPeople().toString()));
        } else {
            peopleRangeBar.setRangePinsByIndices(peopleRangeBar.getLeftIndex(), peopleRangeBar.getTickCount()-1);
        }

        if (filtersManager.isMinActualSaleEnabled()) {
            actualSaleRangeBar.setRangePinsByValue(Float.parseFloat(filtersManager.getMinActualSale().toString()), Integer.parseInt(actualSaleRangeBar.getRightPinValue()));
        } else {
            actualSaleRangeBar.setRangePinsByIndices(0, actualSaleRangeBar.getRightIndex());
        }

        if (filtersManager.isMaxActualSaleEnabled()) {
            actualSaleRangeBar.setRangePinsByValue(Float.parseFloat(actualSaleRangeBar.getLeftPinValue()), Float.parseFloat(filtersManager.getMaxActualSale().toString()));
        } else {
            actualSaleRangeBar.setRangePinsByIndices(actualSaleRangeBar.getLeftIndex(), actualSaleRangeBar.getTickCount()-1);
        }

        if (filtersManager.isMinPriceEnabled()) {
            priceRangeBar.setRangePinsByValue(Float.parseFloat(filtersManager.getMinPrice().toString()), Float.parseFloat(priceRangeBar.getRightPinValue()));
        } else {
            priceRangeBar.setRangePinsByIndices(0, priceRangeBar.getRightIndex());
        }

        if (filtersManager.isMaxPriceEnabled()) {
            priceRangeBar.setRangePinsByValue(Float.parseFloat(priceRangeBar.getLeftPinValue()), Float.parseFloat(filtersManager.getMaxPrice().toString()));
        } else {
            priceRangeBar.setRangePinsByIndices(priceRangeBar.getLeftIndex(), priceRangeBar.getTickCount()-1);
        }

        int yearMin; int monthMin; int dayMin; String minDateString; Calendar cal = Calendar.getInstance();
        if (filtersManager.isMinDateEnabled()) {
            cal.setTime(filtersManager.getMinDate());
        }
        yearMin = cal.get(Calendar.YEAR);
        monthMin = cal.get(Calendar.MONTH);
        dayMin = cal.get(Calendar.DAY_OF_MONTH);
        minDateString = dayMin + "/" + (monthMin + 1) + "/" + yearMin;
        openDateFrom.setText("DAL GIORNO: " + minDateString);
        //minDate.updateDate(yearMin, monthMin, dayMin);
        minDate.init(yearMin,monthMin,dayMin, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String minDateString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                openDateFrom.setText("DAL GIORNO: " + minDateString);
            }
        });

        int yearMax; int monthMax; int dayMax; String maxDateString; cal = Calendar.getInstance();
        if (filtersManager.isMaxDateEnabled()) {
            cal.setTime(filtersManager.getMaxDate());
        }
        yearMax = cal.get(Calendar.YEAR);
        monthMax = cal.get(Calendar.MONTH);
        dayMax = cal.get(Calendar.DAY_OF_MONTH);
        maxDateString = dayMax + "/" + (monthMax + 1) + "/" + yearMax;
        openDateTo.setText("AL GIORNO: " + maxDateString);
        //maxDate.updateDate(yearMax, monthMax, dayMax);
        maxDate.init(yearMax,monthMax,dayMax, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String maxDateString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                openDateTo.setText("AL GIORNO: " + maxDateString);
            }
        });

        if(filtersManager.isMinDateEnabled() && filtersManager.isMaxDateEnabled()){
            activateDateFilter.setChecked(true);
        } else {
            activateDateFilter.setChecked(false);
        }

        /*
        if (filtersManager.isMaxDateEnabled()) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(filtersManager.getMaxDate());
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            maxDate.updateDate(year, month, day);
            String maxDateString = day + "/" + (month + 1) + "/" + year;
            openDateTo.setText(openDateTo.getText() + ": " + maxDateString);
        } else {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            maxDate.updateDate(year, month, day);
            String maxDateString = day + "/" + (month + 1) + "/" + year;
            openDateTo.setText(openDateTo.getText() + ": " + maxDateString);
        }
        */
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    // open and close card containing restaurant or category choise
    private void toggleCard(String action, String target){
        CardView target_card_view = null;
        RelativeLayout grey_background = (RelativeLayout) findViewById(R.id.overlay);
        switch (target) {
            case "categories":
                target_card_view = (CardView) findViewById(R.id.categoriesCardView);
                break;
            case "restaurants":
                target_card_view = (CardView) findViewById(R.id.restaurantsCardView);
                break;
            case "date_from":
                target_card_view = (CardView) findViewById(R.id.dateFromCardView);
                break;
            case "date_to":
                target_card_view = (CardView) findViewById(R.id.dateToCardView);
                break;
        }
        switch (action) {
            case "open":
                Visibility.makeVisible(target_card_view);
                Visibility.makeVisible(grey_background);
                break;
            case "close":
                Visibility.makeInvisible(target_card_view);
                Visibility.makeInvisible(grey_background);
        }
    }

    // add action for back button
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void setNewValuesToFiltersManager() {
        // Participants
        if(Float.parseFloat(peopleRangeBar.getLeftPinValue())==(peopleRangeBar.getTickStart())) {
            filtersManager.setMinPeople(null);
        } else {
            filtersManager.setMinPeople(Integer.parseInt(peopleRangeBar.getLeftPinValue()));
        }

        if(Float.parseFloat(peopleRangeBar.getRightPinValue())==(peopleRangeBar.getTickEnd())) {
            filtersManager.setMaxPeople(null);
        } else {
            filtersManager.setMaxPeople(Integer.parseInt(peopleRangeBar.getRightPinValue()));
        }

        // Price range
        if(Float.parseFloat(priceRangeBar.getLeftPinValue())==(priceRangeBar.getTickStart())) {
            filtersManager.setMinPrice(null);
        } else {
            filtersManager.setMinPrice(Double.parseDouble(priceRangeBar.getLeftPinValue()));
        }

        if(Float.parseFloat(priceRangeBar.getRightPinValue())==(priceRangeBar.getTickEnd())) {
            filtersManager.setMaxPrice(null);
        } else {
            filtersManager.setMaxPrice(Double.parseDouble(priceRangeBar.getRightPinValue()));
        }

        // Sale range
        if(Float.parseFloat(actualSaleRangeBar.getLeftPinValue())==(actualSaleRangeBar.getTickStart())) {
            filtersManager.setMinActualSale(null);
        } else {
            filtersManager.setMinActualSale(Double.parseDouble(actualSaleRangeBar.getLeftPinValue()));
        }

        if(Float.parseFloat(actualSaleRangeBar.getRightPinValue())==(actualSaleRangeBar.getTickEnd())) {
            filtersManager.setMaxActualSale(null);
        } else {
            filtersManager.setMaxActualSale(Double.parseDouble(actualSaleRangeBar.getRightPinValue()));
        }

        if(activateDateFilter.isChecked()) {
            // Min date
            filtersManager.setMinDate(DatePickerUtils.getDateFromDatePicker(minDate));

            // Max date
            filtersManager.setMaxDate(DatePickerUtils.getDateFromDatePicker(maxDate));
        } else {
            filtersManager.removeMaxDate();
            filtersManager.removeMinDate();
        }

        // Set categories
        if(selectedCategoriesList.size() > 0) {
            filtersManager.removeCategories();
            for(Category c : selectedCategoriesList) {
                filtersManager.addCategory(c);
            }
            selectedCategoriesList.clear();
        } else {
            filtersManager.removeCategories();
        }

        // Set restaurants
        if(selectedRestaurantsList.size() > 0) {
            filtersManager.removeRestaurants();
            for(Restaurant r : selectedRestaurantsList) {
                filtersManager.addRestaurant(r);
            }
            selectedRestaurantsList.clear();
        } else {
            filtersManager.removeRestaurants();
        }
    }

    private void resetFilter(){
        EatMeetApp.getFiltersManager().resetFilters();
        goToMainActivity();
    }

    private void applyFilters(){
        setNewValuesToFiltersManager();
        goToMainActivity();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(FiltersActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("applyFilters", "1");
        intent.putExtra("destination", "1");
        startActivity(intent);
    }

    private void retrieveCategories(){
        BackendStatusManager backendStatusManager = new BackendStatusManager();
        CategoryDAO categoryDAO = EatMeetApp.getDaoFactory().getCategoryDAO();

        categoriesListView.setAdapter(categoriesArrayAdapter);
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

        categoriesList.setOnAddListener(new OnAddListener() {
            @Override
            public void onAddEvent(Object item) {
                categoriesArrayAdapter.notifyDataSetChanged();
            }
        });

        categoryDAO.getCategories(categoriesList, backendStatusManager);
    }

    private void retrieveRestaurants(){
        BackendStatusManager backendStatusManager = new BackendStatusManager();
        RestaurantDAO restaurantDAO = EatMeetApp.getDaoFactory().getRestaurantDAO();

        restaurantsListView.setAdapter(restaurantsArrayAdapter);
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

        restaurantsList.setOnAddListener(new OnAddListener() {
            @Override
            public void onAddEvent(Object item) {
                restaurantsArrayAdapter.notifyDataSetChanged();
            }
        });

        restaurantDAO.getRestaurants(restaurantsList, backendStatusManager);
    }
}
