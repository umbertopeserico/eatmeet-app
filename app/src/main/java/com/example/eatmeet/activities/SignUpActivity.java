package com.example.eatmeet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.activitiestest.CategoriesTestActivity;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.UserDAO;
import com.example.eatmeet.entities.User;
import com.example.eatmeet.entities.errors.ErrorsMap;

import java.util.List;

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
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        loadingBar = (ProgressBar) findViewById(R.id.loadingBar);

        final User user = new User();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.cleanErrors();
                SignUpActivity.this.cleanErrors();
                loadingBar.setVisibility(View.VISIBLE);
                user.setName(name.getText().toString());
                user.setSurname(surname.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPassword(password.getText().toString());
                user.setPasswordConfirmation(passwordConfirmation.getText().toString());
                final UserDAO userDAO = EatMeetApp.getDaoFactory().getUserDAO();

                BackendStatusManager backendStatusManager = new BackendStatusManager();
                backendStatusManager.setBackendStatusListener(new BackendStatusListener() {
                    @Override
                    public void onSuccess(Object response, Integer code) {
                        BackendStatusManager signInBM = new BackendStatusManager();
                        signInBM.setBackendStatusListener(new BackendStatusListener() {
                            @Override
                            public void onSuccess(Object response, Integer code) {
                                loadingBar.setVisibility(View.GONE);
                                Intent intent = new Intent(SignUpActivity.this, CategoriesTestActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Object response, Integer code) {
                                loadingBar.setVisibility(View.GONE);
                                Toast.makeText(SignUpActivity.this, "Errore nel login interno. Si prega di riprovare", Toast.LENGTH_SHORT).show();
                            }
                        });
                        userDAO.signIn(user, signInBM);
                    }

                    @Override
                    public void onFailure(Object response, Integer code) {
                        loadingBar.setVisibility(View.GONE);
                        if(response!=null) {
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
                            Toast.makeText(SignUpActivity.this, R.string.registration_error, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                        }
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

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
