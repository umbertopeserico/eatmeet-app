package com.example.eatmeet.dao.interfaces;

import com.example.eatmeet.entities.Category;
import com.example.eatmeet.mainactivityfragments.CategoriesFragment;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public interface CategoryDAO {
    public List<Category> getCategories();
    //public HashMap<String,String> getMeta();
}
