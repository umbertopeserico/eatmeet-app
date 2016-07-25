package com.example.eatmeet.dao.interfaces;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.entities.Category;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public interface CategoryDAO {

    public void getCategories(final List<Category> categories, final BackendStatusManager backendStatusManager);

    @Deprecated
    public List<Category> getCategories();

    @Deprecated
    public HashMap<String,String> getMeta();
}
