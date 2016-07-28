package com.example.eatmeet.dao.implementations.rest;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.connections.HttpRestClient;
import com.example.eatmeet.connections.TokenTextHttpResponseHandler;
import com.example.eatmeet.dao.interfaces.RestaurantDAO;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.entities.Event;
import com.example.eatmeet.entities.Restaurant;
import com.example.eatmeet.utils.Configs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by umberto on 27/07/16.
 */
public class RestaurantDAORest implements RestaurantDAO {
    @Override
    public void getRestaurants(final List<Restaurant> restaurants, final BackendStatusManager backendStatusManager) {
        HttpRestClient.get(Configs.getBackendUrl() + "/api/restaurants", null, new TokenTextHttpResponseHandler() {
            @Override
            public void onFailureAction(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                backendStatusManager.addError(responseString, statusCode);
            }

            @Override
            public void onSuccessAction(int statusCode, Header[] headers, String responseString) {
                backendStatusManager.addSuccess(responseString, statusCode);
                Gson gson = new Gson();
                Type collectionType = new TypeToken<List<Restaurant>>(){}.getType();
                for(Restaurant restaurant : (List<Restaurant>) gson.fromJson(responseString, collectionType)) {
                    restaurants.add(restaurant);
                }
            }
        });
    }

    @Override
    public List<Restaurant> getRestaurants() {
        return null;
    }

    @Override
    public Event getClosestEvent(Restaurant restaurant) {
        return null;
    }
}