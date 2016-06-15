package com.example.eatmeet.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eatmeet.R;
import com.example.eatmeet.adapters.FiltersAdapter;
import com.example.eatmeet.dao.EventDAO;
import com.example.eatmeet.dao.EventDAOImpl;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.utils.Notificable;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity implements Notificable {

    private ArrayAdapter<String> filterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        EventDAO eventDao = new EventDAOImpl(this);
        List<String> filterList = new ArrayList<>();
        filterList.add("Prezzo");
        filterList.add("Categoria");
        filterList.add("Date");
        filterList.add("Vicinanza");
        filterList.add("Sconto");
        filterList.add("Partecipanti");
        filterList.add("Ristorante");

        filterAdapter = new FiltersAdapter(this, R.layout.list_item_filter, filterList);

        ListView listView = (ListView) findViewById(R.id.listview_filters);
        listView.setAdapter(filterAdapter);
    }

    @Override
    public void sendNotify() {
        filterAdapter.notifyDataSetChanged();
    }
}
