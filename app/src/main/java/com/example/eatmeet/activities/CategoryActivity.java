package com.example.eatmeet.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eatmeet.R;
import com.example.eatmeet.dao.CategoryDAO;
import com.example.eatmeet.dao.CategoryDAOImpl;
import com.example.eatmeet.utils.Notificable;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements Notificable {

    private ArrayAdapter<String> categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CategoryDAO categoryDao = new CategoryDAOImpl(this);
        List<String> categoriesList = categoryDao.getCategories();

        categoryAdapter =
                new ArrayAdapter<String>(
                        this, // The current context (this activity)
                        R.layout.list_item_category, // The name of the layout ID.
                        R.id.list_item_category_textview, // The ID of the textview to populate.
                        categoriesList);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) findViewById(R.id.listview_categories);
        listView.setAdapter(categoryAdapter);
    }

    @Override
    public void sendNotify() {
        categoryAdapter.notifyDataSetChanged();
    }
}
