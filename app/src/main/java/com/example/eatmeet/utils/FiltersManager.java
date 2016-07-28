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

    /*old values*/
    String oldF_order_by;

    ArrayList<Integer> oldF_categories;
    boolean old_on_categories;

    Date oldF_min_date;
    Date oldF_max_date;
    boolean old_on_min_date;
    boolean old_on_max_date;

    double oldF_min_price;
    double oldF_max_price;
    boolean old_on_min_price;
    boolean old_on_max_price;

    double oldF_min_actual_sale;
    double oldF_max_actual_sale;
    boolean old_on_min_actual_sale;
    boolean old_on_max_actual_sale;

    int oldF_min_people;
    int oldF_max_people;
    boolean old_on_min_people;
    boolean old_on_max_people;

    ArrayList<Integer> oldF_restaurants;
    boolean old_on_restaurants;

    /*new values*/
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

    private double f_min_actual_sale = 0;
    private double f_max_actual_sale = 0;
    private boolean on_min_actual_sale = false;
    private boolean on_max_actual_sale = false;

    private int f_min_people = 0;
    private int f_max_people = 0;
    private boolean on_min_people = false;
    private boolean on_max_people = false;

    private ArrayList<Integer> f_restaurants = new ArrayList<>();
    private boolean on_restaurants = false;

    public FiltersManager(){
        saveOldFilters();
    }

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
    public void setF_min_date(Date min_date) {
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
    public void setF_min_price(double min_price) {
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
    public void setF_min_actual_sale(double min_actual_sale) {
        f_min_actual_sale = min_actual_sale;
        on_min_actual_sale = true;
    }
    public void setF_max_actual_sale(double max_actual_sale) {
        f_max_actual_sale = max_actual_sale;
        on_max_actual_sale = true;
    }
    public void removeF_min_actual_sale(){
        on_min_actual_sale = false;
    }
    public void removeF_max_actual_sale(){
        on_max_actual_sale = false;
    }
    public double getF_min_actual_sale(){
        return f_min_actual_sale;
    }
    public double getF_max_actual_sale(){
        return f_max_actual_sale;
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
        removeF_min_actual_sale();
        removeF_max_actual_sale();
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

        if(on_min_actual_sale || on_max_actual_sale) {
            JSONObject actual_sale_range = new JSONObject();
            if (on_min_actual_sale) {
                double f_min_actual_sale = getF_min_actual_sale();
                actual_sale_range.put("start", f_min_actual_sale);
            }
            if (on_max_actual_sale) {
                double f_max_actual_sale = getF_max_actual_sale();
                actual_sale_range.put("end", f_max_actual_sale);
            }
            all_filters.put("actual_sale_range", actual_sale_range);
        }

        if(on_min_people || on_max_people) {
            JSONObject participants_range = new JSONObject();
            if (on_min_people) {
                int f_min_people = getF_min_people();
                participants_range.put("start", f_min_people);
            }
            if (on_max_people) {
                int f_max_people = getF_max_people();
                //if(f_max_people==0) f_max_people = 1000;
                participants_range.put("end", f_max_people);
            }
            all_filters.put("participants_range", participants_range);
        }

        if(on_min_date || on_max_date) {
            JSONObject date_range = new JSONObject();
            if (on_min_date) {
                Date f_min_date = getF_min_date();
                date_range.put("start", f_min_date);
            }
            if (on_max_date) {
                Date f_max_date = getF_max_date();
                date_range.put("end", f_max_date);
            }
            all_filters.put("date_range", date_range);
        }

        if(on_min_price || on_max_price) {
            JSONObject price_range = new JSONObject();
            if (on_min_price) {
                double f_min_price = getF_min_price();
                price_range.put("start", f_min_price);
            }
            if (on_max_price) {
                double f_max_price = getF_max_price();
                price_range.put("end", f_max_price);
            }
            all_filters.put("price_range", price_range);
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

        saveOldFilters();

        return parameters;
    }

    public void saveOldFilters(){
        oldF_order_by = getF_order_by();

        oldF_categories = getF_categories();
        old_on_categories = on_categories;

        oldF_min_date = getF_min_date();
        oldF_max_date = getF_max_date();
        old_on_min_date = on_min_date;
        old_on_max_date = on_max_date;

        oldF_min_price = getF_min_price();
        oldF_max_price = getF_max_price();
        old_on_min_price = on_min_price;
        old_on_max_price = on_max_price;

        oldF_min_actual_sale = getF_min_actual_sale();
        oldF_max_actual_sale = getF_max_actual_sale();
        old_on_min_actual_sale = on_min_actual_sale;
        old_on_max_actual_sale = on_max_actual_sale;

        oldF_min_people = getF_min_people();
        oldF_max_people = getF_max_people();
        old_on_min_people = on_min_people;
        old_on_max_people = on_max_people;

        oldF_restaurants = getF_restaurants();
        old_on_restaurants = on_restaurants;
    }

    public void resetOldFilters(){

        setF_order_by(oldF_order_by);

        setF_categories(oldF_categories);
        on_categories = old_on_categories;

        setF_min_date(oldF_min_date);
        setF_max_date(oldF_max_date);
        on_min_date = old_on_min_date;
        on_max_date = old_on_max_date;

        setF_min_price(oldF_min_price);
        setF_max_price(oldF_max_price);
        on_min_price = old_on_min_price;
        on_max_price = old_on_max_price;

        setF_min_actual_sale(oldF_min_actual_sale);
        setF_max_actual_sale(oldF_max_actual_sale);
        on_min_actual_sale = old_on_min_actual_sale;
        on_max_actual_sale = old_on_max_actual_sale;

        setF_min_people(oldF_min_people);
        setF_max_people(oldF_max_people);
        on_min_people = old_on_min_people;
        on_max_people = old_on_max_people;

        setF_restaurants(oldF_restaurants);
        on_restaurants = old_on_restaurants;
    }

}