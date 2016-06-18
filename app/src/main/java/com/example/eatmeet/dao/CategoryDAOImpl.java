package com.example.eatmeet.dao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.eatmeet.utils.Configs;
import com.example.eatmeet.utils.Connection;
import com.example.eatmeet.entities.Category;
import com.example.eatmeet.utils.Images;
import com.example.eatmeet.utils.Notificable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
        final List<Category> allCategories = new ArrayList<Category>();

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
                    String icon = arr.getJSONObject(i).getString("image");

                    Category newCategory = new Category();
                    newCategory.setId(id);
                    newCategory.setName(name);
                    newCategory.setIcon(icon);
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
