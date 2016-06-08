package com.example.eatmeet.entities;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by sofia on 08/06/2016.
 */
public class Event {
    private int id;
    private String title;
    private Date schedule;
    private int max_people;
    private int min_people;
    private int min_price_people;
    private double max_price;
    private double actual_price;
    private double min_price;
    private HashMap<Integer,Double> prices_array;
    private int menu_id;
    private Date created_at;
    private Date updated_at;

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getSchedule() {
        return schedule;
    }

    public void setSchedule(Date schedule) {
        this.schedule = schedule;
    }

    public int getMaxPeople() {
        return max_people;
    }

    public void setMaxPeople(int max_people) {
        this.max_people = max_people;
    }

    public int getMinPeople() {
        return min_people;
    }

    public void setMinPeople(int min_people) {
        this.min_people = min_people;
    }

    public int getMinPricePeople() {
        return min_price_people;
    }

    public void setMinPricePeople(int min_price_people) {
        this.min_price_people = min_price_people;
    }

    public double getMaxPrice() {
        return max_price;
    }

    public void setMaxPrice(double max_price) {
        this.max_price = max_price;
    }

    public double getActualPrice() {
        return actual_price;
    }

    public void setActualPrice(double actual_price) {
        this.actual_price = actual_price;
    }

    public double getMinPrice() {
        return min_price;
    }

    public void setMinPrice(double min_price) {
        this.min_price = min_price;
    }

    public HashMap<Integer, Double> getPrices_array() {
        return prices_array;
    }

    public void setPrices_array(HashMap<Integer, Double> prices_array) {
        this.prices_array = prices_array;
    }

    public int getMenuId() {
        return menu_id;
    }

    public void setMenuId(int menu_id) {
        this.menu_id = menu_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return getId() == event.getId();

    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
