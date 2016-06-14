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
    public List<Category> getCategories() {

        //Connection c = new Connection();
        new Connection(){
            @Override public void onPostExecute(String result)
            {
                Log.d("My tag 2",result);
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray arr = obj.getJSONArray("categories");
                    String name = arr.getJSONObject(0).getString("name");
                    int id = arr.getJSONObject(0).getInt("id");
                    System.out.println(name + " " + id);  // prints "Alice 20"
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute("http://eatmeet.herokuapp.com/api/categories");
        //c.execute("http://eatmeet.herokuapp.com/api/categories");

        Category pizza = new Category();
        pizza.setId(0);
        pizza.setMeta(this.getMeta());
        pizza.setName("pizza");

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

        List<Category> allCategories = new ArrayList<Category>();

        allCategories.add(pizza);
        allCategories.add(sushi);
        allCategories.add(vino);
        allCategories.add(biologico);

        return allCategories;
    }
}
