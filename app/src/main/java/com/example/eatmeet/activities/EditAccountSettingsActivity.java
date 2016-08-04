package com.example.eatmeet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;

import com.example.eatmeet.dao.interfaces.UserDAO;
import com.example.eatmeet.entities.User;
import com.example.eatmeet.entities.errors.ErrorsMap;

import java.util.List;
import java.util.Map;

public class EditAccountSettingsActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText surnameField;
    private EditText emailField;
    private EditText oldPasswordField;
    private EditText passwordField;
    private EditText passwordConfirmationField;
    private TextView nameErrors;
    private TextView surnameErrors;
    private TextView emailErrors;
    private TextView oldPasswordErrors;
    private TextView passwordErrors;
    private TextView passwordConfirmationErrors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account_settings);
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

        nameErrors = (TextView) findViewById(R.id.nameErrors);
        surnameErrors = (TextView) findViewById(R.id.surnameErrors);
        emailErrors = (TextView) findViewById(R.id.emailErrors);
        oldPasswordErrors = (TextView) findViewById(R.id.oldPasswordErrors);
        passwordErrors = (TextView) findViewById(R.id.passwordErrors);
        passwordConfirmationErrors = (TextView) findViewById(R.id.passwordConfirmationErrors);

        setOldValues();
    }

    private void setOldValues() {
        nameField.setText(EatMeetApp.getCurrentUser().getName());
        surnameField.setText(EatMeetApp.getCurrentUser().getSurname());
        emailField.setText(EatMeetApp.getCurrentUser().getEmail());

        nameErrors.setVisibility(View.GONE);
        surnameErrors.setVisibility(View.GONE);
        emailErrors.setVisibility(View.GONE);
        oldPasswordErrors.setVisibility(View.GONE);
        passwordErrors.setVisibility(View.GONE);
        passwordConfirmationErrors.setVisibility(View.GONE);
    }

    private void saveSettings() {
        nameErrors.setVisibility(View.GONE);
        surnameErrors.setVisibility(View.GONE);
        emailErrors.setVisibility(View.GONE);
        oldPasswordErrors.setVisibility(View.GONE);
        passwordErrors.setVisibility(View.GONE);
        passwordConfirmationErrors.setVisibility(View.GONE);

        User user = new User();
        user.setName(nameField.getText().toString());
        user.setSurname(surnameField.getText().toString());
        user.setEmail(emailField.getText().toString());
        user.setPassword(passwordField.getText().toString());
        user.setPasswordConfirmation(passwordConfirmationField.getText().toString());
        user.setOldPassword(oldPasswordField.getText().toString());

        UserDAO userDAO = EatMeetApp.getDaoFactory().getUserDAO();
        BackendStatusManager userBSM = new BackendStatusManager();
        userBSM.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                EatMeetApp.setCurrentUser((User) response);
                Toast.makeText(EditAccountSettingsActivity.this, "Salvataggio completato", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditAccountSettingsActivity.this, AccountSettingsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Object response, Integer code) {
                ErrorsMap errorsMap = (ErrorsMap) response;

                if(errorsMap.get("name")!=null) {
                    List<String> errors = errorsMap.get("name");
                    for(String e : errors) {
                        int index = errors.lastIndexOf(e);
                        String ret = "\n";
                        if(index == errors.size()-1) {
                            ret = "";
                        }
                        nameErrors.setText(e+ret);
                        nameErrors.setVisibility(View.VISIBLE);
                    }
                }

                if(errorsMap.get("surname")!=null) {
                    List<String> errors = errorsMap.get("surname");
                    for(String e : errors) {
                        int index = errors.lastIndexOf(e);
                        String ret = "\n";
                        if(index == errors.size()-1) {
                            ret = "";
                        }
                        surnameErrors.setText(e+ret);
                        surnameErrors.setVisibility(View.VISIBLE);
                    }
                }

                if(errorsMap.get("email")!=null) {
                    List<String> errors = errorsMap.get("email");
                    for(String e : errors) {
                        int index = errors.lastIndexOf(e);
                        String ret = "\n";
                        if(index == errors.size()-1) {
                            ret = "";
                        }
                        emailErrors.setText(e+ret);
                        emailErrors.setVisibility(View.VISIBLE);
                    }
                }

                if(errorsMap.get("old_password")!=null) {
                    List<String> errors = errorsMap.get("old_password");
                    for(String e : errors) {
                        int index = errors.lastIndexOf(e);
                        String ret = "\n";
                        if(index == errors.size()-1) {
                            ret = "";
                        }
                        oldPasswordErrors.setText(e+ret);
                        oldPasswordErrors.setVisibility(View.VISIBLE);
                    }
                }

                if(errorsMap.get("password")!=null) {
                    List<String> errors = errorsMap.get("password");
                    for(String e : errors) {
                        int index = errors.lastIndexOf(e);
                        String ret = "\n";
                        if(index == errors.size()-1) {
                            ret = "";
                        }
                        passwordErrors.setText(e+ret);
                        passwordErrors.setVisibility(View.VISIBLE);
                    }
                }

                if(errorsMap.get("password_confirmation")!=null) {
                    List<String> errors = errorsMap.get("password_confirmation");
                    for(String e : errors) {
                        int index = errors.lastIndexOf(e);
                        String ret = "\n";
                        if(index == errors.size()-1) {
                            ret = "";
                        }
                        passwordConfirmationErrors.setText(e+ret);
                        passwordConfirmationErrors.setVisibility(View.VISIBLE);
                    }
                }

                Toast.makeText(EditAccountSettingsActivity.this, "Errore nel salvataggio", Toast.LENGTH_SHORT).show();
            }
        });
        userDAO.updateProfile(user, userBSM);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_account_settings, menu);
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
