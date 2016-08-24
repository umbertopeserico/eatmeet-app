package com.example.eatmeet.dao.interfaces;

import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.entities.Category;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sofia on 08/06/2016.
 */
public interface CategoryDAO {

    void getCategories(final List<Category> categories, final BackendStatusManager backendStatusManager);
    void getImage(String url, String tmpFileName, BackendStatusManager backendStatusManager, File cacheDir);

    @Deprecated
    List<Category> getCategories();
    
}
