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
import com.example.eatmeet.utils.Visibility;

import org.json.JSONException;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FiltersActivity extends AppCompatActivity {
    //public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    private View view;
    ListView listView;
    ArrayAdapter arrayAdapter;
    /*
    ExpandableListView expandable_people;
    ExpandableListView expandable_date;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    */
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

        /*
        listView.addOnLayoutChangeListener(
                new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View _, int __, int ___, int ____, int bottom, int _____, int ______,
                                               int _______, int old_bottom) {
                        //final ListView list_view = listView;
                        final ViewGroup.LayoutParams params = listView.getLayoutParams();
                        params.height += (old_bottom+bottom);
                        listView.setLayoutParams(params);
                        listView.requestLayout();
                    }
            });
        */


        /*
        CategoryDAO categoryDao = new CategoryDAOImpl(this);
        List<Category> categoriesList = categoryDao.getCategories();

        categoryAdapter = new CategoriesAdapter(getContext(), R.layout.list_item_category, categoriesList);

        ListView listView = (ListView) view.findViewById(R.id.listview_categories);
        listView.setAdapter(categoryAdapter);
        */
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

        Date today = new Date();
        datepicker_max_date.init(today.getYear(), today.getMonth(), today.getDay(), new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Date newMaxDate = new Date(year,monthOfYear,dayOfMonth);
                EatMeetApp.getFiltersManager().setF_max_date(newMaxDate);
            }
        });
        datepicker_min_date.init(today.getYear(), today.getMonth(), today.getDay(), new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Date newMinxDate = new Date(year,monthOfYear,dayOfMonth);
                EatMeetApp.getFiltersManager().setF_min_date(newMinxDate);
            }
        });

        /*
        CheckBox checkbox_category = (CheckBox) findViewById(R.id.checkBoxListItemFilterCategory);
        if (checkbox_category != null) {
            checkbox_category.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    List<Integer> alreadySetCategories = EatMeetApp.getFiltersManager().getF_categories();
                    System.out.println("aggiungo/tolgo la categoria " + buttonView.getId());
                    if(isChecked){
                        alreadySetCategories.add(buttonView.getId());
                    } else {
                        alreadySetCategories.remove(buttonView.getId());
                    }
                }
            });
        }

        CheckBox checkbox_restaurant = (CheckBox) findViewById(R.id.checkBoxListItemFilterRestaurant);
        if (checkbox_restaurant != null) {
            checkbox_restaurant.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    List<Integer> alreadySetRestaurants = EatMeetApp.getFiltersManager().getF_restaurants();
                    System.out.println("aggiungo/tolgo il ristorante " + buttonView.getId());
                    if(isChecked){
                        alreadySetRestaurants.add(buttonView.getId());
                    } else {
                        alreadySetRestaurants.remove(buttonView.getId());
                    }
                }
            });
        }
        */

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

        /*expandable_people = (ExpandableListView) findViewById(R.id.expandable_people);
        expandable_date = (ExpandableListView) findViewById(R.id.expandable_date);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new ExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandable_people.setAdapter(expandableListAdapter);
        expandable_date.setAdapter(expandableListAdapter);
        expandable_people.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        expandable_date.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandable_people.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });
        expandable_date.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandable_people.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });
        expandable_date.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });*/
    }

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
        /*
        Intent intent = NavUtils.getParentActivityIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        NavUtils.navigateUpTo(this, intent);
        */
        finish();
        return true;
    }

    public void closeKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void exitFilter() {
        //closeKeyboard();
        onSupportNavigateUp();
    }

    public void undoFilter(){
        EatMeetApp.getFiltersManager().resetOldFilters();
        exitFilter();
    }
    public void changeFilter(){
        try {
            EatMeetApp.getFiltersManager().constructParameters();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(FiltersActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("from", "FiltersActivity");
        startActivity(intent);
        /*
        EditText filter_min_people = (EditText) findViewById(R.id.filter_min_people);
        if(filter_min_people.getText().toString() == "ernesto") {
            EatMeetApp.getFiltersManager().setF_min_people(Integer.parseInt(filter_min_people.getText().toString()));
        }
        EditText filter_max_people = (EditText) findViewById(R.id.filter_max_people);
        if(filter_max_people.getText().toString() == "ernesto") {
            EatMeetApp.getFiltersManager().setF_max_people(Integer.parseInt(filter_max_people.getText().toString()));
        }
        */
        //rangebar_people
        //refresh();
    }

    /*
    public void sendMessage(View view) {
        Intent intent = new Intent(getContext(), FilterActivity.class);

        //intent.putExtra(EXTRA_MESSAGE, "2");
        startActivity(intent);
    }
    */

}
