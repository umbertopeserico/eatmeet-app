package com.example.eatmeet.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.appyvet.rangebar.RangeBar;
import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.utils.ExpandableListAdapter;
import com.example.eatmeet.utils.ExpandableListDataPump;
import com.example.eatmeet.utils.Visibility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class FiltersActivity extends AppCompatActivity {
    //public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    private View view;

    ExpandableListView expandable_people;
    ExpandableListView expandable_date;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        /*add back button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
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

        expandable_people = (ExpandableListView) findViewById(R.id.expandable_people);
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
        });
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
        EditText filter_min_people = (EditText) findViewById(R.id.filter_min_people);
        if(filter_min_people.getText().toString() == "ernesto") {
            EatMeetApp.getFiltersManager().setF_min_people(Integer.parseInt(filter_min_people.getText().toString()));
        }
        EditText filter_max_people = (EditText) findViewById(R.id.filter_max_people);
        if(filter_max_people.getText().toString() == "ernesto") {
            EatMeetApp.getFiltersManager().setF_max_people(Integer.parseInt(filter_max_people.getText().toString()));
        }
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
