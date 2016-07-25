package com.example.eatmeet.dao.implementations.rest;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.connections.HttpRestClient;
import com.example.eatmeet.connections.TokenTextHttpResponseHandler;
import com.example.eatmeet.dao.interfaces.CategoryDAO;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.utils.Configs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.msebera.android.httpclient.Header;

/**
 * Created by umberto on 25/07/16.
 */
public class CategoryDAORest implements CategoryDAO {
    @Override
    public void getCategories(final List<Category> categories, final BackendStatusManager backendStatusManager) {
        HttpRestClient.get(Configs.getBackendUrl() + "/api/categories", null, new TokenTextHttpResponseHandler() {
            @Override
            public void onFailureAction(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, responseString);
                backendStatusManager.addError(responseString);
            }

            @Override
            public void onSuccessAction(int statusCode, Header[] headers, String responseString) {
                backendStatusManager.addSuccess(responseString);
                Gson gson = new Gson();
                Type collectionType = new TypeToken<List<Category>>(){}.getType();
                for(Category category : (List<Category>) gson.fromJson(responseString, collectionType)) {
                    categories.add(category);
                }
            }
        });
    }

    @Override
    public List<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        BackendStatusManager backendStatusManager = new BackendStatusManager();
        try {
            this.getCategories(categories, backendStatusManager);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage());
        }
        return categories;
    }

    @Override
    public HashMap<String, String> getMeta() {
        return null;
    }
}
