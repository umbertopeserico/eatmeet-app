package com.example.eatmeet.dao;

import android.util.Log;

import com.example.eatmeet.Connection;
import com.example.eatmeet.entities.Category;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.json.*;

/**
 * Created by sofia on 08/06/2016.
 */
public class CategoryDAOImpl implements CategoryDAO {

    public HashMap<String,String> getMeta(){
        HashMap<String,String> meta = new HashMap<>();
        meta.put("events_count","3");
        return null;
    }

    @Override
    public List<String> getCategories() {

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

        final List<Category> allCategories = new ArrayList<Category>();

        allCategories.add(sushi);
        allCategories.add(vino);
        allCategories.add(biologico);

        final ArrayList<String> testCategories = new ArrayList<>();

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
                        System.out.println(name + " " + id);
                        Category newCategory = new Category();
                        newCategory.setId(id);
                        newCategory.setName(name);
                        //categoryAdapter.clear();
                        allCategories.add(newCategory);
                        testCategories.add(newCategory.getName());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute("http://eatmeet.herokuapp.com/api/categories");

        //return allCategories;
        return testCategories;
    }
}
