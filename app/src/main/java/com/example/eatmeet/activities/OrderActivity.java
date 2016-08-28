package com.example.eatmeet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;

public class OrderActivity extends AppCompatActivity {

    private Button applyOrder;
    private Button resetOrder;
    private String selectedOrder;
    private String selectedOrderDirection;
    private RadioButton scheduleOrder;
    private RadioButton actualPriceOrder;
    private RadioButton actualSaleOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar()!=null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViewElements();
        setActions();
    }

    private void initViewElements() {
        applyOrder = (Button) findViewById(R.id.applyOrder);
        resetOrder = (Button) findViewById(R.id.resetOrder);
        scheduleOrder = (RadioButton) findViewById(R.id.scheduleOrder);
        actualPriceOrder = (RadioButton) findViewById(R.id.actualPriceOrder);
        actualSaleOrder = (RadioButton) findViewById(R.id.actualSaleOrder);

        if(EatMeetApp.getFiltersManager().getOrderByField().equals("schedule")) {
            scheduleOrder.setChecked(true);
        }

        if(EatMeetApp.getFiltersManager().getOrderByField().equals("actual_price")) {
            actualPriceOrder.setChecked(true);
        }

        if(EatMeetApp.getFiltersManager().getOrderByField().equals("actual_sale")) {
            actualSaleOrder.setChecked(true);
        }
    }

    private void setActions() {
        applyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyOrder();
                goToMainActivity();
            }
        });

        resetOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetOrder();
                goToMainActivity();
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(OrderActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("applyFilters", "1");
        intent.putExtra("applyOrder", "1");
        intent.putExtra("destination", "1");
        startActivity(intent);
    }

    private void applyOrder() {
        EatMeetApp.getFiltersManager().setOrderBy(selectedOrder, selectedOrderDirection);
    }

    private void resetOrder() {
        EatMeetApp.getFiltersManager().resetOrder();
    }

    // add action for back button
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.scheduleOrder:
                if (checked) {
                    selectedOrder = "schedule";
                    selectedOrderDirection = "asc";
                }
                break;
            case R.id.actualPriceOrder:
                if (checked) {
                    selectedOrder = "actual_price";
                    selectedOrderDirection = "asc";
                }
                break;
            case R.id.actualSaleOrder:
                if (checked) {
                    selectedOrder = "actual_sale";
                    selectedOrderDirection = "desc";
                }
                break;
        }
        System.out.println("ORDER: " +selectedOrder+" "+selectedOrderDirection);
    }
}
