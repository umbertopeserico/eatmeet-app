package com.example.eatmeet.activities;

import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.RestaurantDAO;
import com.example.eatmeet.dao.interfaces.UserDAO;
import com.example.eatmeet.entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText password;
    private EditText passwordConfirmation;
    private TextView nameErrors;
    private TextView surnameErrors;
    private TextView emailErrors;
    private TextView passwordErrors;
    private TextView passwordConfirmationErrors;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inizializzo parametri della vista
        name = (EditText) findViewById(R.id.nameField);
        surname = (EditText) findViewById(R.id.surnameField);
        email = (EditText) findViewById(R.id.emailField);
        password = (EditText) findViewById(R.id.passwordField);
        passwordConfirmation = (EditText) findViewById(R.id.passwordConfirmationField);

        nameErrors = (TextView) findViewById(R.id.nameErrors);
        surnameErrors = (TextView) findViewById(R.id.surnameErrors);
        emailErrors = (TextView) findViewById(R.id.emailErrors);
        passwordErrors = (TextView) findViewById(R.id.passwordErrors);
        passwordConfirmationErrors = (TextView) findViewById(R.id.passwordConfirmationErrors);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        final User user = new User();

        user.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {

                if(event.getPropertyName().equals("addError")) {
                    for(String fieldName : user.getErrors().keySet()) {
                        for(String error : user.getErrors().get(fieldName)) {
                            switch (fieldName) {
                                case "name":
                                    nameErrors.setVisibility(View.VISIBLE);
                                    nameErrors.setText(error+"\n");
                                    break;
                                case "surname":
                                    surnameErrors.setText(error+"\n");
                                    surnameErrors.setVisibility(View.VISIBLE);
                                    break;
                                case "email":
                                    emailErrors.setText(error+"\n");
                                    emailErrors.setVisibility(View.VISIBLE);
                                    break;
                                case "password":
                                    passwordErrors.setText(error+"\n");
                                    passwordErrors.setVisibility(View.VISIBLE);
                                    break;
                                case "password_confirmaion":
                                    passwordConfirmationErrors.setText(error+"\n");
                                    passwordConfirmationErrors.setVisibility(View.VISIBLE);
                                    break;
                                default:

                            }
                        }
                    }
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.cleanErrors();
                SignUpActivity.this.cleanErrors();
                user.setName(name.getText().toString());
                user.setSurname(surname.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPassword(password.getText().toString());
                user.setPasswordConfirmation(passwordConfirmation.getText().toString());
                UserDAO userDAO = EatMeetApp.getDaoFactory().getUserDAO();

                BackendStatusManager backendStatusManager = new BackendStatusManager();
                backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
                    @Override
                    public void onSuccess(Object response, Integer code) {
                        System.out.println(response + "CODE:"+code);
                    }

                    @Override
                    public void onFailure(Object response, Integer code) {

                            System.out.println(response + "CODE:"+code);
                    }
                });

                userDAO.signUp(user, backendStatusManager);
            }
        });
    }

    private void cleanErrors() {
        nameErrors.setVisibility(View.GONE);
        nameErrors.setText("");
        surnameErrors.setText("");
        surnameErrors.setVisibility(View.GONE);
        emailErrors.setText("");
        emailErrors.setVisibility(View.GONE);
        passwordErrors.setText("");
        passwordErrors.setVisibility(View.GONE);
        passwordConfirmationErrors.setText("");
        passwordConfirmationErrors.setVisibility(View.GONE);
    }
}
