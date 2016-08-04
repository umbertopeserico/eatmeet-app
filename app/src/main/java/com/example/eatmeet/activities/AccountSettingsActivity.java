package com.example.eatmeet.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;

public class AccountSettingsActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText surnameField;
    private EditText emailField;
    private EditText oldPasswordField;
    private EditText passwordField;
    private EditText passwordConfirmationField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set variables from view
        nameField = (EditText) findViewById(R.id.nameField);
        surnameField = (EditText) findViewById(R.id.surnameField);
        emailField = (EditText) findViewById(R.id.emailField);
        oldPasswordField = (EditText) findViewById(R.id.oldPasswordField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        passwordConfirmationField = (EditText) findViewById(R.id.passwordConfirmationField);

        setOldValues();

    }

    private void setOldValues() {
        nameField.setText(EatMeetApp.getCurrentUser().getName());
        surnameField.setText(EatMeetApp.getCurrentUser().getSurname());
        emailField.setText(EatMeetApp.getCurrentUser().getEmail());
    }

    private void saveSettings() {

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
            case R.id.saveButton:
                saveSettings();
                break;
            case R.id.backButton:
                setOldValues();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
