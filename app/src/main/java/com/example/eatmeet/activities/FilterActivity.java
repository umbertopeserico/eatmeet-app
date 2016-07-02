package com.example.eatmeet.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.eatmeet.utils.Notificable;

public class FilterActivity extends AppCompatActivity implements Notificable {

    private ArrayAdapter<String> filterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
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

        final ListView listView = (ListView) findViewById(R.id.listview_filters);
        listView.setAdapter(filterAdapter);

        Button filter = (Button) findViewById(R.id.filter);
        assert filter != null;
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {toggleView(listView);}
        });

        Button order = (Button) findViewById(R.id.order);
        assert order != null;
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {toggleView(listView);}
        });
        */
    }

    public void toggleView(View view){
        if(view.getVisibility()==View.GONE)
            view.setVisibility(View.VISIBLE);
        else if(view.getVisibility()==View.VISIBLE)
            view.setVisibility(View.GONE);
    }

    @Override
    public void sendNotify() {
        filterAdapter.notifyDataSetChanged();
    }
}
