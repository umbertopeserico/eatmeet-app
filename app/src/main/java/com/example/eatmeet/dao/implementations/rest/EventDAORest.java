package com.example.eatmeet.dao.implementations.rest;

import android.util.Log;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.connections.HttpRestClient;
import com.example.eatmeet.connections.TokenFileHttpResponseHandler;
import com.example.eatmeet.connections.TokenTextHttpResponseHandler;
import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.utils.Configs;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;


import org.json.JSONObject;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;

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
                Gson gson = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create();
                Type collectionType = new TypeToken<List<Event>>(){}.getType();
                Log.d("GET EVENTS", responseString);
                List<Event> eventsList = (List<Event>) gson.fromJson(responseString, collectionType);
                for(Event event : eventsList) {
                    events.add(event);
                }
                backendStatusManager.addSuccess(eventsList,statusCode);
            }
        });
    }


    @Override
    public void getImage(String url, final BackendStatusManager backendStatusManager, File cacheDir) {
        String tmpFileName = url.substring(url.lastIndexOf("/") + 1);
        File file = new File(cacheDir, tmpFileName);
        HttpRestClient.get(url, null, new TokenFileHttpResponseHandler(file) {
            @Override
            public void onFailureAction(int statusCode, Header[] headers, Throwable throwable, File file) {
                backendStatusManager.addError("Errore caricamento file", statusCode);
            }

            @Override
            public void onSuccessAction(int statusCode, Header[] headers, File file) {
                System.out.println(file.getAbsolutePath());
                backendStatusManager.addSuccess(file, statusCode);
            }
        });
    }

    @Override
    public void getEvent(Integer id, final BackendStatusManager backendStatusManager) {
        HttpRestClient.get(Configs.getBackendUrl() + "/api/events/" + id, null, new TokenTextHttpResponseHandler() {

            @Override
            public void onFailureAction(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                backendStatusManager.addError(responseString,statusCode);
            }

            @Override
            public void onSuccessAction(int statusCode, Header[] headers, String responseString) {
                Gson gson = new Gson();
                Event event = gson.fromJson(responseString, Event.class);
                backendStatusManager.addSuccess(event,statusCode);
            }

        });
    }

    @Override
    public void addParticipation(Event event, Integer participants, final BackendStatusManager backendStatusManager) {
        final RequestParams requestParams = new RequestParams();
        requestParams.put("event_id", event.getId());
        requestParams.put("seats", participants);
        HttpRestClient.post(Configs.getBackendUrl() + "/api/events/"+event.getId()+"/participation/create", requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                backendStatusManager.addError(responseString, statusCode);
                if(responseString != null) {
                    Log.e("EVENT PARTCIPATION", responseString);
                }
                if(throwable != null) {
                    Log.e("EVENT PARTCIPATION", throwable.getStackTrace().toString());
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                backendStatusManager.addSuccess(responseString, statusCode);
            }
        });
    }

    @Override
    public void removeParticipation(Event event, final BackendStatusManager backendStatusManager) {
        final RequestParams requestParams = new RequestParams();
        requestParams.put("event_id", event.getId());

        HttpRestClient.delete(Configs.getBackendUrl() + "/api/events/"+event.getId()+"/participation/destroy", requestParams, new TokenTextHttpResponseHandler() {
            @Override
            public void onFailureAction(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("ON REM ERR",responseString);
                backendStatusManager.addError(responseString, statusCode);
            }

            @Override
            public void onSuccessAction(int statusCode, Header[] headers, String responseString) {
                Log.i("ON REM SUCC",responseString);
                backendStatusManager.addSuccess(responseString, statusCode);
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
