package com.example.eatmeet.dao.implementations.rest;

import android.util.Log;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.connections.HttpRestClient;
import com.example.eatmeet.connections.TokenTextHttpResponseHandler;
import com.example.eatmeet.dao.interfaces.UserDAO;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.entities.User;
import com.example.eatmeet.utils.Configs;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

/**
 * Created by umberto on 28/07/16.
 */
public class UserDAORest implements UserDAO {
    @Override
    public void getUsers(List<User> users, BackendStatusManager backendStatusManager) {

    }

    @Override
    public void signIn(User user, final BackendStatusManager backendStatusManager) {
        final RequestParams requestParams = new RequestParams();
        requestParams.add("email", user.getEmail());
        requestParams.add("password", user.getPassword());
        HttpRestClient.post(Configs.getBackendUrl() + "/api/users/auth/sign_in", requestParams, new TokenTextHttpResponseHandler() {
            @Override
            public void onFailureAction(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if(responseString!=null) {
                    backendStatusManager.addError(responseString, statusCode);
                } else {
                    backendStatusManager.addError(throwable.getMessage(), statusCode);
                }
            }

            @Override
            public void onSuccessAction(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject data = new JSONObject(responseString).getJSONObject("data");
                    Integer id = Integer.parseInt(data.getString("id"));
                    backendStatusManager.addSuccess(id, statusCode);
                } catch (JSONException e) {
                    Log.e("SIGN IN JSON DECODE", e.getMessage());
                }
            }
        });
    }

    @Override
    public void signOut(final BackendStatusManager backendStatusManager) {
        //
        HttpRestClient.delete(Configs.getBackendUrl() + "/api/users/auth/sign_out", null, new TokenTextHttpResponseHandler() {
            @Override
            public void onFailureAction(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                backendStatusManager.addError(responseString, statusCode);
            }

            @Override
            public void onSuccessAction(int statusCode, Header[] headers, String responseString) {
                backendStatusManager.addSuccess(responseString, statusCode);
                HttpRestClient.getCookieStore().clear();
            }
        });
    }

    @Override
    public void signUp(final User user, final BackendStatusManager backendStatusManager) {
        final RequestParams requestParams = new RequestParams();
        //requestParams.setUseJsonStreamer(true);
        System.out.println(user.getName()+"ciao");
        requestParams.put("name", user.getName());
        requestParams.put("surname", user.getSurname());
        requestParams.put("email", user.getEmail());
        requestParams.put("password", user.getPassword());
        requestParams.put("password_confirmation", user.getPasswordConfirmation());

        HttpRestClient.post(Configs.getBackendUrl() + "/api/users/auth", requestParams, new TokenTextHttpResponseHandler() {
            @Override
            public void onFailureAction(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                backendStatusManager.addError(responseString, statusCode);

                if(responseString==null) {
                    responseString = throwable.getMessage();
                }

                try {
                    JSONObject jsonResponse = new JSONObject(responseString);
                    JSONObject errors = jsonResponse.getJSONObject("errors");

                    JSONArray nameFields = errors.names();

                    for(int i=0; i< nameFields.length(); i++) {
                        JSONArray errorList = errors.getJSONArray(nameFields.getString(i));
                        for(int j = 0; j<errorList.length(); j++) {
                            Pattern p = Pattern.compile("_(.)");
                            Matcher m = p.matcher(errorList.getString(j));
                            StringBuffer sb = new StringBuffer();
                            while (m.find()) {
                                m.appendReplacement(sb, m.group(1).toUpperCase());
                            }
                            m.appendTail(sb);
                            String currentError = sb.toString();
                            user.addError(nameFields.getString(i), currentError);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccessAction(int statusCode, Header[] headers, String responseString) {
                backendStatusManager.addSuccess(responseString, statusCode);
            }
        });
    }

    @Override
    public void getUser(User user, final BackendStatusManager backendStatusManager) {
        final RequestParams requestParams = new RequestParams();
        requestParams.put("id", user.getId());
        HttpRestClient.get(Configs.getBackendUrl() + "/api/user_profile", requestParams, new TokenTextHttpResponseHandler() {
            @Override
            public void onFailureAction(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                backendStatusManager.addError(responseString, statusCode);
                System.out.println(responseString);
                System.out.println(throwable.getMessage());
                System.out.println("GET USER FAIL: "+responseString);
            }

            @Override
            public void onSuccessAction(int statusCode, Header[] headers, String responseString) {
                Gson gson = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create();
                User user = gson.fromJson(responseString, User.class);
                backendStatusManager.addSuccess(user, statusCode);

            }
        });
    }

    @Override
    public void validateToken(final BackendStatusManager backendStatusManager) {
        HttpRestClient.get(Configs.getBackendUrl() + "/api/users/auth/validate_token", null, new TokenTextHttpResponseHandler() {
            @Override
            public void onFailureAction(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                backendStatusManager.addError(responseString, statusCode);
            }

            @Override
            public void onSuccessAction(int statusCode, Header[] headers, String responseString) {

                backendStatusManager.addSuccess(responseString, statusCode);
            }
        });
    }

    @Override
    public List<User> getUsers() {
        return null;
    }
}
