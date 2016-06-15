package com.example.eatmeet.dao;

import android.util.Log;

import com.example.eatmeet.utils.Configs;
import com.example.eatmeet.utils.Connection;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.utils.Notificable;

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

    public HashMap<String,String> getMeta(){
        HashMap<String,String> meta = new HashMap<>();
        meta.put("events_count","3");
        return null;
    }

    @Override
    public List<Category> getCategories() {
        /*
        Category sushi = new Category();
        sushi.setId(1);
        sushi.setMeta(this.getMeta());
        sushi.setName("sushi");

        Category vino = new Category();
        vino.setId(2);
        vino.setMeta(this.getMeta());
        vino.setName("vino");

        Category biologico = new Category();
        biologico.setId(3);
        biologico.setMeta(this.getMeta());
        biologico.setName("biologico");
        */

        final List<Category> allCategories = new ArrayList<Category>();

        /*
        allCategories.add(sushi);
        allCategories.add(vino);
        allCategories.add(biologico);
        */

        new Connection(){
            @Override public void onPostExecute(String result)
            {
                Log.d("My tag 2",result);
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray arr = obj.getJSONArray("categories");
                    for(int i = 0; i < arr.length(); i++) {
                        String name = arr.getJSONObject(i).getString("name");
                        int id = arr.getJSONObject(i).getInt("id");
                        String events_count = arr.getJSONObject(i).getJSONObject("meta").getString("events_count");

                        Category newCategory = new Category();
                        newCategory.setId(id);
                        newCategory.setName(name);
                        HashMap<String,String> meta = new HashMap<>();
                        meta.put("events_count",events_count);
                        newCategory.setMeta(meta);
                        //categoryAdapter.clear();
                        allCategories.add(newCategory);
                        mNotificable.sendNotify();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(new Configs().getBackendUrl() + "/api/categories");

        return allCategories;
    }
}
