package com.example.eatmeet.mainactivityfragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eatmeet.R;
import com.example.eatmeet.adapters.CategoriesAdapter;
import com.example.eatmeet.dao.CategoryDAO;
import com.example.eatmeet.dao.CategoryDAOImpl;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.utils.Notificable;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment implements Notificable {

    private ArrayAdapter<String> categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        //setContentView(R.layout.activity_category);

        CategoryDAO categoryDao = new CategoryDAOImpl(this);
        List<Category> categoriesList = categoryDao.getCategories();

        categoryAdapter = new CategoriesAdapter(getContext(), R.layout.list_item_category, categoriesList);
        /*categoryAdapter =
                new ArrayAdapter<String>(
                        this, // The current context (this activity)
                        R.layout.list_item_category, // The name of the layout ID.
                        R.id.list_item_category_textview, // The ID of the textview to populate.
                        categoriesList);*/

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) view.findViewById(R.id.listview_categories);
        listView.setAdapter(categoryAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void sendNotify() {
        categoryAdapter.notifyDataSetChanged();
    }
}


/*
    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

}
*/