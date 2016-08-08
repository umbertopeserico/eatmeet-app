package com.example.eatmeet.utils;

import com.example.eatmeet.entities.Category;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umberto on 08/08/16.
 */
public class FiltersManager {

    // VARIABILI PER ORDINAMENTO
    private String orderByField;
    private String orderByDirection;

    // VARIABILI PER FILTRI
    private List<Category> selectedCategories;

    public FiltersManager() {
        selectedCategories = new ArrayList<>();
        orderByField = "";
        orderByDirection = "";
    }

    // SEZIONE ORDINAMENTO
    public void setOrderBy(String field, String direction) {
        orderByField = field;
        orderByDirection = direction;
    }

    public String getOrderByField() {
        return orderByField;
    }

    public String getOrderByDirection() {
        return orderByDirection;
    }

    public boolean isOrderSet() {
        if(getOrderByDirection().equals("") || getOrderByField().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public void removeOrder() {
        setOrderBy("","");
    }
    // FINE SEZIONE ORDINAMENTO

    // SETTING FILTRI PER CATEGORIA
    public void addCategory(Category category) {
        selectedCategories.add(category);
    }

    public void removeCategory(Category category) {
        selectedCategories.remove(category);
    }

    public boolean isCategoryEnabled() {
        if(selectedCategories.size()==0) {
            return false;
        } else {
            return true;
        }
    }
    // FINE SETTING FILTRI PER CATEGORIA

    public JSONObject buildJson() {
        try {
            JSONObject parameters = new JSONObject();
            JSONObject filters = new JSONObject();

            if(isCategoryEnabled()) {
                filters.put("categories", selectedCategories);
            }

            if(isOrderSet()) {
                JSONObject order = new JSONObject();
                order.put("field", getOrderByField());
                order.put("direction", getOrderByDirection());
                parameters.put("order", order);
            }
            parameters.put("filters", filters);

            System.out.println(parameters);
            return parameters;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
