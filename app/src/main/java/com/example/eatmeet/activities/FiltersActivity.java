package com.example.eatmeet.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.adapters.FilterCategoriesAdapter;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.CategoryDAO;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.observablearraylist.ObservableArrayList;
import com.example.eatmeet.observablearraylist.OnAddListener;
import com.example.eatmeet.utils.Visibility;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FiltersActivity extends AppCompatActivity {
    //public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    private View view;
    ListView listView;
    ArrayAdapter categoriesAdapter;
    /*
    ExpandableListView expandable_people;
    ExpandableListView expandable_date;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        /*add back button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        CategoryDAO categoryDAO = EatMeetApp.getDaoFactory().getCategoryDAO();
        ObservableArrayList<Category> categoryList = new ObservableArrayList<>();
        BackendStatusManager backendStatusManager = new BackendStatusManager();
        listView = (ListView) findViewById(R.id.listview_categories);
        categoriesAdapter = new FilterCategoriesAdapter(this, R.layout.list_item_filter_category, categoryList);
        listView.setAdapter(categoriesAdapter);
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

        categoryList.setOnAddListener(new OnAddListener() {

            @Override
            public void onAddEvent(Object item) {
                categoriesAdapter.notifyDataSetChanged();
                /*
                CardView card_view_categories = (CardView) findViewById(R.id.card_view_categories);
                ListView listview_categories = (ListView) findViewById(R.id.listview_categories);
                LinearLayout list_item_filter_category = (LinearLayout) findViewById(R.id.list_item_filter_category);
                ViewGroup.LayoutParams card_view_categories_params = card_view_categories.getLayoutParams();
                ViewGroup.LayoutParams listview_categories_params = listview_categories.getLayoutParams();
                ViewGroup.LayoutParams list_item_filter_category_params = list_item_filter_category.getLayoutParams();
                //card_view_categories_params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                //listview_categories_params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                //listview_categories_params.height += list_item_filter_category_params.height;
                listview_categories_params.height += 30;
                card_view_categories.setLayoutParams(card_view_categories_params);
                listview_categories.setLayoutParams(listview_categories_params);
                */

            }
        });

        categoryDAO.getCategories(categoryList, backendStatusManager);

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

        CardView card_view_categories_open = (CardView) findViewById(R.id.card_view_categories_open);
        card_view_categories_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardView card_view_categories = (CardView) findViewById(R.id.card_view_categories);
                RelativeLayout grey_background = (RelativeLayout) findViewById(R.id.grey_background);
                Visibility visibility = new Visibility();
                visibility.makeVisible(card_view_categories);
                visibility.makeVisible(grey_background);
            }
        });

        Button card_view_categories_close = (Button) findViewById(R.id.card_view_categories_close);
        card_view_categories_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardView card_view_categories = (CardView) findViewById(R.id.card_view_categories);
                RelativeLayout grey_background = (RelativeLayout) findViewById(R.id.grey_background);
                Visibility visibility = new Visibility();
                visibility.makeInvisible(card_view_categories);
                visibility.makeInvisible(grey_background);
            }
        });


        CardView card_view_restaurants_open = (CardView) findViewById(R.id.card_view_restaurants_open);
        card_view_restaurants_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardView card_view_restaurants = (CardView) findViewById(R.id.card_view_restaurants);
                RelativeLayout grey_background = (RelativeLayout) findViewById(R.id.grey_background);
                Visibility visibility = new Visibility();
                visibility.makeVisible(card_view_restaurants);
                visibility.makeVisible(grey_background);
            }
        });

        Button card_view_restaurants_close = (Button) findViewById(R.id.card_view_restaurants_close);
        card_view_restaurants_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardView card_view_restaurants = (CardView) findViewById(R.id.card_view_restaurants);
                RelativeLayout grey_background = (RelativeLayout) findViewById(R.id.grey_background);
                Visibility visibility = new Visibility();
                visibility.makeInvisible(card_view_restaurants);
                visibility.makeInvisible(grey_background);
            }
        });
        /*
        RangeBar price_rangebar = (RangeBar) findViewById(R.id.rangebar);

        price_rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                System.out.println(Integer.toString(leftPinIndex));
                System.out.println(Integer.toString(rightPinIndex));
                System.out.println(leftPinValue);
                System.out.println(rightPinValue);
            }
        });
        */

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

    public void exitFilter() {
        //closeKeyboard();
        onSupportNavigateUp();
    }

    public void undoFilter(){
        exitFilter();
    }
    public void changeFilter(){
        EatMeetApp.getFiltersManager().removeAllFilters();
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
        exitFilter();
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
