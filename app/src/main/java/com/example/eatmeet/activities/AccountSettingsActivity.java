package com.example.eatmeet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;

public class AccountSettingsActivity extends AppCompatActivity {

    private TextView fullNameField;
    private TextView emailField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fullNameField = (TextView) findViewById(R.id.fullNameField);
        emailField = (TextView) findViewById(R.id.emailField);

        fullNameField.setText(EatMeetApp.getCurrentUser().getFullName());
        emailField.setText(EatMeetApp.getCurrentUser().getEmail());

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editButton:
                Intent intent = new Intent(AccountSettingsActivity.this, EditAccountSettingsActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
