package com.example.eatmeet.utils;

import android.content.Context;

import com.example.eatmeet.activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by sofia on 26/07/2016.
 */
public class FiltersManager {

    private String f_order_by = "order_by_date";

    private ArrayList<Integer> f_categories = new ArrayList<>();
    private boolean on_categories = false;

    private Date f_min_date = null;
    private Date f_max_date = null;
    private boolean on_min_date = false;
    private boolean on_max_date = false;

    private double f_min_price = 0;
    private double f_max_price = 0;
    private boolean on_min_price = false;
    private boolean on_max_price = false;

    private double f_actual_discount = 0;
    private boolean on_actual_discount = false;

    private int f_min_people = 0;
    private int f_max_people = 0;
    private boolean on_min_people = false;
    private boolean on_max_people = false;

    private ArrayList<Integer> f_restaurants = new ArrayList<>();
    private boolean on_restaurants = false;

    /*order_by*/
    public void setF_order_by(String order_by) {
        f_order_by = order_by;
    }
    public String getF_order_by(){
        return f_order_by;
    }

    /*restaurants*/
    public void setF_restaurants(ArrayList<Integer> restaurants) {
        f_restaurants = restaurants;
        on_restaurants = true;
    }
    public void removeF_restaurants(){
        on_restaurants = false;
    }
    public ArrayList<Integer> getF_restaurants(){
        return f_restaurants;
    }

    /*categories*/
    public void setF_categories(ArrayList<Integer> categories) {
        f_categories = categories;
        on_categories = true;
    }
    public void removeF_categories(){
        on_categories = false;
    }
    public ArrayList<Integer> getF_categories(){
        return f_categories;
    }

    /*min_people*/
    public void setF_min_people(int min_people) {
        f_min_people = min_people;
        on_min_people = true;
    }
    public void removeF_min_people(){
        on_min_people = false;
    }
    public int getF_min_people(){
        return f_min_people;
    }

    /*max_people*/
    public void setF_max_people(int max_people) {
        f_max_people = max_people;
        on_max_people = true;
    }
    public void removeF_max_people(){
        on_max_people = false;
    }
    public int getF_max_people(){
        return f_max_people;
    }

    /*min_date*/
    public void f_min_date(Date min_date) {
        f_min_date = min_date;
        on_min_date = true;
    }
    public void removeF_min_date(){
        on_min_date = false;
    }
    public Date getF_min_date(){
        return f_min_date;
    }

    /*max_date*/
    public void setF_max_date(Date max_date) {
        f_max_date = max_date;
        on_max_date = true;
    }
    public void removeF_max_date(){
        on_max_date = false;
    }
    public Date getF_max_date(){
        return f_max_date;
    }

    /*min_price*/
    public void f_min_price(double min_price) {
        f_min_price = min_price;
        on_min_price = true;
    }
    public void removeF_min_price(){
        on_min_price = false;
    }
    public double getF_min_price(){
        return f_min_price;
    }

    /*max_price*/
    public void setF_max_price(double max_price) {
        f_max_price = max_price;
        on_max_price = true;
    }
    public void removeF_max_price(){
        on_max_price = false;
    }
    public double getF_max_price(){
        return f_max_price;
    }

    /*actual_discount*/
    public void setF_actual_discount(double actual_discount) {
        f_actual_discount = actual_discount;
        on_actual_discount = true;
    }
    public void removeF_actual_discount(){
        on_actual_discount = false;
    }
    public double getF_actual_discount(){
        return f_actual_discount;
    }

    /*allFilters*/
    public void removeAllFilters(){
        removeF_max_people();
        removeF_min_people();
        removeF_categories();
        removeF_restaurants();
        removeF_max_date();
        removeF_min_date();
        removeF_max_price();
        removeF_min_price();
        removeF_actual_discount();
    }


    public JSONObject constructParameters() throws JSONException {
        JSONObject parameters= new JSONObject();
        JSONObject all_filters = new JSONObject();

        /*set filter parameters*/
        ArrayList<Integer> f_categories = new ArrayList<>();
        if(on_categories) {
            f_categories = getF_categories();
        }
        ArrayList<Integer> f_restaurants = new ArrayList<>();
        if(on_restaurants) {
            f_restaurants = getF_restaurants();
        }
        all_filters.put("categories", new JSONArray(f_categories));
        all_filters.put("restaurants", new JSONArray(f_restaurants));

        if(on_min_people || on_max_people) {
            int f_min_people = getF_min_people();
            JSONObject participants_range = new JSONObject();
            participants_range.put("start", f_min_people);
            int f_max_people = getF_max_people();
            if(f_max_people==0) f_max_people = 1000;
            participants_range.put("end", f_max_people);
            all_filters.put("participants_range", participants_range);
        }

        parameters.put("filters",all_filters);
        /*set order parameters*/
        if(getF_order_by()=="order_by_date") {
            JSONObject schedule = new JSONObject();
            schedule.put("schedule","asc");
            parameters.put("order", schedule);
        } else if (getF_order_by()=="order_by_price") {
            JSONObject price = new JSONObject();
            price.put("actual_price","desc");
            parameters.put("order", price);
        }
        //System.out.println("PARAMETERS: " + parameters);
        /*
        for (int i = 0; i < f_categories.size() ; i++){
            System.out.println();
            try {
                category_parameter.put(Integer.toString(i),Integer.toString(f_categories.get(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(f_categories.get(i)==f_categories.size()-1){
                try {
                    parameters.put("categories",category_parameter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        */
        return parameters;
    }
}
