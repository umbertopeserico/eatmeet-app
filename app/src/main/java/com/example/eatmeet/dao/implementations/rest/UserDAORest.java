package com.example.eatmeet.dao.implementations.rest;

import android.util.Log;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.connections.HttpRestClient;
import com.example.eatmeet.connections.TokenTextHttpResponseHandler;
import com.example.eatmeet.dao.interfaces.UserDAO;
import com.example.eatmeet.entities.User;
import com.example.eatmeet.utils.Configs;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by umberto on 28/07/16.
 */
public class UserDAORest implements UserDAO {
    @Override
    public void getUsers(List<User> users, BackendStatusManager backendStatusManager) {

    }

    @Override
    public void authenticate(User user, final BackendStatusManager backendStatusManager) {
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
                backendStatusManager.addSuccess(responseString, statusCode);
            }
        });
    }

    @Override
    public void unauthenticate(final BackendStatusManager backendStatusManager) {
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
    public List<User> getUsers() {
        return null;
    }
}
