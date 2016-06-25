package com.example.eatmeet.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eatmeet.MyApp;
import com.example.eatmeet.R;
import com.example.eatmeet.utils.Configs;
import com.example.eatmeet.utils.Connection;
import com.example.eatmeet.utils.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText passwordField;
    private Button signInButton;
    private Button signUpButton;
    private TextView errorsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inizializzo le variabili di istanza con i riferimenti della vists
        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        signInButton = (Button) findViewById(R.id.signInButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        errorsText = (TextView) findViewById(R.id.errorText);

        final JSONObject loginParams = new JSONObject();


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    loginParams.put("email", emailField.getText().toString());
                    loginParams.put("password", passwordField.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new Post() {
                    @Override
                    public void onPostExecute(String result) {
                        try {
                            JSONObject obj = new JSONObject(result);
                            JSONArray errors = null;
                            try {
                                // se ci sono errori continuo qui (Se il login è failed)
                                errors = obj.getJSONArray("errors");
                                for(int i = 0; i < errors.length(); i++) {
                                    errorsText.setText(errors.getString(i)+"\n");
                                }

                            }
                            catch (JSONException e) {
                                // Se non ci sono errori continuo qui (Se il login è successfull)
                                JSONObject data = obj.getJSONObject("data");
                                for(int i = 0; i < data.length(); i++) {
                                    SharedPreferences.Editor editor = MyApp.sharedPref.edit();
                                    editor.putString("email", emailField.getText().toString());
                                    editor.putString("password", passwordField.getText().toString());
                                    editor.commit();
                                }

                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.execute(Configs.getBackendUrl()+"/api/users/auth/sign_in", loginParams);
            }
        });

    }

}
