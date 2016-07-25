package com.example.eatmeet.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.adapters.CategoriesAdapter;
import com.example.eatmeet.adapters.CategoriesAdapterTest;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.CategoryDAO;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.observablearraylist.ObservableArrayList;
import com.example.eatmeet.observablearraylist.OnAddListener;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestActivity extends AppCompatActivity {

    ListView categoryListView;
    CategoriesAdapterTest arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        CategoryDAO categoryDAO = EatMeetApp.getDaoFactory().getCategoryDAO();
        ObservableArrayList<Category> categoryList = new ObservableArrayList<>();
        BackendStatusManager backendStatusManager = new BackendStatusManager();
        categoryListView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new CategoriesAdapterTest(this, R.layout.list_item_category, (List<Category>) categoryList);
        categoryListView.setAdapter(arrayAdapter);

        categoryList.setOnAddListener(new OnAddListener() {
            @Override
            public void onAddEvent(Object item) {
                arrayAdapter.notifyDataSetChanged();
            }
        });

        backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess() {
                Logger.getLogger(TestActivity.this.getClass().getName()).log(Level.INFO, "Connection succeded");
            }

            @Override
            public void onFailure() {
                Logger.getLogger(TestActivity.this.getClass().getName()).log(Level.SEVERE, "Connection NOT succeded");
            }
        });

        categoryDAO.getCategories(categoryList, backendStatusManager);
    }

}
