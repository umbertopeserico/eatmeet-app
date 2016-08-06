package com.example.eatmeet.dao.implementations.rest;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.connections.HttpRestClient;
import com.example.eatmeet.connections.TokenFileHttpResponseHandler;
import com.example.eatmeet.connections.TokenTextHttpResponseHandler;
import com.example.eatmeet.dao.interfaces.CategoryDAO;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.utils.Configs;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
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
                backendStatusManager.addError(responseString, statusCode);
            }

            @Override
            public void onSuccessAction(int statusCode, Header[] headers, String responseString) {
                Gson gson = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create();
                Type collectionType = new TypeToken<List<Category>>(){}.getType();
                for(Category category : (List<Category>) gson.fromJson(responseString, collectionType)) {
                    categories.add(category);
                }
                backendStatusManager.addSuccess(categories, statusCode);
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
                backendStatusManager.addSuccess(file, statusCode);
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

}
