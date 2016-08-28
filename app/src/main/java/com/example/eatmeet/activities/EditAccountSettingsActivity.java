package com.example.eatmeet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;

import com.example.eatmeet.dao.interfaces.UserDAO;
import com.example.eatmeet.entities.User;
import com.example.eatmeet.entities.errors.ErrorsMap;
import com.example.eatmeet.utils.Visibility;

import java.util.List;

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
    private ProgressBar loadingBar;

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

        loadingBar = (ProgressBar) findViewById(R.id.loadingBar);

        setOldValues();
    }

    private void setOldValues() {
        nameField.setText(EatMeetApp.getCurrentUser().getName());
        surnameField.setText(EatMeetApp.getCurrentUser().getSurname());
        emailField.setText(EatMeetApp.getCurrentUser().getEmail());

        Visibility.makeInvisible(nameErrors);
        Visibility.makeInvisible(surnameErrors);
        Visibility.makeInvisible(emailErrors);
        Visibility.makeInvisible(oldPasswordErrors);
        Visibility.makeInvisible(passwordErrors);
        Visibility.makeInvisible(passwordConfirmationErrors);
    }

    private void saveSettings() {
        Visibility.makeInvisible(nameErrors);
        Visibility.makeInvisible(surnameErrors);
        Visibility.makeInvisible(emailErrors);
        Visibility.makeInvisible(oldPasswordErrors);
        Visibility.makeInvisible(passwordErrors);
        Visibility.makeInvisible(passwordConfirmationErrors);

        User user = new User();
        user.setName(nameField.getText().toString());
        user.setSurname(surnameField.getText().toString());
        user.setEmail(emailField.getText().toString());
        user.setPassword(passwordField.getText().toString());
        user.setPasswordConfirmation(passwordConfirmationField.getText().toString());
        user.setCurrentPassword(oldPasswordField.getText().toString());

        UserDAO userDAO = EatMeetApp.getDaoFactory().getUserDAO();
        BackendStatusManager userBSM = new BackendStatusManager();
        userBSM.setBackendStatusListener(new BackendStatusListener() {
            @Override
            public void onSuccess(Object response, Integer code) {
                Visibility.makeInvisible(loadingBar);
                EatMeetApp.setCurrentUser((User) response);
                Toast.makeText(EditAccountSettingsActivity.this, R.string.account_settings_save_success, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditAccountSettingsActivity.this, AccountSettingsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Object response, Integer code) {
                Visibility.makeInvisible(loadingBar);
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
                        Visibility.makeVisible(nameErrors);
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
                        Visibility.makeVisible(surnameErrors);
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
                        Visibility.makeVisible(emailErrors);
                    }
                }

                if(errorsMap.get("current_password")!=null) {
                    List<String> errors = errorsMap.get("current_password");
                    for(String e : errors) {
                        int index = errors.lastIndexOf(e);
                        String ret = "\n";
                        if(index == errors.size()-1) {
                            ret = "";
                        }
                        oldPasswordErrors.setText(e+ret);
                        Visibility.makeVisible(oldPasswordErrors);
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
                        Visibility.makeVisible(passwordErrors);
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
                        Visibility.makeVisible(passwordConfirmationErrors);
                    }
                }

                Toast.makeText(EditAccountSettingsActivity.this, R.string.account_settings_save_failure, Toast.LENGTH_LONG).show();
            }
        });
        userDAO.updateProfile(user, userBSM);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(EditAccountSettingsActivity.this, AccountSettingsActivity.class);
        startActivity(intent);
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
                Visibility.makeVisible(loadingBar);
                saveSettings();
                break;
            case R.id.backButton:
                setOldValues();
                Toast.makeText(EditAccountSettingsActivity.this, R.string.account_setting_reset, Toast.LENGTH_LONG).show();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
