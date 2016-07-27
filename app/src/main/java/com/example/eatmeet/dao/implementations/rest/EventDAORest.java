package com.example.eatmeet.dao.implementations.rest;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.connections.HttpRestClient;
import com.example.eatmeet.connections.TokenTextHttpResponseHandler;
import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.utils.Configs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by umberto on 27/07/16.
 */
public class EventDAORest implements EventDAO {
    @Override
    public void getEvents(final List<Event> events, final BackendStatusManager backendStatusManager, JSONObject parameters) {
        HttpRestClient.post(Configs.getBackendUrl() + "/api/events/search", parameters, new TokenTextHttpResponseHandler() {
            @Override
            public void onFailureAction(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                backendStatusManager.addError(responseString,statusCode);
            }

            @Override
            public void onSuccessAction(int statusCode, Header[] headers, String responseString) {
                backendStatusManager.addSuccess(responseString,statusCode);
                Gson gson = new Gson();
                Type collectionType = new TypeToken<List<Event>>(){}.getType();
                for(Event event : (List<Event>) gson.fromJson(responseString, collectionType)) {
                    events.add(event);
                }
            }
        });
    }

    @Override
    public List<Event> getEvents(JSONObject parameters) {
        return null;
    }

    @Override
    public Event getEventById(int id) {
        return null;
    }
}
