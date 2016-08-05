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

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.UserDAO;
import com.example.eatmeet.entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText passwordField;
    private Button signInButton;
    private Button signUpButton;
    private Button signOutButton;
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
                loadingBar.setVisibility(View.VISIBLE);
                errorText.setVisibility(View.GONE);
                errorText.setText("");

                final UserDAO userDAO = EatMeetApp.getDaoFactory().getUserDAO();
                User user = new User();
                user.setEmail(emailField.getText().toString());
                user.setPassword(passwordField.getText().toString());

                BackendStatusManager backendStatusManager = new BackendStatusManager();
                backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
                    @Override
                    public void onSuccess(Object response, Integer code) {
                        loadingBar.setVisibility(View.GONE);

                        BackendStatusManager userBSM = new BackendStatusManager();
                        userBSM.setBackendStatusListener(new BackendStatusListener() {
                            @Override
                            public void onSuccess(Object response, Integer code) {
                                User user = (User) response;
                                EatMeetApp.setCurrentUser(user);
                                Log.w("CURRENT USER: ", ""+ response.toString());

                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Object response, Integer code) {
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
                        loadingBar.setVisibility(View.GONE);
                        errorText.setVisibility(View.VISIBLE);
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
                            errorText.setText((String) response);
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

        /*signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDAO userDAO = EatMeetApp.getDaoFactory().getUserDAO();
                BackendStatusManager backendStatusManager = new BackendStatusManager();
                backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
                    @Override
                    public void onSuccess(Object response, Integer code) {
                        EatMeetApp.setCurrentUser(null);
                        Toast.makeText(SignInActivity.this, "Log out effettuato correttamente", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(SignInActivity.this, CategoriesTestActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }, 1500);
                    }

                    @Override
                    public void onFailure(Object response, Integer code) {
                        Toast.makeText(SignInActivity.this, "Errore nel logout. Si prega di riprovare", Toast.LENGTH_SHORT).show();
                    }
                });

                userDAO.signOut(backendStatusManager);
            }
        });*/
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}
