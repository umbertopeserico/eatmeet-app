package com.example.eatmeet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.eatmeet.utils.Visibility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText passwordField;
    private Button signInButton;
    private Button signUpButton;
    private TextView errorText;
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Inizializzo le variabili di istanza con i riferimenti della vists
        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        signInButton = (Button) findViewById(R.id.signInButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        errorText = (TextView) findViewById(R.id.errorText);
        loadingBar = (ProgressBar) findViewById(R.id.loadingBar);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Visibility.makeVisible(loadingBar);
                Visibility.makeInvisible(errorText);
                errorText.setText("");

                final UserDAO userDAO = EatMeetApp.getDaoFactory().getUserDAO();
                User user = new User();
                user.setEmail(emailField.getText().toString());
                user.setPassword(passwordField.getText().toString());

                BackendStatusManager backendStatusManager = new BackendStatusManager();
                backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
                    @Override
                    public void onSuccess(Object response, Integer code) {
                        BackendStatusManager userBSM = new BackendStatusManager();
                        userBSM.setBackendStatusListener(new BackendStatusListener() {
                            @Override
                            public void onSuccess(Object response, Integer code) {
                                Visibility.makeInvisible(loadingBar);
                                User user = (User) response;
                                EatMeetApp.setCurrentUser(user);
                                Log.w("CURRENT USER: ", ""+ response.toString());

                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Object response, Integer code) {
                                Visibility.makeInvisible(loadingBar);
                                EatMeetApp.setCurrentUser(null);
                                Log.e("CURRENT USER: ", "Retrieve current user failed: "+code);
                            }
                        });
                        User user = new User();
                        user.setId((Integer) response);
                        userDAO.getUser(user, userBSM);
                    }

                    @Override
                    public void onFailure(Object response, Integer code) {
                        Visibility.makeInvisible(loadingBar);
                        Visibility.makeVisible(errorText);
                        if(code == 401) {
                            try {
                                String errorString = "";
                                JSONObject jsonResponse = new JSONObject((String) response);
                                JSONArray errors = jsonResponse.getJSONArray("errors");
                                for (int i = 0; i < errors.length(); i++) {
                                    errorString += errors.get(i);
                                    if(i!=errors.length()-1)  {
                                        errorString+= "\n";
                                    }
                                }
                                errorText.setText(errorString);
                            } catch (JSONException e) {
                                Log.e("JSON PARSE: ", "Signin response not decoded:\n" + e.getMessage());
                            }
                        } else {
                            Visibility.makeInvisible(loadingBar);
                            Visibility.makeInvisible(errorText);
                            Toast.makeText(SignInActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                userDAO.signIn(user, backendStatusManager);
            }
        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}
