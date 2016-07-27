package com.example.eatmeet;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.eatmeet.connections.HttpRestClient;
import com.example.eatmeet.dao.factories.DAOFactory;
import com.example.eatmeet.dao.factories.RestDAOFactory;
import com.example.eatmeet.utils.Configs;
import com.example.eatmeet.utils.Post;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by umberto on 25/06/16.
 */
public class EatMeetApp extends Application {
    public static SharedPreferences sharedPref;

    private static final DAOFactory daoFactory = new RestDAOFactory();

    @Override
    public void onCreate() {
        super.onCreate();
        HttpRestClient.setConfigurations(this);
        EatMeetApp.sharedPref= getSharedPreferences(
                getString(R.string.saved_credentials_file), Context.MODE_PRIVATE);
        final JSONObject loginParams = new JSONObject();
        try {
            String email = sharedPref.getString("email", null);
            String password = sharedPref.getString("password", null);
            if( email != null && password != null ) {
                loginParams.put("email", email);
                loginParams.put("password",password);
            }
            else
                return;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*new Post() {
            @Override
            public void onPostExecute(String result) {
                /*try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray errors = null;
                    try {
                        // se ci sono errori continuo qui (Se il login è failed)
                        errors = obj.getJSONArray("errors");

                    }
                    catch (JSONException e) {
                        // Se non ci sono errori continuo qui (Se il login è successfull)
                        JSONObject data = obj.getJSONObject("data");
                        for(int i = 0; i < data.length(); i++) {
                            SharedPreferences.Editor editor = sharedPref.edit();
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
                }*/
            /*}
        }.execute(Configs.getBackendUrl()+"/api/users/auth/sign_in", loginParams);*/
    }

    public static DAOFactory getDaoFactory() {
        return daoFactory;
    }
}
