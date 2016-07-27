package com.example.eatmeet.dao.implementations.oldrest;

import android.util.Log;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.CategoryDAO;
import com.example.eatmeet.utils.Configs;
import com.example.eatmeet.utils.Connection;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.utils.Notificable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.*;

/**
 * Created by sofia on 08/06/2016.
 */
public class CategoryDAOImpl implements CategoryDAO {

    Notificable mNotificable;

    public CategoryDAOImpl(Notificable notificable) {
        mNotificable = notificable;
    }
    /*
    public HashMap<String,String> getMeta(){
        HashMap<String,String> meta = new HashMap<>();
        meta.put("events_count","3");
        return null;
    }
    */
    @Override
    public void getCategories(List<Category> categories, BackendStatusManager backendStatusManager) {

    }

    @Override
    public void getImage(String url, BackendStatusManager backendStatusManager, File cacheDir) {

    }

    @Override
    public List<Category> getCategories() {
        final List<Category> allCategories = new ArrayList<Category>();

        new Connection(){
            @Override public void onPostExecute(String result) {
                Log.d("My tag 2", result);
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray errors = null;
                    try {
                        // se ci sono errori continuo qui (Se il login è failed)
                        errors = obj.getJSONArray("errors");
                        return;

                    } catch (JSONException e) {

                        JSONArray arr = obj.getJSONArray("categories");
                        for (int i = 0; i < arr.length(); i++) {
                            String name = arr.getJSONObject(i).getString("name");
                            int id = arr.getJSONObject(i).getInt("id");
                            //String events_count = arr.getJSONObject(i).getJSONObject("meta").getString("events_count");
                            Integer events_count = arr.getJSONObject(i).getInt("events_count");
                            String icon = arr.getJSONObject(i).getString("image");

                            Category newCategory = new Category();
                            newCategory.setId(id);
                            newCategory.setName(name);
                            newCategory.setImage(icon);

                            newCategory.setEventsCount(events_count);
                            //HashMap<String, String> meta = new HashMap<>();
                            //meta.put("events_count", events_count);
                            //categoryAdapter.clear();
                            allCategories.add(newCategory);
                            mNotificable.sendNotify();
                        }
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(Configs.getBackendUrl() + "/api/categories");

        return allCategories;
    }
}
