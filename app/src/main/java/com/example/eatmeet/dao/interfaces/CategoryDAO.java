package com.example.eatmeet.dao.interfaces;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.entities.Category;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public interface CategoryDAO {

    void getCategories(final List<Category> categories, final BackendStatusManager backendStatusManager);

    @Deprecated
    List<Category> getCategories();

    @Deprecated
    HashMap<String,String> getMeta();
}
